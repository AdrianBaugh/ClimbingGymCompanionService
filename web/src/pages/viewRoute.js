import ClimbClient from "../api/climbClient";
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import { formatDateToMMDDYYYY } from '../util/dateUtils';


/**
 * Logic needed for the get route page of the website.
 */
class ViewRoute extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addRouteToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addRouteToPage);

        this.header = new Header(this.dataStore);
        console.log("ViewRoute constructor");
    }

    /**
     * Once the client is loaded, get the pet metadata.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const routeId = urlParams.get('routeId');

        const route = await this.client.viewRoute(routeId);
        this.dataStore.set('route', route);
    }

    /**
     * Add the header to the page and load the ClimbClient.
     */
    mount() {

        this.header.addHeaderToPage();
        this.client = new ClimbClient();
        this.clientLoaded();
    }

    /**
     * When the route is updated in the datastore, update the route metadata on the page.
     */
    addRouteToPage() {
        const route = this.dataStore.get('route');
        if (route == null) {
            return;
        }
    
        document.getElementById('route-location').innerText = route.location;
        document.getElementById('route-status').innerText = route.routeStatus;
        document.getElementById('type').innerText = route.type;
        document.getElementById('difficulty').innerText = route.difficulty;
        document.getElementById('color').innerText = route.color;
        document.getElementById('rating').innerText = route.rating !== null ? route.rating : 'Not yet Rated!';
        document.getElementById('date-created').innerText = formatDateToMMDDYYYY(route.dateCreated);
    
        const toggleButton = document.getElementById('toggleButton');
        const notesList = document.getElementById('notesList');

        toggleButton.addEventListener('click', function () {
            notesList.classList.toggle('hidden');

            if (!notesList.classList.contains('hidden')) {
                generateTableContent(route.notesList, notesList);
            }
        });

        function generateTableContent(notes, notesList) {
            // add logic so that if notes is empty it displays a message to the user
            notesList.innerHTML = '';

            const tableHeaders = document.createElement('tr');
            tableHeaders.innerHTML = '<th>Route Beta</th>';
            notesList.appendChild(tableHeaders);

            notes.forEach(note => {
                const row = document.createElement('tr');
                row.innerHTML = `<td>${note}</td>`;
                notesList.appendChild(row);
            });
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewRoute = new ViewRoute();
    viewRoute.mount();
};

window.addEventListener('DOMContentLoaded', main);



