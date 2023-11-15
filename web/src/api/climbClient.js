import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the ClimbingGymCompanionService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class ClimbClient extends BindingClass {
    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }

    /*
    * Gets all the routes for the Active status.
    * @param errorCallback (Optional) A function to execute if the call fails.
    * @returns The route's metadata.
    */
    async viewAllActiveRoutes(errorCallback) {
        try {
            console.log("Attempting to get all active routes in climbClient.js");  

            const archivedStatus = "FALSE"
            const response = await this.axiosClient.get(`routes?isArchived=${archivedStatus}`);

            console.log("routeResponse: ", response);  

            return response.data.routeList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /*
     * Gets the route for the given ID.
     * @param id Unique identifier for a route
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The route's metadata.
     */
    async viewRoute(routeId, errorCallback) {
        try {
            console.log("Attempting to get info for routeId: ", routeId);  

            const response = await this.axiosClient.get(`routes/${routeId}`);
            console.log("routeResponse: ", response);  

            return response.data.route;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
    * Create a new route by the current user.
    * @param location Metadata associated with a route.
    * @param color Metadata associated with a route.
    * @param routeStatus Metadata associated with a route.
    * @param type Metadata to associated with a route.
    * @param difficulty Metadata to associated with a route.

    * @param errorCallback (Optional) A function to execute if the call fails.
    * @returns The playlist that has been created.
    */
    async createRoute(location, color, routeStatus, type, difficulty, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to create a route!");
            const response = await this.axiosClient.post(`routes`, {
                location: location,
                color: color,
                routeStatus: routeStatus,
                type: type,
                difficulty: difficulty
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.route;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
}