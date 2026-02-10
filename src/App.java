import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        Journal journal = new Journal();
        journal.loadFromFile();

        boolean running = true;
        
        Scanner scanny = new Scanner(System.in);
        while(running) {
            System.out.println("Select one of the options:");
            System.out.println("1. Create a new entry.      2. Delete an entry.         3. View specific entry.         4. View all entries.        5. Close journal.");
            String response = scanny.nextLine();
            int num = Integer.parseInt(response);
            switch(num) {
                case 1:
                    System.out.println("Enter your title: ");
                    String title = scanny.nextLine();
                    System.out.println("Enter your content: ");
                    String content = scanny.nextLine();
                    journal.addJournalEntry(title, content);
                    break;
                case 2:
                    System.out.println("Enter the id of the entry you want to delete: ");
                    int id = scanny.nextInt();
                    scanny.nextLine();
                    journal.deleteJournalEntry(id);
                    break;
                case 3:
                    System.out.println("Enter the id of the entry you want to view: ");
                    id = scanny.nextInt();
                    scanny.nextLine();
                    System.out.println(journal.getJournalEntry(id));
                    break;
                case 4: 
                System.out.println(journal.getEntries());
                    break;
                case 5:
                    running = false;
                    break;
                default: 
                    System.out.println("Invalid input. Please enter 1-5.");
            }
        }
        journal.saveToFile();
        System.out.println("Journal closed, exiting program. Bye, bye.");
        scanny.close();
    }

    
}
