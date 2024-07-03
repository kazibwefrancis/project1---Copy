import java.util.Date;

public class ChallengeAttempt {
    private int challenge_no;
    private String participant_id;
    private Date start_time;
    private int score;

    //constructor
    public ChallengeAttempt(int challenge_no, String participant_id, Date start_time, int score) {
        this.challenge_no = challenge_no;
        this.participant_id = participant_id;
        this.start_time = start_time;
        this.score = score;
    }

    //setters
    public void setChallenge_no(int challenge_no) {
        this.challenge_no = challenge_no;
    }

    public void setParticipant_id(String participant_id) {
        this.participant_id = participant_id;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public void setScore(int score) {
        this.score = score;
    }

    //getters


    public int getChallenge_no() {
        return challenge_no;
    }

    public String getParticipant_id() {
        return participant_id;
    }

    public Date getStart_time() {
        return start_time;
    }

    public int getScore() {
        return score;
    }

    //retrieves a question for an attempt session
    private String getQuestion(){

        return "";
    }

    //generates a report at the end of an attempt session
    public void generateReport(ChallengeAttempt attempt){

    }

    //pupil can reattempt a challenge as long as three times are not exceeded
    public void reDo(){

    }
}
