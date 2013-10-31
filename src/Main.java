/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Feb 26, 2006
 * Time: 11:00:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private static OrderBuffer orderBuffer = new OrderBuffer();
    private static ProduceOrderBuffer produceBuffer = new ProduceOrderBuffer();
    private static OrderManager orderManager = new OrderManager(orderBuffer);
    private static WarehouseManager warehouseManager = new WarehouseManager(orderBuffer, produceBuffer);
    private static ProductManager productManager = new ProductManager(produceBuffer);

    public static void main(String[] args) throws InterruptedException {
        orderManager.start();
        warehouseManager.start();
        productManager.start();
        Thread.sleep(20000);

        System.exit(0);
    }
}
