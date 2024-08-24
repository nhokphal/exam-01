import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GitHubUser {

    private static final String GITHUB_API_URL = "https://api.github.com/users";
    private static final int NUMBER_OF_USERS = 20;

    // Store users in a map, keyed by ID
    private Map<String, User> usersMap = new HashMap<>();

    public static void main(String[] args) {
        GitHubUser fetcher = new GitHubUser();
        fetcher.fetchUsers();
        fetcher.interactWithUser();
    }

    private void fetchUsers() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(GITHUB_API_URL))
                    .GET()
                    .header("Accept", "application/json")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Fetched user data successfully!");

                // Manually parse the JSON response (simple string manipulation)
                String responseBody = response.body();
                String[] usersArray = responseBody.split("\\},\\{");

                // Fetch details for the first 20 users
                for (int i = 0; i < NUMBER_OF_USERS && i < usersArray.length; i++) {
                    String userJson = usersArray[i];
                    String id = extractValue(userJson, "\"id\":");
                    String login = extractValue(userJson, "\"login\":");
                    String detailsUrl = extractValue(userJson, "\"url\":");

                    // Clean up the extracted values
                    id = cleanValue(id);
                    login = cleanValue(login);
                    detailsUrl = cleanValue(detailsUrl);

                    // Fetch detailed user data
                    fetchUserDetails(id, login, detailsUrl);
                }
            } else {
                System.out.println("Failed to fetch data. Response code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchUserDetails(String id, String login, String detailsUrl) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(detailsUrl))
                    .GET()
                    .header("Accept", "application/json")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Manually parse the user details (simple string manipulation)
                String responseBody = response.body();
                String profileUrl = extractValue(responseBody, "\"html_url\":");
                String repos = extractValue(responseBody, "\"public_repos\":");
                String followers = extractValue(responseBody, "\"followers\":");
                String following = extractValue(responseBody, "\"following\":");

                // Clean up the extracted values
                profileUrl = cleanValue(profileUrl);
                repos = cleanValue(repos);
                followers = cleanValue(followers);
                following = cleanValue(following);

                User user = new User(id, login, profileUrl, Integer.parseInt(repos), Integer.parseInt(followers), Integer.parseInt(following));
                usersMap.put(id, user);

            } else {
                System.out.println("Failed to fetch user details for: " + login);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void interactWithUser() {
        // Print all available IDs
        System.out.println("Available User IDs:");
        for (String id : usersMap.keySet()) {
            System.out.println(id);
        }

        // Prompt user to enter an ID
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter a User ID to see details: ");
        String inputId = scanner.nextLine().trim();

        // Display details or handle invalid input
        User user = usersMap.get(inputId);
        if (user != null) {
            System.out.println("User Details:");
            System.out.println(user);
        } else {
            System.out.println("Invalid User ID. Please try again.");
        }

        scanner.close();
    }

    // Helper method to extract the value
    private String extractValue(String json, String key) {
        int startIndex = json.indexOf(key) + key.length();
        int endIndex = json.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = json.indexOf("}", startIndex); 
            // Handle the last value in the JSON object
        }
        return json.substring(startIndex, endIndex);
    }

    // Helper method to clean up the extracted value
    private String cleanValue(String value) {
        return value.replaceAll("[\"{}]", "").trim();
    }
}

class User {
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

