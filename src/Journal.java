import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Journal {
    List<JournalEntry> journal = new ArrayList<>();
    Scanner scanny = new Scanner(System.in);
    int nextId = 0;

    private Gson gson = new Gson();

    private static final String FILE_NAME = "journal.json";

    public Journal() {
        if (journal.isEmpty()) {
            nextId = 1;
        } else {
           int largestId = 0;
            for (JournalEntry entry : journal) {
                int entryId = entry.getId();
                largestId = Math.max(largestId, entryId);
            }
            nextId = largestId + 1;
        }
    }

    public void addJournalEntry(String title, String content) {
        JournalEntry entry = new JournalEntry(nextId, title, content);
        journal.add(entry);
        nextId++;
        System.out.println("Entry added.");
    }

    public String getJournalEntry(int id) {
        for (JournalEntry e : journal) {
            if (e.getId() == id) {
                return e.toString();
            } 
        }
        return "Invalid ID.";
    }

    public void deleteJournalEntry(int id) {
        journal.remove(id);
        System.out.println("Entry deleted.");
    }

    public String getEntries() {
        System.out.println("All entries:");
        String entries = "";
        for (JournalEntry entry : journal) {
            entries += entry.toString() + "\n";
        }
        return entries;
    }

    public void saveToFile() {

        List<Map<String, String>> entriesForJson = new ArrayList<>();
        for (JournalEntry e : journal) {
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(e.getId()));
            map.put("timestamp", e.getTimestampString());
            map.put("title", e.getTitle());
            map.put("content", e.getContent());
            entriesForJson.add(map);
        }

        String json = gson.toJson(entriesForJson);

        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write(json);
        } catch (IOException e) {
            System.out.println("Unable to save file: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            Type listType = new TypeToken<List<Map<String, String>>>(){}.getType();
            List<Map<String, String>> loadedList = gson.fromJson(reader, listType);

            journal.clear();
            int maxId = 0;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy' 'HH:mm:ss");

            for (Map<String, String> map : loadedList) {
                int id = Integer.parseInt(map.get("id"));
                String title = map.get("title");
                String content = map.get("content");
                LocalDateTime timestamp = LocalDateTime.parse(map.get("timestamp"), formatter);

                journal.add(new JournalEntry(id, title, content, timestamp));
                if (id > maxId) maxId = id;
            }

            nextId = maxId + 1;  // ensure unique IDs
        } catch (IOException e) {
            System.out.println("No existing journal found. Starting fresh.");
        }
        }
}


