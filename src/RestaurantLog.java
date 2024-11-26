package src;
import java.util.List;

public class RestaurantLog {
    private final int rank;
    private final float rate;
    private final int totalPpl;
    private final List<Comment> comments;

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