import com.sun.org.apache.xerces.internal.dom.CoreDocumentImpl;
import com.sun.org.apache.xerces.internal.dom.ElementImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Mar 1, 2006
 * Time: 12:29:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class XMLManager implements FilePaths{
    private static int counter = 1;

    //config.xml ==> vector<warehouse>
    public static Vector readConfig() {
        return initWarehouses();
    }

    //Vector<warehouses> ==> document ==> config.xml
    public static void updateConfig(Vector warehouses) {
        Document document = initDocument();

        // Insert Root Order
        Element root = (Element) document.createElement("warehouses");
        document.appendChild(root);

        // Insert Items
        for (int i = 0; i < warehouses.size(); i++) {
            Warehouse warehouse = (Warehouse) warehouses.elementAt(i);
            insertWarehouse(document, root, warehouse);
        }

        // Normalizing the DOM
        document.getDocumentElement().normalize();
        flushToFile(document, FilePaths.config);
//        flushToFile(document, "src\\order\\config\\config" + counter +".xml");
        counter++;
    }

    private static void insertWarehouse(Document document, Element parent, Warehouse warehouse) {
// Insert child Item
        Node itemChild = document.createElement("warehouse");
        parent.appendChild(itemChild);

        // Insert child ID
        Node item = document.createElement("name");
        itemChild.appendChild(item);
        // Insert ID value
        Node value = document.createTextNode(warehouse.getName());
        item.appendChild(value);

        for (int i = 0; i < warehouse.getProducts().size(); i++) {
            Product product = warehouse.getProduct(i);
            insertProduct(document, itemChild, product);
        }

    }

    private static void insertProduct(Document document, Node parent, Product product) {
        // Insert child Item
        Node itemChild = document.createElement("product");
        parent.appendChild(itemChild);

        Node item = document.createElement("name");
        itemChild.appendChild(item);
        Node value = document.createTextNode(product.getName());
        item.appendChild(value);

        item = document.createElement("initialcount");
        itemChild.appendChild(item);
        value = document.createTextNode(String.valueOf(product.getInitialCount()));
        item.appendChild(value);

        item = document.createElement("minimumcount");
        itemChild.appendChild(item);
        value = document.createTextNode(String.valueOf(product.getMinimumCount()));
        item.appendChild(value);

        item = document.createElement("availablecount");
        itemChild.appendChild(item);
        value = document.createTextNode(String.valueOf(product.getAvailableCount()));
        item.appendChild(value);
    }

    private static Document initDocument() {
        Document document = null;
        DocumentBuilder builder = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
            document = builder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return document;
    }

    private static Vector initWarehouses() {
        Document document = initDocument(FilePaths.config);

        NodeList nodelist = document.getElementsByTagName("*");
        Node node;
        Vector warehouses = new Vector();
        Warehouse warehouse;
        Product product;
        for (int i = 0; i < nodelist.getLength(); i++) {
            node = nodelist.item(i);

            if (node.getNodeName().equals("warehouse")) {
                warehouse = new Warehouse();

                NodeList childs = node.getChildNodes();
                for (int j = 0; j < childs.getLength(); j++) {
                    Node child = childs.item(j);

                    if (child.getNodeName().equals("name"))
                        warehouse.setName(child.getFirstChild().getNodeValue());

                    if (child.getNodeName().equals("product")) {
                        /*ElementImpl element = new ElementImpl((CoreDocumentImpl) document, "availablecount");
                        element.setTextContent("0");
                        child.appendChild(element);*/

                        NodeList productChilds = child.getChildNodes();
                        product = new Product();

                        for (int k = 0; k < productChilds.getLength(); k++) {
                            Node productChild = productChilds.item(k);

                            if (productChild.getNodeName().equals("name"))
                                product.setName(productChild.getFirstChild().getNodeValue());

                            if (productChild.getNodeName().equals("initialcount"))
                                product.setInitialCount(Integer.valueOf(productChild.getFirstChild().getNodeValue()));

                            if (productChild.getNodeName().equals("minimumcount"))
                                product.setMinimumCount(Integer.valueOf(productChild.getFirstChild().getNodeValue()));

                        }
                        product.setAvailableCount(product.getInitialCount());
                        warehouse.addProduct(product);
                    }
                }
                warehouses.add(warehouse);
            }
        }
        return warehouses;
    }

    private static Document initDocument(String url) {
        Document document = null;
        try {
            DocumentBuilderFactory factory
                    = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            factory.setNamespaceAware(true);
            DocumentBuilder parser = factory.newDocumentBuilder();
            document = parser.parse(url);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return document;
    }

    //document ==> file
    private static void flushToFile(Node document, String outputURL) {
        try {
            // Prepare the DOM document for writing
            Source source = new DOMSource(document);

            // Prepare the output file
            File file = new File(outputURL);
            Result result = new StreamResult(file);

            // Write the DOM document to the file
            // Get Transformer
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            // Write to a file
            xformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            System.out.println("TransformerConfigurationException: " + e);
        } catch (TransformerException e) {
            System.out.println("TransformerException: " + e);
        }

    }

    //orderFile.xml ==> order
    public static Order initOrder(String orderFile) {
        Document document = initDocument(orderFile);
        NodeList nodelist = document.getElementsByTagName("*");
        Node node;
        Order order = new Order();
        Vector products = new Vector();
        ProductOrder productOrder;
        for (int i = 0; i < nodelist.getLength(); i++) {
            node = nodelist.item(i);
            if (node.getNodeName().equals("serial")) {
                order.setSerialNumber(Integer.valueOf(node.getFirstChild().getNodeValue()));
            }
            if (node.getNodeName().equals("product")) {
                productOrder = new ProductOrder();
                NodeList childs = node.getChildNodes();
                for (int j = 0; j < childs.getLength(); j++) {
                    Node child = childs.item(j);
                    if (child.getNodeName().equals("name"))
                        productOrder.setProductName(child.getFirstChild().getNodeValue());
                    if (child.getNodeName().equals("count"))
                        productOrder.setCount(Integer.valueOf(child.getFirstChild().getNodeValue()));
                }
                products.add(productOrder);
            }
        }
        order.setProductOrders(products);
        return order;
    }

    //order ==> orderFile.xml
    public static void writeOrderFile(Order order, String URL) {
        Document document = initDocument();

        // Insert Root Order
        Element root = (Element) document.createElement("order");
        document.appendChild(root);

        insertSerialNumber(document, root, order.getSerialNumber());

        for (int i = 0; i < order.getNumberOfOrders(); i++) {
            ProductOrder productOrder = order.getProductOrder(i);
            insertProductOrder(document, root, productOrder);
        }

        flushToFile(document, URL);
    }

    private static void insertProductOrder(Document document, Element parent, ProductOrder productOrder) {
        Node itemChild = document.createElement("product");
        parent.appendChild(itemChild);

        Node item = document.createElement("name");
        itemChild.appendChild(item);
        Node value = document.createTextNode(productOrder.getProductName());
        item.appendChild(value);

        item = document.createElement("count");
        itemChild.appendChild(item);
        value = document.createTextNode(String.valueOf(productOrder.getCount()));
        item.appendChild(value);
    }

    private static void insertSerialNumber(Document document, Element parent, int serialNumber) {
        Node item = document.createElement("serial");
        parent.appendChild(item);
        Node value = document.createTextNode(String.valueOf(serialNumber));
        item.appendChild(value);
    }
}
