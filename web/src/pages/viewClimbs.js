import ClimbClient from "../api/climbClient";
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import { formatDateTime } from '../util/dateUtils';
import climbStatus from "../enums/climbStatus";
import routeStatus from "../enums/routeStatus";
import routeTypes from "../enums/routeTypes";
import routeLocations from "../enums/routeLocations";
import routeDifficulties from "../enums/routeDifficulties";
import { getValueFromEnum } from '../util/enumUtils.js';


/**
 * Logic needed for the get route page of the website.
 */
class ViewClimb extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods([
            'clientLoaded',
            'mount',
            'addClimbToPage',
            'addClimbHistoryToPage',
            'submit',
            'updateStatusDropdown',
            'updateLeadClimbCheckbox',
            'redirectToViewClimb',
            'redirectToViewClimbHistory',
            'updateThumbsUpAndDownRadios',
            'updateClimbNotes',
            'delete'
        ], this);

        this.dataStore = new DataStore();

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
        this.addClimbHistoryToPage();


        // Check if climbId is present and not null
        const urlParams = new URLSearchParams(window.location.search);
        const climbId = urlParams.get('climbId');
        if (climbId !== null) {
            const climb = await this.client.viewClimb(climbId);
            this.dataStore.set('climb', climb);
                
            const currentDisplayedRoute = await this.client.viewRoute(climb.routeId);
            this.dataStore.set('currentDisplayedRoute', currentDisplayedRoute);
        }
        this.addClimbToPage();

    }
    

    /**
     * Add the header to the page and load the ClimbClient.
     */
    mount() {
        document.getElementById('updateClimb').addEventListener('click', this.submit);
        document.getElementById('deleteClimbButton').addEventListener('click', this.delete);

        this.header.addHeaderToPage();
        this.client = new ClimbClient();

        this.clientLoaded();

        const openModalButton = document.getElementById('openModalBtn');
        const closeModalButton = document.getElementById('closeModalBtn');
        const modal = document.getElementById('updateClimbModal');
    
        openModalButton.addEventListener('click', () => {
            this.updateStatusDropdown();
            this.updateLeadClimbCheckbox();
            this.updateThumbsUpAndDownRadios();
            this.updateClimbNotes();
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
        } else {
            console.log('Adding current climb details to page ', climb)
            const route = this.dataStore.get('currentDisplayedRoute');
            if (route == null ) {
                return;
            }
        
            document.getElementById('climb-details').style.display = 'block';
        
            const routeLocationElement = document.getElementById('route-location');
            routeLocationElement.innerText = getValueFromEnum(route.location, routeLocations);

            routeLocationElement.style.cursor = 'pointer';
            routeLocationElement.style.color = 'blue'; // Change the link color as needed
            routeLocationElement.style.textDecoration = 'underline';

            routeLocationElement.addEventListener('click', function () {
                window.location.href = `/viewRoute.html?routeId=${route.routeId}`;
            });

            document.getElementById('difficulty').innerText = getValueFromEnum(route.difficulty, routeDifficulties);
            document.getElementById('climb-status').innerText = getValueFromEnum(climb.climbStatus, climbStatus);
            document.getElementById('type').innerText = getValueFromEnum(climb.type !== 'LEAD_CLIMB' ? route.type : climb.type, routeTypes);
            document.getElementById('rating').innerText = climb.thumbsUp !== null ?
                (climb.thumbsUp ? '👍 ' : '👎') :
                'Not Rated!';
            document.getElementById('dateTime-climbed').innerText = formatDateTime(climb.dateTimeClimbed);
            document.getElementById('notes').innerText = (climb.notes === null) ? "No beta recorded." : climb.notes;
        }
    }

    async addClimbHistoryToPage() {
        console.log("add climbHistory to page is starting");
        const climbHistory = this.dataStore.get('climbHistory');
        console.log("climbHistory", climbHistory);
    
        const totalClimbs = document.getElementById('totalClimbs');
        totalClimbs.innerText = '';
        // total climb stats
        totalClimbs.innerText += ' All Time Total Climbs: ' + climbHistory.length;
    
        const climbHistoryElement = document.getElementById('climbHistory');
    
        if (climbHistory == null || climbHistory.length === 0) {
            const messageHtml = '<p>You don\'t have any climbs yet! <a href="createClimb.html" class="button">Create a Climb</a></p>';
            climbHistoryElement.innerHTML = messageHtml;
            return;
        }
    
        const climbHistoryByWeek = new Map();
    
        const textHtml = '<h6>Click a Climb below for details:</h6>';
    
        let climbHtml = '<table><tr><th>Route</th><th>Current Status</th><th>Date / Time Climbed</th></tr>';
        for (const climb of climbHistory) {
            let routeId = climb.routeId;
            let location = routeId.split("::")[0];
    
            climbHtml += `
            <tr onclick="window.location='/viewClimbs.html?climbId=${climb.climbId}'">
                <td>${getValueFromEnum(location, routeLocations)}</td>
                <td>${getValueFromEnum(climb.climbStatus, climbStatus)}</td>
                <td>${formatDateTime(climb.dateTimeClimbed)}</td>
            </tr>
            `;
    
            // GRAPH
           // this.updateTotalClimbsPerWeek(climb, climbHistoryByWeek);
        }
        climbHtml += '</table>';
    
        climbHistoryElement.innerHTML = textHtml + climbHtml;
    
        //this.addWeeklyClimbGraphToPage(climbHistoryByWeek);
        //console.log("Graph data: ", climbHistoryByWeek);
    }
    
    


    // Function to populate the status dropdown
    updateStatusDropdown() {
        const climb = this.dataStore.get('climb');
        if (climb == null) {
            console.log("climb is null for update climb status dropdown");
            return;
        }

        const currClimbStatus = getValueFromEnum(climb.climbStatus, climbStatus);

        const statusDropdown = document.getElementById('statusDropdown');

        statusDropdown.innerHTML = '';

        // Create an option for the current climb status
        const currentOption = document.createElement('option');
        currentOption.value = climb.climbStatus;
        currentOption.textContent = currClimbStatus;
        currentOption.selected = true;
        statusDropdown.appendChild(currentOption);

        // Add other options
        for (const status in climbStatus) {
            if (climbStatus.hasOwnProperty(status) && status !== climb.climbStatus) {
                const option = document.createElement('option');
                option.value = status;
                option.textContent = climbStatus[status];
                statusDropdown.appendChild(option);
            }
        }
    }

    // Function to populate the type dropdown
    updateLeadClimbCheckbox() {
        const climb = this.dataStore.get('climb');
        if (climb == null) {
            console.log("climb is null for updating checkbox");
            return;
        }

        const leadClimbCheckbox = document.getElementById('leadClimbCheckbox');

        // Set the default state based on climb type
        leadClimbCheckbox.checked = climb.type === 'LEAD_CLIMB';

    }

    updateThumbsUpAndDownRadios() {
        const climb = this.dataStore.get('climb');
    
        const radioButtons = document.getElementsByName('thumbs');
    
        for (const radioButton of radioButtons) {
            const radioButtonValue = radioButton.value;
            
            radioButton.checked = (radioButtonValue === 'thumbsUp' && climb.thumbsUp === true) ||
                                  (radioButtonValue === 'thumbsDown' && climb.thumbsUp === false) ||
                                  (radioButtonValue !== 'thumbsUp' && radioButtonValue !== 'thumbsDown' && climb.thumbsUp === null);
        }
    }

    updateClimbNotes() {
        const climb = this.dataStore.get('climb');
        const notesInput = document.getElementById('updateNotes');
        console.log(notesInput)
        if (climb.notes !== null) {
            notesInput.value = climb.notes;
        } else {
            notesInput.value = ''; 
            notesInput.placeholder = "ex. Big dynamic move at the start..."; // Show the placeholder
        }
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
        const notes = document.getElementById('updateNotes').value || null;

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
        const climb = this.dataStore.get('climb');
        const deleteButton = document.getElementById('deleteClimbButton');
        const messageContainer = document.getElementById('messageContainer');
        const deleteMessage = document.getElementById('message');
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');
    
        // Display a confirmation dialog
        const { isConfirmed } = await Swal.fire({
            title: 'Are you sure?',
            text: 'You won\'t be able to revert this!',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'DELETE'
        });
    
        if (isConfirmed) {
            try {
                deleteButton.innerText = 'Deleting...'
                await this.client.deleteClimb(climb.climbId);
                
            } catch (error) {
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
                return;
            }
            deleteButton.style.display = 'none';
            openModalBtn.style.display = 'none';
            messageContainer.style.display = 'block';
            deleteMessage.textContent = 'This climb has been deleted!';
            
            // Redirect after a delay (if needed)
            setTimeout(() => {
                this.redirectToViewClimbHistory();
            }, 3000);
        } else {
            // User clicked Cancel in the confirmation dialog
            console.log('Delete operation canceled');
        }
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
