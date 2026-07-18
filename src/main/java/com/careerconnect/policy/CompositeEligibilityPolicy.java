package com.careerconnect.policy;

import com.careerconnect.model.EligibilityResult;
import com.careerconnect.model.PlacementDrive;
import com.careerconnect.model.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Runs all individual eligibility policies and combines their results.
 * If any single policy fails, the student is ineligible — but we still
 * collect ALL reasons so the student knows everything they need to fix.
 */
@Component
public class CompositeEligibilityPolicy implements EligibilityPolicy {

    private final List<EligibilityPolicy> policies;

    public CompositeEligibilityPolicy() {
        // wire up the individual policy checks
        this.policies = List.of(
                new CgpaPolicy(),
                new BacklogPolicy(),
                new SkillPolicy(),
                new GraduationYearPolicy()
        );
    }

    @Override
    public EligibilityResult evaluate(Student student, PlacementDrive drive) {
        boolean allPassed = true;
        List<String> allReasons = new ArrayList<>();

        for (EligibilityPolicy policy : policies) {
            EligibilityResult result = policy.evaluate(student, drive);
            if (!result.isEligible()) {
                allPassed = false;
            }
            allReasons.addAll(result.getReasons());
        }

        return new EligibilityResult(allPassed, allReasons);
    }
}
