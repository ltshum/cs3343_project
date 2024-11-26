package system;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;


public final class SearchCriteria {

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

    public ArrayList<Restaurant> searchRestaurantsIn( ArrayList<Restaurant> restaurants){
        ArrayList<Restaurant> result = new ArrayList<>();
        for (Restaurant r : restaurants){
            if (restaurantName != null &&  !r.getRestaurantName().contains(restaurantName)){
                continue;
            }
            if (district != null && !r.getDistrict().contains(district)){
                continue;
            }

            if (rateRange != null){
                if (rateRange.length() == 1 && rateRange.charAt(0)-'0' != (int)r.getRate()){
                    continue;
                }
                int minRate = rateRange.charAt(0) - '0';
                int maxRate = rateRange.charAt(rateRange.length()-1) - '0';
                if (r.getRate() > maxRate || r.getRate() < minRate){
                    continue;
                }
            }
            if (type != null && !type.contains(r.getType())){
                continue;
            }
            ArrayList<Table> tables = r.getAllTables();
            for (Table t : tables){
                boolean status;
                if (startTime != null){
                    status = t.canbook(ppl, duration, startTime);
                } else {
                    status = t.canbook(ppl, duration);
                }
                if (status){
                    result.add(r);
                    break;
                }
            }

            // if (ppl >0 && ppl < r.get)

            
        }
        return result;
    }
}
