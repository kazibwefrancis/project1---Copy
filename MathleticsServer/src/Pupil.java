import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
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
    public static void register(Pupil pupil){
        String name = pupil.getName();
        String username = pupil.getUsername();
        String email = pupil.getEmail();
        String password = pupil.getPassword();
        String dateOfBirth = pupil.getDate_of_birth();
        String schoolRegNo = pupil.getSchoolRegNo();


        String sql = "insert into participant(name,username,email,password,date_of_birth,school_reg_no) values(?,?,?,?,?,?)";

        try(Connection con = Model.createConnection();) {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, username);
            st.setString(3, email);
            st.setString(4, password);
            st.setString(5, dateOfBirth);
            st.setString(6, schoolRegNo);
            st.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }

    //logs successfully registered participant into the system
    public static void login(String[] req, PrintWriter out) {
        if (req.length != 4) {
            out.println("Missing parameters");
            return;
        }
        String username = req[2];
        String password = req[3];
        switch (req[1]) {
            case "p":
                if (Model.checkPupilLogin(username, password)) {
                    out.println("Login successful");
                } else {
                    out.println("Invalid username or password");
                }
                break;
            case "sr":
                if (Model.checkSRLogin(username, password)) {
                    out.println("Login successful");
                } else {
                    out.println("Invalid username or password");
                }
                break;
            default:
                out.println("Invalid login type");
                break;
            }
        }
    //Allows login participant to view open challenges
    public static String viewChallenges(PrintWriter printWriter){
        String chal=null;
        String challengeID = null;
        String challengeName = null;
        try(Connection conn = Model.createConnection();){

        String sql = "SELECT ChallengeID, ChallengeName from Challenge WHERE Status = 'Valid'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()){
        challengeID = rs.getString("ChallengeID");
        challengeName = rs.getString("ChallengeName");
        chal=challengeID +"-"+ challengeName;
        printWriter.println(chal);

        }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        };
        chal = (challengeID +"-"+ challengeName);
         return chal;
    }

    //Allows user to attempt a challenge they are interested in
    public void attemptChallenge(){

    }

    //check if reg no supplied is in the database
    public static boolean checkRegNo(Pupil pupil){
        String regNo = pupil.getSchoolRegNo();
        ArrayList<String> regNos = new ArrayList<>();

        try(Connection con =Model.createConnection()){
            String sql ="SELECT school_reg_no FROM School";

            Statement st =con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                regNos.add(rs.getString("school_reg_no"));
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return regNos.contains(pupil.getSchoolRegNo());

    }

    //to add a pupil to a file
    public static void addPupilToFile(Pupil pupil){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("pupils.txt",true))) {
            writer.write(pupil.getName() + "\n" + pupil.getUsername() + "\n" + pupil.getEmail() + "\n" + pupil.getPassword() + "\n" + pupil.getDate_of_birth() + "\n" + pupil.getSchoolRegNo() + "\n" + pupil.getImage() + "\n");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
