import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.*;
import java.io.FileWriter;

public class App {
    public static void main(String[] args) throws Exception {

        String entries = getJournalEntries();

        boolean running = true;
        
        Scanner scanny = new Scanner(System.in);
        while(running) {
            System.out.println("Select one of the options:");
            System.out.println("1. Create a new entry.          2. View previous entries.           3. Close journal");
            String response = scanny.nextLine();
            int num = Integer.parseInt(response);
            switch(num) {
                case 1:
                    entries = newEntry(entries);
                    System.out.println("Entry added!");
                    break;
                case 2:
                    System.out.println(entries);;
                    break;
                case 3:
                    running = false;
                    break;
                default: 
                    System.out.println("Invalid input. Please enter 1, 2, or 3.");
            }
        }
        closeJournal(entries);
        System.out.println("Journal closed, exiting program.");
        scanny.close();
    }

    public static String newEntry(String journal) {
        Scanner scanny = new Scanner(System.in);

        System.out.println("Type an entry: ");
        journal += scanny.nextLine() + "\n";

        return journal;
    }

    public static String getJournalEntries(){

        File journalFile = new File("journal.txt");

        String entries = "";

        if (journalFile.exists()) {
            try (Scanner readJournal = new Scanner (journalFile)){
                while (readJournal.hasNext()) {
                    entries += readJournal.nextLine() + "\n";
                }
                System.out.println("Entries loaded.");
            } catch (FileNotFoundException e){
                System.out.println("Unable to load entries.");
            }
        } else {
            System.out.println("No journal detected. Creating new one.");
        }

        return entries;
    }

    public static void closeJournal(String entries) {
        try (FileWriter writer = new FileWriter("journal.txt")) {
            writer.write(entries);
        } catch (IOException e) {
            System.out.println("Unable to save file.");
        }
    }
}
