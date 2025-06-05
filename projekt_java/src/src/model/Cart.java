package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<Part> items = new ArrayList<>();

    public void addPart(Part p) {
        items.add(p);
    }

    public int getTotalPrice() {
        int sum = 0;
        for (Part p : items) {
            sum += p.getPrice();
        }
        return sum;
    }

    @Override
    public String toString() {
        if (items.isEmpty()) {
            return "Koszyk jest pusty.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            Part p = items.get(i);
            sb.append(p.getName());
            if (i < items.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(". Suma całkowita: ").append(getTotalPrice()).append(" zł");
        return sb.toString();
    }
}
