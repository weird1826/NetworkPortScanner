public class PortScanner {
    public static void main() {
        PortScanResult result1 = new PortScanResult(80, true);
        PortScanResult result2 = new PortScanResult(22, false);

        System.out.println("Scan Results ---");
        result1.display();
        result2.display();
    }
}
