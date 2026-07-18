package com.careerconnect.model;

import java.time.LocalDateTime;

/**
 * Represents a student's application to a placement drive.
 * Status transitions are validated inside this class itself.
 */
public class Application {

    private String id;
    private String studentId;
    private String driveId;
    private ApplicationStatus status;
    private LocalDateTime submittedAt;
    private LocalDateTime updatedAt;

    public Application(String id, String studentId, String driveId) {
        this.id = id;
        this.studentId = studentId;
        this.driveId = driveId;
        this.status = ApplicationStatus.SUBMITTED;
        this.submittedAt = LocalDateTime.now();
        this.updatedAt = this.submittedAt;
    }

    public String getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getDriveId() {
        return driveId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Attempts to move the application to a new status.
     * Returns true if the transition was valid and applied, false otherwise.
     */
    public boolean transitionTo(ApplicationStatus newStatus) {
        if (this.status.canTransitionTo(newStatus)) {
            this.status = newStatus;
            this.updatedAt = LocalDateTime.now();
            return true;
        }
        return false;
    }
}
