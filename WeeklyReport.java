import java.util.Scanner;

public class WeeklyReport {
    private Restaurant restaurant;
    private Server server;

    public WeeklyReport(Account account) {
        if (account instanceof Restaurant tempRestaurant) {
            this.restaurant = tempRestaurant;
            this.server = Server.getInstance();
        } else {
            throw new IllegalArgumentException("Account must be an instance of Restaurant");
        }
    }

    public void displayWeeklyReport(Scanner in) {
        server.generateRestaurantLog();
        String report = generateWeeklyReport();
        System.out.println(report);
        System.out.println("1. Exit\n");
       
        boolean isValidOption = false;
        while (!isValidOption) {
            System.out.print("What action do you want to do?: ");
            try {
                int op = Integer.parseInt(in.next());
                in.nextLine();
                if (op == 1) {
                    isValidOption = true;
                } else {
                    System.out.println("\nInvalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid option. Please try again.");
            }
        }        
    }

    private String generateWeeklyReport() {
        RestaurantLog lastWeekLog = server.getRestaurantLog(restaurant, "lastWeek");
        RestaurantLog thisWeekLog = server.getRestaurantLog(restaurant, "thisWeek");

        StringBuilder report = new StringBuilder();
        report.append("# Here is your weekly report #\n\n");

        report.append("Last week:\n");
        report.append("Rank: ").append(lastWeekLog.getRank()).append("\n");
        report.append("Rate: ").append(lastWeekLog.getRate()).append("\n");
        report.append("Total ppl: ").append(lastWeekLog.getTotalPpl()).append("\n");
        report.append("Comment:\n");
        for (Comment comment : lastWeekLog.getComments()) {
            report.append(comment.getCustomer_name()).append(": ").append(comment.getContent()).append(" ").append(comment.getRate()).append("\n");
        }

        report.append("\nThis week:\n");
        report.append("Rank: ").append(thisWeekLog.getRank()).append("\n");
        report.append("Rate: ").append(thisWeekLog.getRate()).append("\n");
        report.append("Total ppl: ").append(thisWeekLog.getTotalPpl()).append("\n");
        report.append("Comment:\n");
        for (Comment comment : thisWeekLog.getComments()) {
            report.append(comment.getCustomer_name()).append(": ").append(comment.getContent()).append(" ").append(comment.getRate()).append("\n");
        }

        report.append("\nRank decrease/increase ").append(thisWeekLog.getRank() - lastWeekLog.getRank()).append("\n");
        report.append("Rate decrease/increase ").append(thisWeekLog.getRate() - lastWeekLog.getRate()).append("\n");
        report.append("Total ppl decrease/increase ").append(thisWeekLog.getTotalPpl() - lastWeekLog.getTotalPpl()).append("\n");

        return report.toString();
    }
}