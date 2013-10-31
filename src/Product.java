/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Feb 26, 2006
 * Time: 11:47:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Product {
    private String name;
    private int initialCount;
    private int minimumCount;
    private int availableCount;

    public Product() {
    }

    public Product(String name, int initialCount, int minimumCount) {
        this.name = name;
        this.initialCount = initialCount;
        this.minimumCount = minimumCount;
        this.availableCount = initialCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInitialCount() {
        return initialCount;
    }

    public void setInitialCount(int initialCount) {
        this.initialCount = initialCount;
    }

    public int getMinimumCount() {
        return minimumCount;
    }

    public void setMinimumCount(int minimumCount) {
        this.minimumCount = minimumCount;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }


}
