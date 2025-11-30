public class PortScanResult {
    private final int portNumber;
    private final boolean isOpen;

    // Constructor for the class
    public PortScanResult(int port, boolean open) {
        this.portNumber = port;
        this.isOpen = open;
    }

    // Prints whether a port is open or not
    public void display() {
        if(this.isOpen) {
            System.out.println("    [+] Port " + this.portNumber + " is OPEN.");
        } else {
            System.out.println("    [-] Port " + this.portNumber + " is CLOSED.");
        }
    }

    public int getPortNumber() {
        return this.portNumber;
    }

    public boolean isOpen(){
        return this.isOpen;
    }
}
