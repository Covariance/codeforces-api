package ru.covariance.codeforcesapi.entities;

import lombok.Data;

@Data
public class User {

  private String handle; // Codeforces user handle.
  private String email; // Shown only if user allowed to share his contact info.
  private String vkId;  // User id for VK social network. Shown only if user allowed to share his contact info.
  private String openId; // Shown only if user allowed to share his contact info.
  private String firstName;  // Localized. Can be absent.
  private String lastName;  // Localized. Can be absent.
  private String country;  // Localized. Can be absent.
  private String city;  // Localized. Can be absent.
  private String organization;  // Localized. Can be absent.
  private int contribution;  // User contribution.
  private String rank;  // Localized. Current user rank.
  private int rating;  // Current user rating.
  private String maxRank;  // Localized. Max user rank.
  private int maxRating; // Max user rating.
  private int lastOnlineTimeSeconds; // Time, when user was last seen online, in unix format.
  private int registrationTimeSeconds; // Time, when user was registered, in unix format.
  private int friendOfCount; // Amount of users who have this user in friends.
  private String avatar; // User's avatar URL.
  private String titlePhoto;  // User's title photo URL.
}
