package com.careerconnect.dto.request;

import jakarta.validation.constraints.NotBlank;

public class ChatRequest {

    private String studentId;   // optional, needed for eligibility/profile queries
    private String driveId;     // optional, needed for eligibility/preparation queries

    @NotBlank(message = "Message is required")
    private String message;

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getDriveId() { return driveId; }
    public void setDriveId(String driveId) { this.driveId = driveId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
