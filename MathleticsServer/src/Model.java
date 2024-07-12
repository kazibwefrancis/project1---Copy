import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Model {

    public static Connection createConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/ies_math_challenge";
        String username="root";
        String password = "Tribalchief14.";

        return DriverManager.getConnection(url,username,password);
    }

    //checking if a given school registration number is valid
    public static boolean checkRegNo(Pupil pupil){

        try(Connection con =Model.createConnection()){
            String sql ="SELECT 1 FROM School where school_reg_no = ?";

            PreparedStatement st =con.prepareStatement(sql);
            st.setString(1,pupil.getSchoolRegNo());
            ResultSet rs = st.executeQuery();
            if(!rs.isBeforeFirst()){
                return false;
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
      return true;
    }

    //checking if a given student is already registered basing on their name and date of birth
    public static boolean checkStudentRegistration(Pupil pupil){
        String sql = "SELECT 1 FROM participant where name = ? and date_of_birth = ?";
        PreparedStatement st= null;
        ResultSet rs = null;
        boolean isRegistered = false;
        try(Connection con = Model.createConnection()){
            st = con.prepareStatement(sql);
            st.setString(1,pupil.getName());
            st.setString(2,pupil.getDate_of_birth());
            rs = st.executeQuery();
            isRegistered= rs.isBeforeFirst();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return isRegistered;
    }


    //check if a username is taken
    public static boolean checkUsername(Pupil pupil){
        try(Connection con = Model.createConnection()){
            String sql = "SELECT 1 FROM participant where username = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1,pupil.getUsername());
            ResultSet rs = st.executeQuery();
            if(!rs.isBeforeFirst()){
                return false;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return true;
    }

    //get salt from database for a given user
    public static String getSalt(String username){
        String salt = null;
        try(Connection con = Model.createConnection()){
            String sql = "SELECT salt FROM participant where username = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1,username);
            ResultSet rs = st.executeQuery();
            rs.next();
            salt = rs.getString("salt");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return salt;
    }

    //add a pupil to the database
    public static void updatePupil(Pupil pupil){
        String salt = Authenticator.PasswordHasher.generateSalt();
        String hashedPwd = Authenticator.PasswordHasher.hashPassword(pupil.getPassword(),salt);
        String sql = "insert into participant(name,username,email,password,date_of_birth,school_reg_no,pupil_image,salt) values(?,?,?,?,?,?,?,?)";

        try(Connection con = Model.createConnection();
            FileInputStream inputStream = new FileInputStream(pupil.getImageFilePath());) {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, pupil.getName());
            st.setString(2, pupil.getUsername());
            st.setString(3, pupil.getEmail());
            st.setString(4, hashedPwd);
            st.setString(5, pupil.getDate_of_birth());
            st.setString(6, pupil.getSchoolRegNo());
            st.setBlob(7,inputStream);
            st.setString(8,salt);

            st.executeUpdate();


        }catch(SQLException | IOException e){
            System.out.println(e.getMessage());
        }

    }
    //update rejected table if pupil is rejected
    public static void updateRejected(Pupil pupil){
        String sql = "insert into rejected(name,username,email,password,date_of_birth,school_reg_no) values(?,?,?,?,?,?)";

        try(Connection con = Model.createConnection();
            FileInputStream inputStream = new FileInputStream(pupil.getImageFilePath());) {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, pupil.getName());
            st.setString(2, pupil.getUsername());
            st.setString(3, pupil.getEmail());
            st.setString(4, pupil.getDate_of_birth());
            st.setString(5, pupil.getSchoolRegNo());

            st.setBlob(6,inputStream);
            st.executeUpdate();

        }catch(SQLException | IOException e){
            System.out.println(e.getMessage());
        }

    }

    //check if a supplied username and password match for a given pupil
    public static boolean checkPupilLogin(String username, String password){
        String salt = Model.getSalt(username);
        String hashedPwd = Authenticator.PasswordHasher.hashPassword(password,salt);
        try(Connection con = Model.createConnection()){
            String sql = "SELECT 1 FROM participant where username = ? and password = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1,username);
            st.setString(2,hashedPwd);
            ResultSet rs = st.executeQuery();
            if(!rs.isBeforeFirst()){
                return false;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return true;
    }

    //check if a supplied username and password match for a given school representative
    public static boolean checkSRLogin(String name, String password){
        try(Connection con = Model.createConnection()){
            String sql = "SELECT 1 FROM SchoolRepresentative where name = ? and password = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1,name);
            st.setString(2,password);
            ResultSet rs = st.executeQuery();
            if(!(rs.isBeforeFirst())){
                return false;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return true;
    }
    //get school representative email address
    public static String getSchoolRepEmail(String schooolRegNo){
        String email = null;
        System.out.println(schooolRegNo);
        try(Connection con = Model.createConnection()){
            String sql = "SELECT email FROM SchoolRepresentative where school_reg_no = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1,schooolRegNo);
            ResultSet rs = st.executeQuery();
                 rs.next();
                email = rs.getString("email");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return email;
    }
    //get a pupil's id basing on their username
    public static int getPupilId(String username){
        int id = 0;
        try(Connection con = Model.createConnection()){
            String sql = "SELECT participant_id FROM participant where username = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1,username);
            ResultSet rs = st.executeQuery();
            rs.next();
            id = rs.getInt("participant_id");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return id;
    }

    //record a challenge attempted by a participant
    public static void recordChallenge(int challenge_no, int participant_id, LocalDateTime startTime, LocalDateTime endTime, int score){
        String sql = "INSERT INTO ChallengeAttempt VALUES(?,?,?,?,?)";
        try(Connection con= Model.createConnection();){
            PreparedStatement st= con.prepareStatement(sql);
            st.setInt(1,challenge_no);
            st.setInt(2,participant_id);

            Timestamp timeStamp = Timestamp.valueOf(startTime);
            st.setTimestamp(3,timeStamp);
            st.setInt(4,score);

            Timestamp end = Timestamp.valueOf(endTime);
            st.setTimestamp(5,end);
            st.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //check if a particapnt has already attempted a challenge using the challenge number and participant id
    public static boolean checkChallengeAttempt(int challenge_no, int participant_id){
        try(Connection con = Model.createConnection()){
            System.out.println(challenge_no+" "+participant_id);
            String sql = "SELECT 1 FROM ChallengeAttempt where challenge_no = ? and participant_id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1,challenge_no);
            st.setInt(2,participant_id);
            ResultSet rs = st.executeQuery();
            if(rs.isBeforeFirst()){
                return false;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return true;
    }

  //record an attempted question
    public static void recordAttemptedQuestion(int challenge_no, int participant_id, LocalDateTime startTime,int question_no, char status){
        String sql = "INSERT INTO AttemptedQuestion(challenge_no,participant_id,start_time,question_no,status) VALUES(?,?,?,?,?);";
        try(Connection con= Model.createConnection();){
            PreparedStatement st= con.prepareStatement(sql);
            st.setInt(1,challenge_no);
            st.setInt(2,participant_id);

            Timestamp timeStamp = Timestamp.valueOf(startTime);
            st.setTimestamp(3,timeStamp);
            st.setInt(4,question_no);
            st.setString(5,String.valueOf(status));
            st.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //Returns a challenge's duration basing on the challenge number
    public static int getChallengeDuration(int challenge_no){
        int duration = 0;
        try(Connection con = Model.createConnection()){
            String sql = "SELECT duration FROM challenge where challenge_no = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1,challenge_no);
            ResultSet rs = st.executeQuery();
            rs.next();
            duration = rs.getInt("duration");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return duration;

    }

    //get all schools' names from the database
    public static ArrayList<String> getSchools(){
        ArrayList<String> schools = new ArrayList<>();
        try(Connection con = Model.createConnection()){
            String sql = "SELECT school_name FROM School";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                schools.add(rs.getString("school_name"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return schools;
    }

    //get the school registration number for a given school representative basing on the username
    public static String getSchoolRegNo(String username){
        String schoolRegNo = null;
        try(Connection con = Model.createConnection()){
            String sql = "SELECT school_reg_no FROM SchoolRepresentative where name = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1,username);
            ResultSet rs = st.executeQuery();
            rs.next();
            schoolRegNo = rs.getString("school_reg_no");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return schoolRegNo;
    }

}

