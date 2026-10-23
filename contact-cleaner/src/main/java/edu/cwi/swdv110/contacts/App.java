package edu.cwi.swdv110.contacts;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class App {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("contacts.txt"));
        List<Contact> contacts = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            try {
                Contact contact = ContactParser.parseLine(line);
                contacts.add(contact);
            } catch (IllegalArgumentException e) {
                System.out.println("Skipped line " + (i + 1) + ": " + e.getMessage());
            }
        }
System.out.println(ContactReport.buildReport(contacts));
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Search (or 'quit'): ");
            String search = sc.nextLine().trim();
            if (search.equalsIgnoreCase("quit")) {
                System.out.println("Bye!");
                break;
            }
            int matches = 0;
            for (Contact contact : contacts) {
                if (contact.name().toLowerCase().contains(search.toLowerCase())) {
                    System.out.println(contact.name() + " "
                            + ContactParser.maskEmail(contact.email()));
                    matches++;
                }
            }
            System.out.println(matches + " match(es)");
            System.out.println();
        }
        sc.close();
    }
}

