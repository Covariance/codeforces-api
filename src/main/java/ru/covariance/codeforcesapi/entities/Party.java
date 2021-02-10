package ru.covariance.codeforcesapi.entities;

import java.util.List;
import lombok.Data;

@Data
public class Party {

  /**
   * Can be absent. Id of the contest, in which party is participating.
   */
  private Integer contestId;

  /**
   * List of Member objects. Members of the party.
   */
  private List<Member> members;

  /**
   * Enum: CONTESTANT, PRACTICE, VIRTUAL, MANAGER, OUT_OF_COMPETITION.
   */
  private String participantType;

  /**
   * Can be absent. If party is a team, then it is a unique team id. Otherwise, this field is
   * absent.
   */
  private Integer teamId;

  /**
   * Localized. Can be absent. If party is a team or ghost, then it is a localized name of the team.
   * Otherwise, it is absent.
   */
  private String teamName;

  /**
   * If true then this party is a ghost. It participated in the contest, but not on Codeforces. For
   * example, Andrew Stankevich Contests in Gym has ghosts of the participants from Petrozavodsk
   * Training Camp.
   */
  private boolean ghost;

  /**
   * Can be absent. Room of the party. If absent, then the party has no room.
   */
  private Integer room;

  /**
   * Can be absent. Time, when this party started a contest.
   */
  private Integer startTimeSeconds;
}
