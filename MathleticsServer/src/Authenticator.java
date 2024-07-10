import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Authenticator {
    //Login user
    public static void login(String[] req, PrintWriter out, BufferedReader in) throws IOException {
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
                                        Pupil.attemptChallenge(out,in,actions);
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
                                        //out.println("Viewing Applicants");
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

    static class PasswordHasher {

        //hashes a password for security purposes
        public static String hashPassword(String password,String salt){
            byte[] hashedPassword=null;
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(salt.getBytes());
                hashedPassword = md.digest(password.getBytes());

            }catch(NoSuchAlgorithmException e){
                System.out.println(e.getMessage());
            }
            return Base64.getEncoder().encodeToString(hashedPassword);
        }

        //generate a salt for hashing
        public static String generateSalt(){
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            return Base64.getEncoder().encodeToString(salt);
        }
    }


}
