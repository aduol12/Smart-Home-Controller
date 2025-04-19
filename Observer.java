/**
 * Observer interface for the Observer design pattern.
 * Classes implementing this interface can receive updates on state changes.
 */
public interface Observer {
    /**
     * This method is called when a state change occurs in a subject that this observer is watching.
     * @param deviceName The name of the device that changed.
     * @param status The new status of the device.
     * @param message Additional information about the state change.
     */
    void update(String deviceName, boolean status, String message);
}