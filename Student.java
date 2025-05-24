public class Student extends User {
    public Student(String username, String password) {
        super(username, password, "Student");
    }

    @Override
    public void viewNotices() {
        System.out.println("\n--- Active Notices ---");
        NoticeBoardApp.viewAllNotices(false);
    }
}