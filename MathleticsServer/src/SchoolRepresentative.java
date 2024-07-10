import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SchoolRepresentative {
    private String name;
    private String username;
    private String email;
    private String password;
    private String school_reg_no;

    //constructor
    public SchoolRepresentative(String name, String username, String email, String password, String schoolRegNo) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.school_reg_no = schoolRegNo;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSchool_reg_no(String school_reg_no) {
        this.school_reg_no = school_reg_no;
    }

    //getters
    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSchool_reg_no() {
        return school_reg_no;
    }

    //log school representative into system
    public void login(String username, String password){

    }
    //school representative can view pending applicants int the java file for their school
    public static void viewApplicants(String schoolName, PrintWriter out){

        String filename = schoolName + ".txt";
        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;


            while ((line = br.readLine()) != null) {
                //out.println("Applicant 1");
                out.println(line);
            }
//            out.println();
        }catch(IOException e){
                System.out.println(e.getMessage());
            }
    }


    //school representative accepts or rejects applicant
    public static void confirmApplicant(String username, String accept,PrintWriter out) {
        ArrayList<Pupil> applicants = Pupil.addToArrayList("applicants");
       // System.out.println(applicants);
        boolean found = false;

        for (Pupil pupil : applicants) {
            //System.out.println(pupil.getUsername());
            System.out.println(username);
            if (pupil.getUsername().equals(username)) {
                found = true;
                System.out.println("found");
                if (accept.equals("y")) {
                    Model.updatePupil(pupil);
                    Pupil.deleteFromFile(username);
                    out.println("You have confirmed " + pupil.getName());
                    //send email to pupil

                } else if (accept.equals("n")) {
                    Model.updateRejected(pupil);
                    Pupil.deleteFromFile(username);
                    out.println("You have rejected " + pupil.getName());

                    //send email to pupil
                }
            } else {
                System.out.println("confirming ...");
            }
        }
        if (!found) {
            out.println("wrong pupil username");
        }
    }

    //public static void main(String[] args) {
    //    System.out.println("Hello didi");
   // }
    //Test (dont run)
   // public static void main(String[] args) {
     //   ArrayList<Pupil> applicants = viewApplicants(new SchoolRepresentative("John Doe", "johndoe", "john.com", "password", "U001"), new PrintWriter(System.out));
     //   confirmApplicant("asasira", "y", applicants);
   // }
}
