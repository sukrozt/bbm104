
public class Main {
    public static void main(String[] args) {
        try{
            FileOutput.writeToFile("output.txt", "", false, false); //cleans the output file
            boardMoves.readBoard("board.txt", "output.txt"); //reads board file, then converts it arraylist
            boardMoves.readMoves("move.txt", "output.txt"); //reads move file, then calls the methods suitable
            boardMoves.writeResults("output.txt"); //writes results to output file
            FileOutput.writeToFile("output.txt", "\nScore: " + whiteBall.score, true, true);
        }catch(Exception e){
            FileOutput.writeToFile("output.txt", "An error occured.", false, true);
        }
    }
}