import java.util.List;

public class RestaurantLog {
    private int rank;
    private float rate;
    private int totalPpl;
    private List<Comment> comments;

    public RestaurantLog(int rank, float rate, int totalPpl, List<Comment> comments) {
        this.rank = rank;
        this.rate = rate;
        this.totalPpl = totalPpl;
        this.comments = comments;
    }

    public int getRank() {
        return rank;
    }

    public float getRate() {
        return rate;
    }

    public int getTotalPpl() {
        return totalPpl;
    }

    public List<Comment> getComments() {
        return comments;
    }
}