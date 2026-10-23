package edu.cwi.swdv110.contacts;
public final class ContactParser {
private ContactParser() { }
public static String titleCase(String word) {
    if (word.isEmpty()) {
        return "";
    }
    StringBuilder result = new StringBuilder(word.toLowerCase());
    result.setCharAt(0, Character.toUpperCase(result.charAt(0)));
    for (int i = 1; i < result.length(); i++) {
        if (result.charAt(i - 1) == '-') {
            result.setCharAt(i, Character.toUpperCase(result.charAt(i)));
        }
    }
    return result.toString();
}
public static String normalizeName(String raw) {
    String name = raw.trim().replaceAll("\\s+", " ");
    if (name.isEmpty()) {
        return "";
    }
    int commaIndex = name.indexOf(',');
    if (commaIndex != -1) {
        String lastName = name.substring(0, commaIndex).trim();
        String firstName = name.substring(commaIndex + 1).trim();
        name = firstName + " " + lastName;
    }
    String[] words = name.split("\\s+");

    for (int i = 0; i < words.length; i++) {
        words[i] = titleCase(words[i]);
    }
    return String.join(" ", words);
}
public static boolean isValidEmail(String email) {
    String value = email.trim();
    if (value.contains(" ")) {
        return false;
    }
    int atIndex = value.indexOf('@');
    if (atIndex < 1 || atIndex != value.lastIndexOf('@')) {
        return false;
    }
    String domain = value.substring(atIndex + 1);
    if (domain.isEmpty()) {
        return false;
    }
     int dotIndex = domain.indexOf('.');
    if (dotIndex < 1 || domain.endsWith(".") || domain.contains("..")) {
        return false;
    }
    return true;
}
public static String normalizeEmail(String email) {
    return email.trim().toLowerCase();
}
public static String maskEmail(String email) {
    int atIndex = email.indexOf('@');
    if (atIndex < 2) {
        return email;
    }
    return email.substring(0, 1)
            + "*".repeat(atIndex - 1)
            + email.substring(atIndex);
}
public static String formatPhone(String raw) {
    StringBuilder digits = new StringBuilder();
    for (char character : raw.toCharArray()) {
        if (Character.isDigit(character)) {
            digits.append(character);
        }
    }
    if (digits.length() == 11 && digits.charAt(0) == '1') {
        digits.deleteCharAt(0);
    }
    if (digits.length() != 10) {
        return "INVALID";
    }
    return "(" + digits.substring(0, 3) + ") "
            + digits.substring(3, 6) + "-"
            + digits.substring(6);
}
public static String makeUsername(String fullName) {
    String[] words = fullName.trim().split("\\s+");
    String username;
    if (words.length == 1) {
        username = words[0];
    } else {
        username = words[0].substring(0, 1)
                + words[words.length - 1];
    }
    username = username.toLowerCase().replaceAll("[^a-z]", "");

    if (username.length() > 8) {
        username = username.substring(0, 8);
    }
    return username;
}
public static Contact parseLine(String line) {
    String[] fields = line.split("\\|", -1);
    if (fields.length != 3) {
        throw new IllegalArgumentException(
                "expected 3 fields but found " + fields.length);
    }
    String name = normalizeName(fields[0]);
    String email = normalizeEmail(fields[1]);
    String phone = formatPhone(fields[2]);
    if (name.isEmpty()) {
        throw new IllegalArgumentException("name is blank");
    }
    return new Contact(name, email, phone);
}
}
