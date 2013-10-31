import com.sun.org.apache.xerces.internal.dom.CoreDocumentImpl;
import com.sun.org.apache.xerces.internal.dom.ElementImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Vector;
import java.io.IOException;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Feb 26, 2006
 * Time: 11:15:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class WarehouseManager extends Thread {
    private OrderBuffer orderBuffer;
    private ProduceOrderBuffer produceBuffer;
    private static Vector warehouses;
    private final static int flushDelay = 1000;


    public WarehouseManager(OrderBuffer orderBuffer, ProduceOrderBuffer produceBuffer) {
        this.orderBuffer = orderBuffer;
        this.produceBuffer = produceBuffer;
        warehouses = XMLManager.readConfig();
        Thread flushConfig = new Thread(){
            public void run(){
                while (true) {
                    try {
                        sleep(flushDelay);
                        XMLManager.updateConfig(warehouses);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
        };
        flushConfig.start();
    }

    public void run() {
        while (true) {
            Order order = orderBuffer.readOrder();
            if (order != null) {
                OrderProvider provider = new OrderProvider(order, produceBuffer);
                provider.start();
            }
        }

    }

    public synchronized static int testAndSet(ProductOrder order) {
        Product product = findProductByName(order.getProductName());
        if (product != null) {
            if (product.getAvailableCount() - product.getMinimumCount() >= order.getCount()) {
                product.setAvailableCount(product.getAvailableCount() - order.getCount());
                return 0;
            } else {
                int requiredNumber = (order.getCount() + product.getMinimumCount() - product.getAvailableCount() );
                product.setAvailableCount(product.getMinimumCount());
                return requiredNumber;
            }

        }
        System.out.println("No such product found: " + order.getProductName());
        return 0;

    }

    private static Product findProductByName(String productName) {
        for (int i = 0; i < warehouses.size(); i++) {
            Warehouse warehouse = (Warehouse) warehouses.elementAt(i);
            Product product = warehouse.findProductByName(productName);
            if (product != null)
                return product;
        }
        return null;
    }




}
