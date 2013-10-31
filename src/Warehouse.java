import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Feb 27, 2006
 * Time: 7:31:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class Warehouse {
    private Vector products; //<product>
    private String name;

    public Warehouse() {
        this.products = new Vector();
    }

    public Warehouse(Vector products, String name) {
        this.products = products;
        this.name = name;
    }

    public Product findProductByName(String productName) {
        for (int i = 0 ; i < products.size(); i++) {
            Product product = (Product) products.elementAt(i);
            if ((product.getName()).equalsIgnoreCase(productName))
                return product;
        }
        return null;
    }

    public Vector getProducts() {
        return products;
    }

    public void setProducts(Vector products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public Product getProduct(int index) {
        return (Product) this.products.elementAt(index);
    }
}
