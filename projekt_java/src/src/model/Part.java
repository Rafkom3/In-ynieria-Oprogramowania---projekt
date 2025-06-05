package model;

public class Part {
    private final int id;
    private final String name;
    private final String catalogNumber;
    private final int price;

    public Part(int id, String name, String catalogNumber, int price) {
        this.id = id;
        this.name = name;
        this.catalogNumber = catalogNumber;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%2d. %-20s (nr kat.: %s) – %d zł",
                id, name, catalogNumber, price);
    }
}
