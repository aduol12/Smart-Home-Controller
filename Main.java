// File: SmartHomeController.java
//import java.util.List;

/**
 * Main class to demonstrate the Smart Home Controller system.
 * Shows use of Observer, Decorator, Singleton, Factory, and Strategy patterns.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Smart Home Controller System...");
        
        // Get the singleton instance of the controller
        SmartHomeController controller = SmartHomeController.getInstance();
        
        // Create a logger and register it with the controller
        Logger logger = new Logger();
        controller.registerObserver(logger);
        // Create rooms

        controller.createRoom("Kitchen");
        controller.createRoom("Bedroom");
        controller.createRoom("Bathroom");
        controller.createRoom("Hallway");
        
        // Create basic devices using the Factory pattern
        Device livingRoomLight = DeviceFactory.createDevice("light", "Living Room Light");
        Device kitchenLight = DeviceFactory.createDevice("light", "Kitchen Light");
        Device livingRoomAC = DeviceFactory.createDevice("ac", "Living Room AC");
        Device frontDoor = DeviceFactory.createDevice("door", "Front Door");
        
        // Create enhanced devices using the Factory pattern combined with decorators
        Device hallwayLight = DeviceFactory.createMotionSensorDevice("light", "Hallway Light", 30); // 30 second auto-off
        Device bedroomAC = DeviceFactory.createEnergySaverDevice("ac", "Bedroom AC");
        Device bathroomLight = DeviceFactory.createSmartDevice("light", "Bathroom Light", 60); // With motion and energy saving
        
        // Add all devices to the controller
        controller.addDevice(livingRoomLight);
        controller.addDevice(kitchenLight);
        controller.addDevice(livingRoomAC);
        controller.addDevice(frontDoor);
        controller.addDevice(hallwayLight);
        controller.addDevice(bedroomAC);
        controller.addDevice(bathroomLight);
        
        // Add devices to rooms
        controller.addDeviceToRoom("Living Room Light", "Living Room");
        controller.addDeviceToRoom("Living Room AC", "Living Room");
        controller.addDeviceToRoom("Kitchen Light", "Kitchen");
        controller.addDeviceToRoom("Hallway Light", "Hallway");
        controller.addDeviceToRoom("Bedroom AC", "Bedroom");
        controller.addDeviceToRoom("Bathroom Light", "Bathroom");
        controller.addDeviceToRoom("Front Door", "Hallway");
        
        // Demonstrate system functionality
        System.out.println("\n=== Demonstration of Smart Home System ===\n");
        
        // Turn on some devices
        controller.turnOnDevice("Living Room Light");
        controller.turnOnDevice("Living Room AC");
        
        // Set custom properties
        ((Light)livingRoomLight).setBrightness(75);
        ((AirConditioner)livingRoomAC).setTemperature(22);
        ((AirConditioner)livingRoomAC).setMode("COOL");
        
        // Demonstrate door functionality
        ((Door)frontDoor).unlock();
        controller.turnOnDevice("Front Door"); // Open the door
        ((Door)frontDoor).lock();
        
        // Demonstrate motion sensor
        System.out.println("\n--- Motion Sensor Demonstration ---");
        ((MotionSensorDecorator)hallwayLight).motionDetected();
        System.out.println("(Imagine 30 seconds passing with no motion...)");
        // In reality, the timer would automatically turn off the light
        
        // Demonstrate energy saver
        System.out.println("\n--- Energy Saver Demonstration ---");
        controller.turnOnDevice("Bedroom AC");
        ((EnergySaverDecorator)bedroomAC).setEnergySaverMode(true);
        
        // Turn on the enhanced bathroom light (with both decorators)
        System.out.println("\n--- Combined Decorators Demonstration ---");
        controller.turnOnDevice("Bathroom Light");
        
        // Demonstrate room-based control
        System.out.println("\n--- Room-Based Control Demonstration ---");
        System.out.println("Turning on all devices in the Living Room:");
        controller.turnOnRoom("Living Room");
        System.out.println("\nTurning off all devices in the Kitchen:");
        controller.turnOffRoom("Kitchen");
        
        // Demonstrate automation modes using Strategy pattern
        System.out.println("\n--- Automation Modes Demonstration (Strategy Pattern) ---");
        
        // Night Mode
        System.out.println("\n1. Activating Night Mode:");
        AutomationMode nightMode = new NightMode();
        controller.setAutomationMode(nightMode);
        
        // Let's reset for the next demo
        System.out.println("\nResetting devices for next demonstration...");
        resetDevices(controller);
        
        // Movie Mode
        System.out.println("\n2. Activating Movie Mode:");
        AutomationMode movieMode = new MovieMode();
        controller.setAutomationMode(movieMode);
        
        // Let's reset for the next demo
        System.out.println("\nResetting devices for next demonstration...");
        resetDevices(controller);
        
        // Vacation Mode
        System.out.println("\n3. Activating Vacation Mode:");
        VacationMode vacationMode = new VacationMode();
        controller.setAutomationMode(vacationMode);
        
        // For demonstration purposes, call the simulation once directly
        System.out.println("\nSimulating a random occupancy pattern (normally this would run on a timer):");
        vacationMode.simulateOccupancy(controller);
        
        // Cancel the vacation simulation
        vacationMode.cancelSimulation();
        
        // Demonstrate singleton pattern
        System.out.println("\n--- Singleton Pattern Demonstration ---");
        SmartHomeController anotherController = SmartHomeController.getInstance();
        System.out.println("Is the second controller instance the same object? " + 
                          (controller == anotherController ? "Yes" : "No"));
        
        // Demonstrate factory pattern
        System.out.println("\n--- Factory Pattern Demonstration ---");
        System.out.println("Creating a new device using the DeviceFactory:");
        Device newLight = DeviceFactory.createDevice("light", "Dining Room Light");
        controller.addDevice(newLight);
        
        Device smartKitchenLight = DeviceFactory.createSmartDevice("light", "Smart Kitchen Light", 120);
        controller.addDevice(smartKitchenLight);
        System.out.println("Created both a basic light and a smart light with motion and energy saving features");
        
        // Wrap up the demonstration
        System.out.println("\n=== Smart Home Controller Demonstration Complete ===");
        System.out.println("Demonstrated all required patterns:");
        System.out.println("1. Observer Pattern - For logging device state changes");
        System.out.println("2. Decorator Pattern - For adding features to devices");
        System.out.println("3. Singleton Pattern - For ensuring one controller instance");
        System.out.println("4. Factory Pattern - For centralizing device creation");
        System.out.println("5. Strategy Pattern - For implementing different automation modes");
        System.out.println("\nAlso demonstrated room-based device grouping and control");
    }
    
    /**
     * Helper method to reset devices to a neutral state.
     * @param controller The smart home controller.
     */
    private static void resetDevices(SmartHomeController controller) {
        // Turn everything off first
        controller.turnOffAllDevices();
        
        // Set some basic state
        for (Device device : controller.getAllDevices()) {
            switch (device) {
                case Light light -> light.setBrightness(50);
                case AirConditioner ac -> {
                    ac.setTemperature(24);
                    ac.setMode("COOL");
                }
                case Door door -> door.unlock();
                default -> {
                    // Handle other device types if necessary
                }
            }
        }
    }
}