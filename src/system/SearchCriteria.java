package system;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;


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

    // divide-and-conquer algorithm (how complex)
    // returns similarity score between original name and search keyword
    // not using String.compareTo because no normal human would think "eat" is closer to "bibliography" than "seat"
    // return {a, b}: a = score, b = weight
    // precondition: both name and keyword are not null
    public float[] getWordScore(String name_og, String keyword_og) {
        String name = name_og.toLowerCase();
        String keyword = keyword_og.toLowerCase();
        if (keyword.length() <= 1) {
            float[] result = {name.contains(keyword) ? 1 : 0, 1};
            return result;
        }
        else {
            int bp = (int) Math.floor(keyword.length() / 2);
            String key1 = keyword.substring(0, bp);
            String key2 = keyword.substring(bp);

            float[] result = {name.contains(keyword) ? 1 : 0, 1};
            result[0] += getWordScore(name, key1)[0] + getWordScore(name, key2)[0];
            result[1] += getWordScore(name, key1)[1] + getWordScore(name, key2)[1];
            return result;
        }
    }

    public boolean isAllNull() {
        return restaurantName == null
                && district == null
                && rateRange == null
                && type == null
                && ppl == 0
                && startTime == null
                && duration == null;
    }

    // total similarity score between restaurant info and search criteria
    // +1: match, +0: mismatch
    // word score ranges between 0 and 1
    // result = 0: complete mismatch, do not add to results
    public int getSearchScore(Restaurant r) {
        float result = 0;

        if(isAllNull()) {
            // System.out.println("all null");
            return 1;
        }

        if (restaurantName != null) {
            float[] nameScore = getWordScore(r.getRestaurantName(), restaurantName);
            result += nameScore[0] / nameScore[1];
        }

        if (district != null) {
            float[] districtScore = getWordScore(r.getDistrict(), district);
            result += districtScore[0] / districtScore[1];
        }

        if (rateRange != null){
            int minRate = rateRange.charAt(0) - '0';
            int maxRate = rateRange.charAt(rateRange.length()-1) - '0';
            result += r.getRate() > minRate && r.getRate() < maxRate ? 1 : 0;
        }

        if (type != null) {
            float[] typeScore = getWordScore(r.getType(), type);
            result += typeScore[0] / typeScore[1];
        }


        return (int) Math.ceil(result * 100); // higher accuracy for comparator
    }

    public ArrayList<Restaurant> searchRestaurantsIn(ArrayList<Restaurant> restaurants){
        Server server = Server.getInstance();
        ArrayList<Restaurant> result = new ArrayList<>();
        for (Restaurant r : restaurants){
            if (getSearchScore(r) == 0) {
                continue;
            }

            // ArrayList<Table> tables = r.getAllTables();
            ArrayList<Table> tables = server.getAllTablesOf((r));
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
        }

        Comparator<Restaurant> sorter = new Comparator<Restaurant>() {
            @Override
            public int compare(final Restaurant r1, final Restaurant r2) {
                return getSearchScore(r1) - getSearchScore(r2);
            }
        };
        result.sort(sorter.reversed());
        return result;
    }
}
