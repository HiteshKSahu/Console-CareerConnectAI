package com.careerconnect.console;

import com.careerconnect.exception.*;
import com.careerconnect.model.*;
import com.careerconnect.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Interactive Console CLI Interface for Console CareerConnect AI.
 * Runs interactively directly inside the command line terminal window.
 */
@Component
public class ConsoleMenuRunner implements CommandLineRunner {

    private final StudentService studentService;
    private final CompanyService companyService;
    private final DriveService driveService;
    private final ApplicationService applicationService;
    private final CareerAssistantService careerAssistantService;

    public ConsoleMenuRunner(StudentService studentService,
                             CompanyService companyService,
                             DriveService driveService,
                             ApplicationService applicationService,
                             CareerAssistantService careerAssistantService) {
        this.studentService = studentService;
        this.companyService = companyService;
        this.driveService = driveService;
        this.applicationService = applicationService;
        this.careerAssistantService = careerAssistantService;
    }

    @Override
    public void run(String... args) {
        startInteractiveConsole();
    }

    public void startInteractiveConsole() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("\n=======================================================");
        System.out.println("     WELCOME TO CONSOLE CAREERCONNECT AI PLATFORM     ");
        System.out.println("=======================================================");

        while (running) {
            printMenu();
            System.out.print("Select an option (0-9): ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> handleCreateStudent(scanner);
                case "2" -> handleViewStudents();
                case "3" -> handleCreateCompany(scanner);
                case "4" -> handleCreateDrive(scanner);
                case "5" -> handleEvaluateEligibility(scanner);
                case "6" -> handleApplyToDrive(scanner);
                case "7" -> handleUpdateStatus(scanner);
                case "8" -> handleAskAiAssistant(scanner);
                case "9" -> handleRunAutoDemo();
                case "0" -> {
                    System.out.println("\nExiting Console CareerConnect AI. Goodbye!");
                    running = false;
                    System.exit(0);
                }
                default -> System.out.println("\n[!] Invalid option. Please enter a number from 0 to 9.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n================ MAIN MENU ================");
        System.out.println(" 1. [Student]  Create / Register Profile");
        System.out.println(" 2. [Student]  List All Students");
        System.out.println(" 3. [Admin]    Create / Register Company");
        System.out.println(" 4. [Admin]    Publish Placement Drive");
        System.out.println(" 5. [Student]  Check Eligibility for Drive");
        System.out.println(" 6. [Student]  Submit Application to Drive");
        System.out.println(" 7. [Admin]    Update Application Status");
        System.out.println(" 8. [AI Chat]  Ask AI Career Assistant (Ollama)");
        System.out.println(" 9. [Demo]     Run Pre-loaded 7-Step Auto-Demo");
        System.out.println(" 0. Exit System");
        System.out.println("===========================================");
    }

    private void handleCreateStudent(Scanner scanner) {
        System.out.println("\n--- 1. Register Student Profile ---");
        try {
            System.out.print("Full Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Email Address: ");
            String email = scanner.nextLine().trim();

            System.out.print("Programme (e.g. B.Tech Computer Science): ");
            String programme = scanner.nextLine().trim();

            System.out.print("Graduation Year (e.g. 2026): ");
            int year = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("CGPA (0.0 to 10.0): ");
            double cgpa = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Active Backlogs Count: ");
            int backlogs = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Key Skills (comma separated, e.g. Java, Spring Boot, SQL): ");
            String skillsStr = scanner.nextLine().trim();
            List<String> skills = Arrays.stream(skillsStr.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

            Student student = studentService.createStudent(name, email, programme, year, cgpa, backlogs, skills);
            System.out.println("\n[SUCCESS] Student Profile Created Successfully!");
            System.out.println(" Generated Student ID: " + student.getId());
        } catch (Exception e) {
            System.out.println("\n[ERROR] Could not create student: " + e.getMessage());
        }
    }

    private void handleViewStudents() {
        System.out.println("\n--- 2. Registered Students List ---");
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No student profiles registered yet.");
            return;
        }

        for (Student s : students) {
            System.out.printf("ID: %-8s | Name: %-16s | CGPA: %.2f | Backlogs: %d | Skills: %s\n",
                    s.getId(), s.getName(), s.getCgpa(), s.getActiveBacklogs(), String.join(", ", s.getSkills()));
        }
    }

    private void handleCreateCompany(Scanner scanner) {
        System.out.println("\n--- 3. Register Company ---");
        try {
            System.out.print("Company Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Sector (e.g. IT, Finance, Consulting): ");
            String sector = scanner.nextLine().trim();

            System.out.print("Brief Description: ");
            String desc = scanner.nextLine().trim();

            Company company = companyService.createCompany(name, sector, desc);
            System.out.println("\n[SUCCESS] Company Registered Successfully!");
            System.out.println(" Generated Company ID: " + company.getId());
        } catch (Exception e) {
            System.out.println("\n[ERROR] " + e.getMessage());
        }
    }

    private void handleCreateDrive(Scanner scanner) {
        System.out.println("\n--- 4. Publish Placement Drive ---");
        try {
            System.out.print("Company ID (e.g. CMP-001): ");
            String companyId = scanner.nextLine().trim();

            System.out.print("Job Role (e.g. Software Engineer): ");
            String role = scanner.nextLine().trim();

            System.out.print("Job Location (e.g. Bengaluru): ");
            String location = scanner.nextLine().trim();

            System.out.print("Package (LPA): ");
            double pkg = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Min Cutoff CGPA: ");
            double minCgpa = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Max Backlogs Allowed: ");
            int maxBacklogs = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Target Graduation Year: ");
            int year = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Required Skills (comma separated): ");
            List<String> skills = Arrays.stream(scanner.nextLine().split(","))
                    .map(String::trim).filter(s -> !s.isEmpty()).toList();

            LocalDate deadline = LocalDate.now().plusMonths(3);

            PlacementDrive drive = driveService.createDrive(
                    companyId, role, location, pkg, deadline, skills, minCgpa, maxBacklogs, List.of("B.Tech CS"), year
            );
            System.out.println("\n[SUCCESS] Drive Published Successfully!");
            System.out.println(" Generated Drive ID: " + drive.getId());
        } catch (Exception e) {
            System.out.println("\n[ERROR] " + e.getMessage());
        }
    }

    private void handleEvaluateEligibility(Scanner scanner) {
        System.out.println("\n--- 5. Evaluate Eligibility ---");
        System.out.print("Enter Drive ID (e.g. DRV-100): ");
        String driveId = scanner.nextLine().trim();

        System.out.print("Enter Student ID (e.g. STU-001): ");
        String studentId = scanner.nextLine().trim();

        try {
            EligibilityResult result = driveService.evaluateEligibility(driveId, studentId);
            System.out.println("\n================= ELIGIBILITY EVALUATION REPORT =================");
            System.out.println(" Eligibility Status: " + (result.isEligible() ? "PASS (Eligible)" : "FAIL (Ineligible)"));
            System.out.println(" Reasons / Criteria Breakdown:");
            for (String reason : result.getReasons()) {
                System.out.println("   * " + reason);
            }
            System.out.println("=================================================================");
        } catch (Exception e) {
            System.out.println("\n[ERROR] " + e.getMessage());
        }
    }

    private void handleApplyToDrive(Scanner scanner) {
        System.out.println("\n--- 6. Submit Application ---");
        System.out.print("Drive ID: ");
        String driveId = scanner.nextLine().trim();

        System.out.print("Student ID: ");
        String studentId = scanner.nextLine().trim();

        try {
            Application app = applicationService.submitApplication(driveId, studentId);
            System.out.println("\n[SUCCESS] Application Submitted Successfully!");
            System.out.println(" Application ID: " + app.getId());
            System.out.println(" Current Status: " + app.getStatus());
        } catch (Exception e) {
            System.out.println("\n[ERROR] Application Failed: " + e.getMessage());
        }
    }

    private void handleUpdateStatus(Scanner scanner) {
        System.out.println("\n--- 7. Update Application Status ---");
        System.out.print("Application ID (e.g. APP-500): ");
        String appId = scanner.nextLine().trim();

        System.out.print("New Status (SUBMITTED, UNDER_REVIEW, SHORTLISTED, SELECTED, REJECTED, WITHDRAWN): ");
        String statusStr = scanner.nextLine().trim();

        try {
            Application app = applicationService.updateApplicationStatus(appId, statusStr);
            System.out.println("\n[SUCCESS] Application Status Updated!");
            System.out.println(" Application ID: " + app.getId());
            System.out.println(" New Status: " + app.getStatus());
        } catch (Exception e) {
            System.out.println("\n[ERROR] Status Update Rejected: " + e.getMessage());
        }
    }

    private void handleAskAiAssistant(Scanner scanner) {
        System.out.println("\n--- 8. Ask AI Career Assistant (Ollama) ---");
        System.out.print("Student ID (Press Enter if asking general FAQ): ");
        String studentId = scanner.nextLine().trim();

        System.out.print("Drive ID (Press Enter if asking general FAQ): ");
        String driveId = scanner.nextLine().trim();

        System.out.print("Ask your question: ");
        String question = scanner.nextLine().trim();

        System.out.println("\nConnecting to local Ollama LLM model...");
        try {
            String answer = careerAssistantService.handleChat(studentId, driveId, question);
            System.out.println("\n==================== AI ASSISTANT RESPONSE ====================");
            System.out.println(answer);
            System.out.println("\n[Note: This advice is Advisory only. Placement decisions are deterministic]");
            System.out.println("===============================================================");
        } catch (Exception e) {
            System.out.println("\n[ERROR] Ollama Assistant Error: " + e.getMessage());
        }
    }

    private void handleRunAutoDemo() {
        System.out.println("\n=======================================================");
        System.out.println("   AUTOMATED 7-STEP CAPSTONE DEMO SCENARIO EXECUTING    ");
        System.out.println("=======================================================");

        try {
            // 1. Create Student
            System.out.println("\n[Step 1/7] Creating Student Profile...");
            Student student = studentService.createStudent(
                    "Rahul Verma", "rahul.demo@example.com", "B.Tech Computer Science", 2026, 8.5, 0, List.of("Java", "Spring Boot", "SQL")
            );
            System.out.println(" -> Registered Student: " + student.getId() + " (" + student.getName() + ")");

            // 2. Create Company
            System.out.println("\n[Step 2/7] Registering Company...");
            Company company = companyService.createCompany("Acme Systems", "Information Technology", "Cloud & Microservices");
            System.out.println(" -> Registered Company: " + company.getId() + " (" + company.getName() + ")");

            // 3. Create Drive
            System.out.println("\n[Step 3/7] Publishing Placement Drive...");
            PlacementDrive drive = driveService.createDrive(
                    company.getId(), "Backend Engineer", "Bengaluru", 14.5,
                    LocalDate.now().plusMonths(3), List.of("Java", "Spring Boot"),
                    7.5, 0, List.of("B.Tech Computer Science"), 2026
            );
            System.out.println(" -> Published Placement Drive: " + drive.getId() + " [Role: " + drive.getRole() + "]");

            // 4. Check Eligibility
            System.out.println("\n[Step 4/7] Evaluating Deterministic Eligibility Strategy...");
            EligibilityResult eligibility = driveService.evaluateEligibility(drive.getId(), student.getId());
            System.out.println(" -> Evaluation Outcome: " + (eligibility.isEligible() ? "ELIGIBLE" : "INELIGIBLE"));
            eligibility.getReasons().forEach(r -> System.out.println("    * " + r));

            // 5. Submit Application
            System.out.println("\n[Step 5/7] Submitting Application...");
            Application app = applicationService.submitApplication(drive.getId(), student.getId());
            System.out.println(" -> Application Created: " + app.getId() + " | Status: " + app.getStatus());

            // 6. Test Duplicate Guard
            System.out.println("\n[Step 6/7] Testing Duplicate Application Guard...");
            try {
                applicationService.submitApplication(drive.getId(), student.getId());
            } catch (DuplicateResourceException e) {
                System.out.println(" -> [PASSED] Duplicate Application Guard Triggered: " + e.getMessage());
            }

            // 7. Status Lifecycle Transition
            System.out.println("\n[Step 7/7] Executing Status Lifecycle Transition to SHORTLISTED...");
            applicationService.updateApplicationStatus(app.getId(), "UNDER_REVIEW");
            Application finalApp = applicationService.updateApplicationStatus(app.getId(), "SHORTLISTED");
            System.out.println(" -> Transition Success! Application ID " + finalApp.getId() + " is now " + finalApp.getStatus());

            System.out.println("\n=======================================================");
            System.out.println("     [COMPLETE] ALL 7 DEMO STEPS EXECUTED CLEANLY!     ");
            System.out.println("=======================================================");

        } catch (Exception e) {
            System.out.println("[DEMO ERROR] " + e.getMessage());
        }
    }
}
