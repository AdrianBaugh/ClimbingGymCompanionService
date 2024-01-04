import ClimbClient from "../api/climbClient";
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import { formatDateTime } from '../util/dateUtils';
import climbStatus from "../enums/climbStatus";
import routeTypes from "../enums/routeTypes";
import routeLocations from "../enums/routeLocations";
import routeDifficulties from "../enums/routeDifficulties";
import { getValueFromEnum } from '../util/enumUtils.js';
import LoadingSpinner from "../components/LoadingSpinner.js";


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
            'showLoader',
            'hideLoader',
            'delete'
        ], this);

        this.dataStore = new DataStore();
        this.loadingSpinner = new LoadingSpinner();
        this.header = new Header(this.dataStore);
    }

    /**
    * Once the client is loaded, get the climb metadata.
    */
    async clientLoaded() {

        if (await this.client.authenticator.isUserLoggedIn()) {
            // User is logged in
        } else {
            /////////User is not logged in////////
            document.getElementById("loginModal").style.display = "block";

            const loginButton = document.createElement('div');
            loginButton.textContent = 'Login';
            loginButton.classList.add('button');

            loginButton.addEventListener('click', async () => {
                await this.client.login();
            });
            document.getElementById('loginBtn').appendChild(loginButton);
        }



        this.showLoader("...");
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


        const climbHistory = await this.client.viewUsersClimbHistory();
        const userInfo = await this.client.getUserInfo();

        this.dataStore.set('climbHistory', climbHistory);
        this.dataStore.set('userInfo', userInfo);

        this.addClimbHistoryToPage();
        this.hideLoader();
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
            // If there isnt a climbId to showcase, hide the climb details HTML
            document.getElementById('climb-details').style.display = 'none';
            return;
        } else {
            const route = this.dataStore.get('currentDisplayedRoute');
            if (route == null) {
                return;
            }

            document.getElementById('climb-details').style.display = 'block';

            const routeLocationElement = document.getElementById('route-location');
            routeLocationElement.innerText = getValueFromEnum(route.location, routeLocations);

            routeLocationElement.style.cursor = 'pointer';
            routeLocationElement.style.color = 'blue'; 
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
        const climbHistory = this.dataStore.get('climbHistory');

        const totalClimbs = document.getElementById('totalClimbs');
        totalClimbs.innerText = (climbHistory ? 'Total Routes Climbed: ' + climbHistory.length : 'No climbs yet!');

        const climbHistoryElement = document.getElementById('climbHistory');

        if (climbHistory == null || climbHistory.length === 0) {
            const messageHtml = '<p>You don\'t have any climbs yet! <a href="createClimb.html" class="button">Create a Climb</a></p>';
            climbHistoryElement.innerHTML = messageHtml;
            return;
        }

        const climbHistoryFrequencyMap = {};

        const textHtml = '<h6>Click a Climb below for details:</h6>';

        let climbHtml = '<table><tr><th>Route</th><th>Status</th><th>Date / Time Climbed</th></tr>';
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

            // POPULATE GRAPH DATA
            if (climbHistoryFrequencyMap[climb.weekClimbed]) {
                climbHistoryFrequencyMap[climb.weekClimbed]++;
            } else {
                climbHistoryFrequencyMap[climb.weekClimbed] = 1;
            }


        }
        climbHtml += '</table>';

        climbHistoryElement.innerHTML = textHtml + climbHtml;

        // CALL PROCESS GRAPH WITH DATASET
        this.dataStore.set('climbHistoryFrequencyMap', climbHistoryFrequencyMap);
        this.processClimbHistory();


        // backend frequency map data test
        //***************************************************************************************** */
        const userInfo = this.dataStore.get('userInfo');
        console.warn('USER INFO: ', userInfo);
    }

    /* 
    * Method for handling climb history frequency map, sorting, populaing missing data, etc.
    */
    processClimbHistory() {
        const climbHistoryFrequencyMap = this.dataStore.get('climbHistoryFrequencyMap');
    
        const weekNumber = (key) => parseInt(key.split('::')[1]);
    
        const currentMinWeek = Math.min(...Object.keys(climbHistoryFrequencyMap).map(weekNumber));
        const currentMaxWeek = Math.max(...Object.keys(climbHistoryFrequencyMap).map(weekNumber));
    
        let minWeek = currentMinWeek;
        let maxWeek = currentMaxWeek;
    
        // Check if the difference between min and max is less than 4 weeks
        if (maxWeek - minWeek < 4) {
            // Adjust the min value to be 4 less than the max value
            minWeek = Math.max(currentMaxWeek - 4, 1); // Ensure the min is at least 1
        }
    
        // Create a new frequency map with all weeks, filling missing weeks with zero
        let tempMap = {};
        for (let weekNumber = maxWeek; weekNumber >= minWeek; weekNumber--) {
            const key = `2023::${weekNumber}`;
            tempMap[key] = climbHistoryFrequencyMap[key] || 0;
        }
        
        // sort the data beased on week number(keys)
        let sortedKeys = Object.keys(tempMap).sort((a, b) => {
            const numA = parseInt(a.split('::')[1]);
            const numB = parseInt(b.split('::')[1]);
    
            return numA - numB;
        });
    
        // Take the last 5 keys(weeks) of recent data
        sortedKeys = sortedKeys.slice(-5);
    
        // Create a new frequency map with sorted keys and explicitly set the value to 0 for new keys
        let sortedFrequencyMap = {};
        sortedKeys.forEach(key => {
            sortedFrequencyMap[key] = tempMap[key] || 0;
        });
    
        let customLabels = ['4 Weeks Ago', '3 Weeks Ago', '2 Weeks Ago', 'Last Week', 'This Week'];
        let sortedData = Object.values(sortedFrequencyMap);
    
        console.log("FULL PROCESSED 5 WEEK HISTORY MAP: " ,sortedFrequencyMap);

        this.addClimbHistoryGraphToPage(customLabels, sortedData);
    }
    

    addClimbHistoryGraphToPage(labels, data) {
        const canvas = document.getElementById('climbGraph');
        const context = canvas.getContext('2d');

        // Clear the chart
        if (canvas.chart) {
            canvas.chart.destroy();
        }

        let climbHistoryChart = new Chart(context, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Climbs Per Week',
                    data: data,

                    fill: false
                }]
            },
            options: {
                scales: {
                    x: {
                        type: 'category',
                        labels: labels
                    },
                    y: {
                        suggestedMin: 0,
                        beginAtZero: true,
                        stepSize: 1,
                        suggestedMax: Math.max(...data) + 1,
                    }
                }
            }
        });

        canvas.chart = climbHistoryChart;
    }

    updateStatusDropdown() {
        const climb = this.dataStore.get('climb');
        if (climb == null) {
            return;
        }

        const currClimbStatus = getValueFromEnum(climb.climbStatus, climbStatus);

        const statusDropdown = document.getElementById('statusDropdown');

        statusDropdown.innerHTML = '';

        const currentOption = document.createElement('option');
        currentOption.value = climb.climbStatus;
        currentOption.textContent = currClimbStatus;
        currentOption.selected = true;
        statusDropdown.appendChild(currentOption);

        for (const status in climbStatus) {
            if (climbStatus.hasOwnProperty(status) && status !== climb.climbStatus) {
                const option = document.createElement('option');
                option.value = status;
                option.textContent = climbStatus[status];
                statusDropdown.appendChild(option);
            }
        }
    }

    updateLeadClimbCheckbox() {
        const climb = this.dataStore.get('climb');

        if (climb == null) {
            return;
        }

        const leadClimbCheckbox = document.getElementById('leadClimbCheckbox');

        // Set the default state based on if climb type is LEAD_CLIMB or not
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
        if (climb.notes !== null) {
            notesInput.value = climb.notes;
        } else {
            notesInput.value = '';
            notesInput.placeholder = "ex. Big dynamic move at the start..."; 
        }
    }


    redirectToViewClimb() {
        const climb = this.dataStore.get('climb');
        if (climb != null) {
            const currentUrl = new URL(window.location.href);
            if (!currentUrl.searchParams.has('climbId')) {
                window.location.href = `/viewClimbs.html?climbId=${climb.climbId}`;
            }
        }
    }

    redirectToViewClimbHistory() {
        window.location.href = `/viewClimbs.html`;
    }

    async submit(evt) {
        const climb = this.dataStore.get('climb');
        const currRoute = this.dataStore.get('currentDisplayedRoute');

        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');

        const updateButton = document.getElementById('updateClimb');
        const origButtonText = updateButton.innerText;
        updateButton.innerText = 'Updating. . .';


        const leadClimbCheckbox = document.getElementById('leadClimbCheckbox');

        let type = null;
        if (leadClimbCheckbox.checked) {
            type = 'LEAD_CLIMB';
        }

        const climbStatus = document.getElementById('statusDropdown').value || null;
        const notes = document.getElementById('updateNotes').value || null;

        // Get the value of each thumbsUp or down option
        const thumbsUpRadioButton = document.getElementById('thumbsUp');
        const thumbsDownRadioButton = document.getElementById('thumbsDown');

        let thumbsValue = null;

        if (thumbsUpRadioButton.checked) {
            thumbsValue = true;
        } else if (thumbsDownRadioButton.checked) {
            thumbsValue = false;
        }

        try {
            const updatedClimb = await this.client.updateClimb(climb.climbId, climbStatus, thumbsValue, type, notes);
            await this.dataStore.set('climb', updatedClimb);

        } catch (error) {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        }

        const modal = document.getElementById('updateClimbModal')

        setTimeout(() => {
            modal.style.display = 'none';
            updateButton.innerText = origButtonText;
        }, 3000);
        window.location.reload();
    }

    async delete() {
        const climb = this.dataStore.get('climb');
        const deleteButton = document.getElementById('deleteClimbButton');
        const messageContainer = document.getElementById('messageContainer');
        const deleteMessage = document.getElementById('message');
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');

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

            setTimeout(() => {
                this.redirectToViewClimbHistory();
            }, 3000);
        } else {
            // User clicked Cancel in the confirmation dialog
        }
    }

    showLoader(message) {
        this.loadingSpinner.showLoadingSpinner(message);
    }
    hideLoader() {
        this.loadingSpinner.hideLoadingSpinner();
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
