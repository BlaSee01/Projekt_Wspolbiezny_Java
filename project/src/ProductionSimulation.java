public class ProductionSimulation {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("[PRODUKT 1] - WYMAGA 10 ZASOBÓW [S1]");
        System.out.println("[PRODUKT 2] - WYMAGA 20 ZASOBÓW [S2]\n");

        Thread.sleep(3000);

        ProductionLine line = new ProductionLine(10, 20);
        ProductionLine line2 = new ProductionLine(10, 20);
        SupplyTruck truck1 = new SupplyTruck(line);
        ShippingTruck truck2 = new ShippingTruck(line);
        Thread supplyThread = new Thread(() -> truck1.run());
        Thread shippingThread = new Thread(() -> truck2.run());
        supplyThread.start();
        shippingThread.start();

        while (true) {
            line.produce();
            line2.produce();
        }
    }
}