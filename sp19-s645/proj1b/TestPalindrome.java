import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertFalse(palindrome.isPalindrome("Abba"));
        assertTrue(palindrome.isPalindrome("aba"));
        assertTrue(palindrome.isPalindrome("mom"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertFalse(palindrome.isPalindrome("cat"));
        assertFalse(palindrome.isPalindrome("aaaaab"));
        assertFalse(palindrome.isPalindrome("light"));
        assertTrue(palindrome.isPalindrome("G"));
    }
    @Test
    public void testIsPalindromeOffByOne() {
        OffByOne test = new OffByOne();
        assertTrue(palindrome.isPalindrome("", test));
        assertTrue(palindrome.isPalindrome("a", test));
        assertTrue(palindrome.isPalindrome("G", test));
        assertTrue(palindrome.isPalindrome("flake", test));
        assertFalse(palindrome.isPalindrome("awesome", test));

    }
}

