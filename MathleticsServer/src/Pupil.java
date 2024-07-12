
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.time.LocalDateTime;
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
    public static void attemptChallenge(PrintWriter printWriter, BufferedReader br, String[] req,String username){
        String challengeNumber=req[1];
        int participantId = Model.getPupilId(username);

        //check if participant has already attempted the challenge
        if(Model.checkChallengeAttempt(participantId,Integer.parseInt(challengeNumber))){
            printWriter.println("You have already attempted this challenge");
            return;
        }

        LocalDateTime startTime;
        int score;
        LocalDateTime endTime;
        int redos=0;

        try(Connection conn = Model.createConnection();){

        String sql = "SELECT 1 from challenge where challenge_no = ? ";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1,req[1]);
        ResultSet rs = stmt.executeQuery();

            if(rs.isBeforeFirst()){
                while(true) {
                    printWriter.println("retrieving questions...");
                    startTime = LocalDateTime.now();
                    score = Question.retrieveQuestion(printWriter, br, Integer.parseInt(challengeNumber), participantId, startTime);
                    endTime = LocalDateTime.now();
                    printWriter.println("Attempt complete");
                    printWriter.println("Total Marks:" + score);
                    printWriter.println("_______________");

                    //update the attempted challenge table
                    Model.recordChallenge(Integer.parseInt(challengeNumber), participantId, startTime, endTime, score);

                    //allow the pupil two more redos after the first attempt
                    if(redos<2) {
                        printWriter.println("Would you like to try again? Y/N");
                        printWriter.println();
                        String redo = br.readLine();
                        if (redo.equalsIgnoreCase("Y")) {
                            redos++;
                        } else {
                            printWriter.println("Challenge completed");
                            break;
                        }
                    }else {
                        printWriter.println("You have exhausted your redos");
                        //printWriter.println();
                        break;
                    }
                }

            }else{
                printWriter.println("Challenge not found");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        EmailSender.notifySchoolRep(pupil.getSchoolRegNo(),pupil.getName());

    }
    //put all applicants into an arraylist
    public static ArrayList<Pupil> addToArrayList(){
        ArrayList<Pupil> applicants = new ArrayList<>();
//        String school = "applicants";
//        String filename = school + ".txt";
        try(BufferedReader br = new BufferedReader(new FileReader("applicants.txt"))) {
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
        ArrayList<Pupil> applicants = addToArrayList();
//        String school = "applicants";
//        String filename = school + ".txt";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("applicants.txt"));) {
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
