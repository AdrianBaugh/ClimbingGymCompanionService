import ClimbClient from "../api/climbClient";
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import climbStatus from "../enums/climbStatus";
import routeLocations from "../enums/routeLocations";
import routeDifficulties from "../enums/routeDifficulties";
import { getValueFromEnum } from '../util/enumUtils.js';
import LoadingSpinner from "../components/LoadingSpinner.js";


/**
 * Logic needed for the create climb page of the website.
 */
class CreateClimb extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'submit', 'redirectToViewClimb', 'routeDropdown', 'statusDropdown'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.routeDropdown);
        this.header = new Header(this.dataStore);
        this.loadingSpinner = new LoadingSpinner();

        console.log("CreateClimb constructor");
    }

    /**
    * Once the client is loaded, get the route list metadata.
    */
    async clientLoaded() {
        const routes = await this.client.viewAllActiveRoutes();
        this.dataStore.set('routes', routes);

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
        this.routeDropdown();
        this.statusDropdown();
        this.clientLoaded();
    }

    /**
     * populate the routes list with active routes
     */

    routeDropdown() {
        const routes = this.dataStore.get('routes');
    
        if (routes == null) {
            return;
        }
    
        const dropdown = document.getElementById('routeDropdown');
    
        dropdown.innerHTML = '';
    
        const placeholderOption = document.createElement('option');
        placeholderOption.value = '';
        placeholderOption.textContent = 'Select a route'; 
        placeholderOption.disabled = true;
        placeholderOption.selected = true; 
        dropdown.appendChild(placeholderOption);
    
        routes.forEach(route => {
            if (route.routeStatus === "ACTIVE") {
                const option = document.createElement('option');
                option.value = route.routeId;
                option.textContent = getValueFromEnum(route.location, routeLocations) + " | " + getValueFromEnum(route.difficulty, routeDifficulties); 
                dropdown.appendChild(option);
            }
        });
    }
    

    // Function to populate the status dropdown
    statusDropdown() {
        const statusDropdown = document.getElementById('statusDropdown');
    
        statusDropdown.innerHTML = '';
    
        const placeholderOption = document.createElement('option');
        placeholderOption.value = '';
        placeholderOption.textContent = 'Select progress'; 
        placeholderOption.selected = true; 
        statusDropdown.appendChild(placeholderOption);
    
        for (const status in climbStatus) {
            if (climbStatus.hasOwnProperty(status)) {
                const option = document.createElement('option');
                option.value = status;
                option.textContent = climbStatus[status];
                statusDropdown.appendChild(option);
            }
        }
    }

    async submit(evt) {
        this.showLoader();

        console.log('Submit button clicked');
        evt.preventDefault();
    
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');
    
        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Submitting. . .';
    
        const route = document.getElementById('routeDropdown').value;
        if (route === '' ) {
            this.hideLoader();
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = 'Please select a route.';
            errorMessageDisplay.classList.remove('hidden');
            return;
        }

        const climbStatus = document.getElementById('statusDropdown').value || null;
        
        const leadClimbCheckbox = document.getElementById('leadClimbCheckbox');
        const type = leadClimbCheckbox.checked ? 'LEAD_CLIMB' : null;

        const notes = document.getElementById('notes').value || null;
    
        // Get the value of the selected thumbs option
        const thumbsUpRadioButton = document.getElementById('thumbsUp');
        const thumbsDownRadioButton = document.getElementById('thumbsDown');
        
        let thumbsValue = null;
    
        if (thumbsUpRadioButton.checked) {
            thumbsValue = true;
        } else if (thumbsDownRadioButton.checked) {
            thumbsValue = false;
        }
    
        try {
            const climb = await this.client.createClimb(route, climbStatus, thumbsValue, type, notes);
            await this.dataStore.set('climb', climb);
            console.log('Climb data before redirect:', climb);

            // Redirect after setting climb data
            this.redirectToViewClimb();
        } catch (error) {
            this.hideLoader();
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        }
        
    }
    
  
    redirectToViewClimb() {
        const climb = this.dataStore.get('climb');
        console.log('Climb data:', climb);
        if (climb != null) {
            console.log("Redirecting to viewClimbs.html");
            window.location.href = `/viewClimbs.html?climbId=${climb.climbId}`;
        }
    }

    showLoader(message) {
        this.loadingSpinner.showLoadingSpinnerNoMessages(message);
    }
    hideLoader(){
        this.loadingSpinner.hideLoadingSpinnerNoMessages();
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createClimb = new CreateClimb();
    createClimb.mount();
};

window.addEventListener('DOMContentLoaded', main);