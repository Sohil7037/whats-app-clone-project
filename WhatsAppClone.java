import java.util.*;

class User {
    String username;
    String password;
    Map<String, List<String>> chatHistory = new HashMap<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void sendMessage(String receiver, String message, Map<String, User> users) {
        if (!users.containsKey(receiver)) {
            System.out.println("User not found.");
            return;
        }

        users.get(receiver).receiveMessage(this.username, message);
        chatHistory.computeIfAbsent(receiver, k -> new ArrayList<>()).add("You: " + message);
        System.out.println("Message sent to " + receiver);
    }

    public void receiveMessage(String sender, String message) {
        chatHistory.computeIfAbsent(sender, k -> new ArrayList<>()).add(sender + ": " + message);
    }

    public void viewChats() {
        if (chatHistory.isEmpty()) {
            System.out.println("No chats available.");
            return;
        }

        for (String contact : chatHistory.keySet()) {
            System.out.println("Chat with " + contact + ":");
            for (String msg : chatHistory.get(contact)) {
                System.out.println(msg);
            }
            System.out.println("----------------------");
        }
    }
}

public class WhatsAppClone {
    static Map<String, User> users = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);
    static User currentUser = null;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== WhatsApp Clone ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> {
                    System.out.println("Thank you for using WhatsApp Clone!");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println("Username already exists!");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        users.put(username, new User(username, password));
        System.out.println("Registration successful!");
    }

    static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username) && users.get(username).password.equals(password)) {
            currentUser = users.get(username);
            System.out.println("Login successful!");
            userMenu();
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    static void userMenu() {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Send Message");
            System.out.println("2. View Chats");
            System.out.println("3. Logout");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter recipient username: ");
                    String recipient = scanner.nextLine();
                    System.out.print("Enter message: ");
                    String message = scanner.nextLine();
                    currentUser.sendMessage(recipient, message, users);
                }
                case 2 -> currentUser.viewChats();
                case 3 -> {
                    currentUser = null;
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
