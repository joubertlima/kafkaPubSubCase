package util;

public class Tribute {

    private String title;
    private String spider;
    private String company;
    private String date;
    private int jobID;
    private int correct_error_warning_code;
    private String jobReturnMessage;
    private int tributeValue;

    public Tribute() {

    }

    private String type;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpider() {
        return spider;
    }

    public void setSpider(String spider) {
        this.spider = spider;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public int getCorrect_error_warning_code() {
        return correct_error_warning_code;
    }

    public void setCorrect_error_warning_code(int correct_error_warning_code) {
        this.correct_error_warning_code = correct_error_warning_code;
    }

    public String getJobReturnMessage() {
        return jobReturnMessage;
    }

    public void setJobReturnMessage(String jobReturnMessage) {
        this.jobReturnMessage = jobReturnMessage;
    }

    public int getTributeValue() {
        return tributeValue;
    }

    public void setTributeValue(int tributeValue) {
        this.tributeValue = tributeValue;
    }
}
