
package domain;

public class DiscountPolicy {

    public static double calculateDiscount(String customerType, int orderValue) {
        if ("VIP".equalsIgnoreCase(customerType) && orderValue > 1000) {
            return 0.20;
        }
        if ("Regular".equalsIgnoreCase(customerType) && orderValue > 500) {
            return 0.10;
        }
        return 0.0;
    }
}
