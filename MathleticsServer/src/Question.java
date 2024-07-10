
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.Duration;
import java.time.LocalTime;

public class Question {
    private int question_no;
    private String question_text;
    private int answer;
    private int marks;

    //Constructor
    public Question(int question_no, String question_text, int answer, int marks) {
        this.question_no = question_no;
        this.question_text = question_text;
        this.answer = answer;
        this.marks = marks;
    }

    //setters
    public void setQuestion_no(int question_no) {
        this.question_no = question_no;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    //Getter
    public int getQuestion_no() {
        return question_no;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public int getAnswer() {
        return answer;
    }

    public int getMarks() {
        return marks;
    }

    //awards marks to a question depending on the answer supplied
    public static void awardMarks(int answer){

    }
    public static void retrieveQuestion(PrintWriter printWriter, BufferedReader br) throws IOException {
        try(Connection conn = Model.createConnection();){

            String sql = "SELECT question_text from Question ORDER BY RAND() LIMIT 10";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Statement pstmt = conn.createStatement();
            ResultSet rst = pstmt.executeQuery("SELECT Duration from Challenge WHERE challenge_no = 1");

            LocalTime startTime = LocalTime.now();

            int j  = 1;
            while (rs.next()){

                    LocalTime rightNow = LocalTime.now();
                    Duration timeElapsed = Duration.between(startTime, rightNow);
                    int minutes = 2;//sample duration for a challenge

                    Duration timeLimit = Duration.ofMinutes(minutes);

                    if (timeElapsed.compareTo(timeLimit) > 0) {
                        printWriter.println("TIME UP, THANK YOU FOR ATTEMPTING THIS CHALLENGE");
                        break;
                    }
                    Duration timeRemaining = timeLimit.minus(timeElapsed);
                    String jep = ("Time left: " + timeRemaining.toMinutes() + ":" + timeRemaining.toSecondsPart() +":" +timeRemaining.toMillisPart());
                    printWriter.println(jep);
                    int count = 10-j;
                    printWriter.println("Questions left: " +count);

//
                //displaying question on the client
                        printWriter.println(rs.getString("question_text"));
                        printWriter.println();

                        //receiving answer from the pupil
                        String ans = br.readLine();

                        if(j>=3){
                            printWriter.println("CHALLENGE COMPLETE");
                            printWriter.println("---results");
                            printWriter.println("THANKYOU FOR ATTEMPTING THIS CHALLENGE");
                            break;
                        }

                    j++;
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        };
    }}

