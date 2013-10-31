/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Feb 27, 2006
 * Time: 7:59:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProductManager extends Thread {
    private ProduceOrderBuffer produceBuffer;
    public final static int productionDelay = 50;

    public ProductManager(ProduceOrderBuffer produceBuffer) {
        this.produceBuffer = produceBuffer;
    }

    public void run() {
        while (true) {
            Order order = produceBuffer.readProduceOrder();
            if (order != null) {
                Producer producer = new Producer(order);
                producer.start();
            }
        }
    }
}
