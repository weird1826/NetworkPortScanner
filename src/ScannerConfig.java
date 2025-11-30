// Holds the configuration for the scanner
public class ScannerConfig {
    private final String targetIp;
    private int timeoutMilliseconds;

    public ScannerConfig(String targetIp) {
        this.targetIp = targetIp;
        this.timeoutMilliseconds = 2000; // defaulting to 2 sec
    }

    public int getTimeoutMilliseconds() {
        return this.timeoutMilliseconds;
    }

    public void setTimeoutMilliseconds(int newTimeout) {
        if(newTimeout < 500) {
            System.out.println("    [Warning] Timeout cannot be less than 500ms. Keeping it at " + this.timeoutMilliseconds);
        } else {
            this.timeoutMilliseconds = newTimeout;
            System.out.println("    [Info] Timeout set to " + newTimeout + "ms");
        }
    }

    public String getTargetIp() {
        return this.targetIp;
    }
}
