package ru.covariance.codeforcesapi.entities;

import java.util.List;
import lombok.Data;

@Data
public class RanklistRow {

  private Party party; //	Party object. Party that took a corresponding place in the contest.
  private int rank; // Party place in the contest.
  private double points; // Total amount of points, scored by the party.
  private int penalty; // Total penalty (in ICPC meaning) of the party.
  private int successfulHackCount;
  private int unsuccessfulHackCount;
  private List<ProblemResult> problemResults; // Party results for each problem. Order of the problems is the same as in "problems" field of the returned object.
  private int lastSubmissionTimeSeconds; // For IOI contests only. Time in seconds from the start of the contest to the last submission that added some points to the total score of the party.
}
