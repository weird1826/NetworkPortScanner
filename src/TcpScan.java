import java.util.ArrayList;
import java.util.List;

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
        System.out.println("    [DEBUG] Setting scan for port range: " + startPort + "-" + endPort);
        this.portsToScan.clear();
        for(int port = startPort; port <= endPort; port++) {
            this.portsToScan.add(port);
        }
    }

    @Override
    public void run() {
        System.out.println("    [DEBUG] Running a simulated TCP Scan on: " + this.targetIp);

        if(portsToScan.isEmpty()) {
            System.out.println("\t[Warning] No ports selected to scan.");
            return;
        }

        System.out.println("\t[Info] Scanning: " + portsToScan.size() + " ports...");
        for(Integer port : this.portsToScan) {
            System.out.println("\t\t - Simulating scan on port: " + port);
        }
    }
}
