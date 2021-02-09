package ru.covariance.codeforcesapi.entities;

import lombok.Data;

@Data
public class Submission {

  private int id;
  private int contestId; // Can be absent.
  private int creationTimeSeconds; // Time, when submission was created, in unix-format.
  private int relativeTimeSeconds; // Number of seconds, passed after the start of the contest (or a virtual start for virtual parties), before the submission.
  private Problem problem;  // Problem object.
  private Party author; // Party object.
  private String programmingLanguage;
  private String verdict; // Enum: FAILED, OK, PARTIAL, COMPILATION_ERROR, RUNTIME_ERROR, WRONG_ANSWER, PRESENTATION_ERROR, TIME_LIMIT_EXCEEDED, MEMORY_LIMIT_EXCEEDED, IDLENESS_LIMIT_EXCEEDED, SECURITY_VIOLATED, CRASHED, INPUT_PREPARATION_CRASHED, CHALLENGED, SKIPPED, TESTING, REJECTED. Can be absent.
  private String testset; // Enum: SAMPLES, PRETESTS, TESTS, CHALLENGES, TESTS1, ..., TESTS10. Testset used for judging the submission.
  private int passedTestCount; // Number of passed tests.
  private int timeConsumedMillis; // Maximum time in milliseconds, consumed by solution for one test.
  private int memoryConsumedBytes; // Maximum memory in bytes, consumed by solution for one test.
  private double points; // Can be absent. Number of scored points for IOI-like contests.
}
