import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main controller for the smart home system.
 * Manages devices and implements the Subject interface to provide observer pattern functionality.
 */
public class SmartHomeController implements Subject {
    private final Map<String, Device> devices;
    private final List<Observer> observers;
    
    /**
     * Creates a new SmartHomeController.
     */
    public SmartHomeController() {
        this.devices = new HashMap<>();
        this.observers = new ArrayList<>();
    }
    
    /**
     * Adds a device to the controller.
     * @param device The device to add.
     */
    public void addDevice(Device device) {
        devices.put(device.getName(), device);
        System.out.println("Device added: " + device.getName());
    }
    
    /**
     * Removes a device from the controller.
     * @param deviceName The name of the device to remove.
     * @return true if the device was removed, false if it didn't exist.
     */
    public boolean removeDevice(String deviceName) {
        Device removed = devices.remove(deviceName);
        if (removed != null) {
            System.out.println("Device removed: " + deviceName);
            return true;
        }
        System.out.println("Device not found: " + deviceName);
        return false;
    }
    
    /**
     * Gets a device by name.
     * @param deviceName The name of the device to get.
     * @return The device, or null if not found.
     */
    public Device getDevice(String deviceName) {
        return devices.get(deviceName);
    }
    
    /**
     * Gets all devices managed by this controller.
     * @return A list of all devices.
     */
    public List<Device> getAllDevices() {
        return new ArrayList<>(devices.values());
    }
    
    /**
     * Turns on a device by name.
     * @param deviceName The name of the device to turn on.
     * @return true if the device was turned on, false if not found.
     */
    public boolean turnOnDevice(String deviceName) {
        Device device = devices.get(deviceName);
        if (device != null) {
            device.turnOn();
            notifyObservers(deviceName, device.isOn(), "Device turned ON");
            return true;
        }
        System.out.println("Device not found: " + deviceName);
        return false;
    }
    
    /**
     * Turns off a device by name.
     * @param deviceName The name of the device to turn off.
     * @return true if the device was turned off, false if not found.
     */
    public boolean turnOffDevice(String deviceName) {
        Device device = devices.get(deviceName);
        if (device != null) {
            device.turnOff();
            notifyObservers(deviceName, device.isOn(), "Device turned OFF");
            return true;
        }
        System.out.println("Device not found: " + deviceName);
        return false;
    }
    
    /**
     * Turns on all devices.
     */
    public void turnOnAllDevices() {
        for (Device device : devices.values()) {
            device.turnOn();
            notifyObservers(device.getName(), device.isOn(), "Device turned ON (mass action)");
        }
    }
    
    /**
     * Turns off all devices.
     */
    public void turnOffAllDevices() {
        for (Device device : devices.values()) {
            device.turnOff();
            notifyObservers(device.getName(), device.isOn(), "Device turned OFF (mass action)");
        }
    }
    
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }
    
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObservers(String deviceName, boolean status, String message) {
        for (Observer observer : observers) {
            observer.update(deviceName, status, message);
        }
    }
}