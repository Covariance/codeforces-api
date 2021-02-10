package ru.covariance.codeforcesapi.entities;

import lombok.Data;

@Data
public class ProblemStatistics {

  /**
   * Can be absent. Id of the contest, containing the problem.
   */
  private int contestId;

  /**
   * Usually a letter of a letter, followed by a digit, that represent a problem index in a
   * contest.
   */
  private String index;

  /**
   * Number of users, who solved the problem.
   */
  private int solvedCount;
}
