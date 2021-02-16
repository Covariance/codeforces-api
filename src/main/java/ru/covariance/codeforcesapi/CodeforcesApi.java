package ru.covariance.codeforcesapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import ru.covariance.codeforcesapi.entities.BlogEntry;
import ru.covariance.codeforcesapi.entities.Comment;
import ru.covariance.codeforcesapi.entities.Contest;
import ru.covariance.codeforcesapi.entities.ContestStandings;
import ru.covariance.codeforcesapi.entities.Hack;
import ru.covariance.codeforcesapi.entities.Problemset;
import ru.covariance.codeforcesapi.entities.RatingChange;
import ru.covariance.codeforcesapi.entities.RecentAction;
import ru.covariance.codeforcesapi.entities.Submission;
import ru.covariance.codeforcesapi.entities.User;

public class CodeforcesApi {

  private final String key;
  private final String secret;
  private final String lang;

  private static final String CODEFORCES_API = "https://codeforces.com/api/";

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final Random RANDOM = new Random();

  private static final ObjectReader BLOG_ENTRY_LIST_READER =
      OBJECT_MAPPER.readerFor(new TypeReference<List<BlogEntry>>() {
      });
  private static final ObjectReader COMMENT_LIST_READER =
      OBJECT_MAPPER.readerFor(new TypeReference<List<Comment>>() {
      });
  private static final ObjectReader CONTEST_LIST_READER =
      OBJECT_MAPPER.readerFor(new TypeReference<List<Contest>>() {
      });
  private static final ObjectReader HACK_LIST_READER =
      OBJECT_MAPPER.readerFor(new TypeReference<List<Hack>>() {
      });
  private static final ObjectReader RATING_CHANGE_LIST_READER =
      OBJECT_MAPPER.readerFor(new TypeReference<List<RatingChange>>() {
      });
  private static final ObjectReader RECENT_ACTION_LIST_READER =
      OBJECT_MAPPER.readerFor(new TypeReference<List<RecentAction>>() {
      });
  private static final ObjectReader STRING_LIST_READER =
      OBJECT_MAPPER.readerFor(new TypeReference<List<String>>() {
      });
  private static final ObjectReader SUBMISSION_LIST_READER =
      OBJECT_MAPPER.readerFor(new TypeReference<List<Submission>>() {
      });
  private static final ObjectReader USER_LIST_READER =
      OBJECT_MAPPER.readerFor(new TypeReference<List<User>>() {
      });

  public static final int PROBLEMSET_RECENT_SUBMISSIONS_LIMIT = 1000;
  public static final int RECENT_ACTIONS_LIMIT = 100;
  public static final int STANDINGS_HANDLES_LIMIT = 10000;
  public static final int USER_INFO_LIMIT = 10000;

  /**
   * Creates connector to the Codeforces API with specified key, secret and language settings.
   *
   * @param key    key for usage of authorized methods. Con be omitted by passing null.
   * @param secret secret for usage of authorized methods. Con be omitted by passing null.
   * @param lang   language option to be used as preferable. Can be either "en" or "ru".
   */
  public CodeforcesApi(final String key, final String secret, final String lang) {
    if ((key != null && secret == null) || (key == null && secret != null)) {
      this.key = null;
      this.secret = null;
    } else {
      this.key = key;
      this.secret = secret;
    }
    if (lang != null && !lang.equals("en") && !lang.equals("ru")) {
      this.lang = null;
    } else {
      this.lang = lang;
    }
  }

  public CodeforcesApi(final String key, final String secret) {
    this(key, secret, null);
  }

  public CodeforcesApi(final String lang) {
    this(null, null, lang);
  }

  public CodeforcesApi() {
    this(null, null, null);
  }

  private JsonNode request(final String request)
      throws MalformedURLException, CodeforcesApiException {
    URL requestUrl = new URL(request);

    InputStream responseStream;
    try {
      HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
      responseStream = connection.getInputStream();
    } catch (IOException e) {
      throw new CodeforcesApiException(
          "IOException occurred while performing " + request + " query: " + e.getMessage()
      );
    }

    try {
      synchronized (OBJECT_MAPPER) {
        return OBJECT_MAPPER.readTree(responseStream);
      }
    } catch (IOException e) {
      throw new CodeforcesApiException(
          "IOException occurred while performing " + request + " query: " + e.getMessage()
      );
    }
  }

