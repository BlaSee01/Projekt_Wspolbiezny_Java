import java.util.Random;
import java.util.concurrent.Semaphore;
class ProductionLine {
    private Semaphore semaphore1;
    private Semaphore semaphore2;
    private int resource1;
    private int resource2;
    private int finishedProducts;
    private int resource1Threshold;
    private int resource2Threshold;
    private Random rand;

    public ProductionLine(int resource1Threshold, int resource2Threshold) {
        this.semaphore1 = new Semaphore(resource1Threshold);
        this.semaphore2 = new Semaphore(resource2Threshold);
        this.resource1 = 0;
        this.resource2 = 0;
        this.finishedProducts = 0;
        this.resource1Threshold = resource1Threshold;
        this.resource2Threshold = resource2Threshold;
        this.rand = new Random();
    }

    public synchronized void produce() throws InterruptedException {
        if (resource1 < resource1Threshold || resource2 < resource2Threshold) {
            System.out.println("Brak surowców. POTRZEBNA DOSTAWA");
            return;
        }
        while(resource1 >= resource1Threshold) {
            int productionTime = rand.nextInt(1000);
            resource1 -= resource1Threshold;
            try {
                semaphore1.acquire();
                System.out.println("Wytworzono [Produkt 1]" + ", Zostało: " + resource1);
                Thread.sleep(productionTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finishedProducts += 1;
        }
        while(resource2 >= resource2Threshold) {
            int productionTime2 = rand.nextInt(1000);
            resource2 -= resource2Threshold;
            try {
                semaphore2.acquire();
                System.out.println("Wytworzono [Produkt 2]" + ", Zostało: " + resource2);
                Thread.sleep(productionTime2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finishedProducts += 1;
        }

        System.out.println("----------(END OF PRODUKCJA NA LINII)----------");
        Thread.sleep(2000);
    }

    public synchronized void addResource1(int amount) {
        resource1 += amount;
        semaphore1.release(amount);
        System.out.println("Dostarczono Surowiec 1" + ", W ilości: " + amount);
        notifyAll();
    }

    public synchronized void addResource2(int amount) {
        resource2 += amount;
        semaphore2.release(amount);
        System.out.println("Dostarczono Surowiec 2" + ", W ilości: " + amount);
        notifyAll();
    }
    public int getResource1() {
        return resource1;
    }

    public int getResource2() {
        return resource2;
    }

    public int getFinishedProducts() {
        return finishedProducts;
    }
    public synchronized int takeFinishedProducts(int amount) {
        while (finishedProducts < amount) {
            try {
                wait();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        finishedProducts -= amount;
        notifyAll();
        System.out.println("Brak materialow. POTRZEBNA DOSTAWA!");

        return amount;
    }
}