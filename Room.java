import java.util.ArrayList;
import java.util.List;

/**
 * Represents a room in a smart home, containing multiple devices.
 * Allows for collective control of all devices in the room.
 */
public class Room {
    private final String name;
    private final List<Device> devices;
    
    /**
     * Creates a new room with the given name.
     * @param name The name of the room.
     */
    public Room(String name) {
        this.name = name;
        this.devices = new ArrayList<>();
    }
    
    /**
     * Gets the name of the room.
     * @return The room name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Adds a device to this room.
     * @param device The device to add.
     */
    public void addDevice(Device device) {
        devices.add(device);
        System.out.println(device.getName() + " added to " + name);
    }
    
    /**
     * Removes a device from this room.
     * @param device The device to remove.
     * @return true if the device was removed, false if it wasn't in the room.
     */
    public boolean removeDevice(Device device) {
        boolean removed = devices.remove(device);
        if (removed) {
            System.out.println(device.getName() + " removed from " + name);
        }
        return removed;
    }
    
    /**
     * Gets all devices in this room.
     * @return A list of all devices.
     */
    public List<Device> getDevices() {
        return new ArrayList<>(devices);
    }
    
    /**
     * Gets a specific device by name.
     * @param deviceName The name of the device.
     * @return The device, or null if not found.
     */
    public Device getDevice(String deviceName) {
        for (Device device : devices) {
            if (device.getName().equals(deviceName)) {
                return device;
            }
        }
        return null;
    }
    
    /**
     * Turns on all devices in the room.
     */
    public void turnOnAllDevices() {
        System.out.println("Turning on all devices in " + name);
        for (Device device : devices) {
            device.turnOn();
        }
    }
    
    /**
     * Turns off all devices in the room.
     */
    public void turnOffAllDevices() {
        System.out.println("Turning off all devices in " + name);
        for (Device device : devices) {
            device.turnOff();
        }
    }
    
    /**
     * Gets a specific type of devices from the room.
     * @param deviceClass The class of devices to get.
     * @return A list of devices of the specified type.
     */
    public <T extends Device> List<T> getDevicesOfType(Class<T> deviceClass) {
        List<T> result = new ArrayList<>();
        for (Device device : devices) {
            if (deviceClass.isInstance(device)) {
                result.add(deviceClass.cast(device));
            }
        }
        return result;
    }
    
    /**
     * Returns a string representation of the room.
     * @return A string representation including the room name and device count.
     */
    @Override
    public String toString() {
        return name + " (" + devices.size() + " devices)";
    }
}