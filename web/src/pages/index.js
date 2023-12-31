import ClimbClient from '../api/climbClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from '../util/DataStore';
import { formatDate } from '../util/dateUtils';
import routeStatus from "../enums/routeStatus";
import routeLocations from "../enums/routeLocations";
import routeDifficulties from "../enums/routeDifficulties";
import { getValueFromEnum } from '../util/enumUtils.js';
import LoadingSpinner from "../components/LoadingSpinner.js";



class Homepage extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods([
            'clientLoaded',
            'mount',
            'addRoutesToPage',
            'showLoader',
            'hideSimpleLoader'
        ], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addRoutesToPage);
        this.header = new Header(this.dataStore);
        this.loadingSpinner = new LoadingSpinner();

    }

    async clientLoaded() {
        this.showLoader();

        const routes = await this.client.viewAllActiveRoutes();
        this.dataStore.set('routes', routes);
    }

    mount() {

        this.header.addHeaderToPage();
        this.client = new ClimbClient();
        this.clientLoaded();
    }

    addRoutesToPage() {
        const routes = this.dataStore.get('routes');

        if (routes == null) {
            return;
        }
        let routeHtml = '<div><table><tr><th>Route</th><th>Difficulty</th><th>Current Status</th><th>Date Set</th><th>ThumbsUP! 👍</th></tr>';

        for (const route of routes) {
            routeHtml += `
            <tr onclick="window.location='/viewRoute.html?routeId=${route.routeId}'">
                <td>${getValueFromEnum(route.location, routeLocations)}</td>
                <td>${getValueFromEnum(route.difficulty, routeDifficulties)}</td>
                <td>${getValueFromEnum(route.routeStatus, routeStatus)}</td>
                <td>${formatDate(route.dateCreated)}</td>
                <td>${route.rating !== null ? route.rating + '%' : 'Not yet Rated!'}</td>
            </tr>
            `;
        }
        routeHtml += '</table></div>';
        this.hideSimpleLoader();
        document.getElementById('routeList').innerHTML = routeHtml;

    }

    showLoader() {
        this.loadingSpinner.showLoadingSpinnerNoMessages();
    }
    hideSimpleLoader() {
        this.loadingSpinner.hideLoadingSpinnerNoMessages();
    }

}

const main = async () => {
    const homePage = new Homepage();
    homePage.mount();
};

window.addEventListener('DOMContentLoaded', main);

