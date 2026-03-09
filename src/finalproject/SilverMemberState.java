package finalproject;

/**
 * Represents the "Silver" membership state with no discount.
 */
public class SilverMemberState implements MemberState {
    @Override
    public double applyDiscount(double totalCost) {
        return totalCost; // No discount
    }

    @Override
    public String getStatus() {
        return "Silver";
    }
    // No override for redeemPoints() ? uses default behavior (no redemption)
}