
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;

public class Pupil {
    private int participantId;
    private String name;
    private String username;
    private String email;
    private String password;
    private String date_of_birth;
    private String schoolRegNo;
    private String imageFilePath;

    //default constructor
    public Pupil() {
    }

    //constructor
    public Pupil(String name, String username, String email, String password, String date_of_birth, String schoolRegNo, String image) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.date_of_birth = date_of_birth;
        this.schoolRegNo = schoolRegNo;
        this.imageFilePath = image;
    }
    //Setters
    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

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

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setSchoolRegNo(String schoolRegNo) {
        this.schoolRegNo = schoolRegNo;
    }

    public void setImage(String image) {
        this.imageFilePath = image;
    }

    //Getters
    public int getParticipantId() {
        return participantId;
    }

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

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getSchoolRegNo() {
        return schoolRegNo;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    //Register new interested participant
    public static void register(String[] req, PrintWriter out){
        if (req.length!=9){
            out.println("Missing parameters");
            out.println("");
        }else {
            String username = req[1];
            String name = req[2] +" "+ req[3];
            String email = req[4];
            String password = req[5];
            String dateOfBirth = req[6];
            String schoolRegNo = req[7];
            String imageFilePath = req[8];

            Pupil pupil = new Pupil(name, username, email, password, dateOfBirth, schoolRegNo, imageFilePath);
            if (!Model.checkRegNo(pupil)) {
                out.println("School not registered, please contact the system administrator to register your school first");
                out.println("");
            } else if (Model.checkUsername(pupil)) {
                out.println("Username already taken,try another one");
                out.println("");
            } else if (Model.checkStudentRegistration(pupil)) {
                out.println("Student already registered, please login to attempt challenges");
                out.println("");
            } else {
                Pupil.addPupilToFile(pupil);
                out.println("Wait for confirmation email from the system administrator");
                out.println("");
            }
        }
    }


    //Allows logged in participant to view open challenges
    public static void viewChallenges(PrintWriter printWriter){
        String chal=null;
        try(Connection conn = Model.createConnection();){

        String sql = "SELECT challenge_no, challenge_name from challenge";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()){
        chal=rs.getString("challenge_no")+"."+rs.getString("challenge_name");
        printWriter.println(chal);
        }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        };
    }

    //Allows user to attempt a challenge they are interested in
    public static void attemptChallenge(PrintWriter printWriter, BufferedReader br, String[] req){

        try(Connection conn = Model.createConnection();){

        String sql = "SELECT challenge_no from challenge";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String tempID = rs.getString("challenge_no");
                //System.out.println(tempID);
                    if(req[1].equals(tempID)){
                        printWriter.println("retrieving questions");
                        Question.retrieveQuestion(printWriter, br);
                        break;
                    }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ;
    }

    //check if reg no supplied is in the database

    //to add a pupil to a file
    public static void addPupilToFile(Pupil pupil){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("applicants.txt",true));
        ) {
            writer.write(pupil.getName() + " " + pupil.getUsername() + " " + pupil.getEmail() + " " + pupil.getPassword() + " " + pupil.getDate_of_birth() + " " + pupil.getSchoolRegNo() + " " + pupil.getImageFilePath() + "\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //notify school representative
        //EmailSender.notifySchoolRep(pupil.getSchoolRegNo(),pupil.getEmail(),pupil.getName());

    }
    //put all applicants into an arraylist
    public static ArrayList<Pupil> addToArrayList(String username){
        ArrayList<Pupil> applicants = new ArrayList<>();
        String school = "applicants";
        String filename = school + ".txt";
        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null){
                String[] parts = line.trim().split(" ");
                Pupil pupil = new Pupil();

                pupil.setName(parts[0]+ " " + parts[1]);
                pupil.setUsername(parts[2]);
                pupil.setEmail(parts[3]);
                pupil.setPassword(parts[4]);
                pupil.setDate_of_birth(parts[5]);
                pupil.setSchoolRegNo(parts[6]);
                pupil.setImage(parts[7]);
                applicants.add(pupil);
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return applicants;
    }

    //to delete a pupil from the file after being confirmed or rejected
    public static void deleteFromFile(String username){
        ArrayList<Pupil> applicants = addToArrayList(username);
        String school = "applicants";
        String filename = school + ".txt";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename));) {
            for (Pupil pupil : applicants) {
                if (!pupil.getUsername().equals(username)) {
                    bw.write(pupil.getName() + " " + pupil.getUsername() + " " + pupil.getEmail() + " " + pupil.getPassword() + " " + pupil.getDate_of_birth() + " " + pupil.getSchoolRegNo() + " " + pupil.getImageFilePath() + "\n");
                }
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
