import java.util.ArrayList;

public class Library {
    protected static ArrayList<Book> library = new ArrayList<>();
    private static int numberOfBorrowed;
    private static int numberOfInLibrary;

    public Library(){
    }
    public int getNumberOfBorrowed() {
        return numberOfBorrowed;
    }

    public int getNumberOfInLibrary() {
        return numberOfInLibrary;
    }

    public static void setNumberOfBorrowed(int num) {
        if(num < 0)
            numberOfBorrowed--;
        else
            numberOfBorrowed++;
    }

    public static void setNumberOfInLibrary(int num) {
        if(num > 0)
            numberOfInLibrary++;
        else
            numberOfInLibrary--;
    }

    public static void addBook(String typeOfBook, String outputFile) throws Exception {
        Book book = new Book(typeOfBook);
        library.add(book);
        FileOutput.writeToFile(outputFile, String.format("Created new book: %s [id: %d]", book.getTypeOfBook(),
                book.getIDOfBook()), true, true);
    }
    public void getTheHistory(String outputFile){ //prints every object of library at history
        FileOutput.writeToFile(outputFile, "History of library:", true, true);
        FileOutput.writeToFile(outputFile, "\nNumber of students: " + Member.getNumberOfStudent(), true, true);
        for(Member member : Member.members){
            if(member.getTypeOfMember().equals("Student"))
                FileOutput.writeToFile(outputFile, String.format("Student [id: %d]", member.getID()), true, true);
        }
        FileOutput.writeToFile(outputFile, "\nNumber of academics: " + Member.getNumberOfAcademic(), true, true);
        for(Member member : Member.members){
            if(member.getTypeOfMember().equals("Academic"))
                FileOutput.writeToFile(outputFile, String.format("Academic [id: %d]", member.getID()), true, true);
        }
        FileOutput.writeToFile(outputFile, "\nNumber of printed books: " + Book.getNumberOfPrinted(), true, true);
        for(Book book : library){
            if(book.getTypeOfBook().equals("Printed"))
                FileOutput.writeToFile(outputFile, String.format("Printed [id: %d]", book.getIDOfBook()), true, true);
        }
        FileOutput.writeToFile(outputFile, "\nNumber of handwritten books: " + Book.getNumberOfHandwritten(), true, true);
        for(Book book : library){
            if(book.getTypeOfBook().equals("Handwritten"))
                FileOutput.writeToFile(outputFile, String.format("Handwritten [id: %d]", book.getIDOfBook()), true, true);
        }
        FileOutput.writeToFile(outputFile, "\nNumber of borrowed books: " + numberOfBorrowed, true, true);
        for(Book book : library){
            if(book.isBorrowed() && book.getReportOfBookBorrowed() != null)
                FileOutput.writeToFile(outputFile, book.getReportOfBookBorrowed(), true, true);
        }
        FileOutput.writeToFile(outputFile, "\nNumber of books read in library: " + numberOfInLibrary, true, true);
        for(Book book : library){
            if(!book.isReturned() && book.getReportOfBookLibrary() != null)
                FileOutput.writeToFile(outputFile, book.getReportOfBookLibrary(), true, true);
        }
    }
}
