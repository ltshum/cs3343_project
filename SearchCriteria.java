
public class SearchCriteria {

    private String restaurantName;
    private String district;
    private String rateRange;
    private String type;
    private int ppl;
    private String startTime;
    private String session;

    public boolean isNonNull(String input) {
        if (input.equals("null")) {
            return false;
        } else {
            return true;
        }
    }

    public SearchCriteria(String restaurantName, String district, String rateRange, String type, String ppl, String startTime, String session) {
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
            this.ppl = Integer.valueOf(ppl);
        }
        if (isNonNull(startTime)) {
            this.startTime = startTime;
        }
        if (isNonNull(session)) {
            this.session = session;
        }
    }
}
