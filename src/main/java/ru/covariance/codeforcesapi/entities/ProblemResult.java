package ru.covariance.codeforcesapi.entities;

import lombok.Data;

@Data
public class ProblemResult {

  /**
   * Points for this problem.
   */
  private double points;

  /**
   * Penalty (in ICPC meaning) of the party for this problem.
   */
  private int penalty;

  /**
   * Number of incorrect submissions.
   */
  private int rejectedAttemptCount;

  /**
   * Enum: PRELIMINARY, FINAL. If type is PRELIMINARY then points can decrease (if, for example,
   * solution will fail during system test). Otherwise, party can only increase points for this
   * problem by submitting better solutions.
   */
  private String type;

  /**
   * Number of seconds after the start of the contest before the submission, that brought maximal
   * amount of points for this problem.
   */
  private int bestSubmissionTimeSeconds;
}
