import java.util.ArrayList;
import java.util.Arrays;

public class boardMoves {
    static ArrayList<ArrayList<String>> allBoard = new ArrayList<ArrayList<String>>();
    public static ArrayList<String> readMoves(String pathInput, String pathOutput){ //reads move.txt file and converts the data to an arraylist
        String[] inputAsArray = FileInput.readFile(pathInput, true, false); //method reads input file
        ArrayList<String> inputAsList = new ArrayList<String> (Arrays.asList(inputAsArray)); //input file has been turned to arraylist from string[]
        String[] movesAsArray = (inputAsList.get(0)).split(" ", 30); //inputs have been splited
        ArrayList<String> movesAsList = new ArrayList<String> (Arrays.asList(movesAsArray)); //because split() method returns String[], I had to convert it to arraylist
        FileOutput.writeToFile(pathOutput, "\nYour movement is:",true,true);
        for (String movement : movesAsList){ //switch case situation to define how white ball should move
            FileOutput.writeToFile(pathOutput, movement + " ",true,false);
            switch (movement){
                case "L":
                    whiteBall.turnLeft(allBoard);
                    break;
                case "R":
                    whiteBall.turnRight(allBoard);
                    break;
                case "U":
                    whiteBall.turnUp(allBoard);
                    break;
                case "D":
                    whiteBall.turnDown(allBoard);
                    break;
                default:
                    System.out.println("there is no such a movement command");
            }
        }
        return movesAsList;
    }
    public static void readBoard(String pathBoard, String pathOutput){ //reads board.txt file and converts the data to an arraylist
        FileOutput.writeToFile(pathOutput, "Game board:\n",true,false); //I created an arraylist for the last version of the board
        String[] boardAsArray = FileInput.readFile(pathBoard, true, true); //method reads board at the input file
        ArrayList<ArrayList<String>> boardAsList = new ArrayList<ArrayList<String>> (); //converts input data from array to arraylist
        for (String row : boardAsArray){ //makes a 2d array
            String[] rowAsArray = row.split(" ");
            ArrayList<String> rowAsList = new ArrayList<String> (Arrays.asList(rowAsArray));
            for (String letter : rowAsList){
                FileOutput.writeToFile(pathOutput, letter + " ",true,false);
                }
            FileOutput.writeToFile(pathOutput, "",true,true);
            allBoard.add(rowAsList);
        }
    }
    public static void writeResults(String pathOutput){
        FileOutput.writeToFile(pathOutput, "\n\nYour output is:",true,true);
        for(ArrayList<String> row : allBoard){
            for(String letter : row){
                FileOutput.writeToFile(pathOutput, letter + " ", true, false);}
            FileOutput.writeToFile(pathOutput, "", true, true);
        }
    }
}
