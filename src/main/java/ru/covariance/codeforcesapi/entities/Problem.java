package ru.covariance.codeforcesapi.entities;

import java.util.List;
import lombok.Data;

@Data
public class Problem {

  /**
   * Can be absent. Id of the contest, containing the problem.
   */
  private int contestId;

  /**
   * Can be absent. Short name of the problemset the problem belongs to.
   */
  private String problemsetName;

  /**
   * Usually a letter followed by a digit that represent a problem index in a contest.
   */
  private String index;

  /**
   * Localized.
   */
  private String name;

  /**
   * Enum: PROGRAMMING, QUESTION.
   */
  private String type;

  /**
   * Floating point number. Can be absent. Maximum amount of points for the problem.
   */
  private double points;

  /**
   * Can be absent. Problem rating (difficulty).
   */
  private int rating;

  /**
   * Problem tags.
   */
  private List<String> tags;
}
