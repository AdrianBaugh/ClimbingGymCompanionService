import ClimbClient from '../api/climbClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from '../util/DataStore';
import { formatDateToMMDDYYYY } from '../util/dateUtils';



class Homepage extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['clientLoaded','mount', 'addRoutesToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addRoutesToPage);
        this.header = new Header(this.dataStore);

        console.log("Homepage constructor");
    }

    async clientLoaded() {
        const routes = await this.client.viewAllActiveRoutes();
        this.dataStore.set('routes', routes);
    }

    mount() {

        this.header.addHeaderToPage();
        this.client = new ClimbClient();
        this.clientLoaded();
    }


    addRoutesToPage() {
        console.log("add routes to page is starting");
        const routes = this.dataStore.get('routes')
        console.log("routes", routes);
    
        if (routes == null) {
            return;
        }
        let routeHtml = '<table><tr><th>Route Location</th><th>Difficulty</th><th>Current Status</th><th>Date Set</th><th>% ThumbsUP!</th></tr>';
    
        for (const route of routes) {
            routeHtml += `
            <tr onclick="window.location='/viewRoute.html?routeId=${route.routeId}'">
                <td>${route.location}</td>
                <td>${route.difficulty}</td>
                <td>${route.routeStatus}</td>
                <td>${formatDateToMMDDYYYY(route.dateCreated)}</td>
                <td>${route.rating !== null ? route.rating : 'Not yet Rated!'}</td>
            </tr>
            `;
        }
        routeHtml += '</table>';
    
        document.getElementById('routeList').innerHTML = routeHtml;
    }
    
}

const main = async () => {
    const homePage = new Homepage();
    homePage.mount();
};

window.addEventListener('DOMContentLoaded', main);

