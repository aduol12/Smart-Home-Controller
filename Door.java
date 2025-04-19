/**
 * Implementation of a smart door device.
 */
public class Door implements Device {
    private final String name;
    private boolean status; // true = open, false = closed
    private boolean locked;
    
    /**
     * Creates a new Door with the given name.
     * @param name The name of the door.
     */
    public Door(String name) {
        this.name = name;
        this.status = false; // Default to closed
        this.locked = true;  // Default to locked
    }
    
    @Override
    public void turnOn() {
        // For a door, "on" means open
        if (locked) {
            System.out.println(name + " door cannot be opened. It is locked.");
            return;
        }
        this.status = true;
        System.out.println(name + " door opened");
    }
    
    @Override
    public void turnOff() {
        // For a door, "off" means closed
        this.status = false;
        System.out.println(name + " door closed");
    }
    
    @Override
    public boolean isOn() {
        return status;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    /**
     * Locks the door.
     */
    public void lock() {
        this.locked = true;
        System.out.println(name + " door locked");
        
        // Close the door if it's open
        if (status) {
            turnOff();
        }
    }
    
    /**
     * Unlocks the door.
     */
    public void unlock() {
        this.locked = false;
        System.out.println(name + " door unlocked");
    }
    
    /**
     * Checks if the door is locked.
     * @return true if the door is locked, false otherwise.
     */
    public boolean isLocked() {
        return locked;
    }
}