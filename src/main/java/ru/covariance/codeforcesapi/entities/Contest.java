package ru.covariance.codeforcesapi.entities;

import lombok.Data;

@Data
public class Contest {

  private int id;
  private String name; // Localized.
  private String type; //	Enum: CF, IOI, ICPC. Scoring system used for the contest.
  private String phase; //	Enum: BEFORE, CODING, PENDING_SYSTEM_TEST, SYSTEM_TEST, FINISHED.
  private boolean frozen; // If true, then the ranklist for the contest is frozen and shows only submissions, created before freeze.
  private int durationSeconds; // Duration of the contest in seconds.
  private int startTimeSeconds; // Can be absent. Contest start time in unix format.
  private int relativeTimeSeconds; // Can be absent. Number of seconds, passed after the start of the contest. Can be negative.
  private String preparedBy; // Can be absent. Handle of the user, how created the contest.
  private String websiteUrl; // Can be absent. URL for contest-related website.
  private String description; // Localized. Can be absent.
  private int difficulty; // Can be absent. From 1 to 5. Larger number means more difficult problems.
  private String kind; // Localized. Can be absent. Human-readable type of the contest from the following categories: Official ICPC Contest, Official School Contest, Opencup Contest, School/University/City/Region Championship, Training Camp Contest, Official International Personal Contest, Training Contest.
  private String icpcRegion; // Localized. Can be absent. Name of the Region for official ICPC contests.
  private String country; // Localized. Can be absent.
  private String city; // Localized. Can be absent.
  private String season; // Can be absent.
}
