import java.io.RandomAccessFile;
import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Feb 27, 2006
 * Time: 12:19:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class OrderProvider extends Thread implements FilePaths{
    private Order order;
    private ProduceOrderBuffer produceBuffer;

    public OrderProvider(Order order, ProduceOrderBuffer produceBuffer) {
        this.order = order;
        this.produceBuffer = produceBuffer;
    }

    public void run() {
        System.out.println("order number in provider = " + order.getSerialNumber());
        Order productOrder = new Order(order.getSerialNumber());
        for (int i = 0; i < order.getNumberOfOrders(); i++) {
            int requiredNumber = WarehouseManager.testAndSet(order.getProductOrder(i));
            System.out.println("requiredNumber for " + order.getProductOrder(i) +" = " + requiredNumber);
            if (requiredNumber != 0) {
                ProductOrder newOrder = new ProductOrder(order.getProductOrder(i).getProductName(),requiredNumber);
                System.out.println("new order for producer " + newOrder);
                productOrder.addProductOrder(newOrder);
            }
        }


        if (productOrder.getNumberOfOrders() != 0) {
            while (! produceBuffer.writeProduceOrder(productOrder));
            boolean found = false;
            while (! found) {
                try {
                    new RandomAccessFile(FilePaths.produceResults + order.getSerialNumber() + ".xml", "r");
                    found = true;
                } catch (FileNotFoundException e) {
                    found = false;
                }
            }
        }
            else
            System.out.println("order " + order.getSerialNumber() + "sent nothing to the producer");
        System.out.println("order finished in order provider = " + order.getSerialNumber());
    }
}
