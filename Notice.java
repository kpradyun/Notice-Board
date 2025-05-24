import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Notice {
    private static int counter = 1;
    private final int id;
    public final String title;
    public final String content;
    private final String postedBy;
    private LocalDate scheduledDate;
    public String status;

    public Notice(String title, String content, String postedBy, LocalDate scheduledDate) {
        this.id = counter++;
        this.title = title;
        this.content = content;
        this.postedBy = postedBy;
        this.scheduledDate = scheduledDate;
        this.status = scheduledDate.isAfter(LocalDate.now())
              ? "Inactive"
              : "Active";
    }

    public int getId() { return id; }
    public String getPostedBy() { return postedBy; }
    public LocalDate getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(LocalDate date) { this.scheduledDate = date; }

    public boolean isActive() {
        return status.equalsIgnoreCase("Active") &&!LocalDate.now().isBefore(scheduledDate);
    }    

    public void setStatus(String status) {
        if (status.equalsIgnoreCase("Active") ||
            status.equalsIgnoreCase("Completed") ||
            status.equalsIgnoreCase("Inactive")) {
            this.status = status;
        }
    }

    public void display() {
        System.out.println("\nID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Content: " + content);
        System.out.println("Posted By: " + postedBy);
        System.out.println("Scheduled Date: " + scheduledDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        System.out.println("Status: " + status);
    }
}