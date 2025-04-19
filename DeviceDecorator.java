/**
 * Abstract base class for all device decorators.
 * Uses the Decorator pattern to add functionality to devices.
 */
public abstract class DeviceDecorator implements Device {
    protected Device device;
    
    /**
     * Creates a new DeviceDecorator.
     * @param device The device to decorate.
     */
    public DeviceDecorator(Device device) {
        this.device = device;
    }
    
    @Override
    public void turnOn() {
        device.turnOn();
    }
    
    @Override
    public void turnOff() {
        device.turnOff();
    }
    
    @Override
    public boolean isOn() {
        return device.isOn();
    }
    
    @Override
    public String getName() {
        return device.getName();
    }
}