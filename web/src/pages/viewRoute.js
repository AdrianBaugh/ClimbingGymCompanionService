import ClimbClient from "../api/climbClient";
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import { formatDate } from '../util/dateUtils';


/**
 * Logic needed for the get route page of the website.
 */
class ViewRoute extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addRouteToPage', 'statusDropdown', 'submit', 'redirectToViewRoute'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addRouteToPage);
        this.dataStore.addChangeListener(this.redirectToViewRoute);
        this.header = new Header(this.dataStore);
        console.log("ViewRoute constructor");
    }

    /**
     * Once the client is loaded, get the route metadata.
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
        document.getElementById('updateStatus').addEventListener('click', this.submit);

        this.header.addHeaderToPage();
        this.client = new ClimbClient();
        this.statusDropdown();
        this.clientLoaded();

        const openModalButton = document.getElementById('openModalBtn');
        const closeModalButton = document.getElementById('closeModalBtn');
        const routeStatusModal = document.getElementById('routeStatusModal');
    
        openModalButton.addEventListener('click', () => {
            routeStatusModal.style.display = 'block';
        });
    
        closeModalButton.addEventListener('click', () => {
            routeStatusModal.style.display = 'none';
        });
    
        // Close the modal if the user clicks outside of it
        window.addEventListener('click', (event) => {
            if (event.target === routeStatusModal) {
                routeStatusModal.style.display = 'none';
            }
        });
    }

    /**
     * When the route is updated in the datastore, update the route metadata on the page.
     */
    async addRouteToPage() {
        const route = this.dataStore.get('route');
        if (route == null) {
            return;
        }

        // IMAGE 
        this.displayRouteImage(route);

        document.getElementById('route-location').innerText = route.location;
        document.getElementById('route-status').innerText = route.routeStatus;
        document.getElementById('type').innerText = route.type;
        document.getElementById('difficulty').innerText = route.difficulty;
        document.getElementById('color').innerText = route.color;
        document.getElementById('rating').innerText = route.rating !== null ? route.rating : 'Not yet Rated!';
        document.getElementById('date-created').innerText = formatDate(route.dateCreated);
    
        const toggleButton = document.getElementById('toggleButton');
        const notesList = document.getElementById('notesList');
        const noBetaMessage = document.getElementById("noBetaMessage");
        const betaLabel = document.getElementById('betaLabel')
        if (route.notesList == null || route.notesList.length === 0) {
            noBetaMessage.style.display = "block";
          } else {
            betaLabel.style.display = "block";
            toggleButton.style.display = "block";
          }

        toggleButton.addEventListener('click', function () {
            notesList.classList.toggle('hidden');

            if (!notesList.classList.contains('hidden')) {
                generateTableContent(route.notesList, notesList);
            }
        });

        function generateTableContent(notes, notesList) {
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

/**
 * Function to display the image on screen or No image is null
 * @param {*} imageKey 
 */
async displayRouteImage(route) {
    const routeImageElement = document.getElementById('route-image');
    if (route.imageKey != null) {
        const imageUrl = await this.client.getPresignedS3Image(route.imageKey);
        routeImageElement.src = imageUrl.s3PreSignedUrl;
        console.log("imageURL: ", imageUrl.s3PreSignedUrl);
        routeImageElement.alt = route.imageName;
        const imageLabel = document.getElementById("imageLabel");
        imageLabel.style.display = "block";
    } else {
        const imageCard = document.getElementById('image');
        const noImageMessage = document.getElementById("noImageMessage");
        noImageMessage.style.display = "block";
        routeImageElement.style.display = "none"; // Hide only the image, not the entire card
    }
}



   /**
    * Function to populate the status dropdown
    */
    statusDropdown() {
        const statusDropdown = document.getElementById('statusDropdown');
    
        statusDropdown.innerHTML = '';
    
        const statuses = ['ARCHIVED', 'TOURNAMENT_ONLY', 'CLOSED_MAINTENANCE', 'ACTIVE' ];

        const placeholderOption = document.createElement('option');
        placeholderOption.value = '';
        placeholderOption.textContent = 'Select a status'; 
        placeholderOption.disabled = true;
        placeholderOption.selected = true; 
        statusDropdown.appendChild(placeholderOption);
    
        statuses.forEach(status => {
        const option = document.createElement('option');
        option.value = status;
        option.textContent = status;
        statusDropdown.appendChild(option);
        });
    }
    
    redirectToViewRoute() {
        const route = this.dataStore.get('route');
        if (route != null) {
            const currentUrl = new URL(window.location.href);
            if (!currentUrl.searchParams.has('routeId')) {
                console.log("Redirecting to viewRoute.html");
                window.location.href = `/viewRoute.html?routeId=${route.routeId}`;
                

            }
        }
    }
    

    async submit(evt) {
        console.log('Submit button clicked');
        evt.preventDefault();
    
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');
    
        const updateButton = document.getElementById('updateStatus');
        const origButtonText = updateButton.innerText;
        updateButton.innerText = 'Loading. . .';
    
        const routeStatus = document.getElementById('statusDropdown').value;

        const route = this.dataStore.get('route');
        const updatedRoute = await this.client.updateRouteStatus(route.routeId, routeStatus, (error) => {
            updateButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
    
        this.dataStore.set('route', updatedRoute);
    
        this.redirectToViewRoute();
        
        const modal = document.getElementById('myModal')
        setTimeout(() => {
            modal.style.display = 'none';
            updateButton.innerText= origButtonText;
        }, 3000);
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



