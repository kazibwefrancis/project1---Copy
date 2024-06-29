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
}
