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
        this.bindClassMethods(['clientLoaded', 'mount', 'addClimbToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addClimbToPage);
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
    
            document.getElementById("loginModal").style.display = "flex";
    
            const loginButton = document.createElement('div');
            loginButton.textContent = 'Login';
            loginButton.classList.add('button'); 
    
            loginButton.addEventListener('click', async () => {
                await this.client.login();
            });
                document.getElementById('loginBtn').appendChild(loginButton);
        }

        const urlParams = new URLSearchParams(window.location.search);
        const climbId = urlParams.get('climbId');
        const climb = await this.client.viewClimb(climbId);
        this.dataStore.set('climb', climb);

        // const climbs = await this.client.viewAllClimbs();
        // this.dataStore.set('climbs', climbs);
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
        if(climb == null) {
            return;
        }

        document.getElementById('route-location').innerText = climb.location;
        document.getElementById('difficulty').innerText = climb.difficulty;
        document.getElementById('climb-status').innerText = climb.climbStatus;
        document.getElementById('type').innerText = climb.type;
        document.getElementById('rating').innerText = climb.rating !== null ? climb.thumbsUp : 'Not yet Rated!';
        document.getElementById('dateTime-climbed').innerText = formatDateTime(climb.dateTimeClimbed);

    }

    // addClimbsToPage() {
    //     console.log("add climbs to page is starting");
    //     const climbs = this.dataStore.get('climbs')
    //     console.log("climbs", climbs);
    
    //     if (climbs == null) {
    //         return;
    //     }
    //     let climbHtml = '<table><tr><th>Route Location</th><th>Current Status</th><th>Date / Time Climbed</th><th>% ThumbsUP!</th></tr>';
    
    //     for (const climb of climbs) {
    //         climbHtml += `
    //         <tr onclick="window.location='/viewClimbs.html?climbId=${climb.climbId}'">
    //             <td>${climb.location}</td>
    //             <td>${climb.climbStatus}</td>
                
    //             <td>${climb.dateTimeClimbed}</td>
    //         </tr>
    //         `;
    //     }
    //     climbHtml += '</table>';
    
    //     document.getElementById('climbList').innerHTML = climbHtml;
    // }
}


/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewClimb = new ViewClimb();
    viewClimb.mount();
};

window.addEventListener('DOMContentLoaded', main);
