package ru.covariance.codeforcesapi.entities;

import lombok.Data;

@Data
public class Comment {

  
  private int id;
  private int creationTimeSeconds; // Time, when comment was created, in unix format.
  private String commentatorHandle;
  private String locale;
  private String text;
  private Integer parentCommentId; // Can be absent.
  private int rating;
}
