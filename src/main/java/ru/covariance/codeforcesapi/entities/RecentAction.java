package ru.covariance.codeforcesapi.entities;

import lombok.Data;

@Data
public class RecentAction {

  private int timeSeconds; // Action time, in unix format.
  private BlogEntry blogEntry; //	BlogEntry object in short form. Can be absent.
  private Comment comment; //	Comment object. Can be absent.
}
