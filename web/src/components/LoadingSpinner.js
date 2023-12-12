export default class LoadingSpinner {

    // Function to show the loading spinner
    showLoadingSpinner(message = "") {
        document.getElementById("loading-message").innerText = message;
        document.getElementById("loading-message-sub").innerText = "Hold on, sweeping up chalk!";
        document.getElementById('loading-spinner').style.display = 'flex';
    }

    // Function to show the loading spinner w/ alt message
    showLoadingSpinnerAltMessage(message = "") {
        document.getElementById("loading-message").innerText = message;
        document.getElementById("loading-message-sub").innerText = "Gimmie a sec, gathering ropes!";
        document.getElementById('loading-spinner').style.display = 'flex';
    }

    showLoadingSpinnerNoMessages() {
        document.getElementById('loading-spinner-no-message').style.display = 'flex';
    }

    // Function to hide the loading spinner
    hideLoadingSpinner() {
        document.getElementById('loading-spinner').style.display = 'none';
    }

    // Function to hide the loading spinner
    hideLoadingSpinnerNoMessages() {
        document.getElementById('loading-spinner-no-message').style.display = 'none';
    }

}