  private void checkTextualField(final JsonNode node, final String field)
      throws CodeforcesApiException {
    if (!node.has(field)) {
      throw new CodeforcesApiException("Response has no " + field + " field: " + node);
    }
    if (!node.get(field).isTextual()) {
      throw new CodeforcesApiException("Response " + field + " field is not textual: " + node);
    }
  }

  private Optional<JsonNode> unpackResponse(final JsonNode root) throws CodeforcesApiException {
    checkTextualField(root, "status");

    String status = root.get("status").asText();
    if (!status.equals("OK") && !status.equals("FAILED")) {
      throw new CodeforcesApiException("Response status field is malformed: " + root);
    }

    if (status.equals("OK")) {
      if (!root.has("result")) {
        throw new CodeforcesApiException("Response returned OK but has no result: " + root);
      }
      return Optional.of(root.get("result"));
    } else {
      checkTextualField(root, "comment");
      return Optional.empty();
    }
  }

  private String combineParameters(final Map<String, String> parameters) {
    return parameters.entrySet()
        .stream()
        .map(i -> i.getKey() + '=' + i.getValue())
        .collect(Collectors.joining("&"));
  }

  private String calculateSha512(final String input) {
    try {
      return new BigInteger(
          1,
          MessageDigest.getInstance("SHA-512").digest(input.getBytes())
      ).toString(16);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("No SHA-512 algorithm specified.");
    }
  }

  private void addAuthorizationParameters(final String method, Map<String, String> parameters) {
    parameters.put("apiKey", key);
    parameters.put("time", Long.toString(System.currentTimeMillis() / 1000));

    String rand;
    synchronized (RANDOM) {
      rand = RANDOM.ints('a', 'z' + 1)
          .limit(6)
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString();
    }

    parameters.put(
        "apiSig",
        rand + calculateSha512(
            rand
                + "/"
                + method
                + "?"
                + parameters.entrySet()
                .stream()
                .sorted(
                    Comparator.comparing(Entry<String, String>::getKey)
                        .thenComparing(Entry::getValue)
                )
                .map(i -> i.getKey() + "=" + i.getValue())
                .collect(Collectors.joining("&"))
                + "#"
                + secret
        )
    );
  }

  private void addLocalizationParameters(Map<String, String> parameters) {
    parameters.put("lang", lang);
  }

  private String formRequest(final String method, Map<String, String> parameters) {
    if (isAuthorized()) {
      addAuthorizationParameters(method, parameters);
    }
    if (isLocalized()) {
      addLocalizationParameters(parameters);
    }

    return CODEFORCES_API + method + "?" + combineParameters(parameters);
  }

  private JsonNode wrappedRequest(final String method, Map<String, String> parameters)
      throws CodeforcesApiException {
    JsonNode result;
    try {
      result = request(formRequest(method, parameters));
    } catch (MalformedURLException e) {
      throw new RuntimeException("inner exception in CodeforcesAPI wrapper: " + e);
    }

    Optional<JsonNode> unpackedResult = unpackResponse(result);
    if (!unpackedResult.isPresent()) {
      throw new CodeforcesApiException("Request failed: " + result.get("comment").asText());
    }
    return unpackedResult.get();
  }

  private CodeforcesApiException malformedResultException(final IOException e) {
    return new CodeforcesApiException("Result is malformed: " + e.getMessage());
  }

  /**
   * Checks if this API connector is anonymous.
   *
   * @return true if and only if key and secret are provided for this API connector.
   */
  public boolean isAuthorized() {
    return key != null;
  }

  /**
   * Checks if the language for this API connector is specified.
   *
   * @return true if and only if language is provided for this API connector.
   */
  public boolean isLocalized() {
    return lang != null;
  }

