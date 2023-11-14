import ClimbClient from "../api/climbClient";
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import { formatDateToMMDDYYYY } from '../util/dateUtils';


/**
 * Logic needed for the create route page of the website.
 */
class CreateRoute extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewRoute'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addRouteToPage);

        this.header = new Header(this.dataStore);
        console.log("ViewRoute constructor");
    }

    /**
     * Add the header to the page and load the ClimbClient.
     */
    mount() {
        document.getElementById('create').addEventListener('click', this.submit);

        this.header.addHeaderToPage();
        this.client = new ClimbClient();
        this.clientLoaded();
    }

    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading. . .';

        const location = document.getElementById('location').value;
        const color = document.getElementById('color').value;
        const routeStatus = document.getElementById('routeStatus').value;
        const type = document.getElementById('type').value;
        const difficulty = document.getElementById('difficulty').value;
        
        const route = await this.client.createRoute(location, color, routeStatus, type, difficulty, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('route', route);
    }

    redirectToViewRoute() {
        const route = this.dataStore.get('route');
        if (route != null) {
            window.location.href = `/viewRoute.html?id=${route.routeId}`;
        }
    }


}
    /**
     * Main method to run when the page contents have loaded.
     */
    const main = async () => {
        const viewRoute = new CreateRoute();
        viewRoute.mount();
    };

    window.addEventListener('DOMContentLoaded', main);


