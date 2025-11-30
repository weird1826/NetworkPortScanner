import java.util.ArrayList;
import java.util.List;

public class PortScanner {
    public static void main() {
        System.out.println("\nInitializing Scan Job ---");
        TcpScan tcpJob = new TcpScan("127.0.0.1");

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
    }
}
