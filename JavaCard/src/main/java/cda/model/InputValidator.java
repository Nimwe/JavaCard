package cda.model;

public class InputValidator {

    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean isValidName(String name) {
        return name != null && name.matches("^[A-Za-zÀ-ÖØ-öø-ÿ'\\-\\s]+$");
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("\\S+@\\S+\\.\\S+");
    }

    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("\\d{10,15}");
    }

    public static boolean isValidGitLink(String link) {
        return link == null || link.trim().isEmpty() || link.matches("^(https?://)?(www\\.)?github\\.com/\\S+/?$");
    }

    public static boolean isValidWebsite(String url) {
        return url == null || url.trim().isEmpty() || url.matches("^(https?://)?[\\w.-]+\\.[a-z]{2,}$");
    }

    public static boolean isChoiceSelected(String value) {
        return value != null && !value.trim().isEmpty();
    }
}

