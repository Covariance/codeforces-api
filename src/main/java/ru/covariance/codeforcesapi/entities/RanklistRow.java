package ru.covariance.codeforcesapi.entities;

import java.util.List;
import lombok.Data;

@Data
public class RanklistRow {

  /**
   * Party object. Party that took a corresponding place in the contest.
   */
  private Party party;

  /**
   * Party place in the contest.
   */
  private int rank;

  /**
   * Total amount of points, scored by the party.
   */
  private double points;

  /**
   * Total penalty (in ICPC meaning) of the party.
   */
  private int penalty;

  /**
   * Count of successful hacks.
   */
  private int successfulHackCount;

  /**
   * Count of unsuccessful hacks.
   */
  private int unsuccessfulHackCount;

  /**
   * Party results for each problem. Order of the problems is the same as in "problems" field of the
   * returned object.
   */
  private List<ProblemResult> problemResults;

  /**
   * For IOI contests only. Time in seconds from the start of the contest to the last submission
   * that added some points to the total score of the party.
   */
  private int lastSubmissionTimeSeconds;
}
