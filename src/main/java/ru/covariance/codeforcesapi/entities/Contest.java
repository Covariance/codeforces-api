package ru.covariance.codeforcesapi.entities;

import lombok.Data;

@Data
public class Contest {

  /**
   * Contest id.
   */
  private int id;

  /**
   * Localized.
   */
  private String name;

  /**
   * Enum: CF, IOI, ICPC. Scoring system used for the contest.
   */
  private String type;

  /**
   * Enum: BEFORE, CODING, PENDING_SYSTEM_TEST, SYSTEM_TEST, FINISHED.
   */
  private String phase;

  /**
   * If true, then the ranklist for the contest is frozen and shows only submissions, created before
   * freeze.
   */
  private boolean frozen;

  /**
   * Duration of the contest in seconds.
   */
  private int durationSeconds;

  /**
   * Can be absent. Contest start time in unix format.
   */
  private Integer startTimeSeconds;

  /**
   * Can be absent. Number of seconds, passed after the start of the contest. Can be negative.
   */
  private Integer relativeTimeSeconds;

  /**
   * Can be absent. Handle of the user, how created the contest.
   */
  private String preparedBy;

  /**
   * Can be absent. URL for contest-related website.
   */
  private String websiteUrl;

  /**
   * Localized. Can be absent.
   */
  private String description;

  /**
   * Can be absent. Integer from 1 to 5. Larger number means more difficult problems.
   */
  private Integer difficulty;

  /**
   * Localized. Can be absent. Human-readable type of the contest from the following categories:
   * Official ICPC Contest, Official School Contest, Opencup Contest, School/University/City/Region
   * Championship, Training Camp Contest, Official International Personal Contest, Training
   * Contest.
   */
  private String kind;

  /**
   * Localized. Can be absent. Name of the Region for official ICPC contests.
   */
  private String icpcRegion;

  /**
   * Localized. Can be absent.
   */
  private String country;

  /**
   * Localized. Can be absent.
   */
  private String city;

  /**
   * Can be absent.
   */
  private String season;
}
