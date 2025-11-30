import java.util.ArrayList;
import java.util.List;

public class PortScanner {
    public static void main() {
        System.out.println("\nInitializing Scan Job ---");
        TcpScan tcpJob = new TcpScan("127.0.0.1");

        tcpJob.setPortsToScan(80);
        tcpJob.run();

        tcpJob.setPortsToScan(1, 1024);
        List<Scan> scanQueue = new ArrayList<>();
        scanQueue.add(tcpJob);

        System.out.println("\nRunning All Scans in Queue (range): ---");
        long startTime = System.currentTimeMillis();
        for(Scan currentScan : scanQueue) {
            currentScan.run();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("\nTotal Scan Time: " + (endTime - startTime) + "ms ---");

        tcpJob.setPortsToScan(80, 455, 8080);
        scanQueue.clear();
        scanQueue.add(tcpJob);

        System.out.println( "\nRunning All Scans in Queue (varargs): ---");
        for(Scan currentScan : scanQueue) {
            currentScan.run();
        }

        // Simulated results
        PortScanResult result1 = new PortScanResult(80, true);
        PortScanResult result2 = new PortScanResult(22, false);

        System.out.println("\nGenerating Report ---");
        List<IDisplayable> reportItems = new ArrayList<>();
        reportItems.add(result1);
        reportItems.add(result2);

        for(IDisplayable item : reportItems) {
            item.display();
        }
    }
}
