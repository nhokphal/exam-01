// User class to store user data
public class User {
    private String id;
    private String login;
    private String profileUrl;
    private int repos;
    private int followers;
    private int following;

    public User(String id, String login, String profileUrl, int repos, int followers, int following) {
        this.id = id;
        this.login = login;
        this.profileUrl = profileUrl;
        this.repos = repos;
        this.followers = followers;
        this.following = following;
    }

    @Override
    public String toString() {
        return "Login Name: " + login + "\n" +
               "Profile URL: " + profileUrl + "\n" +
               "Number of Repositories: " + repos + "\n" +
               "Number of Followers: " + followers + "\n" +
               "Number of Following: " + following;
    }
}
