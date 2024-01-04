import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the ClimbingGymCompanionService.
  */
export default class ClimbClient extends BindingClass {
    constructor(props = {}) {
        super();

        const methodsToBind = [
            'clientLoaded',
            'getIdentity',
            'login',
            'logout'];
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
            const archivedStatus = "FALSE"
            const response = await this.axiosClient.get(`routes?isArchived=${archivedStatus}`);

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
            const response = await this.axiosClient.get(`routes/${routeId}`);

            return response.data.route;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }


    async getUserInfo(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to view your climbs!");

            const response = await this.axiosClient.get(`userInfo`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            console.error('USERINFO LAMBDA RESPONSE: ', response)
            return response.data.userInfoModel;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }


    /** 
     * Gets the climb for the given ID.
     * @param id Unique identifier for a climb
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The climb's metadata.
     */
    async viewClimb(climbId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to view your climbs!");

            const response = await this.axiosClient.get(`climbs/${climbId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            return response.data.climb;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /*
     * Gets the climb history for the current user.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The climbs's metadata.
     */
    async viewUsersClimbHistory(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to view your climbs!");

            const response = await this.axiosClient.get(`climbsByUser`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            return response.data.climbList;
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
    * @returns The route that has been created.
    */
    async createRoute(location, color, routeStatus, type, difficulty, routeImageFile, imageKey, errorCallback) {
        try {

            const token = await this.getTokenOrThrow("You must be logged in to create a route!");

            let imageName = null;
            let imageType = null;

            if (routeImageFile) {
                // The S3 object info for the uploaded image
                imageName = routeImageFile.name;
                imageType = routeImageFile.type;
            }

            const response = await this.axiosClient.post(`routes`, {
                location: location,
                color: color,
                routeStatus: routeStatus,
                type: type,
                difficulty: difficulty,
                imageName: imageName,
                imageType: imageType,
                imageKey: imageKey,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.route;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }
    /**
     * 
     * @param {*} imageKey 
     * @param {*} errorCallback 
     * @returns 
     */
    async getPresignedS3Url(imageKey, errorCallback) {
        try {
            const response = await this.axiosClient.get(`/s3upload/${imageKey}`, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    /**
 * 
 * @param {*} imageKey 
 * @param {*} errorCallback 
 * @returns 
 */
    async getPresignedS3Image(imageKey, errorCallback) {
        try {
            const response = await this.axiosClient.get(`/s3download/${imageKey}`, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }
    /**
     * 
     * @param {*} s3PresignedUrl 
     * @param {*} routeImageFile 
     * @param {*} errorCallback 
     * @returns 
     */
    async uploadToS3(s3PresignedUrl, routeImageFile, errorCallback) {
        try {
            const response = await this.axiosClient.put(s3PresignedUrl, routeImageFile, {
                headers: {
                    'Content-Type': 'image/webp',
                }
            });
            return response;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    /**
    * Update a new route by the current user.
    * @param routeId Metadata associated with a route.
    * @param routeStatus Metadata associated with a route.
    * @param errorCallback (Optional) A function to execute if the call fails.
    * @returns The route that has been updated.
    */
    async updateRouteStatus(routeId, routeStatus, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to update a route!");

            const response = await this.axiosClient.put(`routes/${routeId}`, {
                routeStatus: routeStatus
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

    /**
     * 
     * @param routeId 
     * @param climbStatus 
     * @param thumbsUp 
     * @param publicBeta 
     * @param errorCallback 
     * @returns 
     */
    async createClimb(routeId, climbStatus, thumbsUp, type, publicBeta, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to add a new climb!");

            const response = await this.axiosClient.post(`climbs`, {
                routeId: routeId,
                climbStatus: climbStatus,
                thumbsUp: thumbsUp,
                type: type,
                publicBeta: publicBeta
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.climb;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
    * 
    * @param climbId 
    * @param climbStatus 
    * @param thumbsUp 
    * @param publicBeta 
    * @param errorCallback 
    * @returns 
    */
    async updateClimb(climbId, climbStatus, thumbsUp, type, publicBeta, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to update a climb!");

            const response = await this.axiosClient.put(`climbs/${climbId}`, {
                climbStatus: climbStatus,
                thumbsUp: thumbsUp,
                type: type,
                publicBeta: publicBeta
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.climb;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }


    /**
    * 
    * @param climbId 
    * @param errorCallback 
    * @returns 
    */
    async deleteClimb(climbId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to delete a climb!");

            const response = await this.axiosClient.delete(`climbs/${climbId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.climb;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
}
