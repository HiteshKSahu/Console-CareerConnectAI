package com.careerconnect.model;

import java.util.Map;
import java.util.Set;

/**
 * Tracks an application through the placement pipeline.
 * Each status has a fixed set of valid next states.
 */
public enum ApplicationStatus {

    SUBMITTED,
    UNDER_REVIEW,
    SHORTLISTED,
    SELECTED,
    REJECTED,
    WITHDRAWN;

    // defines which transitions are allowed from each status
    private static final Map<ApplicationStatus, Set<ApplicationStatus>> VALID_TRANSITIONS = Map.of(
            SUBMITTED, Set.of(UNDER_REVIEW, WITHDRAWN),
            UNDER_REVIEW, Set.of(SHORTLISTED, REJECTED, WITHDRAWN),
            SHORTLISTED, Set.of(SELECTED, REJECTED),
            SELECTED, Set.of(),
            REJECTED, Set.of(),
            WITHDRAWN, Set.of()
    );

    public boolean canTransitionTo(ApplicationStatus next) {
        return VALID_TRANSITIONS.getOrDefault(this, Set.of()).contains(next);
    }
}
