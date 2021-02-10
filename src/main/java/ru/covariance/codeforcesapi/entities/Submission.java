package ru.covariance.codeforcesapi.entities;

import lombok.Data;

@Data
public class Submission {

  /**
   * Submission id.
   */
  private int id;

  /**
   * Can be absent.
   */
  private Integer contestId;

  /**
   * Time, when submission was created, in unix-format.
   */
  private int creationTimeSeconds;

  /**
   * Number of seconds, passed after the start of the contest (or a virtual start for virtual
   * parties), before the submission.
   */
  private int relativeTimeSeconds;

  /**
   * Corresponding problem object.
   */
  private Problem problem;

  /**
   * Author of the submission.
   */
  private Party author;

  /**
   * Programming language of the submission.
   */
  private String programmingLanguage;

  /**
   * Enum: FAILED, OK, PARTIAL, COMPILATION_ERROR, RUNTIME_ERROR, WRONG_ANSWER, PRESENTATION_ERROR,
   * TIME_LIMIT_EXCEEDED, MEMORY_LIMIT_EXCEEDED, IDLENESS_LIMIT_EXCEEDED, SECURITY_VIOLATED,
   * CRASHED, INPUT_PREPARATION_CRASHED, CHALLENGED, SKIPPED, TESTING, REJECTED. Can be absent.
   */
  private String verdict;

  /**
   * Enum: SAMPLES, PRETESTS, TESTS, CHALLENGES, TESTS1, ..., TESTS10. Testset used for judging the
   * submission.
   */
  private String testset;

  /**
   * Number of passed tests.
   */
  private int passedTestCount;

  /**
   * Maximum time in milliseconds, consumed by solution for one test.
   */
  private int timeConsumedMillis;

  /**
   * Maximum memory in bytes, consumed by solution for one test.
   */
  private int memoryConsumedBytes;

  /**
   * Can be absent. Number of scored points for IOI-like contests.
   */
  private Double points;
}
