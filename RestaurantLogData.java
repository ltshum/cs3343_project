import java.util.ArrayList;

public class RestaurantLogData{
    private ArrayList<Comment> lastWeekComments;
    private int lastWeekTotalPpl;
    private float lastWeekRate;
    private int lastWeekRank;
    private ArrayList<Comment> thisWeekComments;
    private int thisWeekTotalPpl;
    private float thisWeekRate;
    private int thisWeekRank;

    public RestaurantLogData(ArrayList<Comment> lastWeekComments, int lastWeekTotalPpl, float lastWeekRate, int lastWeekRank, ArrayList<Comment> thisWeekComments, int thisWeekTotalPpl, float thisWeekRate, int thisWeekRank) {
        this.lastWeekComments = lastWeekComments;
        this.lastWeekTotalPpl = lastWeekTotalPpl;
        this.lastWeekRate = lastWeekRate;
        this.lastWeekRank = lastWeekRank;
        this.thisWeekComments = thisWeekComments;
        this.thisWeekTotalPpl = thisWeekTotalPpl;
        this.thisWeekRate = thisWeekRate;
        this.thisWeekRank = thisWeekRank;
    }

    public ArrayList<Comment> getLastWeekComments() {
        return lastWeekComments;
    }

    public int getLastWeekTotalPpl() {
        return lastWeekTotalPpl;
    }

    public float getLastWeekRate() {
        return lastWeekRate;
    }

    public int getLastWeekRank() {
        return lastWeekRank;
    }

    public ArrayList<Comment> getThisWeekComments() {
        return thisWeekComments;
    }

    public int getThisWeekTotalPpl() {
        return thisWeekTotalPpl;
    }

    public float getThisWeekRate() {
        return thisWeekRate;
    }

    public int getThisWeekRank() {
        return thisWeekRank;
    }

    public void setLastWeekComments(ArrayList<Comment> lastWeekComments) {
        this.lastWeekComments = lastWeekComments;
    }

    public void setLastWeekTotalPpl(int lastWeekTotalPpl) {
        this.lastWeekTotalPpl = lastWeekTotalPpl;
    }

    public void setLastWeekRate(float lastWeekRate) {
        this.lastWeekRate = lastWeekRate;
    }

    public void setLastWeekRank(int lastWeekRank) {
        this.lastWeekRank = lastWeekRank;
    }

    public void setThisWeekComments(ArrayList<Comment> thisWeekComments) {
        this.thisWeekComments = thisWeekComments;
    }

    public void setThisWeekTotalPpl(int thisWeekTotalPpl) {
        this.thisWeekTotalPpl = thisWeekTotalPpl;
    }

    public void setThisWeekRate(float thisWeekRate) {
        this.thisWeekRate = thisWeekRate;
    }

    public void setThisWeekRank(int thisWeekRank) {
        this.thisWeekRank = thisWeekRank;
    }
    
}
