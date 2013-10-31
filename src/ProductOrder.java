/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Feb 27, 2006
 * Time: 12:09:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProductOrder {
    private String productName;
    private int count;

    public ProductOrder() {
    }

    public ProductOrder(String productName, int count) {
        this.productName = productName;
        this.count = count;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String toString() {
        return productName + "," + count;
    }
}
