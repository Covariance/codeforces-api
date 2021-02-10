package ru.covariance.codeforcesapi.entities;

import lombok.Data;

@Data
public class User {

  /**
   * Codeforces user handle.
   */
  private String handle;

  /**
   * Shown only if user allowed to share his contact info.
   */
  private String email;

  /**
   * User id for VK social network. Shown only if user allowed to share his contact info.
   */
  private String vkId;

  /**
   * Shown only if user allowed to share his contact info.
   */
  private String openId;

  /**
   * Localized. Can be absent.
   */
  private String firstName;

  /**
   * Localized. Can be absent.
   */
  private String lastName;

  /**
   * Localized. Can be absent.
   */
  private String country;

  /**
   * Localized. Can be absent.
   */
  private String city;

  /**
   * Localized. Can be absent.
   */
  private String organization;

  /**
   * User contribution.
   */
  private int contribution;

  /**
   * Localized. Current user rank.
   */
  private String rank;

  /**
   * Current user rating.
   */
  private int rating;

  /**
   * Localized. Max user rank.
   */
  private String maxRank;

  /**
   * Max user rating.
   */
  private int maxRating;

  /**
   * Time, when user was last seen online, in unix format.
   */
  private int lastOnlineTimeSeconds;

  /**
   * Time, when user was registered, in unix format.
   */
  private int registrationTimeSeconds;

  /**
   * Amount of users who have this user in friends.
   */
  private int friendOfCount;

  /**
   * User's avatar URL.
   */
  private String avatar;

  /**
   * User's title photo URL.
   */
  private String titlePhoto;
}
