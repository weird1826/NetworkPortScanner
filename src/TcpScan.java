import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    /**
     * This method scans for a single port
     * @param singlePort - Port Number to Scan
     */
    public void setPortsToScan(int singlePort) {
        System.out.println("\t[Debug] Setting Scan for a single port: " + singlePort);
        this.portsToScan.clear();
        this.portsToScan.add(singlePort);
    }

    /**
     * This port receives a start port and end port to scan a port range
     * @param startPort - Starting port
     * @param endPort - Ending Port
     */
    public void setPortsToScan(int startPort, int endPort) {
        System.out.println("\t[DEBUG] Setting scan for port range: " + startPort + "-" + endPort);
        this.portsToScan.clear();
        for(int port = startPort; port <= endPort; port++) {
            this.portsToScan.add(port);
        }
    }

    /**
     * This method takes variable amount of specific port numbers
     * @param ports - Port number
     */
    public void setPortsToScan(int... ports) {
        System.out.println("\t[DEBUG] Setting scan for ports: " + Arrays.toString(ports));
        this.portsToScan.clear();
        for(int p : ports) {
            this.portsToScan.add(p);
        }
    }

    public void setPortsToScan(String portString) {
        if(portString.contains("-")) {
            try {
                String[] parts = portString.split("-");
                int start = Integer.parseInt(parts[0]);
                int end = Integer.parseInt(parts[1]);
                this.setPortsToScan(start, end);
            } catch (Exception e) {
                System.err.println("[Error] Invalid port range: " + portString);
            }
        } else {
            try {
                int port = Integer.parseInt(portString); // Convert text "80" to number 80
                this.setPortsToScan(port);
            } catch (NumberFormatException e) {
                System.err.println("[Error] Invalid port number: " + portString);
            }
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

//        System.out.println("\t[Info] Scan complete. Printing results:");
//        for (PortScanResult res : results) {
//            res.display();
//        }

        System.out.println("\t[INFO] Scan complete. Total ports scanned: " + results.size());
        System.out.println("--- OPEN PORTS FOUND ---");
        results.stream()
                .filter(PortScanResult::isOpen)
                .forEach(PortScanResult::display);

        long openPortCount = results.stream()
                .filter(PortScanResult::isOpen)
                .count();

        System.out.println("--- Summary: ---");
        System.out.println("\tTotal open ports: " + openPortCount);

        List<PortScanResult> openPorts = results.stream()
                .filter(PortScanResult::isOpen).toList();
        saveReportToFile(openPorts);
    }

    private void saveReportToFile(List<PortScanResult> openPorts) {
        String filename = "scan_report_for_" + this.targetIp + ".txt";
        Path filePath = Paths.get(filename);

        List<String> reportLines = new ArrayList<>();
        reportLines.add("--- Scan report for " + this.targetIp + " ---");
        reportLines.add("Total open ports found: " + openPorts.size());
        reportLines.add("-------------------------------------------");

        List<String> portLines = openPorts.stream()
                .map(res -> "\t[+] Port " + res.getPortNumber() + " is OPEN")
                .toList();

        reportLines.addAll(portLines);

        try {
            System.out.println("\n\t[Info] Saving report to " + filename + "...");
            Files.write(filePath, reportLines);
            System.out.println("\t[Info] Report saved successfully.");
        } catch (IOException e) {
            System.err.println("[Error] Could not save report: " + e.getMessage());
        }
    }
}
