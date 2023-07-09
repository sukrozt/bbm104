public class Commands extends Library{
    public void readCommand(String[] line, String outputFile) throws Exception { //reads the command and calls the suitable method
        String command = line[0];
        switch (command) {
            case "addBook":
                Library.addBook(line[1],outputFile);
                break;
            case "addMember":
                Member.addMember(line[1], outputFile);
                break;
            case "borrowBook":
                Book.borrowBook(Integer.parseInt(line[1]), Integer.parseInt(line[2]), line[3], outputFile);
                break;
            case "returnBook":
                Book.returnBook(Integer.parseInt(line[1]), Integer.parseInt(line[2]), line[3], outputFile);
                break;
            case "extendBook":
                Book.extendBook(Integer.parseInt(line[1]), Integer.parseInt(line[2]), line[3], outputFile);
                break;
            case "readInLibrary":
                Book.readInLibrary(Integer.parseInt(line[1]), Integer.parseInt(line[2]), line[3], outputFile);
                break;
            case "getTheHistory":
                getTheHistory(outputFile);
                break;
        }
    }
}
