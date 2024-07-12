
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

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
        Random rand = new Random(10);
        System.out.println(rand.toString());
    }
    public static void retrieveQuestion(PrintWriter printWriter, BufferedReader br){
        try(Connection conn = Model.createConnection();){

            String sql = "SELECT QuestionText from Question ORDER BY RAND() LIMIT 3";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);


            Statement pstmt = conn.createStatement();
            ResultSet rst = pstmt.executeQuery("SELECT Duration from Challenge WHERE ChallengeID = 'CH01'");
            //int duration = rst.getInt("Duration");
        System.out.println("Retrieving question...");

            LocalTime startTime = LocalTime.now();
        System.out.println("hello1");
                outer:while (rs.next()) {
                    String[] row = new String[rs.getMetaData().getColumnCount()];

                    for(int j=0;j<10;j++){
                        LocalTime rightNow = LocalTime.now();
                        Duration timeElapsed = Duration.between(startTime, rightNow);
                        int minutes = 2;//rst.getInt("Duration");

                        Duration timeLimit = Duration.ofMinutes(minutes);

                        if (timeElapsed.compareTo(timeLimit) > 0) {
                            printWriter.println("TIME UP, THANK YOU FOR ATTEMPTING THIS CHALLENGE");
                            break outer;
                        }
                        Duration timeRemaining = timeLimit.minus(timeElapsed);
                        String jep = ("Time left: " + timeRemaining.toMinutes() + ":" + timeRemaining.toSecondsPart() +":" +timeRemaining.toMillisPart());
                        printWriter.println(jep);
                        int count = 10-j;
                        printWriter.println("Questions left: " +count);

                        if(count ==1){
                            printWriter.println("THANKYOU FOR ATTEMPTING THIS CHALLENGE");
                            break outer;
                        }

                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            String qn = rs.getString("QuestionText");
                            printWriter.println(qn);
                            String ans = br.readLine();

                            }
                        }
                }
        }
    }
}