  /**
   * Returns a list of comments to the specified blog entry.
   *
   * @param blogEntryId Id of the blog entry. It can be seen in blog entry URL.
   * @return list of comments corresponding to the specified blog entry id.
   * @throws CodeforcesApiException if either API call returns malformed response or specified
   *                                parameters are invalid.
   */
  public List<Comment> blogEntryComments(final int blogEntryId) throws CodeforcesApiException {
    Map<String, String> parameters = new HashMap<>();
    parameters.put("blogEntryId", Integer.toString(blogEntryId));

    JsonNode result = wrappedRequest("blogEntry.comments", parameters);

    try {
      synchronized (COMMENT_LIST_READER) {
        return COMMENT_LIST_READER.readValue(result);
      }
    } catch (IOException e) {
      throw malformedResultException(e);
    }
  }

  /**
   * Returns full version of blog entry.
   *
   * @param blogEntryId Id of the blog entry. It can be seen in blog entry URL.
   * @return blog entry corresponding to the specified blog entry id
   * @throws CodeforcesApiException if either API call returns malformed response or specified
   *                                parameters are invalid.
   */
  public BlogEntry blogEntryView(final int blogEntryId) throws CodeforcesApiException {
    Map<String, String> parameters = new HashMap<>();
    parameters.put("blogEntryId", Integer.toString(blogEntryId));

    JsonNode result = wrappedRequest("blogEntry.view", parameters);

    try {
      synchronized (OBJECT_MAPPER) {
        return OBJECT_MAPPER.treeToValue(result, BlogEntry.class);
      }
    } catch (JsonProcessingException e) {
      throw malformedResultException(e);
    }
  }

  /**
   * Returns list of hacks in the specified contests. Full information about hacks is available only
   * after some time after the contest end. During the contest user can see only own hacks.
   *
   * @param contestId Id of the contest. It is not the round number. It can be seen in contest URL.
   * @return list of hacks corresponding to the specified contest id.
   * @throws CodeforcesApiException if either API call returns malformed response or specified
   *                                parameters are invalid.
   */
  public List<Hack> contestHacks(final int contestId) throws CodeforcesApiException {
    Map<String, String> parameters = new HashMap<>();
    parameters.put("contestId", Integer.toString(contestId));

    JsonNode result = wrappedRequest("contest.hacks", parameters);

    try {
      synchronized (HACK_LIST_READER) {
        return HACK_LIST_READER.readValue(result);
      }
    } catch (IOException e) {
      throw malformedResultException(e);
    }
  }

  /**
   * Returns information about all available contests.
   *
   * @param gym If true — than gym contests are returned. Otherwise, regular contests are returned.
   *            Can be omitted by passing null.
   * @return If this method is called not anonymously, then all available contests for a calling
   *     user will be returned too, including mashups and private gyms.
   * @throws CodeforcesApiException if API call returns malformed response.
   */
  public List<Contest> contestList(final Boolean gym) throws CodeforcesApiException {
    Map<String, String> parameters = new HashMap<>();
    parameters.put("contestId", gym ? "true" : "false");

    JsonNode result = wrappedRequest("contest.list", parameters);

    try {
      synchronized (CONTEST_LIST_READER) {
        return CONTEST_LIST_READER.readValue(result);
      }
    } catch (IOException e) {
      throw malformedResultException(e);
    }
  }

  /**
   * Returns rating changes after the contest.
   *
   * @param contestId Id of the contest. It is not the round number. It can be seen in contest URL.
   * @return rating changes corresponding to the specified contest id.
   * @throws CodeforcesApiException if either API call returns malformed response or specified
   *                                parameters are invalid.
   */
  public List<RatingChange> contestRatingChanges(final int contestId)
      throws CodeforcesApiException {
    Map<String, String> parameters = new HashMap<>();
    parameters.put("contestId", Integer.toString(contestId));

    JsonNode result = wrappedRequest("contest.ratingChanges", parameters);

    try {
      synchronized (RATING_CHANGE_LIST_READER) {
        return RATING_CHANGE_LIST_READER.readValue(result);
      }
    } catch (IOException e) {
      throw malformedResultException(e);
    }
  }

