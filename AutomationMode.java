/**
 * Interface for the Strategy pattern to implement different automation modes.
 * Each mode provides a different strategy for controlling devices.
 */
public interface AutomationMode {
    /**
     * Gets the name of this automation mode.
     * @return The name of the mode.
     */
    String getName();
    
    /**
     * Applies this automation mode to the smart home controller.
     * @param controller The controller to apply the mode to.
     */
    void apply(SmartHomeController controller);
}