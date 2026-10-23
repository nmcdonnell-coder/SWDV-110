package edu.cwi.swdv110.contacts;
import java.util.List;
public final class ContactReport {
    private static final String ROW = "%-22s %-30s %-15s %-9s%n";
    private ContactReport() { }
    public static String buildReport(List<Contact> contacts) {
        StringBuilder report = new StringBuilder();
        int invalidEmails = 0;
        int invalidPhones = 0;
        report.append(String.format(ROW, "NAME", "EMAIL", "PHONE", "USERNAME"));
        report.append("-".repeat(78)).append(System.lineSeparator());
        for (Contact contact : contacts) {
            String email = contact.email();
            String phone = contact.phone();
            if (!ContactParser.isValidEmail(email)) {
                email = "!! " + email;
                invalidEmails++;
            }
            if (phone.equals("INVALID")) {
                invalidPhones++;
            }
            String username = ContactParser.makeUsername(contact.name());

            report.append(String.format(ROW,
                    contact.name(), email, phone, username));
        }
        report.append("-".repeat(78)).append(System.lineSeparator());
        report.append(String.format(
                "Total contacts: %d | Invalid emails: %d | Invalid phones: %d",
                contacts.size(), invalidEmails, invalidPhones));
        return report.toString();
    }
}
