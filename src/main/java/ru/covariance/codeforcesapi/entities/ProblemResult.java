package ru.covariance.codeforcesapi.entities;

import lombok.Data;

@Data
public class ProblemResult {

  private double points;
  private int penalty; // Penalty (in ICPC meaning) of the party for this problem.
  private int rejectedAttemptCount; // Number of incorrect submissions.
  private String type; // Enum: PRELIMINARY, FINAL. If type is PRELIMINARY then points can decrease (if, for example, solution will fail during system test). Otherwise, party can only increase points for this problem by submitting better solutions.
  private int bestSubmissionTimeSeconds; // Number of seconds after the start of the contest before the submission, that brought maximal amount of points for this problem.
}
