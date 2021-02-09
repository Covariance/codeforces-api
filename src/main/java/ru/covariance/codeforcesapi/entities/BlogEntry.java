package ru.covariance.codeforcesapi.entities;

import java.util.List;
import lombok.Data;

@Data
public class BlogEntry {

  private int id;
  private String originalLocale; // Original locale of the blog entry.
  private int creationTimeSeconds; // Time, when blog entry was created, in unix format.
  private String authorHandle; // Author user handle.
  private String title; // Localized.
  private String content; // Localized. Not included in short version.
  private String locale;
  private int modificationTimeSeconds; // Time, when blog entry has been updated, in unix format.
  private boolean allowViewHistory; // If true, you can view any specific revision of the blog entry.
  private List<String> tags;
  private int rating;
}
