package convertidor_kgalb_prueba;

/**
 *
 * @author Sergio Trinidad
 */
class Product {
    private String name;
    private int quantity;
    private String conversionType;
    private double value;

    public Product(String name, int quantity, String conversionType, double value) {
        this.name = name;
        this.quantity = quantity;
        this.conversionType = conversionType;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getConversionType() {
        return conversionType;
    }

    public double getValue() {
        return value;
    }
}
