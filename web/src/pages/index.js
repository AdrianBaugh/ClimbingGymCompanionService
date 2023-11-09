import ClimbClient from '../api/climbClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from '../util/DataStore';



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
    }


    addRoutesToPage() {
        console.log("add routes to page is starting");
        const routes = this.dataStore.get('routes')
        console.log("routes", routes);

        if (routes == null) {
            return;
        }
        let routeHtml = '<table><tr><th>Route ID</th><th>Date Set</th><th>Difficulty</th></tr>';

        for (const route of routes) {
            routeHtml += `
            <tr>
                <td>
                    <a href="/viewRoute.html?id=${route.routeId}">${route.routeId}</a>
                </td>    
                    <td>${formatDateToMMDDYYYY(route.dateCreated)}</td>
                    <td>${(route.difficulty)}</td>
                </tr>
            `;
        }
        document.getElementById('routeList').innerHTML = routeHtml;
    }   
}

const main = async () => {
    const homePage = new Homepage();
    homePage.mount();
};

window.addEventListener('DOMContentLoaded', main);

