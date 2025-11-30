import java.util.ArrayList;
import java.util.List;

public class PortScanner {
    public static void main() {
        ScannerConfig config = new ScannerConfig("127.0.0.1");
        System.out.println("Configuring Scanner ---");
        config.setTimeoutMilliseconds(3000);
        System.out.println("    [DEBUG] Final Config: Target = " + config.getTargetIp() + ", Timeout = " + config.getTimeoutMilliseconds());

        System.out.println("\nInitializing Scan Job ---");
        TcpScan tcpJob = new TcpScan("127.0.0.1");
        tcpJob.setPortsToScan(80);
        TcpScan tcpJob2 = new TcpScan("192.168.1.1");
        tcpJob2.setPortsToScan(1, 1024);

        List<Scan> scanQueue = new ArrayList<>();
        scanQueue.add(tcpJob);
        scanQueue.add(tcpJob2);

        System.out.println("\nRunning All Scans in Queue: ---");
        for(Scan currentScan : scanQueue) {
            currentScan.run();
        }

        // Simulated results
        PortScanResult result1 = new PortScanResult(80, true);
        PortScanResult result2 = new PortScanResult(22, false);

        System.out.println("\nGenerating Report ---");
        List<IDisplayable> reportItems = new ArrayList<>();
        reportItems.add(config);
        reportItems.add(result1);
        reportItems.add(result2);

        for(IDisplayable item : reportItems) {
            item.display();
        }
    }
}
