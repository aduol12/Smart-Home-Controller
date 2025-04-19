/**
 * The base interface for all smart home devices.
 */
public interface Device {
    /**
     * Turns the device on.
     */
    void turnOn();
    
    /**
     * Turns the device off.
     */
    void turnOff();
    
    /**
     * Gets the current status of the device.
     * @return true if the device is on, false otherwise.
     */
    boolean isOn();
    
    /**
     * Gets the name of the device.
     * @return String representing the device name.
     */
    String getName();
}