import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Book extends Library {
    private String typeOfBook;
    private boolean isBorrowed;
    private boolean isExtended;
    private boolean isReturned;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private LocalDateTime dueDate;
    private int ID = 1;
    private String reportOfBookBorrowed;
    private String reportOfBookLibrary;
    private static int numberOfPrinted;
    private static int numberOfHandwritten;
    private long Fee;

    public Book(String typeOfBook) throws Exception {
        setTypeOfBook(typeOfBook);
        setIDOfBook();
        isBorrowed = false;
    }

    public int getIDOfBook() {
        return ID;
    }

    public String getTypeOfBook() {
        return typeOfBook;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public static int getNumberOfHandwritten() {
        return numberOfHandwritten;
    }

    public static int getNumberOfPrinted() {
        return numberOfPrinted;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public long getFee() {
        return Fee;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setIDOfBook() {
        this.ID = ID + library.size();
    }

    public void setDueDate(String dueDate) { //sets due date for every book as the type of member that borrows
        String[] date = dueDate.split("-");
        this.dueDate = LocalDateTime.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]),
                Integer.parseInt(date[2]), 0, 0, 0);
    }

    public void setTypeOfBook(String typeOfBook) throws Exception { //sets type of book for every book as the given letter at the input file
        if (typeOfBook.equals("H")) {
            this.typeOfBook = "Handwritten";
            numberOfHandwritten++;
        } else if (typeOfBook.equals("P")) {
            this.typeOfBook = "Printed";
            numberOfPrinted++;
        } else
            throw new Exception("there's no type of book in this library");

    }

    public void setBorrowed(boolean borrowed) throws Exception {
        if (this.isBorrowed && borrowed)
            throw new Exception("You can not read this book!");
        this.isBorrowed = borrowed;
    }

    public void setBorrowDate(String borrowDate) { //sets the date which book has borrowed
        String[] date = borrowDate.split("-");
        this.borrowDate = LocalDateTime.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]),
                Integer.parseInt(date[2]), 0, 0, 0);

    }
    public void setReturnDate(String returnDate){ //sets the date which is book has returned
        String[] date = returnDate.split("-");
        this.returnDate = LocalDateTime.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]),
                Integer.parseInt(date[2]), 0, 0, 0);
        setFee();
    }

    public String getReportOfBookBorrowed() {
        return reportOfBookBorrowed;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public String getReportOfBookLibrary() {
        return reportOfBookLibrary;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReportOfBookBorrowed(String reportOfBookBorrowed) {
        this.reportOfBookBorrowed = reportOfBookBorrowed;
    }

    public void setReportOfBookLibrary(String reportOfBookLibrary) {
        this.reportOfBookLibrary = reportOfBookLibrary;
    }

    public void setFee() { //calculates the fee of late returned book
        if (dueDate.isAfter(returnDate) || dueDate.isEqual(returnDate))
            this.Fee = 0;
        else {
            this.Fee = ChronoUnit.DAYS.between(dueDate, returnDate);
        }
    }

    public void setExtended(boolean extended) {
        isExtended = extended;
    }

    public boolean isExtended() {
        return isExtended;
    }


    public String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(dateTime);
    }
    public static void borrowBook(Integer bookID, Integer memberID, String date, String outputFile) { //method for borrow a book
        try {
            for (Book book : Library.library) {
                if (bookID.equals(book.getIDOfBook())) {
                    for (Member member : Member.members) {
                        if (memberID.equals(member.getID())) {
                            book.setBorrowed(true);
                            Library.setNumberOfBorrowed(1);
                            book.setBorrowDate(date);
                            if (member.getTypeOfMember().equals("Student")) { //sets the due date for student member
                                book.setDueDate(book.formatDateTime(book.borrowDate.plusDays(7)));
                                if(member.getLimitOfStudent() < 2)
                                    member.setLimitOfStudent();
                                else
                                    throw new NumberFormatException("You have exceeded the borrowing limit!");
                            } else if (member.getTypeOfMember().equals("Academic")) { //sets the due date for academic member
                                book.setDueDate(book.formatDateTime(book.borrowDate.plusDays(14)));
                                if(member.getLimitOfAcademic() < 4)
                                    member.setLimitOfAcademic();
                                else
                                    throw new NumberFormatException("You have exceeded the borrowing limit!");
                            }
                            book.setReportOfBookBorrowed(String.format("The book [%d] was borrowed by member [%d] at %s", //sets report of book for history of library
                                    book.getIDOfBook(), member.getID(), date));
                            FileOutput.writeToFile(outputFile, book.reportOfBookBorrowed, true, true);
                            break;
                        }
                    }
                }
            }
        }catch (NumberFormatException e){
            FileOutput.writeToFile(outputFile, e.getMessage(), true, true);

        }
        catch (Exception e) {
            FileOutput.writeToFile(outputFile, "You cannot borrow this book!", true, true);
        }
    }

    public static void returnBook(Integer bookID, Integer memberID, String date, String outputFile) {
        try {
            for (Book book : Library.library) {
                if (bookID.equals(book.getIDOfBook())) {
                    for (Member member : Member.members) {
                        if (memberID.equals(member.getID())) {
                            book.isReturned = true;
                            book.setBorrowed(false);
                            book.setBorrowDate(date);
                            book.setReturnDate(date);
                            if(book.getReportOfBookLibrary() != null)
                                Library.setNumberOfInLibrary(-1);
                            else if (book.getReportOfBookBorrowed() != null) {
                                Library.setNumberOfBorrowed(-1);
                            }
                            FileOutput.writeToFile(outputFile, String.format("The book [%d] was returned by member [%d] at %s Fee: %d", book.getIDOfBook(), member.getID(), date, book.Fee), true, true);
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            FileOutput.writeToFile(outputFile, "You cannot return this book!", true, true);
        }
    }

    public static void extendBook(Integer bookID, Integer memberID, String date, String outputFile) {
        try {
            for (Book book : Library.library) {
                if (bookID.equals(book.getIDOfBook())) {
                    if(book.isExtended)
                        throw new Exception();
                    for (Member member : Member.members) {
                        if (memberID.equals(member.getID())) {
                            book.isExtended = true;
                            if(member.getTypeOfMember().equals("Student")) { //sets the due date for student member
                                book.setDueDate(book.formatDateTime((book.getDueDate().plusDays(7))));
                            }
                            else if (member.getTypeOfMember().equals("Academic")){ //sets the due date for academic member
                                book.setDueDate(book.formatDateTime(book.getDueDate().plusDays(14)));
                            }
                            FileOutput.writeToFile(outputFile, String.format("The deadline of book [%d] was extended by " +
                                    "member [%d] at %s", book.getIDOfBook(), member.getID(), date), true, true);
                            FileOutput.writeToFile(outputFile, String.format("TNew deadline of book [%d] is %s", book.getIDOfBook(), book.formatDateTime(book.dueDate)), true, true);
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            FileOutput.writeToFile(outputFile, "You cannot extend the deadline!", true, true);
        }
    }

    public static void readInLibrary(Integer bookID, Integer memberID, String date, String outputFile) {
        try {
            for (Book book : Library.library) {
                if (bookID.equals(book.getIDOfBook())) {
                    for (Member member : Member.members) {
                        if (memberID.equals(member.getID())) {
                            if(member.getTypeOfMember().equals("Student") && book.typeOfBook.equals("Handwritten"))
                                throw new Exception("Students can not read handwritten books!"); //for handwritten book rule
                            Library.setNumberOfInLibrary(1);
                            book.setBorrowed(true);
                            book.setReportOfBookLibrary(String.format("The book [%d] was read in library by member [%d] at %s",
                                    book.getIDOfBook(), member.getID(), date)); //sets report of book for history of library
                            FileOutput.writeToFile(outputFile, book.reportOfBookLibrary, true, true);
                            break;
                        }

                    }
                }

            }
        }
        catch (Exception e) {
            FileOutput.writeToFile(outputFile, e.getMessage(), true, true);
        }
    }
}