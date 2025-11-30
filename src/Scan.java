public abstract class Scan {
    protected String targetIp;

    public Scan(String targetIp) {
        this.targetIp = targetIp;
    }

    public abstract void run();
}
