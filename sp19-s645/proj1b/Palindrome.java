public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> mydeque = new LinkedListDeque<>();

        for (int i = 0; i < word.length(); i++) {
            mydeque.addLast(word.charAt(i));
        }

        return mydeque;
    }
    public boolean isPalindrome(String word) {

        Deque<Character> yourdeque = wordToDeque(word);

        if (yourdeque.size() == 0 || yourdeque.size() == 1) {
            return true;
        } else {

            if (yourdeque.removeFirst() == yourdeque.removeLast()) {
                return isPalindrome(help(yourdeque));
            } else {
                return false;
            }
        }
    }






    private String help(Deque d) {
        String someth = "";
        while (d.size() > 0) {
            someth += d.removeFirst();
        }
        return someth;
    }
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> herdeque = wordToDeque(word);

        if (herdeque.size() == 0 || herdeque.size() == 1) {
            return true;
        } else {

            if (cc.equalChars(herdeque.removeFirst(), herdeque.removeLast())) {
                return isPalindrome(help(herdeque), cc);
            } else {
                return false;
            }
        }
    }

    public static void main (String [] args) {
        Palindrome p = new Palindrome();
        System.out.println(p.isPalindrome("isadi"));
    }
}
