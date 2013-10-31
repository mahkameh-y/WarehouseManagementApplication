import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Feb 26, 2006
 * Time: 10:25:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderManager extends Thread implements FilePaths{

    private static int lastOrder = 1;
    private RandomAccessFile reader;
    private OrderBuffer buffer;

    public OrderManager(OrderBuffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        while (true) {
            try {
                reader = new RandomAccessFile(FilePaths.newOrders +"order" + lastOrder + ".xml", "r");
                while (!buffer.writeOrder(reader));
                lastOrder++;
                reader.close();
            } catch (FileNotFoundException e) {
                //System.out.println("order" + lastOrder + ".xml : NOT FOUND");
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
