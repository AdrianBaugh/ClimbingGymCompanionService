// climbStatus.js

const climbStatus = {
    // first attempt completion start to finish
    COMPLETED_FLASHED: 'COMPLETED FLASED',
    // completion start to finish
    COMPLETED_SENT: 'COMPLETED SENT',
    // completion start to finish but fell off wall in between
    COMPLETED_IN_STAGES: 'COMPLETED IN STAGES',
    // attempting to complete but it is a work in progress
    PROJECTING: 'PROJECTING',
    // not yet climbed but want to
    WANT_TO_CLIMB: 'WANT TO CLIMB',
    // attempted but too difficult to complete
    TOO_DIFFICULT: ' TOO DIFFICULT'
};

export default climbStatus;