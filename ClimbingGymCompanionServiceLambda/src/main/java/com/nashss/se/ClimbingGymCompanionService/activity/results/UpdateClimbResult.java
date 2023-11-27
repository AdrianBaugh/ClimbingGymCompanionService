package com.nashss.se.ClimbingGymCompanionService.activity.results;

import com.nashss.se.ClimbingGymCompanionService.models.ClimbModel;

public class UpdateClimbResult {
    private final ClimbModel climb;

    private UpdateClimbResult(ClimbModel climb) {
        this.climb = climb;
    }

    public ClimbModel getClimb() {
        return climb;
    }

    @Override
    public String toString() {
        return "UpdateClimbResult{" +
                "climb=" + climb +
                '}';
    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ClimbModel climb;

        public Builder withClimb(ClimbModel climb) {
            this.climb = climb;
            return this;
        }

        public UpdateClimbResult build() {
            return new UpdateClimbResult(climb);
        }
    }
}
