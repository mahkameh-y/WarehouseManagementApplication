import java.io.RandomAccessFile;
import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Feb 27, 2006
 * Time: 8:02:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class Producer extends Thread implements FilePaths{
    private Order order;

    public Producer(Order order) {
        this.order = order;
    }

    public void run() {
        try {
            sleep(ProductManager.productionDelay);
            new RandomAccessFile(FilePaths.produceResults + order.getSerialNumber() + ".xml", "rw");
            System.out.println("order with number = " + order.getSerialNumber() +" was supplied");
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


}
