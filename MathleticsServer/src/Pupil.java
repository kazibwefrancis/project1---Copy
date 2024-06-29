public class Pupil {
    private String participantId;
    private String name;
    private String username;
    private String email;
    private String password;
    private String date_of_birth;
    private String schoolRegNo;
    private String image;

    //constructor
    public Pupil(String participantId, String name, String username, String email, String password, String date_of_birth, String schoolRegNo, String image) {
        this.participantId = participantId;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.date_of_birth = date_of_birth;
        this.schoolRegNo = schoolRegNo;
        this.image = image;
    }
    //Setters
    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setSchoolRegNo(String schoolRegNo) {
        this.schoolRegNo = schoolRegNo;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //Getters
    public String getParticipantId() {
        return participantId;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getSchoolRegNo() {
        return schoolRegNo;
    }

    public String getImage() {
        return image;
    }

    //Register new interested participant
    public void register(Pupil pupil){

    }

    //logs successfully registered participant into the system
    public void login(String username,String password){

    }

    //Allows login participant to view open challenges
    public void viewChallenges(){

    }

    //Allows user to attempt a challenge they are interested in
    public void attemptChallenge(){

    }
}
