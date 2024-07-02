import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static final String DB_URL = "jdbc:mysql://localhost:3306/dummy_db";
    static String username = "root";
    static String password = "";
    public static void main(String[] args) {
        ExecutorService executorService= Executors.newCachedThreadPool();
        try(ServerSocket ss =new ServerSocket(8888)){
            System.out.println("Waiting for Client connection..");
            while(true) {
                Socket soc = ss.accept();
                System.out.println("Connection established");
                soc.setSoTimeout(900000);
                executorService.submit(()->handleClientRequest(soc));


            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void handleClientRequest(Socket socket){
        try(socket;
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ){

            while (true) {
                String clientRequest = in.readLine();
                System.out.println("Server received: " + clientRequest + " command");
                String[] req = clientRequest.split(" ");
                switch(req[0]){
                    case "Register":
                        //call appropriate method
                    case "login":
                        //call appropriate method
                    case "viewChallenges":
                        viewChallenges();
                        out.println(viewChallenges());
                        //call appropriate method
                    case "attemptChallenge":
                        //call appropriate method
                    case "viewApplicants":
                        //call appropriate method
                    case "confirmApplicant":
                        //call appropriate method
                    case "getStudent":
                        if(req.length==2){
                            String serverResponse = getPupil(clientRequest);
                            out.println(serverResponse);
                        }else
                            out.println("Missing parameters");

                    case "done":
                        break;
                    default:
                        String response = "Command not recognized";
                        System.out.println(response);
                        out.println(response);
                }

            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }

    }

    public static String getPupil(String pupil_id) {
        String sql = "Select username from pupil";
        ResultSet pupils=null;
        String result=null;

        try(Connection con = Model.createConnection();){
            Statement st= con.createStatement();
            pupils = st.executeQuery(sql);
            pupils.next();
            result=pupils.getString(1);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return result;
    }

}