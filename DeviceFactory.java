/**
 * Factory for creating different types of smart home devices.
 * Implements the Factory pattern to centralize device creation logic.
 */
public class DeviceFactory {
    
    /**
     * Creates a basic device of the specified type.
     * @param type The type of device to create (light, airconditioner, door).
     * @param name The name to give the device.
     * @return A new device of the specified type.
     * @throws IllegalArgumentException if the device type is unknown.
     */
    public static Device createDevice(String type, String name) {
        switch(type.toLowerCase()) {
            case "light" -> {
                return new Light(name);
            }
            case "airconditioner", "ac" -> {
                return new AirConditioner(name);
            }
            case "door" -> {
                return new Door(name);
            }
            default -> throw new IllegalArgumentException("Unknown device type: " + type);
        }
    }
    
    /**
     * Creates a device with motion sensor capability.
     * @param type The type of device to create (light, airconditioner, door).
     * @param name The name to give the device.
     * @param autoOffDelay The delay in seconds before turning off after no motion.
     * @return A new device with motion sensor capability.
     */
    public static Device createMotionSensorDevice(String type, String name, int autoOffDelay) {
        Device baseDevice = createDevice(type, name);
        return new MotionSensorDecorator(baseDevice, autoOffDelay);
    }
    
    /**
     * Creates a device with energy saving capability.
     * @param type The type of device to create (light, airconditioner, door).
     * @param name The name to give the device.
     * @return A new device with energy saving capability.
     */
    public static Device createEnergySaverDevice(String type, String name) {
        Device baseDevice = createDevice(type, name);
        return new EnergySaverDecorator(baseDevice);
    }
    
    /**
     * Creates a device with both motion sensor and energy saver capabilities.
     * @param type The type of device to create (light, airconditioner, door).
     * @param name The name to give the device.
     * @param autoOffDelay The delay in seconds before turning off after no motion.
     * @return A new device with both motion sensor and energy saver capabilities.
     */
    public static Device createSmartDevice(String type, String name, int autoOffDelay) {
        Device baseDevice = createDevice(type, name);
        Device motionSensorDevice = new MotionSensorDecorator(baseDevice, autoOffDelay);
        return new EnergySaverDecorator(motionSensorDevice);
    }
}