  /**
   * Returns the description of the contest and the requested part of the standings.
   *
   * @param contestId      Id of the contest. It is not the round number. It can be seen in contest
   *                       URL.
   * @param from           1-based index of the standings row to start the ranklist. Can be omitted
   *                       by passing null.
   * @param count          Number of standing rows to return. Can be omitted by passing null.
   * @param handles        List of user handles to filter submissions. No more than {@value
   *                       STANDINGS_HANDLES_LIMIT} handles is accepted. Can be omitted by passing
   *                       null.
   * @param room           If specified, than only participants from this room will be shown in the
   *                       result. If not — all the participants will be shown. Can be omitted by
   *                       passing null.
   * @param showUnofficial If true than all participants (virtual, out of competition) are shown.
   *                       Otherwise, only official contestants are shown. Can be omitted by passing
   *                       null.
   * @return ContestStandings object containing the description of the contest and the requested
   *     part of the standings.
   * @throws CodeforcesApiException if either API call returns malformed response or specified
   *                                parameters are invalid.
   */
  public ContestStandings contestStanding(final int contestId, final Integer from,
      final Integer count, final List<String> handles, final Integer room,
      final Boolean showUnofficial) throws CodeforcesApiException {
    if (handles != null && handles.size() > STANDINGS_HANDLES_LIMIT) {
      throw new CodeforcesApiException(
          "No more than " + STANDINGS_HANDLES_LIMIT + " handles accepted");
    }

    Map<String, String> parameters = new HashMap<>();
    parameters.put("contestId", Integer.toString(contestId));
    if (from != null) {
      parameters.put("from", from.toString());
    }
    if (count != null) {
      parameters.put("count", count.toString());
    }
    if (handles != null) {
      parameters.put("handles", String.join(";", handles));
    }
    if (room != null) {
      parameters.put("room", room.toString());
    }
    if (showUnofficial != null) {
      parameters.put("showUnofficial", showUnofficial ? "true" : "false");
    }

    JsonNode result = wrappedRequest("contest.standings", parameters);

    try {
      synchronized (OBJECT_MAPPER) {
        return OBJECT_MAPPER.treeToValue(result, ContestStandings.class);
      }
    } catch (JsonProcessingException e) {
      throw malformedResultException(e);
    }
  }

  /**
   * Returns submissions for specified contest. Optionally can return submissions of specified
   * user.
   *
   * @param contestId Id of the contest. It is not the round number. It can be seen in contest URL.
   * @param handle    Codeforces user handle. Can be omitted by passing null.
   * @param from      1-based index of the first submission to return. Can be omitted by passing
   *                  null.
   * @param count     Number of returned submissions. Can be omitted by passing null.
   * @return list of submissions, sorted in decreasing order of submission id.
   * @throws CodeforcesApiException if either API call returns malformed response or specified
   *                                parameters are invalid.
   */
  public List<Submission> contestStatus(final int contestId, final String handle,
      final Integer from, final Integer count) throws CodeforcesApiException {
    Map<String, String> parameters = new HashMap<>();
    parameters.put("contestId", Integer.toString(contestId));
    if (handle != null) {
      parameters.put("handle", handle);
    }
    if (from != null) {
      parameters.put("from", from.toString());
    }
    if (count != null) {
      parameters.put("count", count.toString());
    }

    JsonNode result = wrappedRequest("contest.status", parameters);

    try {
      synchronized (SUBMISSION_LIST_READER) {
        return SUBMISSION_LIST_READER.readValue(result);
      }
    } catch (IOException e) {
      throw malformedResultException(e);
    }
  }

  /**
   * Returns all problems from problemset. Problems can be filtered by tags.
   *
   * @param tags           list of tags to filter by. Can be omitted by passing null or empty list.
   * @param problemsetName Custom problemset short name, like "acmsguru"
   * @return returns two lists packed in a Problemset object.
   * @throws CodeforcesApiException if either API call returns malformed response or specified
   *                                parameters are invalid.
   */
  public Problemset problemsetProblems(final List<String> tags, final String problemsetName)
      throws CodeforcesApiException {
    Map<String, String> parameters = new HashMap<>();
    if (tags != null) {
      parameters.put("tags", String.join(";", tags));
    }
    if (problemsetName != null) {
      parameters.put("problemsetName", problemsetName);
    }

    JsonNode result = wrappedRequest("problemset.problems", parameters);

    try {
      synchronized (OBJECT_MAPPER) {
        return OBJECT_MAPPER.treeToValue(result, Problemset.class);
      }
    } catch (JsonProcessingException e) {
      throw malformedResultException(e);
    }
  }

