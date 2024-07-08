
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    private String image;

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
        this.image = image;
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
        this.image = image;
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

    public String getImage() {
        return image;
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
            String imageFile = req[8];

            Pupil pupil = new Pupil(name, username, email, password, dateOfBirth, schoolRegNo, imageFile);
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
                //call method to send email to school representative
                out.println("Wait for confirmation email from the system administrator");
                out.println("");
            }
        }
    }

    //Login participant
      public static void login(String[] req, PrintWriter out,BufferedReader in) throws IOException {
          if (req.length != 4) {
              out.println("Missing parameters");
              out.println("");
          }else {
              String username = req[2];
              String password = req[3];
              outerlogin:
              {
                  switch (req[1]) {
                      case "p":
                          if (Model.checkPupilLogin(username, password)) {
                              //out.println("Login successful");
                              out.println("Hello " + username + ", welcome to Mathletics challenges");
                              out.println("Available commands:\nviewChallenges\nattemptChallenge <challenge_no>");
                              out.println("");

                              while(true) {
                                  String clientRequest = in.readLine();
                                  System.out.println(username + " sent: " + clientRequest + " command");
                                  String[] actions = clientRequest.trim().split(" ");
                                  switch (actions[0]) {
                                      //Challenges available after login
                                      case "viewChallenges":
                                          Pupil.viewChallenges(out);
                                          out.println();
                                          break;

                                      case "attemptChallenge":
                                          //call appropriate method
                                          out.println();
                                          break;
                                      case "logout":
                                          out.println("logging out...");
                                          out.println();
                                          break outerlogin;
                                  }
                              }
                          } else {
                              out.println("Invalid username or password");
                              out.println("");
                              break;
                          }

                      case "sr":
                          if (Model.checkSRLogin(username, password)) {
                              out.println("************** Login successful: " + username + " ***************");
                              out.println("              -------------------                    ");
                              out.println("Available commands:\nviewApplicants\nconfirmApplicant <y>(yes)/<n>(no) username");
                              out.println("");

                              while (true) {
                                  String clientRequest = in.readLine();
                                  System.out.println(username + " sent: " + clientRequest + " command");
                                  String[] actions = clientRequest.trim().split(" ");
                                  switch (actions[0]) {
                                      case "viewApplicants":
                                          SchoolRepresentative.viewApplicants("applicants", out);
                                          out.println();
                                          break;
                                      case "confirmApplicant":
                                          SchoolRepresentative.confirmApplicant(actions[2], actions[1], out);
                                          out.println();
                                          break;
                                      case "logout":
                                          out.println("logging out...");
                                          out.println();
                                          break outerlogin;
                                      default:
                                          out.println("Invalid command");
                                          out.println();
                                          break;
                                  }
                              }
                          } else {
                              out.println("Invalid username or password");
                              out.println("");
                              break;
                          }
                      default:
                          out.println("Invalid login type");
                          out.println("");
                          break;
                  }
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

        String sql = "SELECT ChallengeID from Challenge";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String tempID = rs.getString("ChallengeID");
                    if(req[1].equals(tempID)){
                        Question.retrieveQuestion(printWriter, br);
                    }
                    else{
                        String error = "ChallengeID not recognised";
                        printWriter.println(error);
                    }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        };
    }

    //check if reg no supplied is in the database

    //to add a pupil to a file
    public static void addPupilToFile(Pupil pupil){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("applicants.txt",true));
        ) {
            writer.write(pupil.getName() + " " + pupil.getUsername() + " " + pupil.getEmail() + " " + pupil.getPassword() + " " + pupil.getDate_of_birth() + " " + pupil.getSchoolRegNo() + " " + pupil.getImage() + "\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

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
                    bw.write(pupil.getName() + " " + pupil.getUsername() + " " + pupil.getEmail() + " " + pupil.getPassword() + " " + pupil.getDate_of_birth() + " " + pupil.getSchoolRegNo() + " " + pupil.getImage() + "\n");
                }
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    //to string
    public String toString() {
        return "Pupil{" +
                "participantId=" + participantId +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", schoolRegNo='" + schoolRegNo + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
