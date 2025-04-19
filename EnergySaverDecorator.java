// EnergySaverDecorator.java
// Place all files in the same directory for this approach

/**
 * Adds energy saving functionality to a device.
 * Adjusts device settings to optimize energy usage.
 */
public class EnergySaverDecorator extends DeviceDecorator {
    private boolean energySaverMode;
    
    /**
     * Creates a new EnergySaverDecorator.
     * @param device The device to decorate.
     */
    public EnergySaverDecorator(Device device) {
        super(device);
        this.energySaverMode = false;
    }
    
    @Override
    public void turnOn() {
        device.turnOn();
        
        // Apply energy saving settings if mode is enabled
        if (energySaverMode) {
            applyEnergySavingSettings();
        }
    }
    
    /**
     * Enables or disables energy saver mode.
     * @param enabled true to enable energy saver mode, false to disable it.
     */
    public void setEnergySaverMode(boolean enabled) {
        this.energySaverMode = enabled;
        
        if (enabled) {
            System.out.println("Energy saver mode enabled for " + device.getName());
            
            // Apply energy saving settings if the device is on
            if (device.isOn()) {
                applyEnergySavingSettings();
            }
        } else {
            System.out.println("Energy saver mode disabled for " + device.getName());
        }
    }
    
    /**
     * Applies energy saving settings based on device type.
     */
    private void applyEnergySavingSettings() {
        switch (device) {
            case Light light -> {
// For lights, reduce brightness
int currentBrightness = light.getBrightness();
int newBrightness = Math.max(currentBrightness - 30, 20); // Reduce by 30%, but min 20%

light.setBrightness(newBrightness);
System.out.println("Energy saver reduced brightness of " + device.getName() + " to " + newBrightness + "%");
            }
            case AirConditioner ac -> {
// For AC, adjust temperature to save energy
String mode = ac.getMode();
int currentTemp = ac.getTemperature();

if (mode.equals("COOL")) {
    // In cooling mode, increase temperature to save energy
    int newTemp = Math.min(currentTemp + 2, 26); // Increase by 2°C, max 26°C
    ac.setTemperature(newTemp);
    System.out.println("Energy saver increased cooling temperature of " + device.getName() + " to " + newTemp + "°C");
}
else if (mode.equals("HEAT")) {
    // In heating mode, decrease temperature to save energy
    int newTemp = Math.max(currentTemp - 2, 20); // Decrease by 2°C, min 20°C
    ac.setTemperature(newTemp);
    System.out.println("Energy saver decreased heating temperature of " + device.getName() + " to " + newTemp + "°C");
}
            }
            default -> {
            }
        }
        // Other device types can be handled here
    }
    
    /**
     * Checks if energy saver mode is enabled.
     * @return true if energy saver mode is enabled, false otherwise.
     */
    public boolean isEnergySaverModeEnabled() {
        return energySaverMode;
    }
}