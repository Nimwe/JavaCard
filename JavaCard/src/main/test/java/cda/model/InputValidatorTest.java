package cda.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    @Test
    void testIsNotEmpty() {
        assertTrue(InputValidator.isNotEmpty("John"));
        assertFalse(InputValidator.isNotEmpty(""));
        assertFalse(InputValidator.isNotEmpty("   "));
        assertFalse(InputValidator.isNotEmpty(null));
    }

    @Test
    void testIsValidName() {
        assertTrue(InputValidator.isValidName("Jean-Pierre"));
        assertTrue(InputValidator.isValidName("Élodie"));
        assertTrue(InputValidator.isValidName("O'Connor"));
        assertFalse(InputValidator.isValidName("123"));
        assertFalse(InputValidator.isValidName("John@"));
        assertFalse(InputValidator.isValidName(null));
    }

    @Test
    void testIsValidEmail() {
        assertTrue(InputValidator.isValidEmail("user@example.com"));
        assertTrue(InputValidator.isValidEmail("user.name@domain.co.uk"));
        assertFalse(InputValidator.isValidEmail("invalid-email"));
        assertFalse(InputValidator.isValidEmail("user@.com"));
        assertFalse(InputValidator.isValidEmail(null));
    }

    @Test
    void testIsValidPhoneNumber() {
        assertTrue(InputValidator.isValidPhoneNumber("0123456789"));
        assertTrue(InputValidator.isValidPhoneNumber("0123456789012"));
        assertFalse(InputValidator.isValidPhoneNumber("01234"));
        assertFalse(InputValidator.isValidPhoneNumber("abcdefghij"));
        assertFalse(InputValidator.isValidPhoneNumber(null));
    }

    @Test
    void testIsValidGitHubLink() {
        assertTrue(InputValidator.isValidGitLink("https://github.com/username"));
        assertTrue(InputValidator.isValidGitLink("github.com/username"));
        assertTrue(InputValidator.isValidGitLink(""));
        assertTrue(InputValidator.isValidGitLink(null));
        assertFalse(InputValidator.isValidGitLink("https://gitlab.com/username"));
        assertFalse(InputValidator.isValidGitLink("not-a-link"));
    }

    @Test
    void testIsValidWebsite() {
        assertTrue(InputValidator.isValidWebsite("https://example.com"));
        assertTrue(InputValidator.isValidWebsite("example.com"));
        assertTrue(InputValidator.isValidWebsite(""));
        assertTrue(InputValidator.isValidWebsite(null));
        assertFalse(InputValidator.isValidWebsite("invalid-website"));
        assertFalse(InputValidator.isValidWebsite("http:/incomplete.com"));
    }

    @Test
    void testIsChoiceSelected() {
        assertTrue(InputValidator.isChoiceSelected("MALE"));
        assertFalse(InputValidator.isChoiceSelected(""));
        assertFalse(InputValidator.isChoiceSelected("   "));
        assertFalse(InputValidator.isChoiceSelected(null));
    }
}

