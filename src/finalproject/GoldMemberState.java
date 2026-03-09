package finalproject;

/**
 * Represents the "Gold" membership state with a 10% discount.
 */
public class GoldMemberState implements MemberState {

    /**
     *
     * @param totalCost
     * @return
     */
    @Override
    public double applyDiscount(double totalCost) {
        return totalCost * 0.9; // 10% discount
    }

    @Override
    public String getStatus() {
        return "Gold";
    }

    @Override
    public int redeemPoints(int currentPoints) {
        // Gold members can redeem points for additional discounts
        return Math.max(0, currentPoints - 100); // Deduct 100 points per redemption
    }
}