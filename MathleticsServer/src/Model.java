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
        String sql = "insert into participant(name,username,email,password,date_of_birth,school_reg_no) values(?,?,?,?,?,?)";

        try(Connection con = Model.createConnection();) {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, pupil.getName());
            st.setString(2, pupil.getUsername());
            st.setString(3, pupil.getEmail());
            st.setString(4, pupil.getPassword());
            st.setString(5, pupil.getDate_of_birth());
            st.setString(6, pupil.getSchoolRegNo());
            st.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }
    //update rejected table if pupil is rejected
    public static void updateRejected(Pupil pupil){
        String sql = "insert into rejected(name,username,email,password,date_of_birth,school_reg_no) values(?,?,?,?,?,?)";

        try(Connection con = Model.createConnection();) {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, pupil.getName());
            st.setString(2, pupil.getUsername());
            st.setString(3, pupil.getEmail());
            st.setString(4, pupil.getPassword());
            st.setString(5, pupil.getDate_of_birth());
            st.setString(6, pupil.getSchoolRegNo());
            st.executeUpdate();

        }catch(SQLException e){
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
            if(!rs.isBeforeFirst()){
                return false;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return true;
    }

}

