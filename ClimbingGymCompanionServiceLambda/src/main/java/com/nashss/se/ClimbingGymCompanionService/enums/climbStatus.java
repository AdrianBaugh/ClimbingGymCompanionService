package com.nashss.se.ClimbingGymCompanionService.enums;

public enum climbStatus {
    // first attempt completion start to finish
    COMPLETED_FLASHED,
    // completion start to finish
    COMPLETED_SENT,
    // completion start to finish but fell off wall in between
    COMPLETED_IN_STAGES,
    // attempting to complete but it is a work in progress
    PROJECTING,
    // not yet climbed but want to
    WANT_TO_CLIMB,
    // attempted but too difficult to complete
    TOO_DIFFICULT
}
