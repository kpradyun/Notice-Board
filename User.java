import java.io.Serializable;

public abstract class User implements Serializable{
    private static final long serialVersionUID = 1L;
    protected String username;
    protected String password;
    protected String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    public abstract void viewNotices();
    public void postNotice() {}
    public void removeNotice() {}
    public void changeStatus() {}
}