// import com.google.gson.JsonArray;
// import com.google.gson.JsonElement;
// import com.google.gson.JsonObject;
// import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GitHubUser {

    private static final String GITHUB_API_URL = "https://api.github.com/users";
    private static final int NUMBER_OF_USERS = 20;

    private Map<String, User> usersMap = new HashMap<>();

    public static void main(String[] args) {
        GitHubUser fetcher = new GitHubUser();
        fetcher.fetchUsers();
        fetcher.interactWithUser();
    }

    private void fetchUsers() {
        try {
            URL url = new URL(GITHUB_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            int responseCode = connection.getResponseCode();
            int connectionSuccess = HttpURLConnection.HTTP_OK;

            if (responseCode == connectionSuccess) {
                System.out.println("Respone Okey" + responseCode);
                StringBuilder sb = new StringBuilder();
                Scanner scanner = new Scanner(connection.getInputStream());
                while (scanner.hasNext()) {
                    sb.append(scanner.nextLine());
                }



                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                in.close();
                connection.disconnect();

                // JsonArray users = JsonParser.parseString(content.toString()).getAsJsonArray();
                // for (int i = 0; i < NUMBER_OF_USERS && i < users.size(); i++) {
                //     JsonObject userJson = users.get(i).getAsJsonObject();
                //     String id = userJson.get("id").getAsString();
                //     String login = userJson.get("login").getAsString();
                //     String detailsUrl = userJson.get("url").getAsString();

                //     // Fetch detailed user data
                //     fetchUserDetails(id, login, detailsUrl);
                // }
            } else {
                System.out.println("Failed to fetch data. Response code: " + connection.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchUserDetails(String id, String login, String detailsUrl) {
        try {
            URL url = new URL(detailsUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                in.close();
                connection.disconnect();

                // JsonObject userDetails = JsonParser.parseString(content.toString()).getAsJsonObject();
                // String name = userDetails.has("name") ? userDetails.get("name").getAsString() : "N/A";
                // int followers = userDetails.get("followers").getAsInt();
                // int following = userDetails.get("following").getAsInt();
                // String company = userDetails.has("company") && !userDetails.get("company").isJsonNull() ? 
                //                  userDetails.get("company").getAsString() : "N/A";

                // User user = new User(id, login, name, followers, following, company);
                // usersMap.put(id, user);
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
        String inputId = scanner.nextLine();

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
}
