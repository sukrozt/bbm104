import java.util.Stack;

public class paranthesisChecker {
    public static boolean parenthesisCheck(String word) {
        Stack<Character> stack = new Stack<>();
        String paranthesis = word.replaceAll("[0-9a-zA-Z ]", "");
        for (int i = 0; i < paranthesis.length(); i++) {
            char c = paranthesis.charAt(i);
            if (c == '(' ||c == ')'||c == '['||c == ']'||c == '{'||c == '}')
                stack.push(c);
        }
        int a = stack.size()-1;
        for (int i = 0; i <= (a+1) / 2; i++) {
            switch (stack.get(i)){
                case '(':
                    if (stack.get(a).equals(')'))
                        a--;
                    else
                        return false;
                case '{':
                    if (stack.get(a).equals('}'))
                        a--;
                    else
                        return false;
                case '[':
                    if (stack.get(a).equals('}'))
                        a--;
                    else
                        return false;
            }
        }
        return true;
    }
}
