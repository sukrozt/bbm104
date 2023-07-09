import java.util.Arrays;
import java.util.LinkedList;

public class Palindrome {
    public static boolean isPalindrome(String word) {
        String[] cleanWord = word.split("[ ,;:.]");
        LinkedList<String> linkedList = new LinkedList<String>(Arrays.asList(cleanWord));
        int a = linkedList.size() - 1;
        for (int i = 0; i <= linkedList.size() / 2; i++) {
            if (linkedList.get(i).equals(linkedList.get(a)))
                a--;
            else
                return false;
        }
        return true;
    }
}
