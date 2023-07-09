import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
            String inputFile = args[0];
            String outputFile = args[1];
            String[] inputData = FileInput.readFile(inputFile, true, true);
            FileOutput.writeToFile(outputFile, "", false, false);
            String[] fullLine;
            for (int i = 0; i < inputData.length; i++) {
                fullLine = inputData[i].split(":");
                String[] askedData = fullLine[1].split("[\t]");
                if (inputData[i].contains("Convert")) {
                    String binaryNum = baseConverter.baseConvert(Integer.parseInt(askedData[1]));
                    FileOutput.writeToFile(outputFile, String.format("Equivalent of %s (base 10) in base 2 is: %s", askedData[1], binaryNum), true,true);
                }
                else if (inputData[i].contains("Count")) {
                    LinkedList<String> binaryList = countInBinary.countBinary(Integer.parseInt(askedData[1]));
                    FileOutput.writeToFile(outputFile, String.format("Counting from 1 up to %s in binary:", askedData[1]), true, false);
                    for(String number : binaryList){
                        FileOutput.writeToFile(outputFile, "\t" + number, true, false);
                    }
                    FileOutput.writeToFile(outputFile, "", true, true);

                }
                else if (inputData[i].contains("palindrome"))
                    if (Palindrome.isPalindrome(askedData[0]))
                        FileOutput.writeToFile(outputFile, String.format("'%s' is a palindrome.", askedData[1]), true, true);
                    else
                        FileOutput.writeToFile(outputFile, String.format("'%s' is not a palindrome.", askedData[1]), true, true);
                else if (inputData[i].contains("valid"))
                    if (paranthesisChecker.parenthesisCheck(askedData[1]))
                        FileOutput.writeToFile(outputFile, String.format("'%s' is a valid expression.", askedData[1]), true, true);
                    else
                        FileOutput.writeToFile(outputFile, String.format("'%s' is not a valid expression.", askedData[1]), true, true);

            }

        }
    }
