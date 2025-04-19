/**
 * Implementation of a smart light device.
 */
public class Light implements Device {
    private final String name;
    private boolean status;
    private int brightness;
    
    /**
     * Creates a new Light device with the given name.
     * @param name The name of the light.
     */
    public Light(String name) {
        this.name = name;
        this.status = false;
        this.brightness = 50; // Default brightness is 50%
    }
    
    @Override
    public void turnOn() {
        this.status = true;
        System.out.println(name + " light turned on with brightness " + brightness + "%");
    }
    
    @Override
    public void turnOff() {
        this.status = false;
        System.out.println(name + " light turned off");
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
     * Sets the brightness level for the light.
     * @param brightness The brightness level (0-100).
     */
    public void setBrightness(int brightness) {
        if (brightness < 0) {
            this.brightness = 0;
        } else if (brightness > 100) {
            this.brightness = 100;
        } else {
            this.brightness = brightness;
        }
        
        if (status) {
            System.out.println(name + " brightness set to " + this.brightness + "%");
        }
    }
    
    /**
     * Gets the current brightness level.
     * @return The current brightness level.
     */
    public int getBrightness() {
        return brightness;
    }
}