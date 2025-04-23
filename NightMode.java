import java.util.List;

/**
 * Night Mode automation strategy.
 * Turns off most lights, locks doors, and sets a comfortable sleeping temperature.
 */
public class NightMode implements AutomationMode {
    private static final int NIGHT_TEMPERATURE = 20; // in Celsius
    
    @Override
    public String getName() {
        return "Night Mode";
    }
    
    @Override
    public void apply(SmartHomeController controller) {
        System.out.println("Applying Night Mode...");
        
        // Turn off all lights except for minimal security lighting
        for (Device device : controller.getAllDevices()) {
            if (device instanceof Light light) {
                // If it's a night light or security light, dim it
                if (light.getName().toLowerCase().contains("night") || 
                    light.getName().toLowerCase().contains("security")) {
                    light.turnOn();
                    light.setBrightness(20); // Dim night lights
                    System.out.println("Dimmed " + light.getName() + " to 20%");
                } else {
                    // Turn off regular lights
                    light.turnOff();
                    System.out.println("Turned off " + light.getName());
                }
            }
        }
        
        // Set all ACs to night temperature
        for (Device device : controller.getAllDevices()) {
            if (device instanceof AirConditioner ac) {
                ac.setTemperature(NIGHT_TEMPERATURE);
                System.out.println("Set " + device.getName() + " to " + NIGHT_TEMPERATURE + "°C");
            }
        }
        
        // Lock all doors
        for (Device device : controller.getAllDevices()) {
            if (device instanceof Door door) {
                door.lock();
                System.out.println("Locked " + device.getName());
            }
        }
        
        // Room-based control if rooms are available
        for (Room room : controller.getAllRooms()) {
            // Bedroom specific settings
            if (room.getName().toLowerCase().contains("bedroom")) {
                // Apply bedroom-specific settings
                List<AirConditioner> acs = room.getDevicesOfType(AirConditioner.class);
                for (AirConditioner ac : acs) {
                    // Maybe bedrooms get slightly cooler
                    ac.setTemperature(19);
                    System.out.println("Set bedroom " + ac.getName() + " to 19°C");
                }
            }
        }
    }
}