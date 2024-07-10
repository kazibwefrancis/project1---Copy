import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
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
            String sql ="SELECT 1 FROM School where School_reg_no = ?";

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
        try(Connection con = Model.createConnection()){
            String sql = "SELECT 1 FROM participant where username = ? and password = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1,username);
            st.setString(2,password);
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
    public static boolean checkSRLogin(String email, String password){
        try(Connection con = Model.createConnection()){
            String sql = "SELECT 1 FROM SchoolRepresentative where email = ? and password = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1,email);
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

    //storing a pupils image in the database
//    public static void storePupilImage(String username){
//        String filepath = "images/"+username+".png";
//        String sql ="INSERT INTO participant(pupil_image) VALUES(?);";
//        try(Connection con =Model.createConnection();
//            FileInputStream inputStream = new FileInputStream(filepath);){
//            PreparedStatement stmt = con.prepareStatement(sql);
//            stmt.setBlob(1,inputStream);
//            stmt.executeUpdate();
//
//        }catch(SQLException | IOException e){
//            System.out.println(e.getMessage());
//        }
//    }

}

