import java.util.Random;
class SupplyTruck {
    private ProductionLine line;
    private Random rand;
    public SupplyTruck(ProductionLine line) {
        this.line = line;
        this.rand = new Random();
    }
    public void run() {
        while (true) {
            int resource1Amount = rand.nextInt(100);
            int resource2Amount = rand.nextInt(100);
            line.addResource1(resource1Amount);
            line.addResource2(resource2Amount);
            int supplyTime = rand.nextInt(100);
            try {
                Thread.sleep(supplyTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}