package com.careerconnect.controller;

import com.careerconnect.dto.request.SubmitApplicationRequest;
import com.careerconnect.dto.request.UpdateStatusRequest;
import com.careerconnect.dto.response.ApplicationResponse;
import com.careerconnect.model.Application;
import com.careerconnect.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // Submit an application for a drive
    @PostMapping("/api/drives/{driveId}/applications")
    public ResponseEntity<ApplicationResponse> submitApplication(
            @PathVariable String driveId,
            @Valid @RequestBody SubmitApplicationRequest request) {

        Application application = applicationService.submitApplication(driveId, request.getStudentId());
        return new ResponseEntity<>(toResponse(application), HttpStatus.CREATED);
    }

    // Get all applications for a specific student
    @GetMapping("/api/students/{studentId}/applications")
    public ResponseEntity<List<ApplicationResponse>> getStudentApplications(@PathVariable String studentId) {
        List<ApplicationResponse> list = applicationService.getApplicationsByStudent(studentId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    // Get a specific application by ID
    @GetMapping("/api/applications/{applicationId}")
    public ResponseEntity<ApplicationResponse> getApplication(@PathVariable String applicationId) {
        Application application = applicationService.getApplicationById(applicationId);
        return ResponseEntity.ok(toResponse(application));
    }

    // Update application status (Placement coordinator action)
    @PatchMapping("/api/applications/{applicationId}/status")
    public ResponseEntity<ApplicationResponse> updateStatus(
            @PathVariable String applicationId,
            @Valid @RequestBody UpdateStatusRequest request) {

        Application application = applicationService.updateApplicationStatus(applicationId, request.getStatus());
        return ResponseEntity.ok(toResponse(application));
    }

    private ApplicationResponse toResponse(Application app) {
        return new ApplicationResponse(
                app.getId(),
                app.getStudentId(),
                app.getDriveId(),
                app.getStatus().name(),
                app.getSubmittedAt(),
                app.getUpdatedAt()
        );
    }
}
