package ru.covariance.codeforcesapi.entities;

import java.util.List;
import lombok.Data;

@Data
public class ContestStandings {

  private Contest contest;
  private List<Problem> problems;
  private List<RanklistRow> rows;
}