  /**
   * Returns recent submissions.
   *
   * @param count          Number of submissions to return. Can be up to {@value
   *                       PROBLEMSET_RECENT_SUBMISSIONS_LIMIT}.
   * @param problemsetName Custom problemset short name, like 'acmsguru'. Can be omitted by passing
   *                       null.
   * @return returns a list of submissions, sorted in decreasing order of submission id.
   * @throws CodeforcesApiException if either API call returns malformed response or specified
   *                                parameters are invalid.
   */
  public List<Submission> problemsetRecentStatus(final int count, final String problemsetName)
      throws CodeforcesApiException {
    if (count > PROBLEMSET_RECENT_SUBMISSIONS_LIMIT) {
      throw new CodeforcesApiException(
          "No more than " + PROBLEMSET_RECENT_SUBMISSIONS_LIMIT + " submissions can be returned");
    }
    Map<String, String> parameters = new HashMap<>();
    parameters.put("count", Integer.toString(count));
    if (problemsetName != null) {
      parameters.put("problemsetName", problemsetName);
    }

    JsonNode result = wrappedRequest("problemset.recentStatus", parameters);

    try {
      synchronized (SUBMISSION_LIST_READER) {
        return SUBMISSION_LIST_READER.readValue(result);
      }
    } catch (IOException e) {
      throw malformedResultException(e);
    }
  }

  /**
   * Returns recent actions.
   *
   * @param maxCount Number of recent actions to return. Can be up to {@value
   *                 RECENT_ACTIONS_LIMIT}.
   * @return list of recent actions.
   * @throws CodeforcesApiException if either API call returns malformed response or specified
   *                                parameters are invalid.
   */
  public List<RecentAction> recentActions(final int maxCount) throws CodeforcesApiException {
    if (maxCount > RECENT_ACTIONS_LIMIT) {
      throw new CodeforcesApiException(
          "No more than " + RECENT_ACTIONS_LIMIT + " recent actions can be returned");
    }

    Map<String, String> parameters = new HashMap<>();
    parameters.put("maxCount", Integer.toString(maxCount));

    JsonNode result = wrappedRequest("recentActions", parameters);

    try {
      synchronized (RECENT_ACTION_LIST_READER) {
        return RECENT_ACTION_LIST_READER.readValue(result);
      }
    } catch (IOException e) {
      throw malformedResultException(e);
    }
  }

  /**
   * Returns a list of all user's blog entries.
   *
   * @param handle codeforces user handle
   * @return returns a list of blog entries in short form.
   * @throws CodeforcesApiException if either API call returns malformed response or specified
   *                                parameters are invalid.
   */
  public List<BlogEntry> userBlogEntries(final String handle) throws CodeforcesApiException {
    if (handle == null) {
      throw new CodeforcesApiException("Handle cannot be null");
    }
    Map<String, String> parameters = new HashMap<>();
    parameters.put("handle", handle);

    JsonNode result = wrappedRequest("user.blogEntries", parameters);

    try {
      synchronized (BLOG_ENTRY_LIST_READER) {
        return BLOG_ENTRY_LIST_READER.readValue(result);
      }
    } catch (IOException e) {
      throw malformedResultException(e);
    }
  }

  /**
   * Returns authorized user's friends. Using this method requires authorization.
   *
   * @param onlyOnline If true — only online friends are returned. Otherwise, all friends are
   *                   returned.
   * @return returns a list of strings — user friends' handles.
   * @throws CodeforcesApiException if API call returns malformed response.
   */
  public List<String> userFriends(final boolean onlyOnline) throws CodeforcesApiException {
    if (!isAuthorized()) {
      throw new CodeforcesApiException("Connector must be authorized in order to use this method");
    }
    Map<String, String> parameters = new HashMap<>();
    parameters.put("onlyOnline", onlyOnline ? "true" : "false");

    JsonNode result = wrappedRequest("user.friends", parameters);

    try {
      synchronized (STRING_LIST_READER) {
        return STRING_LIST_READER.readValue(result);
      }
    } catch (IOException e) {
      throw malformedResultException(e);
    }
  }

