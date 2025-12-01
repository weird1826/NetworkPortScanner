import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PortScanner {
    public static void main(String[] args) {

        if (args.length < 2){
            printUsage();
            return;
        }

        String targetIp = null;
        String portNumber = null;
        String rangeString = null;
        String timeout = null;
        int[] ports = null;
        int portFlagCount = 0;

        // --- revised parsing block

        for(int i = 0; i < args.length; i++) {
            String arg = args[i];

            try {
                switch (arg) {
                    case "--ipaddr":
                    case "-ip":
                        if (targetIp != null) {
                            System.err.println("[Error] Duplicate argument: --ip flag specified more than once.");
                            printUsage();
                            return;
                        }
                        targetIp = args[i+1];
                        i++;
                        break;

                    case "--port":
                    case "-p":
                        portNumber = args[i+1];
                        i++;
                        portFlagCount++;
                        break;

                    case "--range":
                    case "-r":
                        rangeString = args[i+1];
                        i++;
                        portFlagCount++;
                        break;

                    case "--ports":
                    case "--pts":
                        String[] portList = args[i+1].split(",");
                        ports = Arrays.stream(portList)
                                .mapToInt(Integer::parseInt)
                                .toArray();
                        i++;
                        portFlagCount++;
                        break;

                    case "--timeout":
                    case "--t":
                        timeout = args[i+1];
                        i++;
                        break;

                    default:
                        // Ignore unknown arguments
                        System.out.println("Ignoring unknown argument: " + arg);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // catches a missing value (e.g., "--ip" with no IP)
                System.err.println("[Error] Missing value for argument: " + arg);
                return; // Stop the program
            } catch (NumberFormatException e) {
                // catches bad port numbers (e.g., "--ports 80,abc")
                System.err.println("[Error] Invalid number for port argument: " + args[i+1]);
                return; // Stop the program
            } catch (Exception e) {
                // A general catch-all for other weird errors
                System.err.println("[Error] Unexpected error parsing arguments: " + e.getMessage());
                return; // Stop the program
            }
        }

        // --- validation ---
        if (targetIp == null) {
            System.err.println("[Error] Missing required argument: --ip <address>");
            printUsage(); // Assuming you have this helper method
            return;
        }

        if (portFlagCount == 0) {
            System.err.println("[Error] Missing required port argument: --port, --range, or --ports");
            printUsage();
            return;
        }

        if (portFlagCount > 1) {
            System.err.println("[Error] Conflicting or duplicate arguments: --port, --range, and --ports are mutually exclusive.");
            System.err.println("Please provide only one port option.");
            printUsage();
            return;
        }

        if (timeout == null){
            timeout = "200";
        }

        System.out.println("\nInitializing Scan Job ---");
        TcpScan tcpJob = new TcpScan(targetIp);

        if(portNumber != null) {
            tcpJob.setPortsToScan(portNumber);
        } else if (rangeString != null) {
            tcpJob.setPortsToScan(rangeString);
        } else if (ports != null) {
            tcpJob.setPortsToScan(ports);
        } else {
            System.err.println("[Error] Missing required port argument: --port, --range, or --ports");
            printUsage();
            return;
        }

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

    private static void printUsage() {
        System.out.println("--- Java Port Scanner ---");
        System.out.println("Usage: Java PortScanner <target_ip> <port_range>");
        System.out.println("  java PortScanner 127.0.0.1 80");
        System.out.println("  java PortScanner 127.0.0.1 1-1024");
    }
}
