import java.util.Random;
class ShippingTruck {
    private ProductionLine line;
    private Random rand;

    public ShippingTruck(ProductionLine line) {
        this.line = line;
        this.rand = new Random();
    }

    public void run() {
        while (true) {
            int packageAmount = rand.nextInt(100);
            line.takeFinishedProducts(packageAmount);
            int shippingTime = rand.nextInt(100);
            try {
                Thread.sleep(shippingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}