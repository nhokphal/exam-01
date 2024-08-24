public class User {
    private String id;
    private String login;
    private String name;
    private int followers;
    private int following;
    private String company;

    // Constructor
    public User(String id, String login, String name, int followers, int following, String company) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.followers = followers;
        this.following = following;
        this.company = company;
    }

    // Getters
    public String getId() { return id; }
    public String getLogin() { return login; }
    public String getName() { return name; }
    public int getFollowers() { return followers; }
    public int getFollowing() { return following; }
    public String getCompany() { return company; }

    @Override
    public String toString() {
        return "ID: " + id + "\nLogin: " + login + "\nName: " + name +
               "\nFollowers: " + followers + "\nFollowing: " + following +
               "\nCompany: " + (company != null ? company : "N/A");
    }
}
