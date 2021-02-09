package ru.covariance.codeforcesapi.entities;

import java.util.List;
import lombok.Data;

@Data
public class Party {

  private int contestId; // Can be absent. Id of the contest, in which party is participating.
  private List<Member> members; //	List of Member objects. Members of the party.
  private String participantType; //	Enum: CONTESTANT, PRACTICE, VIRTUAL, MANAGER, OUT_OF_COMPETITION.
  private int teamId; // Can be absent. If party is a team, then it is a unique team id. Otherwise, this field is absent.
  private String teamName; // Localized. Can be absent. If party is a team or ghost, then it is a localized name of the team. Otherwise, it is absent.
  private boolean ghost; // If true then this party is a ghost. It participated in the contest, but not on Codeforces. For example, Andrew Stankevich Contests in Gym has ghosts of the participants from Petrozavodsk Training Camp.
  private int room; // Can be absent. Room of the party. If absent, then the party has no room.
  private int startTimeSeconds; // Can be absent. Time, when this party started a contest.
}
