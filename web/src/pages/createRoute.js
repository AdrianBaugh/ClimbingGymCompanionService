import ClimbClient from "../api/climbClient";
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";


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
    
            document.getElementById("loginModal").style.display = "flex";
    
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
    
        // image
        const routeImageInput = document.getElementById('route-image');
        const routeImageFile = routeImageInput.files.length > 0 ? routeImageInput.files[0] : null;
    
        const route = await this.client.createRoute(location, color, routeStatus, type, difficulty, routeImageFile, (error) => {
        createButton.innerText = origButtonText;
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('route', route);
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
