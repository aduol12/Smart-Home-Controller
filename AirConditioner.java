/**
 * Implementation of a smart air conditioner device.
 */
public class AirConditioner implements Device {
    private final String name;
    private boolean status;
    private int temperature;
    private String mode; // COOL, HEAT, FAN
    
    /**
     * Creates a new AirConditioner with the given name.
     * @param name The name of the air conditioner.
     */
    public AirConditioner(String name) {
        this.name = name;
        this.status = false;
        this.temperature = 24; // Default temperature in Celsius
        this.mode = "COOL";    // Default mode
    }
    
    @Override
    public void turnOn() {
        this.status = true;
        System.out.println(name + " AC turned on. Mode: " + mode + ", Temperature: " + temperature + "°C");
    }
    
    @Override
    public void turnOff() {
        this.status = false;
        System.out.println(name + " AC turned off");
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
     * Sets the target temperature for the air conditioner.
     * @param temperature The target temperature in Celsius.
     */
    public void setTemperature(int temperature) {
        // Limit temperature to a reasonable range
        if (temperature < 16) {
            this.temperature = 16;
        } else if (temperature > 30) {
            this.temperature = 30;
        } else {
            this.temperature = temperature;
        }
        
        if (status) {
            System.out.println(name + " temperature set to " + this.temperature + "°C");
        }
    }
    
    /**
     * Gets the current target temperature.
     * @return The current temperature setting in Celsius.
     */
    public int getTemperature() {
        return temperature;
    }
    
    /**
     * Sets the operation mode of the air conditioner.
     * @param mode The operation mode (COOL, HEAT, FAN).
     */
    public void setMode(String mode) {
        if (mode.equals("COOL") || mode.equals("HEAT") || mode.equals("FAN")) {
            this.mode = mode;
            
            if (status) {
                System.out.println(name + " mode set to " + this.mode);
            }
        } else {
            System.out.println("Invalid mode. Use COOL, HEAT, or FAN.");
        }
    }
    
    /**
     * Gets the current operation mode.
     * @return The current operation mode.
     */
    public String getMode() {
        return mode;
    }
}