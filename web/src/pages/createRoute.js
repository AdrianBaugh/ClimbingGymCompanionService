import ClimbClient from "../api/climbClient";
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import idUtils from "../util/idUtils";


/**
 * Logic needed for the create route page of the website.
 */
class CreateRoute extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'submit', 'redirectToViewRoute', 'colorsDropdown', 'statusDropdown', 'typeDropdown'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewRoute);
        this.header = new Header(this.dataStore);
        console.log("CreateRoute constructor");
    }

    async clientLoaded() {
        if (await this.client.authenticator.isUserLoggedIn()) {
            console.log('User is logged in');
        } else {
            console.log('/////////User is not logged in////////');
    
            document.getElementById("loginModal").style.display = "block";
    
            const loginButton = document.createElement('div');
            loginButton.textContent = 'Login';
            loginButton.classList.add('button'); 
    
            loginButton.addEventListener('click', async () => {
                await this.client.login();
            });
                document.getElementById('loginBtn').appendChild(loginButton);
        }
    }
    

    /**
     * Add the header to the page and load the ClimbClient.
     */
    mount() {
        document.getElementById('create').addEventListener('click', this.submit);

        this.header.addHeaderToPage();
        this.client = new ClimbClient();
        this.colorsDropdown(); 
        this.statusDropdown();
        this.typeDropdown();
        this.clientLoaded();
    }

    // Function to populate the colors dropdown
    colorsDropdown() {
        const colorsDropdown = document.getElementById('colorDropdown');
    
        // Clear existing options
        colorsDropdown.innerHTML = '';
    
        // Define the colors array
        const colors = ['BLACK', 'WHITE', 'RED', 'BLUE', 'GREEN', 'PINK', 'YELLOW', 'PURPLE', 'ORANGE', 'BROWN'];
    
        const placeholderOption = document.createElement('option');
        placeholderOption.value = '';
        placeholderOption.textContent = 'Select a color'; // Placeholder text
        placeholderOption.disabled = true;
        placeholderOption.selected = true; // Optional: Select this by default
        colorsDropdown.appendChild(placeholderOption);
        
        // Add options based on the colors array
        colors.forEach(color => {
        const option = document.createElement('option');
        option.value = color;
        option.textContent = color;
        colorsDropdown.appendChild(option);
        });
    }

    // Function to populate the status dropdown
    statusDropdown() {
        const statusDropdown = document.getElementById('statusDropdown');
    
        statusDropdown.innerHTML = '';
    
        const statuses = ['ACTIVE', 'TOURNAMENT_ONLY', ];

        const placeholderOption = document.createElement('option');
        placeholderOption.value = '';
        placeholderOption.textContent = 'Select a status'; // Placeholder text
        placeholderOption.disabled = true;
        placeholderOption.selected = true; // Optional: Select this by default
        statusDropdown.appendChild(placeholderOption);
    
        statuses.forEach(status => {
        const option = document.createElement('option');
        option.value = status;
        option.textContent = status;
        statusDropdown.appendChild(option);
        });
    }

    // Function to populate the status dropdown
    typeDropdown() {
        const typeDropdown = document.getElementById('typeDropdown');
    
        typeDropdown.innerHTML = '';
    
        const types = ['BOULDER', 'TOP_ROPE', 'LEAD_CLIMB', 'AUTO_BELAY'];

        const placeholderOption = document.createElement('option');
        placeholderOption.value = '';
        placeholderOption.textContent = 'Select a type'; // Placeholder text
        placeholderOption.disabled = true;
        placeholderOption.selected = true; // Optional: Select this by default
        typeDropdown.appendChild(placeholderOption);
    
        types.forEach(type => {
        const option = document.createElement('option');
        option.value = type;
        option.textContent = type;
        typeDropdown.appendChild(option);
        });
    }
    
    async submit(evt) {
        console.log('Submit button clicked');
        evt.preventDefault();
    
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');
    
        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading. . .';
    
        const location = document.getElementById('location').value;
        if (location === '' ) {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = 'Please enter a route.';
            errorMessageDisplay.classList.remove('hidden');
            return;
        }
        const color = document.getElementById('colorDropdown').value;
        if (color === '' ) {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = 'Please select a route color.';
            errorMessageDisplay.classList.remove('hidden');
            return;
        }
        const routeStatus = document.getElementById('statusDropdown').value || null;
        const type = document.getElementById('typeDropdown').value || null;
        const difficulty = document.getElementById('difficulty').value || null;
    

        // Handle the Image
        const routeImageInput = document.getElementById('route-image');
        let routeImageFile = null;

        try {
            routeImageFile = routeImageInput.files.length > 0 ? routeImageInput.files[0] : null;
            if (routeImageFile != null) {
                console.log("Image is not null and starting getPresigned String")
                //const imageKey = this.idUtils.generateImageKey(routeImageFile.name)

                const s3string = await this.client.getPresignedS3Url(routeImageFile.name);
                console.log("presigned URL String: " , s3string)
                const s3response = await this.uploadImageToS3(s3string.s3PreSignedUrl, routeImageFile);

            }
        } catch (error) {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
            return;
        }
        // End Image Handling
    
        // const route = await this.client.createRoute(location, color, routeStatus, type, difficulty, routeImageFile, (error) => {
        // createButton.innerText = origButtonText;
        // errorMessageDisplay.innerText = `Error: ${error.message}`;
        // errorMessageDisplay.classList.remove('hidden');
        // });
        // this.dataStore.set('route', route);
    }

    async uploadImageToS3(s3PresignedUrl, routeImageFile) {

        console.log("S3 URL BEFORE UPLOAD ATTEMPT: " , s3PresignedUrl)

        if (routeImageFile == null) {
          console.warn("No image available.");
          return;
        }
        console.log("Attempting to upload to S3")
      try {
          const uploadResponse = await this.client.uploadToS3(s3PresignedUrl, routeImageFile);
          // return uploadResponse;
          console.log("Success uploading to S3", uploadResponse)

          return uploadResponse;
      } catch (error) {
          console.error('Error uploading image to S3: ', error);
      }
    }
    
    redirectToViewRoute() {
        const route = this.dataStore.get('route');
        if (route != null) {
            console.log("Redirecting to viewRoute.html");
            window.location.href = `/viewRoute.html?routeId=${route.routeId}`;
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createRoute = new CreateRoute();
    createRoute.mount();
};

window.addEventListener('DOMContentLoaded', main);
