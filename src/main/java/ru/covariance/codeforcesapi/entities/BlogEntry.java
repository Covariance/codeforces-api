package ru.covariance.codeforcesapi.entities;

import java.util.List;
import lombok.Data;

@Data
public class BlogEntry {

  /**
   * Blog entry id.
   */
  private int id;

  /**
   * Original locale of the blog entry.
   */
  private String originalLocale;

  /**
   * Time, when blog entry was created, in unix format.
   */
  private int creationTimeSeconds;

  /**
   * Author user handle.
   */
  private String authorHandle;

  /**
   * Localized.
   */
  private String title;

  /**
   * Localized. Not included in short version.
   */
  private String content;

  /**
   * Locale of a blog entry.
   */
  private String locale;

  /**
   * Time, when blog entry has been updated, in unix format.
   */
  private int modificationTimeSeconds;

  /**
   * If true, you can view any specific revision of the blog entry.
   */
  private boolean allowViewHistory;

  /**
   * List of tags corresponding to blog entry.
   */
  private List<String> tags;

  /**
   * Rating of the blog entry.
   */
  private int rating;
}
