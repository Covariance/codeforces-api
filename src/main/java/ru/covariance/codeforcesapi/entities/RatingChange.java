package ru.covariance.codeforcesapi.entities;

import lombok.Data;

@Data
public class RatingChange {

  /**
   * Contest id.
   */
  private int contestId;

  /**
   * Localized.
   */
  private String contestName;

  /**
   * Codeforces user handle.
   */
  private String handle;

  /**
   * Place of the user in the contest. This field contains user rank on the moment of rating update.
   * If afterwards rank changes (e.g. someone get disqualified), this field will not be update and
   * will contain old rank.
   */
  private int rank;

  /**
   * Time, when rating for the contest was update, in unix-format.
   */
  private int ratingUpdateTimeSeconds;

  /**
   * User rating before the contest.
   */
  private int oldRating;

  /**
   * User rating after the contest.
   */
  private int newRating;
}
