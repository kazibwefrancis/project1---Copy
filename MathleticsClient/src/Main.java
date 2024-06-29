import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Client started..");
        try(Socket soc = new Socket("localhost",2212);){
            BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            PrintWriter out = new PrintWriter(soc.getOutputStream(),true);
            String request;
            String response;
            Scanner scanner = new Scanner(System.in);
            showMenu();
            do{
                System.out.print("IES_MCS>>");
                request=scanner.nextLine();
                out.println(request);
                if(request.equalsIgnoreCase("done")){
                    break;
                }

                response=in.readLine();
                System.out.println(response);
            }while(!request.equalsIgnoreCase("done"));


        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void showMenu(){
        String instructionSet1= """
                Available commands:
                _______________________________________________________________________________________________________
                register username firstname lastname email password DateOfBirth school_reg_no imageFile.png to register
                login username password to log into the system
                view_challenges to view available
                attempt_challenge challenge_no to select a challenge to do
                view_applicants to view applicants awaiting verification
                confirm_applicant Y(y)/N(n) username to confirm applicants
                getStudent student_id to retrieve a particular student
                """;
        System.out.println(instructionSet1);
    }
}
