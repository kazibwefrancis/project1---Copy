import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
    public void viewApplicants(String filename){
        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }catch(IOException e){
                System.out.println(e.getMessage());
            }


    }
    //school representative accepts or rejects applicant
    public void confirmApplicant(){

    }
}
