import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JournalEntry {
    private int id;
    private transient LocalDateTime timestamp;
    private String title;
    private String content;
    private String timestampString;

    //For new journal entries
    public JournalEntry(int id, String title, String content) {
        this.id = id;
        timestamp = LocalDateTime.now();
        this.title = title;
        this.content = content;
    }
    //When loading from a JSON file
    public JournalEntry(int id, String title, String content, LocalDateTime timestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    public void setId(int id) { this.id = id; }

    public int getId() { return id; }

    public void setTime() { timestamp = LocalDateTime.now(); }

    public String getTimestampString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        timestampString = formatter.format(timestamp);
        return timestampString;
    }

    public LocalDateTime getTimestamp() { return timestamp; }

    
    public void setTitle(String title) { this.title = title; }

    public String getTitle() { return title; }

    public void setContent(String content) { this.content = content; }

    public String getContent() { return content; }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        return "------------------------------------\nEntry #" + id +
               "\nDate: " + formatter.format(timestamp) +
               "\nTitle: " + title +
               "\n" + content;
    }
}
