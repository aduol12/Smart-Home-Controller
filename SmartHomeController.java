import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main controller for the smart home system.
 * Implements the Singleton pattern to ensure only one instance exists.
 * Also implements the Subject interface to provide observer pattern functionality.
 */
public class SmartHomeController implements Subject {
    // Singleton instance
    private static SmartHomeController instance;
    
    private final Map<String, Device> devices;
    private final Map<String, Room> rooms;
    private final List<Observer> observers;
    private AutomationMode currentMode;
    
    /**
     * Private constructor to prevent instantiation outside of this class.
     * Part of the Singleton pattern implementation.
     */
    private SmartHomeController() {
        this.devices = new HashMap<>();
        this.rooms = new HashMap<>();
        this.observers = new ArrayList<>();
        this.currentMode = null;
    }
    
    /**
     * Gets the singleton instance of the SmartHomeController.
     * Creates it if it doesn't exist yet.
     * @return The singleton instance of SmartHomeController
     */
    public static SmartHomeController getInstance() {
        if (instance == null) {
            instance = new SmartHomeController();
        }
        return instance;
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
    
    /**
     * Creates a new room.
     * @param roomName The name of the room to create.
     * @return The newly created room.
     */
    public Room createRoom(String roomName) {
        Room room = new Room(roomName);
        rooms.put(roomName, room);
        System.out.println("Room created: " + roomName);
        return room;
    }
    
    /**
     * Gets a room by name.
     * @param roomName The name of the room.
     * @return The room, or null if not found.
     */
    public Room getRoom(String roomName) {
        return rooms.get(roomName);
    }
    
    /**
     * Gets all rooms.
     * @return A list of all rooms.
     */
    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms.values());
    }
    
    /**
     * Adds a device to a room.
     * @param deviceName The name of the device to add.
     * @param roomName The name of the room to add the device to.
     * @return true if the device was added, false if the device or room was not found.
     */
    public boolean addDeviceToRoom(String deviceName, String roomName) {
        Device device = devices.get(deviceName);
        Room room = rooms.get(roomName);
        
        if (device == null) {
            System.out.println("Device not found: " + deviceName);
            return false;
        }
        
        if (room == null) {
            System.out.println("Room not found: " + roomName);
            return false;
        }
        
        room.addDevice(device);
        return true;
    }
    
    /**
     * Turns on all devices in a room.
     * @param roomName The name of the room.
     * @return true if the room was found, false otherwise.
     */
    public boolean turnOnRoom(String roomName) {
        Room room = rooms.get(roomName);
        if (room == null) {
            System.out.println("Room not found: " + roomName);
            return false;
        }
        
        System.out.println("Turning on all devices in room: " + roomName);
        room.turnOnAllDevices();
        return true;
    }
    
    /**
     * Turns off all devices in a room.
     * @param roomName The name of the room.
     * @return true if the room was found, false otherwise.
     */
    public boolean turnOffRoom(String roomName) {
        Room room = rooms.get(roomName);
        if (room == null) {
            System.out.println("Room not found: " + roomName);
            return false;
        }
        
        System.out.println("Turning off all devices in room: " + roomName);
        room.turnOffAllDevices();
        return true;
    }
    
    /**
     * Sets the current automation mode.
     * @param mode The automation mode to set.
     */
    public void setAutomationMode(AutomationMode mode) {
        this.currentMode = mode;
        if (mode != null) {
            System.out.println("Activating automation mode: " + mode.getName());
            mode.apply(this);
        } else {
            System.out.println("Automation mode disabled");
        }
    }
    
    /**
     * Gets the current automation mode.
     * @return The current automation mode, or null if none is active.
     */
    public AutomationMode getCurrentMode() {
        return currentMode;
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