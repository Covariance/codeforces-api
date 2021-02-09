package ru.covariance.codeforcesapi.entities;

import lombok.Data;

@Data
public class ProblemStatistics {

  private int contestId; // Can be absent. Id of the contest, containing the problem.
  private String index; // Usually a letter of a letter, followed by a digit, that represent a problem index in a contest.
  private int solvedCount; // Number of users, who solved the problem.
}
