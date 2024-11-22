
import java.time.Duration;
import java.time.LocalTime;


public class SearchCriteria {

    private String restaurantName;
    private String district;
    private String rateRange;
    private String type;
    private int ppl;
    private LocalTime startTime;
    private Duration duration;

    public boolean isNonNull(String input) {
        return !input.equals("null");
    }

    public SearchCriteria(String restaurantName, String district, String rateRange, String type, String ppl, String startTime, String duration) {
        if (isNonNull(restaurantName)) {
            this.restaurantName = restaurantName;
        }
        if (isNonNull(district)) {
            this.district = district;
        }
        if (isNonNull(rateRange)) {
            this.rateRange = rateRange;
        }
        if (isNonNull(type)) {
            this.type = type;
        }
        if (isNonNull(ppl)) {
            this.ppl = Integer.parseInt(ppl);
        }
        if (isNonNull(startTime)) {
            this.startTime = LocalTime.parse(startTime);
        }
        if (isNonNull(duration)) {
            this.duration = Duration.ofMinutes(Integer.parseInt(duration));
        }
    }
}
