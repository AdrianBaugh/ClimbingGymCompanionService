package com.nashss.se.ClimbingGymCompanionService.activity.results;

import com.nashss.se.ClimbingGymCompanionService.models.ClimbModel;

public class CreateClimbResult {
    private final ClimbModel climb;

    private CreateClimbResult(ClimbModel climb) {
        this.climb = climb;
    }

    public ClimbModel getClimb() {
        return climb;
    }

    @Override
    public String toString() {
        return "CreateClimbResult{" +
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

        public CreateClimbResult build() {
            return new CreateClimbResult(climb);
        }
    }
}
