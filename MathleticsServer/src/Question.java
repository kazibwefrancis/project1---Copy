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

    }

    public static void retrieveQuestion(PrintWriter printWriter, BufferedReader br){
        try(Connection conn = Model.createConnection();){

            String sql = "SELECT QuestionText from Question ORDER BY RAND() LIMIT 1";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
    
            while (rs.next()){
                String qtn = rs.getString("QuestionText");
                printWriter.println(qtn);         
                } 
    
            }catch (SQLException e){
                System.out.println(e.getMessage());
            };
    }
}
