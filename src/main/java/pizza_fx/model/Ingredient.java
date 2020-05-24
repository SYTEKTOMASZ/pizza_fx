package pizza_fx.model;

import java.util.Arrays;
import java.util.List;

public enum Ingredient {
    THIN_CRUST("cienkie ciasto", 12, false, false),
    THICK_CRUST("grube ciasto", 14, false, false),
    TOMATO_SUACE("sos pomidorowy", 0, false, false),
    CHEESE("ser", 1, false, false),
    ONION("cebula", 1, false, false),
    PINEAPPLE("ananas", 1, false, false),
    BROCCOLI("brokuły", 1, false, false),
    BEAN("fasola", 1, false, false),
    CORN("kukurydza", 1, false, false),
    PEPERONI("papryka peperoni", 1, false, true),
    MUSHROOMS("pieczarki", 1, false, false),
    TOMATO("pomidor", 1, false, false),
    FETA("ser feta", 2, false, false),
    MOZARELLA("mozarella", 2, false, false),
    SUASAGE("kiełbasa", 2, true, false),
    CHICKEN("kurczak", 2, true, false),
    OLIVES("oliwki", 2, false, false),
    SALAMI("salami", 2, true, false),
    TUNA("tuńczyk", 2, false, false),
    BECON("bekon", 3, true, false),
    ARUGULA("rukola", 3, false, false),
    BLUE_CHEESE("ser pleśniowy", 2, false, false),
    HAM("szynka", 3, true, false),
    BASIL("bazylia", 0, false, false),
    PEPPER("pieprz", 0, false, true),
    TABASCO_SUACE("tabasco", 0, false, true);
    private final String name;
    private final int price;
    private final boolean meat;
    private final boolean spicy;

    private Ingredient(String name, int price, boolean meat, boolean spicy) {
        this.name = name;
        this.price = price;
        this.meat = meat;
        this.spicy = spicy;
    }

    /**
     * Zwraca nazwe skladnika
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Zwraca cene skladnika
     */
    public int getPrice() {
        return price;
    }

    /**
     * zwraca true jesli skladnik jest miesem (nie dla
     * wegetarian)
     */
    public boolean isMeat() {
        return meat;
    }

    /**
     * zwraca true jesli skladnik jest pikantny
     */
    public boolean isSpicy() {
        return spicy;
    }

    @Override
    public String toString() {
        return name;
    }
}
