public class TcpScan extends Scan {
    public TcpScan(String targetIp) {
        super(targetIp);
        System.out.println("    [DEBUG] Child 'TcpScan' constructor is running...");
    }

    @Override
    public void run() {
        System.out.println("    [DEBUG] Running a simulated TCP Scan on: " + this.targetIp);
    }
}
