import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    
    public static void main(String[] args) {
        ExecutorService executorService= Executors.newCachedThreadPool();
        try(ServerSocket ss =new ServerSocket(2212)){
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
                int len=req.length;
                switch(req[0]){
                    case "register": {
                        if (len==9) {
                            String username = req[1];
                            String name = req[2] + req[3];
                            String email = req[4];
                            String password = req[5];
                            String dateOfBirth = req[6];
                            String schoolRegNo = req[7];
                            String imageFile = req[8];

                            Pupil pupil = new Pupil(name, username, email, password, dateOfBirth, schoolRegNo, imageFile);

                            //check if regno is valid
                            boolean regNoValid = Pupil.checkRegNo(pupil);
                            if (!regNoValid) {
                                out.println("Invalid school registration number");
                            } else {
                                Pupil.addPupilToFile(pupil);
                                out.println("Successfully added to file,an email will be sent when you are confirmed by your school representative");
                                return;
                            }
                        } else
                            out.println("Missing parameters");
                            break;
                    }
                    case "login":
                        Pupil.login(req, out);
                        break;

                        //Commands available after login
                    case "viewChallenges":
                        Pupil.viewChallenges(out);

                    case "attemptChallenge":
                        Pupil.attemptChallenge(out,in,req);
                        
                    case "viewApplicants":
                        //call appropriate method
                    case "confirmApplicant":
                        //call appropriate method
                    case "getStudent":
                        if(req.length==2){
                            String serverResponse = Arrays.deepToString(getPupil(clientRequest));
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

    public static String[][] getPupil(String pupil_id) {
        String sql = "select * from pupil";
        ResultSet pupils=null;
        String[][] result=new String[10][3];

        try(Connection con = Model.createConnection();){
            Statement st= con.createStatement();
            pupils = st.executeQuery(sql);

//            result=pupils.getString(1);
            int i=0;
            while(pupils.next()){
                result[i][0]=pupils.getString("pupil_id");
                result[i][1]=pupils.getString("username");
                result[i][2]=pupils.getString("email");
            }
            System.out.println(Arrays.deepToString(result));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return result;
    }

}
