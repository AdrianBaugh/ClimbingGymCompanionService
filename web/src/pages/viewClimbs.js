import ClimbClient from "../api/climbClient";
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import { formatDateTime } from '../util/dateUtils';



/**
 * Logic needed for the get route page of the website.
 */
class ViewClimb extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addClimbToPage', 'addClimbHistoryToPage',
         'submit', 'statusDropdown', 'typeDropdown', 'redirectToViewClimb', 'redirectToViewClimbHistory', 'delete'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addClimbToPage);
        this.dataStore.addChangeListener(this.addClimbHistoryToPage);
        this.dataStore.addChangeListener(this.redirectToViewClimb);
        

        // *********************************************** //
        // this.dataStore.addChangeListener(this.redirectToViewClimbHistory)


        this.header = new Header(this.dataStore);
        console.log("ViewClimb constructor");
    }

     /**
     * Once the client is loaded, get the climb metadata.
     */
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
        const climbHistory = await this.client.viewUsersClimbHistory();
        this.dataStore.set('climbHistory', climbHistory);
        
        // Check if climbId is present and not null
        const urlParams = new URLSearchParams(window.location.search);
        const climbId = urlParams.get('climbId');
        if (climbId !== null) {
            const climb = await this.client.viewClimb(climbId);
            this.dataStore.set('climb', climb);
                   
            const currentDisplayedRoute = await this.client.viewRoute(climb.routeId);
            this.dataStore.set('currentDisplayedRoute', currentDisplayedRoute);
        }
    }
    

    /**
     * Add the header to the page and load the ClimbClient.
     */
    mount() {
        document.getElementById('updateClimb').addEventListener('click', this.submit);
        document.getElementById('deleteClimbButton').addEventListener('click', this.delete);


        this.header.addHeaderToPage();
        this.client = new ClimbClient();
        this.statusDropdown();
        this.typeDropdown();
        this.clientLoaded();

        const openModalButton = document.getElementById('openModalBtn');
        const closeModalButton = document.getElementById('closeModalBtn');
        const modal = document.getElementById('updateClimbModal');
    
        openModalButton.addEventListener('click', () => {
            modal.style.display = 'block';
        });
    
        closeModalButton.addEventListener('click', () => {
            modal.style.display = 'none';
        });
    
        // Close the modal if the user clicks outside of it
        window.addEventListener('click', (event) => {
            if (event.target === modal) {
                modal.style.display = 'none';
            }
        });
    }

    addClimbToPage() {
        const climb = this.dataStore.get('climb');
        if (climb == null) {
            // If climbId is null, hide the corresponding HTML
            document.getElementById('climb-details').style.display = 'none';
            return;
        }
        console.log('Adding currnet climb to page ', climb)
        const route = this.dataStore.get('currentDisplayedRoute');
        if (route == null ) {
            return;
        }
    
        document.getElementById('climb-details').style.display = 'block';
    
        const routeLocationElement = document.getElementById('route-location');
        routeLocationElement.innerText = route.location;

        routeLocationElement.style.cursor = 'pointer';
        routeLocationElement.style.color = 'blue'; // Change the color as needed
        routeLocationElement.style.textDecoration = 'underline';

        routeLocationElement.addEventListener('click', function () {
            window.location.href = `/viewRoute.html?routeId=${route.routeId}`;
        });

        document.getElementById('difficulty').innerText = route.difficulty;
        document.getElementById('climb-status').innerText = climb.climbStatus;
        document.getElementById('type').innerText = climb.type;
        document.getElementById('rating').innerText = climb.thumbsUp !== null ?
            (climb.thumbsUp ? 'Thumbs Up!' : 'Thumbs Down') :
            'Not yet Rated!';
        document.getElementById('dateTime-climbed').innerText = formatDateTime(climb.dateTimeClimbed);
        document.getElementById('notes').innerText = climb.notes;
    }

    async addClimbHistoryToPage() {
        console.log("add climbHistory to page is starting");
        const climbHistory = this.dataStore.get('climbHistory');
        console.log("climbHistory", climbHistory);
    
        const climbHistoryElement = document.getElementById('climbHistory');
    
        if (climbHistory == null || climbHistory.length === 0) {
            const messageHtml = '<p>You don\'t have any climbs yet! <a href="createClimb.html" class="button">Create a Climb</a></p>';
            climbHistoryElement.innerHTML = messageHtml;
            return;
        }

        const textHtml = '<h4>Click a Climb below for more details:</h4>';
    
        let climbHtml = '<table><tr><th>Route Location</th><th>Current Status</th><th>Date / Time Climbed</th></tr>';
    
        for (const climb of climbHistory) {
            let routeId = climb.routeId;
            let location = routeId.split("::")[0];
    
            climbHtml += `
            <tr onclick="window.location='/viewClimbs.html?climbId=${climb.climbId}'">
                <td>${location}</td>
                <td>${climb.climbStatus}</td>
                <td>${formatDateTime(climb.dateTimeClimbed)}</td>
            </tr>
            `;
        }
        climbHtml += '</table>';

        climbHistoryElement.innerHTML = textHtml + climbHtml;
    }


    // Function to populate the status dropdown
    statusDropdown() {
        const statusDropdown = document.getElementById('statusDropdown');
    
        statusDropdown.innerHTML = '';
    
        const statuses = ['COMPLETED_FLASHED', 'COMPLETED_SENT', 'COMPLETED_IN_STAGES', 'PROJECTING', 'WANT_TO_CLIMB', 'TOO_DIFFICULT' ];

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

    redirectToViewClimb() {
        const climb = this.dataStore.get('climb');
        if (climb != null) {
            const currentUrl = new URL(window.location.href);
            if (!currentUrl.searchParams.has('climbId')) {
            console.log("Redirecting to viewClimbs.html");
            window.location.href = `/viewClimbs.html?climbId=${climb.climbId}`;
            }
        }
    }

    redirectToViewClimbHistory() {
        console.log("Redirecting to viewClimbs.html");
        window.location.href = `/viewClimbs.html`;
    }

    async submit(evt) {
        console.log('Submit button clicked');
        evt.preventDefault();
    
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');
    
        const updateButton = document.getElementById('updateClimb');
        const origButtonText = updateButton.innerText;
        updateButton.innerText = 'Loading. . .';
    
        const type = document.getElementById('typeDropdown').value || null;
        const climbStatus = document.getElementById('statusDropdown').value || null;
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


        const climb = this.dataStore.get('climb');
        try {
            const updatedClimb = await this.client.updateClimb(climb.climbId, climbStatus, thumbsValue, type, notes);
            this.dataStore.set('climb', updatedClimb);
            console.log('updatedClimb data before redirect:', updatedClimb);

        } catch (error) {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        }

        // Redirect after updating climb data
        this.redirectToViewClimb();
        const modal = document.getElementById('updateClimbModal')

        setTimeout(() => {
            modal.style.display = 'none';
            updateButton.innerText = origButtonText;
        }, 3000);
    }

    async delete() {
        console.log('Delete climb button clicked');
        const climb = this.dataStore.get('climb');
        const deleteButton = document.getElementById('deleteClimbButton');
        const messageContainer = document.getElementById('messageContainer');
        const deleteMessage = document.getElementById('message');
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');

        try {
            console.log('Delete climb before redirect and backend call');
            await this.client.deleteClimb(climb.climbId);
            
        } catch (error) {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        }
        setTimeout(() => {
            deleteButton.style.display = 'none';
            openModalBtn.style.display = 'none';
            messageContainer.style.display = 'block';
        
            deleteMessage.textContent = 'This climb has been deleted!';
        }, 3000);

        this.redirectToViewClimbHistory();

        console.log('Deleted climb after redirect')
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewClimb = new ViewClimb();
    viewClimb.mount();
};

window.addEventListener('DOMContentLoaded', main);
