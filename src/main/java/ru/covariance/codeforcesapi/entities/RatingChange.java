package ru.covariance.codeforcesapi.entities;

import lombok.Data;

@Data
public class RatingChange {

  private int contestId;
  private String contestName; // Localized.
  private String handle; // Codeforces user handle.
  private int rank; // Place of the user in the contest. This field contains user rank on the moment of rating update. If afterwards rank changes (e.g. someone get disqualified), this field will not be update and will contain old rank.
  private int ratingUpdateTimeSeconds; // Time, when rating for the contest was update, in unix-format.
  private int oldRating; // User rating before the contest.
  private int newRating; // User rating after the contest.
}
