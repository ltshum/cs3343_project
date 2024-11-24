import java.util.ArrayList;

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

    public ArrayList<Restaurant> searchRestaurantsIn( ArrayList<Restaurant> restaurants){
        ArrayList<Restaurant> result = new ArrayList<>();
        for (Restaurant r : restaurants){
            if (restaurantName != null &&  !r.getRestaurantName().equals(restaurantName)){
                continue;
            }
            if (district != null && !r.getDistrict().equals(district)){
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
            if (type != null && !type.equals(r.getType())){
                continue;
            }
            ArrayList<Table> tables = r.getAllTables();
            for (Table t : tables){
                boolean status;
                if (startTime != null){
                    status = t.canbook(ppl, session, startTime);
                } else {
                    status = t.canbook(ppl, session);
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
