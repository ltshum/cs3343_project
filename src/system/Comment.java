package system;

import java.time.LocalDate;

public class Comment {

    private String customer_name;
    private String content;
    private float rate;
    private LocalDate date;

    public Comment(String customer_name, String content, float rate, LocalDate date) {
        this.customer_name = customer_name;
        this.content = content;
        this.rate = rate;
        this.date = date;
    }

    public String getCommentCustomerName() {
        return customer_name;
    }

    public String getCommentContent() {
        return content;
    }

    public LocalDate getCommentDate() {
        return date;
    }

    public float getCommentRate() {
        return rate;
    }
}
