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
        const modal = document.getElementById('myModal');
    
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

    /**
     * When the route is updated in the datastore, update the route metadata on the page.
     */
    async addRouteToPage() {
        const route = this.dataStore.get('route');
        if (route == null) {
            return;
        }

        //BEGIN IMAGE TESTING
        //const routeImage = null;
        const base64String = route.routeImageBase64;
        if ( base64String != null) {
            //routeImage = this.convertBase64ToFile(base64String, route.imageName, route.imageType)
       
            // Assuming 'routeImageBase64' is the base64 string of your image
            // const routeImageBase64 = "your_base64_string_here";
            const routeImageName = route.imageName;
            const routeImageType = route.imageType; // e.g., "image/jpeg", "image/png", etc.

            // Get the img element by its id
            const routeImageElement = document.getElementById('route-image');

            // Construct the data URL using the routeImageBase64, routeImageType, and routeImageName
            if (base64String) {
                // Convert base64 to File
                const routeImageFile = await this.convertBase64ToFile(base64String, routeImageName, routeImageType);

                // Create a data URL using the File object
                const dataURL = URL.createObjectURL(routeImageFile);

                routeImageElement.src = dataURL;
                routeImageElement.alt = routeImageName;
            } else {
                // Set an alternative text if routeImageBase64 is null
                routeImageElement.src = ''; // Clear the src attribute
                routeImageElement.alt = 'No image yet';
            }
        }
        //document.getElementById('route-image').innerText = routeImage !== null ? routeImage : 'No image yet!';

        // END IMAGE TESTING

        document.getElementById('route-location').innerText = route.location;
        document.getElementById('route-status').innerText = route.routeStatus;
        document.getElementById('type').innerText = route.type;
        document.getElementById('difficulty').innerText = route.difficulty;
        document.getElementById('color').innerText = route.color;
        document.getElementById('rating').innerText = route.rating !== null ? route.rating : 'Not yet Rated!';
        document.getElementById('date-created').innerText = formatDate(route.dateCreated);
    
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

        /**
     * Function to convert a base64 string to a File object 
     * 
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * this method may need to be moved to where ever the file or image is being used or displayed so like the viewClimb.js file
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * 
     * @param {*} base64String 
     * @param {*} fileName 
     * @param {*} fileType 
     * @returns the file that has been converted
     */
        convertBase64ToFile(base64String, fileName, fileType) {
            console.log('Attempting to convert base64String to file');
            const byteCharacters = atob(base64String);
            const byteArrays = [];
    
            for (let offset = 0; offset < byteCharacters.length; offset += 512) {
                const slice = byteCharacters.slice(offset, offset + 512);
    
                const byteNumbers = new Array(slice.length);
                for (let i = 0; i < slice.length; i++) {
                    byteNumbers[i] = slice.charCodeAt(i);
                }
    
                const byteArray = new Uint8Array(byteNumbers);
                byteArrays.push(byteArray);
            }
    
            const blob = new Blob(byteArrays, { type: fileType });
            return new File([blob], fileName, { type: fileType });
        }

        // Function to populate the status dropdown
    statusDropdown() {
        const statusDropdown = document.getElementById('statusDropdown');
    
        statusDropdown.innerHTML = '';
    
        const statuses = ['ARCHIVED', 'TOURNAMENT_ONLY', 'CLOSED_MAINTENANCE', 'ACTIVE' ];

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



