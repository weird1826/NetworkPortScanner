public class UdpScan extends Scan {
    public UdpScan(String targetIp) {
        super(targetIp);
        System.out.println("    [DEBUG] Child UdpScan constructor is running...");
    }

    @Override
    public void run() {
        System.out.println("    [DEBUG] Running a simulated UDP scan on: " + this.targetIp);
    }
}
