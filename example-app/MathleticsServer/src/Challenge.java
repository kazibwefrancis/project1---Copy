import java.util.Date;

public class Challenge {
    private int challenge_no;
    private String challenge_name;
    private int duration;
    private Date start_date;
    private Date end_date;

    //constructor
    public Challenge(int challenge_no, String challenge_name, int duration, Date start_date, Date end_date) {
        this.challenge_no = challenge_no;
        this.challenge_name = challenge_name;
        this.duration = duration;
        this.start_date = start_date;
        this.end_date = end_date;
    }
    //Setters
    public void setChallenge_no(int challenge_no) {
        this.challenge_no = challenge_no;
    }

    public void setChallenge_name(String challenge_name) {
        this.challenge_name = challenge_name;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    //Getters
    public int getChallenge_no() {
        return challenge_no;
    }

    public String getChallenge_name() {
        return challenge_name;
    }

    public int getDuration() {
        return duration;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    //Retrieve questions for a particular challenge
    public String[] getQuestions(int challenge_no){

        return new String[0];
    }
}
