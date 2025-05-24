import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class NoticeBoardApp {

    static final int MAX_USERS = 100;
    static final int MAX_NOTICES = 100;
    static User[] users = new User[MAX_USERS];
    static int userCount = 0;
    static Notice[] notices = new Notice[MAX_NOTICES];
    static int noticeCount = 0;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadUsersFromFile("users.dat");
        loadNoticesFromFile("notices.txt");
        int choice;
        do {
            System.out.println("Welcome to the Notice Board System!");
            System.out.println("Please choose an option:");
            System.out.println("1. Register\n2. Login\n0. Exit");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 ->
                    register();
                case 2 ->
                    login();
                case 0 ->
                    System.out.println("Goodbye!");
                default ->
                    System.out.println("Invalid choice");
            }
        } while (choice != 0);
        saveUsersToFile("users.dat");
        saveNoticesToFile("notices.txt");
        sc.close();
    }

    public static void addUser(User user) {
        if (userCount < MAX_USERS) {
            users[userCount++] = user;
        }
    }

    // private static String readPassword(String prompt) {
    //     Console console = System.console();
    //     if (console != null) {
    //         char[] passwordArray = console.readPassword(prompt);
    //         return new String(passwordArray);
    //     } else {
    //         System.out.print(prompt);
    //         return sc.nextLine();
    //     }
    // }
    
    static void register() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        System.out.print("Enter role (Admin/Faculty/Student): ");
        String role = sc.nextLine();
        if (role.equalsIgnoreCase("Admin")) {
            addUser(new Admin(username, password));
        } else if (role.equalsIgnoreCase("Faculty")) {
            addUser(new Faculty(username, password));
        } else if (role.equalsIgnoreCase("Student")) {
            addUser(new Student(username, password));
        } else {
            System.out.println("Invalid role. Registration failed.");
            return;
        }
        System.out.println("Registration successful!");
    }

    static void login() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        User user = AuthService.authenticate(username, password);
        if (user != null) {
            System.out.println("Welcome, " + user.getUsername() + "!");
            userMenu(user);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    static void userMenu(User user) {
        int choice;
        do {
            System.out.println("\nLogged in as: " + user.getRole());
            if (user instanceof Admin) {
                System.out.println("1. View Notices\n2. Add Notice\n3. Remove Notice\n4. Change Status\n0. Logout");
            } else if (user instanceof Faculty) {
                System.out.println("1. View Notices\n2. Add Notice\n3. Remove Notice\n0. Logout");
            } else {
                System.out.println("1. View Notices\n0. Logout");
            }

            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 ->
                    user.viewNotices();
                case 2 -> {
                    if (!user.getRole().equals("Student")) {
                        user.postNotice();
                    }
                }
                case 3 -> {
                    if (user.getRole().equals("Faculty") || user.getRole().equals("Admin")) {
                        user.removeNotice();
                    }
                }
                case 4 -> {
                    if (user.getRole().equals("Admin")) {
                        user.changeStatus();
                    }
                }
                case 0 ->
                    System.out.println("Logged out.");
                default ->
                    System.out.println("Invalid choice");
            }
        } while (choice != 0);
    }

    public static void postNotice(User user) {
        if (noticeCount >= MAX_NOTICES) {
            System.out.println("Notice board is full.");
            return;
        }

        System.out.print("Enter title: ");
        String title = sc.nextLine();
        System.out.print("Enter content: ");
        String content = sc.nextLine();
        System.out.print("1. Normal Notice\n2. Scheduled Notice\nChoose: ");
        int type = sc.nextInt();
        sc.nextLine();

        LocalDate date = LocalDate.now();
        if (type == 2) {
            System.out.print("Enter scheduled date (yyyy-MM-dd): ");
            try {
                date = LocalDate.parse(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid date. Using today.");
            }
        }

        notices[noticeCount++] = new Notice(title, content, user.getUsername(), date);
        System.out.println("Notice posted.");
    }

    public static void viewAllNotices(boolean showAll) {
        for (int i = 0; i < noticeCount; i++) {
            if (showAll || notices[i].isActive()) {
                notices[i].display();
            }
        }
    }

    public static void removeNotice(User user) {
        System.out.print("Enter Notice ID to remove: ");
        int id = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < noticeCount; i++) {
            if (notices[i].getId() == id
                    && (user.getRole().equals("Admin") || notices[i].getPostedBy().equals(user.getUsername()))) {
                for (int j = i; j < noticeCount - 1; j++) {
                    notices[j] = notices[j + 1];
                }
                noticeCount--;
                System.out.println("Notice removed.");
                return;
            }
        }
        System.out.println("Notice not found or no permission.");
    }

    public static void saveNoticesToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (int i = 0; i < noticeCount; i++) {
                Notice n = notices[i];
                pw.println(n.getId() + "|" + n.getPostedBy() + "|" + n.getScheduledDate() + "|" + n.status + "|" + n.title + "|" + n.content);
            }
            System.out.println("Notices saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving notices: " + e.getMessage());
        }
    }

    public static void loadNoticesFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    String postedBy = parts[1];
                    LocalDate date = LocalDate.parse(parts[2]);
                    Notice notice = new Notice(parts[4], parts[5], postedBy, date);
                    notice.setStatus(parts[3]);
                    notices[noticeCount++] = notice;
                }
            }
            System.out.println("Notices loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading notices: " + e.getMessage());
        }
    }

    public static void saveUsersToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeInt(userCount);
            for (int i = 0; i < userCount; i++) {
                oos.writeObject(users[i]);
            }
            System.out.println("Users saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public static void loadUsersFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            userCount = ois.readInt();
            for (int i = 0; i < userCount; i++) {
                users[i] = (User) ois.readObject();
            }
            System.out.println("Users loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    public static void changeStatus() {
        System.out.print("Enter Notice ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < noticeCount; i++) {
            if (notices[i].getId() == id) {
                System.out.println("1. Change Scheduled Date");
                System.out.println("2. Change Status (Active/Completed/Inactive)");
                System.out.print("Choose option: ");
                int option = sc.nextInt();
                sc.nextLine();

                switch (option) {
                    case 1 -> {
                        System.out.print("Enter new scheduled date (yyyy-MM-dd): ");
                        try {
                            LocalDate newDate = LocalDate.parse(sc.nextLine());
                            notices[i].setScheduledDate(newDate);
                            System.out.println("Notice rescheduled.");
                        } catch (Exception e) {
                            System.out.println("Invalid date format.");
                        }
                    }
                    case 2 -> {
                        System.out.print("Enter new status (Active/Completed/Inactive): ");
                        String newStatus = sc.nextLine();
                        if (newStatus.equalsIgnoreCase("Active")
                                || newStatus.equalsIgnoreCase("Completed")
                                || newStatus.equalsIgnoreCase("Inactive")) {
                            notices[i].setStatus(newStatus);
                            System.out.println("Status updated.");
                        } else {
                            System.out.println("Invalid status.");
                        }
                    }
                    default ->
                        System.out.println("Invalid option.");
                }
                return;
            }
        }
        System.out.println("Notice ID not found.");
    }
}
