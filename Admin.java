public class Admin extends Faculty {
    public Admin(String username, String password) {
        super(username, password);
        this.role = "Admin";
    }

    @Override
    public void changeStatus() {
        NoticeBoardApp.changeStatus();
    }
}