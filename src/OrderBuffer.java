import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Feb 26, 2006
 * Time: 10:44:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderBuffer implements FilePaths{
    private RandomAccessFile writer;

    public OrderBuffer() {
    }

    public synchronized boolean writeOrder(RandomAccessFile reader) {
        boolean successWrite = true;
        try {
            writer = new RandomAccessFile(FilePaths.sharedOrder, "rw");
            if (writer.length() == 0) {
                String line = reader.readLine();
                while (line != null) {
                    writer.writeBytes(line + "\n");
                    line = reader.readLine();
                }
            } else {
                successWrite = false;
                wait();
            }
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return successWrite;
    }

    public synchronized Order readOrder() {
        Order order = null;
        try {
            writer = new RandomAccessFile(FilePaths.sharedOrder, "rw");
            if (writer.length() == 0) {
                return null;
            }
            order = XMLManager.initOrder(FilePaths.sharedOrder);
            writer.setLength(0);
            writer.close();
            notify();

        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("order read = " + order.getSerialNumber());
        return order;
    }




}
