import java.util.ArrayList;

public class Member {
    public static ArrayList<Member> members = new ArrayList<>();
    private String typeOfMember;
    private int ID = 1;
    private static int numberOfStudent = 0;
    private static int numberOfAcademic = 0;
    private int limitOfStudent;
    private int limitOfAcademic;
    public Member(String typeOfMember) {
        setTypeOfMember(typeOfMember);
        setID();
    }
    public static void addMember(String typeOfMember, String outputFile) { //adds member to members arraylist and prints the situation
        Member member = new Member(typeOfMember);
        members.add(member);
        FileOutput.writeToFile(outputFile, String.format("Created new member: %s [id: %s]", member.getTypeOfMember(),
                member.getID()), true, true);

    }

    public int getID() {
        return ID;
    }

    public String getTypeOfMember() {
        return typeOfMember;
    }

    public static int getNumberOfAcademic() {
        return numberOfAcademic;
    }

    public static int getNumberOfStudent() {
        return numberOfStudent;
    }

    public int getLimitOfAcademic() {
        return limitOfAcademic;
    }

    public int getLimitOfStudent() {
        return limitOfStudent;
    }

    public void setLimitOfStudent() {
        this.limitOfStudent++;
    }

    public void setLimitOfAcademic() {
        this.limitOfAcademic++;
    }

    public void setTypeOfMember(String typeOfMember) { //assigns type of members by the letter that given at the input file
        if (typeOfMember.equals("S")) {
            this.typeOfMember = "Student";
            numberOfStudent++;
        }
        else if (typeOfMember.equals("A")) {
            this.typeOfMember = "Academic";
            numberOfAcademic++;
        }
    }

    public void setID() {
        this.ID = ID + members.size();
    }
}
