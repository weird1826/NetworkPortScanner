import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TcpScan extends Scan {

    private final List<Integer> portsToScan;

    public TcpScan(String targetIp) {
        super(targetIp);
        this.portsToScan = new ArrayList<>();
    }

    public void setPortsToScan(int singlePort) {
        System.out.println("\t[Debug] Setting Scan for a single port: " + singlePort);
        this.portsToScan.clear();
        this.portsToScan.add(singlePort);
    }

    public void setPortsToScan(int startPort, int endPort) {
        System.out.println("\n\t[DEBUG] Setting scan for port range: " + startPort + "-" + endPort);
        this.portsToScan.clear();
        for(int port = startPort; port <= endPort; port++) {
            this.portsToScan.add(port);
        }
    }

    public void setPortsToScan(int... ports) {
        System.out.println("    [DEBUG] Setting scan for ports: " + Arrays.toString(ports));
        this.portsToScan.clear();
        for(int p : ports) {
            this.portsToScan.add(p);
        }
    }

    private PortScanResult scanPort(int port) {
        try (Socket socket = new Socket()) {
            InetSocketAddress address = new InetSocketAddress(this.targetIp, port);
            socket.connect(address, 200);
            return new PortScanResult(port, true);
        } catch (IOException e) {
            return new PortScanResult(port, false);
        }
    }

    @Override
    public void run() {
        System.out.println("\tStarting Multithreaded TCP Scan on: " + this.targetIp);

        if(portsToScan.isEmpty()) {
            System.out.println("\t[Warning] No ports selected to scan.");
            return;
        }

        int numThreads = 20;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<PortScanResult> results = Collections.synchronizedList(new LinkedList<>());

        for(Integer port: this.portsToScan) {
            Runnable job = () -> {
                PortScanResult result = scanPort(port);
                results.add(result);
            };
            executor.submit(job);
        }

        executor.shutdown();

        try {
            System.out.println("\t[INFO] All scan jobs submitted. Waiting...");
            executor.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.err.println("\t[ERROR] Scan was interrupted.");
        }

        System.out.println("\t[Info] Scan complete. Printing results:");
        for (PortScanResult res : results) {
            res.display();
        }
    }
}
