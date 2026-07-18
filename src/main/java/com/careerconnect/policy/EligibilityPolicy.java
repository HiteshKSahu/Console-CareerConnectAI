package com.careerconnect.policy;

import com.careerconnect.model.EligibilityResult;
import com.careerconnect.model.PlacementDrive;
import com.careerconnect.model.Student;

/**
 * Strategy interface for eligibility evaluation.
 * Each implementation checks one specific criterion.
 */
public interface EligibilityPolicy {

    EligibilityResult evaluate(Student student, PlacementDrive drive);
}
