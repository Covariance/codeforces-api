package ru.covariance.codeforcesapi.entities;

import lombok.Data;

@Data
public class RecentAction {

  /**
   * Action time, in unix format.
   */
  private int timeSeconds;

  /**
   * BlogEntry object in short form. Can be absent.
   */
  private BlogEntry blogEntry;

  /**
   * Comment object. Can be absent.
   */
  private Comment comment;
}
