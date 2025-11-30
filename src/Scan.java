public abstract class Scan {
    protected String targetIp;

    public Scan(String targetIp) {
        System.out.println("    [DEBUG] Parent 'Scan' constructor is running...");
        this.targetIp = targetIp;
    }

    public abstract void run();
}
