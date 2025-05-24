public class AuthService {
    public static void register(String username, String password, String role) {
        User user;
        switch (role) {
            case "Admin" -> user = new Admin(username, password);
            case "Faculty" -> user = new Faculty(username, password);
            case "Student" -> user = new Student(username, password);
            default -> {
                System.out.println("Invalid role.");
                return;
            }
        }

        NoticeBoardApp.addUser(user);
        System.out.println(role + " registered successfully!");
    }

    public static User authenticate(String username, String password) {
        for (int i = 0; i < NoticeBoardApp.userCount; i++) {
            User user = NoticeBoardApp.users[i];
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}