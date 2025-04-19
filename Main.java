/**
 * Main class to demonstrate the Smart Home Controller system.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Smart Home Controller System...");
        
        // Create the controller
        SmartHomeController controller = new SmartHomeController();
        
        // Create a logger and register it with the controller
        Logger logger = new Logger();
        controller.registerObserver(logger);
        
        // Create basic devices
        Light livingRoomLight = new Light("Living Room Light");
        Light kitchenLight = new Light("Kitchen Light");
        AirConditioner livingRoomAC = new AirConditioner("Living Room AC");
        Door frontDoor = new Door("Front Door");
        
        // Add basic functionality for enhanced devices using decorators
        
        // Motion sensor light in the hallway
        Light hallwayLight = new Light("Hallway Light");
        MotionSensorDecorator hallwayMotionLight = new MotionSensorDecorator(hallwayLight, 30); // 30 second auto-off
        
        // Energy saving AC in the bedroom
        AirConditioner bedroomAC = new AirConditioner("Bedroom AC");
        EnergySaverDecorator ecoBedroomAC = new EnergySaverDecorator(bedroomAC);
        
        // Energy saving motion sensor light in the bathroom
        Light bathroomLight = new Light("Bathroom Light");
        Device enhancedBathroomLight = new EnergySaverDecorator(
                                            new MotionSensorDecorator(bathroomLight, 60) // 60 second auto-off
                                        );
        
        // Add all devices to the controller
        controller.addDevice(livingRoomLight);
        controller.addDevice(kitchenLight);
        controller.addDevice(livingRoomAC);
        controller.addDevice(frontDoor);
        controller.addDevice(hallwayMotionLight);
        controller.addDevice(ecoBedroomAC);
        controller.addDevice(enhancedBathroomLight);
        
        // Demonstrate system functionality
        System.out.println("\n=== Demonstration of Smart Home System ===\n");
        
        // Turn on some devices
        controller.turnOnDevice("Living Room Light");
        controller.turnOnDevice("Living Room AC");
        
        // Set custom properties
        livingRoomLight.setBrightness(75);
        livingRoomAC.setTemperature(22);
        livingRoomAC.setMode("COOL");
        
        // Demonstrate door functionality
        frontDoor.unlock();
        controller.turnOnDevice("Front Door"); // Open the door
        frontDoor.lock();
        
        // Demonstrate motion sensor
        System.out.println("\n--- Motion Sensor Demonstration ---");
        ((MotionSensorDecorator)hallwayMotionLight).motionDetected();
        System.out.println("(Imagine 30 seconds passing with no motion...)");
        // In reality, the timer would automatically turn off the light
        
        // Demonstrate energy saver
        System.out.println("\n--- Energy Saver Demonstration ---");
        controller.turnOnDevice("Bedroom AC");
        ((EnergySaverDecorator)ecoBedroomAC).setEnergySaverMode(true);
        
        // Turn on the enhanced bathroom light (with both decorators)
        System.out.println("\n--- Combined Decorators Demonstration ---");
        controller.turnOnDevice("Bathroom Light");
        
        // Turn off all devices at once
        System.out.println("\n--- Global Controls Demonstration ---");
        System.out.println("Turning off all devices...");
        controller.turnOffAllDevices();
        
        // Show device status
        System.out.println("\n--- System Status ---");
        for (Device device : controller.getAllDevices()) {
            System.out.println(device.getName() + " is " + (device.isOn() ? "ON" : "OFF"));
        }
        
        // Create a scene (group of actions)
        System.out.println("\n--- Scene Activation Demonstration ---");
        System.out.println("Activating 'Movie Night' scene...");
        
        // Movie night scene: dim lights, set AC to comfortable temperature
        livingRoomLight.setBrightness(30);
        controller.turnOnDevice("Living Room Light");
        livingRoomAC.setTemperature(23);
        livingRoomAC.setMode("COOL");
        controller.turnOnDevice("Living Room AC");
        controller.turnOffDevice("Kitchen Light");
        frontDoor.lock();
        
        System.out.println("\n--- Device Removal Demonstration ---");
        controller.removeDevice("Hallway Light");
        
        // Demonstrate scheduled actions
        System.out.println("\n--- Scheduled Actions Demonstration ---");
        System.out.println("Scheduling actions (simulated)...");
        System.out.println("- Kitchen Light: Turn ON at 6:00 AM");
        System.out.println("- Kitchen Light: Turn OFF at 8:00 AM");
        System.out.println("- Living Room AC: Turn ON at 5:00 PM");
        System.out.println("- All Lights: Turn OFF at 11:00 PM");
        
       // Wrap up the demonstration
       System.out.println("\n=== Smart Home Controller Demonstration Complete ===");
       System.out.println("The system is now running and ready for real use.");
   }
}
