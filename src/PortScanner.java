public class PortScanner {
    public static void main() {
        PortScanResult result1 = new PortScanResult();

        result1.portNumber = 80;
        result1.isOpen = true;

        PortScanResult result2 = new PortScanResult();

        result2.portNumber = 22;
        result2.isOpen = false;

        System.out.println("--- Scan Results ---");
        System.out.println("Port: " + result1.portNumber + " is open: " + result1.isOpen);
        System.out.println("Port: " + result2.portNumber + " is open: " + result2.isOpen);
    }
}
