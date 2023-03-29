import java.util.ArrayList;
import java.util.Collections;

public class whiteBall extends boardMoves {
    static int score = 0;

    public static void findScore(String letter) {
        switch (letter) {
            case "R":
                score += 10;
                break;
            case "Y":
                score += 5;
                break;
            case "B":
                score -= 5;
                break;
        }
    }
    public static void ballFallsHole(String pathOutput){
        writeResults(pathOutput);
        FileOutput.writeToFile(pathOutput, "\nGame Over!", true, true);
        FileOutput.writeToFile(pathOutput, "Score: " + whiteBall.score,true,true);
        System.exit(0);
    }

    public static void horizontalMove(ArrayList<String> row, int index, int theChosenIndex, int indexWhiteBall) {
        String theChosenLetter = row.get(index);
        switch (theChosenLetter) {
            case "H":
                row.set(indexWhiteBall, " ");
                ballFallsHole("output.txt");
            case "W":
                if (row.get(theChosenIndex).matches("[RYB]")) {
                    findScore(row.get(theChosenIndex));
                    row.set(theChosenIndex, "X");
                }
                Collections.swap(row, theChosenIndex, indexWhiteBall);
                break;
            default:
                if (theChosenLetter.matches("[RYB]")) {
                    findScore(theChosenLetter);
                    row.set(index, "X");
                }
                Collections.swap(row, index, indexWhiteBall);
                break;
        }
    }

    public static void verticalMove(ArrayList<ArrayList<String>> boardList, ArrayList<String> row, ArrayList<String> theRow, int rowIndex, int indexWB, int upDown) {
        switch (theRow.get(indexWB)) {
            case "H":
                row.set(indexWB, " ");  //WB disappears
                ballFallsHole("output.txt");
            case "W":
                findScore(boardList.get(rowIndex + upDown).get(indexWB));
                row.set(indexWB, boardList.get(rowIndex + upDown).get(indexWB)); //the place of WB will be the upper or the lower rows letter
                boardList.get(rowIndex + upDown).set(indexWB, "*"); //the upper or the lower letter will be "*"
                break;
            default:
                if (theRow.get(indexWB).matches("[RYB]")) {
                    findScore(theRow.get(indexWB));
                    theRow.set(indexWB, "X");
                }
                row.set(indexWB, theRow.get(indexWB));
                theRow.set(indexWB, "*");
                break;
        }
    }

    public static void turnRight(ArrayList<ArrayList<String>> boardList) {
        for (ArrayList<String> row : boardList) {
            int indexWhiteBall = row.indexOf("*");
            if (indexWhiteBall == -1) continue; //if there is no WB in row, passes
            int theLastIndex = row.size() - 1;
            if (indexWhiteBall == (theLastIndex)) {
                horizontalMove(row, 0, indexWhiteBall - 1, indexWhiteBall);
            } else {
                horizontalMove(row, indexWhiteBall + 1, indexWhiteBall - 1, indexWhiteBall);
            }
        }
    }

    public static void turnLeft(ArrayList<ArrayList<String>> boardList) {
        for (ArrayList<String> row : boardList) {
            int indexWhiteBall = row.indexOf("*");
            if (indexWhiteBall == -1) continue; //if there is no WB in row, passes
            if (indexWhiteBall == 0) {
                horizontalMove(row, boardList.size() - 1, indexWhiteBall - 1, indexWhiteBall);
            } else {
                horizontalMove(row, indexWhiteBall - 1, indexWhiteBall + 1, indexWhiteBall);
            }
        }
    }

    public static void turnUp(ArrayList<ArrayList<String>> boardList) {
        for (int rowIndex = 0; rowIndex < boardList.size(); rowIndex++) {
            ArrayList<String> row = boardList.get(rowIndex);
            int indexWB = row.indexOf("*");
            if (indexWB == -1) continue;  //if there is no WB in row, passes
            int theLastRowIndex = boardList.size() - 1;
            ArrayList<String> theLastRow = boardList.get(theLastRowIndex);
            ArrayList<String> theFirstRow = boardList.get(0);
            if (rowIndex == 0) {
                verticalMove(boardList, row, theLastRow, rowIndex, indexWB, 1);
            } else if (rowIndex == boardList.size() - 1) {
                ArrayList<String> upperRow = boardList.get(rowIndex - 1);
                String upperLetter = upperRow.get(indexWB);
                switch (upperLetter) {
                    case "H":
                        row.set(indexWB, " ");
                        ballFallsHole("output.txt");
                    case "W":
                        row.set(indexWB, theFirstRow.get(indexWB));
                        theFirstRow.set(indexWB, "*");
                        break;
                    default:
                        if (upperLetter.matches("[RYB]")) {
                            findScore(upperLetter);
                            upperRow.set(indexWB, "X");
                        }
                        row.set(indexWB, upperRow.get(indexWB));
                        upperRow.set(indexWB, "*");
                        break;
                }
            } else {
                verticalMove(boardList, row, boardList.get(rowIndex - 1), rowIndex, indexWB,1);
            }
            break;
        }
    }

    public static void turnDown(ArrayList<ArrayList<String>> boardList) {
        for (int rowIndex = 0; rowIndex < boardList.size(); rowIndex++) {
            ArrayList<String> row = boardList.get(rowIndex);
            int indexWB = row.indexOf("*");
            if (indexWB == -1) continue; //if there is no WB in row, passes
            int lastRowIndex = boardList.size() - 1;
            ArrayList<String> theLastRow = boardList.get(lastRowIndex);
            if (rowIndex == lastRowIndex) {
                verticalMove(boardList, row, boardList.get(0), rowIndex, indexWB,-1);
            } else if (rowIndex == 0) {
                ArrayList<String> lowerRow = boardList.get(rowIndex + 1);
                String lowerLetter = lowerRow.get(indexWB);
                switch (lowerLetter) {
                    case "H":
                        row.set(indexWB, " ");
                        ballFallsHole("output.txt");
                    case "W":
                        row.set(indexWB, theLastRow.get(indexWB));
                        theLastRow.set(indexWB, "*");
                        break;
                    default:
                        if (lowerLetter.matches("[RYB]")) {
                            findScore(lowerLetter);
                            lowerRow.set(indexWB, "X");
                        }
                        row.set(indexWB, lowerRow.get(indexWB));
                        lowerRow.set(indexWB, "*");
                        break;
                }
            } else {
                verticalMove(boardList, row, boardList.get(rowIndex + 1), rowIndex, indexWB,-1);
            }
            break;
        }
    }
}
