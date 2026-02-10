import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.lang.reflect.Type;

import java.time.LocalDateTime;



public class Journal {
    Map<Integer, JournalEntry> journal;
    Scanner scanny = new Scanner(System.in);
    int nextId = 0;

    private Gson gson = new Gson();

    private static final String FILE_NAME = "journal.json";

    public Journal() {
        journal = new HashMap<>();
        nextId = 1;
    }

    public void addJournalEntry(String title, String content) {
        JournalEntry entry = new JournalEntry(nextId, title, content);
        journal.put(nextId, entry);
        nextId++;
        
    }

    public String getJournalEntry(int id) {
        JournalEntry entry = journal.get(id);
        System.out.println(entry.toString());
        return "Invalid ID.";
    }

    public void deleteJournalEntry(int id) {
        journal.remove(id);
    }

    public String getEntries() {
        System.out.println("All entries:");
        String entries = "";
        for (JournalEntry entry : journal.values()) {
            entries += entry.toString() + "\n";
        }
        return entries;
    }

    public void saveToFile() {

        List<Map<String, String>> data = new ArrayList<>();
        for (JournalEntry e : journal.values()) {
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(e.getId()));
            map.put("timestamp", e.getTimestamp().toString());
            map.put("title", e.getTitle());
            map.put("content", e.getContent());
            data.add(map);
        }

        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.out.println("Save failed.");
        }
    }

    public void loadFromFile() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
    
            Type listType = new TypeToken<List<Map<String, String>>>(){}.getType();
            List<Map<String, String>> loaded = gson.fromJson(reader, listType);
    
            journal.clear();
            int maxId = 0;
    
            for (Map<String, String> map : loaded) {
                int id = Integer.parseInt(map.get("id"));
                String title = map.get("title");
                String content = map.get("content");
                LocalDateTime timestamp =
                    LocalDateTime.parse(map.get("timestamp"));
    
                JournalEntry entry = new JournalEntry(id, title, content, timestamp);
                journal.put(id, entry);
    
                if (id > maxId) maxId = id;
            }
    
            nextId = maxId + 1;
    
            System.out.println("Journal loaded.");
    
        } catch (IOException e) {
            System.out.println("No journal file found.");
        }
    }
}


