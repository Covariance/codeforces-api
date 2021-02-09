package ru.covariance.codeforcesapi.entities;

import java.util.List;
import lombok.Data;

@Data
public class Problem {
  private int contestId; // Can be absent. Id of the contest, containing the problem.
  private String problemsetName; // Can be absent. Short name of the problemset the problem belongs to.
  private String index; // Usually a letter of a letter, followed by a digit, that represent a problem index in a contest.
  private String name; // Localized.
  private String type; //	Enum: PROGRAMMING, QUESTION.
  private double points; //	Floating point number. Can be absent. Maximum amount of points for the problem.
  private int rating; // Can be absent. Problem rating (difficulty).
  private List<String> tags; // Problem tags.
}
