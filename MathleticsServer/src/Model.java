import java.sql.*;

public class Model {

    public static Connection createConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/ies_math_challenge";
        String username="root";
        String password = "Tribalchief14.";

        return DriverManager.getConnection(url,username,password);
    }
//check if a supplied username and password match for a given school representative
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
    public static boolean checkSRLogin(String username, String password){
        try(Connection con = Model.createConnection()){
            String sql = "SELECT 1 FROM School where username = ? and password = ?";
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
}
