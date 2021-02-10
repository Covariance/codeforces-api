package ru.covariance.codeforcesapi.entities;

import lombok.Data;

@Data
public class Hack {

  /**
   * Object with three fields: "manual", "protocol" and "verdict". Field manual can have values
   * "true" and "false". If manual is "true" then test for the hack was entered manually. Fields
   * "protocol" and "verdict" contain human-readable description of judge protocol and hack
   * verdict.
   */
  @Data
  static class JudgeProtocol {

    private boolean manual;
    private String protocol;
    private String verdict;
  }

  /**
   * Hack id.
   */
  private int id;

  /**
   * Hack creation time in unix format.
   */
  private int creationTimeSeconds;

  /**
   * Party object.
   */
  private Party hacker;

  /**
   * Party object.
   */
  private Party defender;

  /**
   * Enum: HACK_SUCCESSFUL, HACK_UNSUCCESSFUL, INVALID_INPUT, GENERATOR_INCOMPILABLE,
   * GENERATOR_CRASHED, IGNORED, TESTING, OTHER. Can be absent.
   */
  private String verdict;

  /**
   * Problem object. Hacked problem.
   */
  private Problem problem;

  /**
   * Can be absent.
   */
  private String test;

  /**
   * Localized. Can be absent.
   */
  private JudgeProtocol judgeProtocol;
}
