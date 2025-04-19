import java.util.Timer;
import java.util.TimerTask;

/**
 * Adds motion sensor functionality to a device.
 * The device will automatically turn on when motion is detected and turn off after a delay.
 */
public class MotionSensorDecorator extends DeviceDecorator {
    private Timer timer;
    private int autoOffDelay; // in seconds
    
    /**
     * Creates a new MotionSensorDecorator.
     * @param device The device to decorate.
     * @param autoOffDelay Delay in seconds before the device automatically turns off after motion stops.
     */
    public MotionSensorDecorator(Device device, int autoOffDelay) {
        super(device);
        this.timer = new Timer();
        this.autoOffDelay = autoOffDelay;
    }
    
    /**
     * Simulates motion detection.
     * When motion is detected, the device is turned on and a timer is started to turn it off later.
     */
    public void motionDetected() {
        System.out.println("Motion detected near " + device.getName());
        
        // Turn on the device if it's not already on
        if (!device.isOn()) {
            device.turnOn();
        }
        
        // Reset the timer to turn off after delay
        resetTimer();
    }
    
    /**
     * Resets the auto-off timer.
     * Cancels any existing timer and starts a new one.
     */
    private void resetTimer() {
        // Cancel any existing timer
        if (timer != null) {
            timer.cancel();
        }
        
        // Create new timer
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (device.isOn()) {
                    System.out.println("No motion detected for " + autoOffDelay + " seconds. Turning off " + device.getName());
                    device.turnOff();
                }
            }
        }, autoOffDelay * 1000); // Convert seconds to milliseconds
    }
    
    /**
     * Sets the auto-off delay.
     * @param seconds Delay in seconds before the device turns off after no motion is detected.
     */
    public void setAutoOffDelay(int seconds) {
        this.autoOffDelay = seconds;
        System.out.println("Auto-off delay for " + device.getName() + " set to " + seconds + " seconds");
    }
    
    /**
     * Gets the current auto-off delay.
     * @return The current auto-off delay in seconds.
     */
    public int getAutoOffDelay() {
        return autoOffDelay;
    }
}