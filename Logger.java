import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Logger implementation of the Observer interface.
 * Logs all device state changes to console and a log file.
 */
public class Logger implements Observer {
    private static final String LOG_FILE = "smart_home.log";
    private final SimpleDateFormat dateFormat;
    
    /**
     * Creates a new Logger.
     */
    public Logger() {
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Override
    public void update(String deviceName, boolean status, String message) {
        String statusText = status ? "ON" : "OFF";
        String timestamp = dateFormat.format(new Date());
        String logMessage = String.format("[%s] %s is %s - %s", timestamp, deviceName, statusText, message);
        
        // Log to console
        System.out.println("LOG: " + logMessage);
        
        // Log to file
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(logMessage + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
    
    /**
     * Clears the log file.
     */
    public void clearLog() {
        try (FileWriter writer = new FileWriter(LOG_FILE, false)) {
            writer.write(""); // Write empty string to clear file
            System.out.println("Log file cleared.");
        } catch (IOException e) {
            System.err.println("Error clearing log file: " + e.getMessage());
        }
    }
}