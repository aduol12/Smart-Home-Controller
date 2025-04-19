/**
 * Subject interface for the Observer design pattern.
 * Classes implementing this interface can be observed by observers.
 */
public interface Subject {
    /**
     * Registers an observer to this subject.
     * @param observer The observer to register.
     */
    void registerObserver(Observer observer);
    
    /**
     * Removes an observer from this subject.
     * @param observer The observer to remove.
     */
    void removeObserver(Observer observer);
    
    /**
     * Notifies all registered observers of a state change.
     * @param deviceName The name of the device that changed.
     * @param status The new status of the device.
     * @param message Additional information about the state change.
     */
    void notifyObservers(String deviceName, boolean status, String message);
}