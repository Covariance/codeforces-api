package ru.covariance.codeforcesapi.entities;

import java.util.List;
import lombok.Data;

@Data
public class Problemset {
  private List<Problem> problems;
  private List<ProblemStatistics> problemStatistics;
}