  /**
   * Returns information about one or several users.
   *
   * @param handles list of handles. No more than {@value USER_INFO_LIMIT} handles are accepted.
   * @return returns a list of users, sorted in decreasing order of rating.
   * @throws CodeforcesApiException if either API call returns malformed response or specified
   *                                parameters are invalid.
   */
  public List<User> userInfo(final List<String> handles) throws CodeforcesApiException {
    if (handles == null) {
      throw new CodeforcesApiException("Handle list must not be null");
    }
    if (handles.size() > USER_INFO_LIMIT) {
      throw new CodeforcesApiException(
          "No more than " + USER_INFO_LIMIT + " users can be queried.");
    }
    Map<String, String> parameters = new HashMap<>();
    parameters.put("handles", String.join(";", handles));

    JsonNode result = wrappedRequest("user.info", parameters);

    try {
      synchronized (USER_LIST_READER) {
        return USER_LIST_READER.readValue(result);
      }
    } catch (IOException e) {
      throw malformedResultException(e);
    }
  }

  /**
   * Returns the list users who have participated in at least one rated contest.
   *
   * @param activeOnly If true then only users, who participated in rated contest during the last
   *                   month are returned. Otherwise, all users with at least one rated contest are
   *                   returned.
   * @return returns a list of users, sorted in decreasing order of rating.
   * @throws CodeforcesApiException if API call returns malformed response.
   */
  public List<User> userRatedList(final boolean activeOnly) throws CodeforcesApiException {
    Map<String, String> parameters = new HashMap<>();
    parameters.put("activeOnly", activeOnly ? "true" : "false");

    JsonNode result = wrappedRequest("user.ratedList", parameters);

    try {
      synchronized (USER_LIST_READER) {
        return USER_LIST_READER.readValue(result);
      }
    } catch (IOException e) {
      throw malformedResultException(e);
    }
  }

  /**
   * Returns rating history of the specified user.
   *
   * @param handle codeforces user handle.
   * @return returns a list of rating changes for requested user.
   * @throws CodeforcesApiException if either API call returns malformed response or specified
   *                                parameters are invalid.
   */
  public List<RatingChange> userRating(final String handle) throws CodeforcesApiException {
    if (handle == null) {
      throw new CodeforcesApiException("Handle must not be null");
    }
    Map<String, String> parameters = new HashMap<>();
    parameters.put("handle", handle);

    JsonNode result = wrappedRequest("user.rating", parameters);

    try {
      synchronized (RATING_CHANGE_LIST_READER) {
        return RATING_CHANGE_LIST_READER.readValue(result);
      }
    } catch (IOException e) {
      throw malformedResultException(e);
    }
  }

  /**
   * Returns submissions of specified user.
   *
   * @param handle Codeforces user handle.
   * @param from   1-based index of the first submission to return. Can be omitted by passing null.
   * @param count  Number of returned submissions. Can be omitted by passing null.
   * @return returns a list of submissions sorted in decreasing order of submission id.
   * @throws CodeforcesApiException if either API call returns malformed response or specified
   *                                parameters are invalid.
   */
  public List<Submission> userStatus(final String handle, final Integer from, final Integer count)
      throws CodeforcesApiException {
    if (handle == null) {
      throw new CodeforcesApiException("Handle must not be null");
    }
    Map<String, String> parameters = new HashMap<>();
    parameters.put("handle", handle);
    if (from != null) {
      parameters.put("from", from.toString());
    }
    if (count != null) {
      parameters.put("count", count.toString());
    }

    JsonNode result = wrappedRequest("user.status", parameters);

    try {
      synchronized (SUBMISSION_LIST_READER) {
        return SUBMISSION_LIST_READER.readValue(result);
      }
    } catch (IOException e) {
      throw malformedResultException(e);
    }
  }
}
