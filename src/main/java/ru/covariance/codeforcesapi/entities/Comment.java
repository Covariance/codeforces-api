package ru.covariance.codeforcesapi.entities;

import lombok.Data;

@Data
public class Comment {

  /**
   * Comment id.
   */
  private int id;

  /**
   * Time, when comment was created, in unix format.
   */
  private int creationTimeSeconds;

  /**
   * Handle of comment author.
   */
  private String commentatorHandle;

  /**
   * Comment locale.
   */
  private String locale;

  /**
   * Comment's content.
   */
  private String text;

  /**
   * Parent comment id. Can be absent.
   */
  private Integer parentCommentId;

  /**
   * Rating of comment.
   */
  private int rating;
}
