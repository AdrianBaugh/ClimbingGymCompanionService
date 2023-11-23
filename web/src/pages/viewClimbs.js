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
        this.bindClassMethods(['clientLoaded', 'mount', 'addClimbToPage', 'addClimbHistoryToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addClimbToPage);
        this.dataStore.addChangeListener(this.addClimbHistoryToPage);
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
        
        // Check if climbId is present and not null
        const urlParams = new URLSearchParams(window.location.search);
        const climbId = urlParams.get('climbId');
        if (climbId !== null) {
            const climb = await this.client.viewClimb(climbId);
            this.dataStore.set('climb', climb);
                   
            const currentDisplayedRoute = await this.client.viewRoute(climb.routeId);
            this.dataStore.set('currentDisplayedRoute', currentDisplayedRoute);
        }

        const climbHistory = await this.client.viewUsersClimbHistory();
        this.dataStore.set('climbHistory', climbHistory);
    }
    

    /**
     * Add the header to the page and load the ClimbClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new ClimbClient();
        this.clientLoaded();
    }

    addClimbToPage() {
        const climb = this.dataStore.get('climb');
        if (climb == null) {
            // If climbId is null, hide the corresponding HTML
            document.getElementById('climb-details').style.display = 'none';
            return;
        }
    
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
        document.getElementById('rating').innerText = climb.rating !== null ?
            (climb.rating ? 'Thumbs Up!' : 'Thumbs Down') :
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
            const currentRoute = await this.client.viewRoute(climb.routeId);
    
            climbHtml += `
            <tr onclick="window.location='/viewClimbs.html?climbId=${climb.climbId}'">
                <td>${currentRoute.location}</td>
                <td>${climb.climbStatus}</td>
                <td>${formatDateTime(climb.dateTimeClimbed)}</td>
            </tr>
            `;
        }
        climbHtml += '</table>';

        climbHistoryElement.innerHTML = textHtml + climbHtml;
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
