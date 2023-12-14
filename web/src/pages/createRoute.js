import ClimbClient from "../api/climbClient";
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import { generateImageKey } from "../util/idUtils";
import routeColors from "../enums/routeColors";
import routeStatus from "../enums/routeStatus";
import routeTypes from "../enums/routeTypes";
import routeLocations from "../enums/routeLocations";
import routeDifficulties from "../enums/routeDifficulties";
import LoadingSpinner from "../components/LoadingSpinner.js";



/**
 * Logic needed for the create route page of the website.
 */
class CreateRoute extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods([
            'clientLoaded',
            'mount',
            'submit',
            'redirectToViewRoute',
            'locationDropdown',
            'colorsDropdown',
            'statusDropdown',
            'typeDropdown',
            'difficultyDropdown'
        ], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewRoute);
        this.header = new Header(this.dataStore);
        this.loadingSpinner = new LoadingSpinner();

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
        this.locationDropdown();
        this.colorsDropdown();
        this.statusDropdown();
        this.typeDropdown();
        this.difficultyDropdown();
        this.clientLoaded();
    }

    // Function to populate the locations dropdown
    locationDropdown() {
        const locationDropdown = document.getElementById('locationDropdown');

        locationDropdown.innerHTML = '';

        const placeholderOption = document.createElement('option');
        placeholderOption.value = '';
        placeholderOption.textContent = 'Select a location'; // Placeholder text
        placeholderOption.disabled = true;
        placeholderOption.selected = true;
        locationDropdown.appendChild(placeholderOption);

        for (const location in routeLocations) {
            if (routeLocations.hasOwnProperty(location)) {
                const option = document.createElement('option');
                option.value = location;
                option.textContent = routeLocations[location];
                locationDropdown.appendChild(option);
            }
        }
    }

    // Function to populate the colors dropdown
    colorsDropdown() {
        const colorsDropdown = document.getElementById('colorDropdown');

        colorsDropdown.innerHTML = '';

        const placeholderOption = document.createElement('option');
        placeholderOption.value = '';
        placeholderOption.textContent = 'Select a color'; // Placeholder text
        placeholderOption.disabled = true;
        placeholderOption.selected = true;
        colorsDropdown.appendChild(placeholderOption);

        for (const color in routeColors) {
            if (routeColors.hasOwnProperty(color)) {
                const option = document.createElement('option');
                option.value = color;
                option.textContent = routeColors[color];
                colorsDropdown.appendChild(option);
            }
        }
    }

    // Function to populate the status dropdown
    statusDropdown() {
        const statusDropdown = document.getElementById('statusDropdown');

        statusDropdown.innerHTML = '';

        const placeholderOption = document.createElement('option');
        placeholderOption.value = '';
        placeholderOption.textContent = 'Select a status'; // Placeholder text
        placeholderOption.disabled = true;
        placeholderOption.selected = true;
        statusDropdown.appendChild(placeholderOption);

        for (const status in routeStatus) {
            if (routeStatus.hasOwnProperty(status)) {
                const option = document.createElement('option');
                option.value = status;
                option.textContent = routeStatus[status];
                statusDropdown.appendChild(option);
            }
        }
    }

    // Function to populate the type dropdown
    typeDropdown() {
        const typeDropdown = document.getElementById('typeDropdown');

        typeDropdown.innerHTML = '';

        const placeholderOption = document.createElement('option');
        placeholderOption.value = '';
        placeholderOption.textContent = 'Select a type'; // Placeholder text
        placeholderOption.disabled = true;
        placeholderOption.selected = true;
        typeDropdown.appendChild(placeholderOption);

        for (const type in routeTypes) {
            if (routeTypes.hasOwnProperty(type)) {
                const option = document.createElement('option');
                option.value = type;
                option.textContent = routeTypes[type];
                typeDropdown.appendChild(option);
            }
        }
    }

    // Function to populate the difficulty dropdown
    difficultyDropdown() {
        const difficultyDropdown = document.getElementById('difficultyDropdown');

        difficultyDropdown.innerHTML = '';

        const placeholderOption = document.createElement('option');
        placeholderOption.value = '';
        placeholderOption.textContent = 'Select a difficulty'; // Placeholder text
        placeholderOption.disabled = true;
        placeholderOption.selected = true;
        difficultyDropdown.appendChild(placeholderOption);

        for (const difficulty in routeDifficulties) {
            if (routeDifficulties.hasOwnProperty(difficulty)) {
                const option = document.createElement('option');
                option.value = difficulty;
                option.textContent = routeDifficulties[difficulty];
                difficultyDropdown.appendChild(option);
            }
        }
    }

    async submit(evt) {
        this.showLoader();

        console.log('Submit button clicked');
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Submitting. . .';

        const location = document.getElementById('locationDropdown').value;
        if (location === '') {
            this.hideLoader();
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = 'Please select a route.';
            errorMessageDisplay.classList.remove('hidden');
            return;
        }
        const color = document.getElementById('colorDropdown').value;
        if (color === '') {
            this.hideLoader();
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = 'Please select a route color.';
            errorMessageDisplay.classList.remove('hidden');
            return;
        }
        const routeStatus = document.getElementById('statusDropdown').value || null;
        const type = document.getElementById('typeDropdown').value || null;
        const difficulty = document.getElementById('difficultyDropdown').value || null;


        // Handle the Image
        const routeImageInput = document.getElementById('route-image');
        let routeImageFile = null;
        let imageKey = null;

        try {
            routeImageFile = routeImageInput.files.length > 0 ? routeImageInput.files[0] : null;
            if (routeImageFile != null) {
                console.log("Image is not null and starting getPresigned String")
                // Create the key to use for the image in S3 also save to DDB
                imageKey = generateImageKey(routeImageFile.name)

                const s3string = await this.client.getPresignedS3Url(imageKey);
                console.log("presigned URL String: ", s3string)
                const s3response = await this.uploadImageToS3(s3string.s3PreSignedUrl, routeImageFile);

            }
        } catch (error) {
            this.hideLoader();
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
            return;
        }
        // End Image Handling
        console.log("Attempting to create the new route and send to backend")
        const route = await this.client.createRoute(location, color, routeStatus, type, difficulty, routeImageFile, imageKey, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('route', route);
    }

    async uploadImageToS3(s3PresignedUrl, routeImageFile) {

        console.log("S3 URL BEFORE UPLOAD ATTEMPT: ", s3PresignedUrl)

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

    showLoader(message) {
        this.loadingSpinner.showLoadingSpinnerNoMessages(message);
    }
    hideLoader() {
        this.loadingSpinner.hideLoadingSpinnerNoMessages();
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
