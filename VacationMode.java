import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Vacation Mode automation strategy.
 * Simulates occupancy by randomly turning lights on and off,
 * maintains minimal climate control, and ensures security.
 */
public class VacationMode implements AutomationMode {
    private static final int MIN_TEMPERATURE = 16; // Minimum AC temperature in Celsius
    private static final int MAX_TEMPERATURE = 28; // Maximum AC temperature in Celsius
    private Timer simulationTimer;
    private final Random random = new Random();
    
    @Override
    public String getName() {
        return "Vacation Mode";
    }
    
    @Override
    public void apply(SmartHomeController controller) {
        System.out.println("Applying Vacation Mode...");
        
        // Stop any existing simulation
        if (simulationTimer != null) {
            simulationTimer.cancel();
        }
        
        // Set minimal climate control
        for (Device device : controller.getAllDevices()) {
            if (device instanceof AirConditioner ac) {
                
                // Set to energy saving temperature, either cooling or heating depending on mode
                if (ac.getMode().equals("COOL")) {
                    ac.setTemperature(MAX_TEMPERATURE); // Higher temperature to save energy
                } else if (ac.getMode().equals("HEAT")) {
                    ac.setTemperature(MIN_TEMPERATURE); // Lower temperature to save energy
                }
                
                System.out.println("Set " + device.getName() + " to " + ac.getTemperature() + "°C (" + ac.getMode() + ")");
            }
        }
        
        // Lock all doors
        for (Device device : controller.getAllDevices()) {
            if (device instanceof Door door) {
                door.lock();
                System.out.println("Locked " + door.getName() + " for security");
            }
        }
        
        // Initially turn off all lights
        for (Device device : controller.getAllDevices()) {
            if (device instanceof Light) {
                device.turnOff();
            }
        }
        
        // Set up random light simulation to make the house look occupied
        simulationTimer = new Timer();
        simulationTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                simulateOccupancy(controller);
            }
        }, 0, 30 * 60 * 1000); // Run every 30 minutes
        
        System.out.println("Vacation mode activated. Random light patterns will be generated to simulate occupancy.");
    }
    
    /**
     * Simulates occupancy by randomly turning lights on and off.
     * @param controller The smart home controller.
     */
    public void simulateOccupancy(SmartHomeController controller) {
        System.out.println("Running occupancy simulation...");
        
        // Get time of day (simplified)
        int hourOfDay = (int) ((System.currentTimeMillis() / (1000 * 60 * 60)) % 24);
        boolean isNighttime = hourOfDay >= 18 || hourOfDay < 6;
        
        // Adjust simulation based on time of day
        int numLightsToTurnOn = isNighttime ? 2 + random.nextInt(3) : random.nextInt(2);
        
        // Turn off all lights first
        for (Device device : controller.getAllDevices()) {
            if (device instanceof Light) {
                device.turnOff();
            }
        }
        
        // Turn on random lights
        int lightsCount = 0;
        for (Device device : controller.getAllDevices()) {
            if (device instanceof Light && random.nextBoolean() && lightsCount < numLightsToTurnOn) {
                device.turnOn();
                
                // Set random brightness
                Light light = (Light) device;
                light.setBrightness(40 + random.nextInt(60)); // Between 40% and 100%
                
                System.out.println("Simulation turned on " + device.getName() + 
                                 " with brightness " + light.getBrightness() + "%");
                lightsCount++;
            }
        }
        
        // Schedule next change
        Timer changeTimer = new Timer();
        changeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Turn off a random light that is on
                for (Device device : controller.getAllDevices()) {
                    if (device instanceof Light && device.isOn() && random.nextBoolean()) {
                        device.turnOff();
                        System.out.println("Simulation turned off " + device.getName());
                        break;
                    }
                }
            }
        }, (10 + random.nextInt(50)) * 60 * 1000); // Random time between 10 and 60 minutes
    }
    
    /**
     * Cancels the occupancy simulation.
     * Should be called when switching to another mode.
     */
    public void cancelSimulation() {
        if (simulationTimer != null) {
            simulationTimer.cancel();
            simulationTimer = null;
            System.out.println("Vacation mode simulation canceled");
        }
    }
}