/**
 * Movie Mode automation strategy.
 * Dims lights, sets a comfortable viewing temperature, and can lock doors.
 */
public class MovieMode implements AutomationMode {
    private static final int MOVIE_TEMPERATURE = 23; // Comfortable movie watching temperature in Celsius
    private static final int LIGHT_BRIGHTNESS = 20; // Dim but not dark
    
    @Override
    public String getName() {
        return "Movie Mode";
    }
    
    @Override
    public void apply(SmartHomeController controller) {
        System.out.println("Applying Movie Mode...");
        
        // Check if we have a living room or media room
        Room targetRoom = null;
        for (Room room : controller.getAllRooms()) {
            String roomName = room.getName().toLowerCase();
            if (roomName.contains("living") || roomName.contains("media") || roomName.contains("theater")) {
                targetRoom = room;
                break;
            }
        }
        
        if (targetRoom != null) {
            System.out.println("Setting up " + targetRoom.getName() + " for movie watching");
            
            // Dim the lights in the target room
            for (Device device : targetRoom.getDevices()) {
                if (device instanceof Light light) {
                    light.setBrightness(LIGHT_BRIGHTNESS);
                    light.turnOn();
                    System.out.println("Dimmed " + light.getName() + " to " + LIGHT_BRIGHTNESS + "%");
                }
                
                // Set the AC to a comfortable temperature
                if (device instanceof AirConditioner ac) {
                    ac.setTemperature(MOVIE_TEMPERATURE);
                    ac.setMode("COOL");
                    ac.turnOn();
                    System.out.println("Set " + ac.getName() + " to " + MOVIE_TEMPERATURE + "°C");
                }
            }
            
            // Turn off lights in adjacent rooms
            for (Room room : controller.getAllRooms()) {
                if (room != targetRoom) {
                    for (Device device : room.getDevices()) {
                        if (device instanceof Light) {
                            device.turnOff();
                            System.out.println("Turned off " + device.getName() + " in " + room.getName());
                        }
                    }
                }
            }
        } else {
            // No specific movie room found, apply general settings
            System.out.println("No dedicated movie room found. Applying general movie settings.");
            
            // Dim all lights
            for (Device device : controller.getAllDevices()) {
                if (device instanceof Light light) {
                    if (light.getName().toLowerCase().contains("kitchen") || 
                        light.getName().toLowerCase().contains("bathroom")) {
                        // Keep these lights off
                        light.turnOff();
                        System.out.println("Turned off " + light.getName());
                    } else {
                        // Dim other lights
                        light.setBrightness(LIGHT_BRIGHTNESS);
                        light.turnOn();
                        System.out.println("Dimmed " + light.getName() + " to " + LIGHT_BRIGHTNESS + "%");
                    }
                }
                
                // Set living room AC to a comfortable temperature
                if (device instanceof AirConditioner && 
                    device.getName().toLowerCase().contains("living")) {
                    AirConditioner ac = (AirConditioner) device;
                    ac.setTemperature(MOVIE_TEMPERATURE);
                    ac.setMode("COOL");
                    ac.turnOn();
                    System.out.println("Set " + ac.getName() + " to " + MOVIE_TEMPERATURE + "°C");
                }
            }
        }
        
        // Optional: Lock front door for security during the movie
        for (Device device : controller.getAllDevices()) {
            if (device instanceof Door && 
                device.getName().toLowerCase().contains("front")) {
                Door door = (Door) device;
                door.lock();
                System.out.println("Locked " + door.getName() + " for movie time security");
            }
        }
    }
}