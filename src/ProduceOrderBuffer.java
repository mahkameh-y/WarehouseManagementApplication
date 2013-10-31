import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Feb 27, 2006
 * Time: 4:49:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProduceOrderBuffer implements FilePaths{
    private RandomAccessFile writer;

    public synchronized boolean writeProduceOrder(Order order) {
        boolean successWrite = true;
        try {
            writer = new RandomAccessFile(FilePaths.sharedProduceRequest, "rw");
            if (writer.length() == 0) {
                XMLManager.writeOrderFile(order, FilePaths.sharedProduceRequest);
                XMLManager.writeOrderFile(order, FilePaths.newProduceRequest +order.getSerialNumber() + ".xml");
            }
            else
                successWrite = false;
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return successWrite;
    }

    public synchronized Order readProduceOrder() {
        Order order = null;
        try {
            writer = new RandomAccessFile(FilePaths.sharedProduceRequest, "rw");
            if (writer.length() == 0) {
                return null;
            }
            order = XMLManager.initOrder(FilePaths.sharedProduceRequest);
            writer.setLength(0);
            writer.close();

        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("order received at producer = " + order.getSerialNumber());
        return order;
    }
}
