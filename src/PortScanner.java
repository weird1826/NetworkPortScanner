public class PortScanner {
    public static void main() {
        PortScanResult result1 = new PortScanResult(80, true);
        PortScanResult result2 = new PortScanResult(22, false);

        ScannerConfig config = new ScannerConfig("127.0.0.1");
        System.out.println("Configuring Scanner ---");
        System.out.println("    [DEBUG] Attempting to set a bad timeout...");
        config.setTimeoutMilliseconds(100);
        System.out.println("    [DEBUG] Attempting to set a good timeout...");
        config.setTimeoutMilliseconds(3000);
        System.out.println("    [DEBUG] Final Config: Target = " + config.getTargetIp() + ", Timeout = " + config.getTimeoutMilliseconds());

        System.out.println("\nInitializing Scan Job ---");
        TcpScan myScanJob = new TcpScan(config.getTargetIp());

        myScanJob.run();

        System.out.println("\nJust Checking Ports: ---");
        int p1 = result1.getPortNumber();
        System.out.println("    " + p1);
        int p2 = result2.getPortNumber();
        System.out.println("    " + p2);

        System.out.println("\nScan Results ---");
        result1.display();
        result2.display();
    }
}
