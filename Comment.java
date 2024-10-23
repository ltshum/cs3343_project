
public class Comment {

    private String customer_name;
    private String content;
    private float rate;

    public Comment(String customer_name, String content, float rate) {
        this.customer_name = customer_name;
        this.content = content;
        this.rate = rate;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

}
