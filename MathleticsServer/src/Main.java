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
                String[] req = clientRequest.trim().split(" ");

                switch(req[0]){
                    case "register":
                        Pupil.register(req, out);
                        break;
                    case "login":
                        Authenticator.login(req,out,in);
                        break;
                    default:
                        System.out.println("Command not recognized");
                        out.println("Command not recognized");
                        out.println();
                        break;
                }

            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }

    }

}
