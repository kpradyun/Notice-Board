public class Faculty extends User {
    public Faculty(String username, String password) {
        super(username, password, "Faculty");
    }

    @Override
    public void viewNotices() {
        System.out.println("\n--- All Notices (Active + Scheduled) ---");
        NoticeBoardApp.viewAllNotices(true);
    }

    @Override
    public void postNotice() {
        NoticeBoardApp.postNotice(this);
    }

    @Override
    public void removeNotice() {
        NoticeBoardApp.removeNotice(this);
    }
}