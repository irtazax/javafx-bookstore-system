package finalproject;

/**
 * Represents a user with membership state capabilities.
 * @author dtorallo
 */
public class User {
    private String username, password;
    private int points;
    private MemberState state;  // State Pattern integration

    // Default constructor (unchanged)
    public User() {
        this.username = "";
        this.password = "";
        this.points = 0;
        this.state = new SilverMemberState(); // Default state
    }

    // Owner constructor (unchanged)
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.points = 0;
        this.state = new SilverMemberState();
    }

    // Customer constructor (unchanged, but adds state initialization)
    public User(String username, String password, int points) {
        this.username = username;
        this.password = password;
        this.points = points;
        updateState(); // Initialize state based on points
    }

    //================= Existing Getters/Setters (unchanged) =================
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public int getPoints() {
        return this.points;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    // Modified setter to auto-update state when points change
    public void setPoints(int points) {
        this.points = points;
        updateState(); // Re-evaluate membership state
    }

    //================= State Pattern Additions =================
    /**
     * Updates the membership state (Silver/Gold) based on points.
     */
    private void updateState() {
        this.state = (this.points >= 1000) 
            ? new GoldMemberState() 
            : new SilverMemberState();
    }

    /**
     * Applies discount based on current membership state.
     * @param totalCost Original cost before discounts
     * @return Discounted cost
     */
    public double applyDiscount(double totalCost) {
        return this.state.applyDiscount(totalCost);
    }

    /**
     * Gets the current membership status (e.g., "Gold").
     * @return 
     */
    public String getMembershipStatus() {
        return this.state.getStatus();
    }

    /**
     * (Optional) Redeems points according to state rules.
     * @return 
     */
    public int redeemPoints() {
        return this.state.redeemPoints(this.points);
    }
}