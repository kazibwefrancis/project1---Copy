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
    public static void viewApplicants(String username, PrintWriter out){
        ArrayList<Pupil> applicants = Pupil.addToArrayList();
        String school_reg_no = Model.getSchoolRegNo(username);

        //check if there is a pupil in the arraylist whose school_reg_no is equal to the school_representative's school_reg_no
        boolean found = false;
        for (Pupil pupil : applicants) {
            if (pupil.getSchoolRegNo().equals(school_reg_no)) {
                found = true;
                break;
            }
        }
        //display only pupils from a particular school representative
        int i=1;
        out.println("Applicants for your school:");
        if (!found) {
            out.println("No pending applicants for your school");
        }else {
            for (Pupil pupil : applicants) {
                if (pupil.getSchoolRegNo().equals(school_reg_no)) {
                    out.println(i + ". " + pupil.getName() + " " + pupil.getUsername());
                    i++;
                }
            }
        }


    }


    //school representative accepts or rejects applicant
    public static void confirmApplicant(String username, String accept,PrintWriter out) {
        ArrayList<Pupil> applicants = Pupil.addToArrayList();
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
                    EmailSender.notifyPupil(pupil.getEmail());

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

}
