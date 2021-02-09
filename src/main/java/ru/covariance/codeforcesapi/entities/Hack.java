package ru.covariance.codeforcesapi.entities;

import lombok.Data;

@Data
public class Hack {

  /**
   * Object with three fields: "manual", "protocol" and "verdict".
   * Field manual can have values "true" and "false".
   * If manual is "true" then test for the hack was entered manually.
   * Fields "protocol" and "verdict" contain human-readable
   * description of judge protocol and hack verdict.
   */
  @Data
  static class JudgeProtocol {
    private boolean manual;
    private String protocol;
    private String verdict;
  }

  private int id;
  private int creationTimeSeconds; // Hack creation time in unix format.
  private Party hacker; // Party object.
  private Party defender; // Party object.
  private String verdict; // Enum: HACK_SUCCESSFUL, HACK_UNSUCCESSFUL, INVALID_INPUT, GENERATOR_INCOMPILABLE, GENERATOR_CRASHED, IGNORED, TESTING, OTHER. Can be absent.
  private Problem problem; // Problem object. Hacked problem.
  private String test; // Can be absent.
  private JudgeProtocol judgeProtocol; // Localized. Can be absent.
}
