import java.lang.Math;
import java.util.*;

public class Main {

    public static int exponential(int x, int y) { //I could use Math.pow() instead
        int k = 1;
        for (int i = 0; i < y; i++) {
            k *= x;
        }
        return k;
    }

    public static int summation(ArrayList<Integer> array) {  //method to find summation of elements in the array
        int summation = 0;
        for (int i = 0; i < array.size(); i++) {
            summation += array.get(i);
        }
        return summation;
    }

    public static boolean isPrime(int number) {
        if (number == 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void armstrongNumber(int lastNumber) {  //I worked with arraylist on this method
        FileOutput.writeToFile("output.txt", "Armstrong numbers up to " + lastNumber + ":", true, true);
        for (int i = 1; i <= lastNumber; i++) {
            if (i < 10) {
                FileOutput.writeToFile("output.txt", i + " ", true, false);
            } else {
                ArrayList<Integer> arrayExpDigits = new ArrayList<Integer>();
                String stringNumber = Integer.toString(i);  //to convert i to str
                int exponent = stringNumber.length();  //to find count of digit of the number
                int copyOfi = i;  //to make i local, I created a new variable as copyOfi
                while (true) {
                    int lastDigit = copyOfi % 10;
                    arrayExpDigits.add(exponential(lastDigit, exponent));
                    copyOfi /= 10;
                    if (copyOfi < 10) {
                        arrayExpDigits.add(exponential(copyOfi, exponent));
                        break;
                    }
                }
                if (i == summation(arrayExpDigits)) { //if the number is an armstrong number, method prints it
                    FileOutput.writeToFile("output.txt", i + " ", true, false);
                }
            }
        }
        FileOutput.writeToFile("output.txt", "\n", true, true);
    }

    public static void emirpNumber(int number) {
        FileOutput.writeToFile("output.txt", "Emirp numbers up to " + number + ":", true, true);
        for (int i = 1; i <= number; i++) {
            String strNum = Integer.toString(i); //i converted number to string for reversing
            String reversed = new StringBuilder(strNum).reverse().toString(); //reversed it
            int reversedPrimeNum = Integer.parseInt(reversed);  //converted to integer again
            if (isPrime(reversedPrimeNum) && isPrime(i) && reversedPrimeNum != i) { //checks the number if its prime, also the reversed form is prime and not palindromic
                FileOutput.writeToFile("output.txt", i + " ", true, false);
            }
        }
        FileOutput.writeToFile("output.txt", "\n", true, true);
    }

    public static ArrayList<Integer> findDivisiors(int number) { //to find divisors of a number
        ArrayList<Integer> allDivisor = new ArrayList<Integer>();
        for (int i = 1; i < number; i++) {
            if (number % i == 0) { //if iterator can divide number, method adds the number to "allDivisor" list
                allDivisor.add(i);
            }
        }
        return allDivisor;
    }

    public static void abundantNumber(int number) {
        FileOutput.writeToFile("output.txt", "Abundant numbers up to " + number + ":", true, true);
        for (int i = 1; i <= number; i++) {
            int summationDivisors = summation(findDivisiors(i)); //first method finds the summation of divisors arraylist
            if (summationDivisors > i) {
                FileOutput.writeToFile("output.txt", i + " ", true, false);
            }
        }
        FileOutput.writeToFile("output.txt", "\n", true, true);
    }

    public static void sortAscending(ArrayList<Integer> numbers) {
        Collections.sort(numbers); //firstly,  method sorts all elements in given arraylist
        for (int i = 0; i < numbers.size(); i++) {
            FileOutput.writeToFile("output.txt", numbers.get(i) + " ", true, false); //then prints them respectively
        }
        FileOutput.writeToFile("output.txt", "\n", true, false);
    }

    public static void sortDescending(ArrayList<Integer> numbers) {
        Collections.sort(numbers); //firstly,  method sorts all elements in given arraylist
        for (int i = numbers.size() - 1; i > -1; i--) {
            FileOutput.writeToFile("output.txt", numbers.get(i) + " ", true, false); //then prints them with a reverse order
        }
        FileOutput.writeToFile("output.txt", "\n", true, false);
    }

    public static void main(String[] args) {
        try {
            String inputPath = args[0];
            FileOutput.writeToFile("output.txt", "", false, false); //cleans the output.txt file
            FileInput input = new FileInput();
            String[] file = input.readFile(inputPath, true, false); //reads input file
            List<String> listfile = Arrays.asList(file); //I converted it to a list to use contains() method
            for (int i = 0; i < listfile.size(); i++) { //loop checks all elements of list to find what is asked
                if ((listfile.get(i)).contains("Armstrong numbers up to:")) {
                    int number = Integer.parseInt(listfile.get(i + 1)); //when there is a command, the next line given is used
                    armstrongNumber(number);
                } else if ((listfile.get(i)).contains("Emirp numbers up to:")) {
                    int number = Integer.parseInt(listfile.get(i + 1));
                    emirpNumber(number);
                } else if ((listfile.get(i)).contains("Abundant numbers up to:")) {
                    int number = Integer.parseInt(listfile.get(i + 1));
                    abundantNumber(number);
                } else if ((listfile.get(i)).contains("Ascending order sorting:")) {
                    ArrayList<Integer> ascNumbers = new ArrayList<Integer>();
                    FileOutput.writeToFile("output.txt", "Ascending order sorting:", true, true);
                    while (true) { //all numbers given after command has been added to an arraylist to sort increasingly
                        int number = Integer.parseInt(listfile.get(i + 1));
                        ascNumbers.add(number);
                        if (number == -1) { //method stops adding when there is -1
                            break;
                        }
                        sortAscending(ascNumbers);
                        i++;
                    }
                    FileOutput.writeToFile("output.txt", "", true, true);
                } else if ((listfile.get(i)).contains("Descending order sorting:")) {
                    ArrayList<Integer> descNumbers = new ArrayList<Integer>();
                    FileOutput.writeToFile("output.txt", "Descending order sorting:", true, true);
                    while (true) { //all numbers given after command has been added to an arraylist to sort decreasingly
                        int number = Integer.parseInt(listfile.get(i + 1));
                        descNumbers.add(number);
                        if (number == -1) { //method stops adding when there is -1
                            break;
                        }
                        sortDescending(descNumbers);
                        i++;
                    }
                    FileOutput.writeToFile("output.txt", "", true, true);
                } else if ((listfile.get(i)).contains("Exit")) {
                    FileOutput.writeToFile("output.txt", "Finished...", true, false);
                    System.exit(0); //to exit
                }
            }
        }
        catch (Exception e) {
            FileOutput.writeToFile("output.txt", "An error occured.", false, true);
        }

    }

}

//Sukriye Ozturk 2210356110
