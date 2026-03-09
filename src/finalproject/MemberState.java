package finalproject;

/**
 * Interface defining the contract for all membership states.
 * Ensures consistent behavior across concrete states (Gold/Silver).
 */
public interface MemberState {
    /**
     * Applies a discount to the total cost based on membership state.
     * @param totalCost The original cost before discounts.
     * @return Discounted cost.
     */
    double applyDiscount(double totalCost);

    /**
     * Returns the status name of the membership (e.g., "Gold").
     * @return 
     */
    String getStatus();

    /**
     * (Optional) Handles points redemption logic for the state.
     * @param currentPoints The user's current points.
     * @return New points after redemption.
     */
    default int redeemPoints(int currentPoints) {
        return currentPoints; // Default: no redemption logic
    }
}