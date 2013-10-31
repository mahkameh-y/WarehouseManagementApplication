import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Feb 27, 2006
 * Time: 12:07:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class Order {
    private Vector productOrders; //<product order>
    private int serialNumber;

    public Order() {
    }

    public Order(int serialNumber) {
        this.serialNumber = serialNumber;
        this.productOrders = new Vector();
    }

    public Order(Vector productOrders, int serialNumber) {
        this.productOrders = productOrders;
        this.serialNumber = serialNumber;
    }

    public Vector getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(Vector productOrders) {
        this.productOrders = productOrders;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void addProductOrder(ProductOrder order) {
        if (this.productOrders != null)
            this.productOrders.add(order);
    }

    public int getNumberOfOrders() {
        if (this.productOrders != null)
            return productOrders.size();
        return 0;
    }

    public ProductOrder getProductOrder(int index) {
        if (index < getNumberOfOrders())
            return (ProductOrder) this.productOrders.elementAt(index);
        return null;
    }
}
