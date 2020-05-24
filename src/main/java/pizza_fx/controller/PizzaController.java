package pizza_fx.controller;


import pizza_fx.model.Ingredient;
import pizza_fx.model.Pizza;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class PizzaController {

    public Pizza findCheapestSpicy() {
        return Arrays.stream(Pizza.values())
                .filter(pizza -> pizza.getIngredients().stream()
                        .anyMatch(Ingredient::isSpicy))
                .sorted(Comparator.comparing(pizza -> getPizzaPrice(pizza)))
                .findFirst()
                .get();
    }

    public Pizza findCheapest() {
        return Arrays.stream(Pizza.values())
                .sorted(Comparator.comparing(pizza -> getPizzaPrice(pizza)))
                .limit(1)
                .findFirst()
                .get();
    }
    public double getPizzaPrice(Pizza pizza) {
        return pizza.getIngredients()
                .stream()
                .mapToDouble(Ingredient::getPrice)
                .sum();
    }

    public Pizza findMostExpensiveVegetarian() {
        return Arrays.stream(Pizza.values())
                .filter(pizza -> pizza.getIngredients().stream()
                        .noneMatch(Ingredient::isMeat))
                .max(Comparator.comparing(pizza -> getPizzaPrice(pizza)))
                .get();
    }

    public List<Pizza> iLikeMeat(){
        return Arrays.stream(Pizza.values())
                .filter(pizza -> pizza.getIngredients()
                        .stream()
                        .anyMatch(Ingredient::isMeat))
                .sorted(Comparator.comparing(pizza -> pizza.getIngredients()
                        .stream()
                        .filter(Ingredient::isMeat)
                        .count(), Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    public Map groupByPrice(){
        return Arrays.stream(Pizza.values())
                .collect(Collectors.groupingBy(pizza -> getPizzaPrice(pizza)));

    }

    public TreeMap<Long, List<Pizza>> groupByNumberOfSpicyIngredients(){
        return new TreeMap<Long, List<Pizza>>(Arrays.stream(Pizza.values())
                .collect(Collectors.groupingBy(pizza -> pizza.getIngredients()
                        .stream()
                        .filter(Ingredient::isSpicy)
                        .count())
                ));
    }

    public TreeMap<Integer, List<Pizza>> groupByNumberOfIngredientsGreaterThan4(){
        return new TreeMap<Integer, List<Pizza>>(Arrays.stream(Pizza.values())
                .filter(pizza -> pizza.getIngredients().size() > 4 && pizza.getIngredients().size() < 7)
                .collect(Collectors.groupingBy(pizza -> pizza.getIngredients().size()))
        );
    }
    // meetoda zwracajaca menu | nazwa pizzy | ostra lub lagodna | miesna lub vege | nazwa składnika1, ..., nazwa składnikaN | cena |
    public String formatedMenu(){
        Pizza pizzaOfDay = getRandomPizza();
        return Arrays.stream(Pizza.values())
                .map(pizza -> String.format(
                        "| %20s | %8s | %8s | %5.2f zł | %100s |",
                        pizza.equals(pizzaOfDay) ? pizza.getName() + "*"    : pizza.getName(),
                        pizza.getIngredients().stream().anyMatch(Ingredient::isSpicy) ? "ostra" : "łagodna",
                        pizza.getIngredients().stream().anyMatch(Ingredient::isMeat) ? "Normalna" : "vege",
                        pizza.equals(pizzaOfDay) ? getPizzaPrice(pizza)*0.8  :   getPizzaPrice(pizza),
                        pizza.getIngredients()
                                .stream()
                                .map(Ingredient::getName)
                                .collect(Collectors.joining(", "))
                        )
                )
                .collect(Collectors.joining("\n"));
    }

    public Pizza getRandomPizza(){
        return Pizza.values()[new Random().nextInt(Pizza.values().length)];

    }





    public static void main(String[] args) {
        PizzaController pc = new PizzaController();
        System.out.println(pc.getPizzaPrice(Pizza.MARGHERITA));
        System.out.println("Najtańsza Pizza: " + pc.findCheapest());
        System.out.println("Najtańsza ostra Pizza: " + pc.findCheapestSpicy());
        System.out.println("Najdroższa Pizza Vege: " + pc.findMostExpensiveVegetarian());
        System.out.println("Pizze mięsne posortowane po ilości składników mięsnych DESC");
        pc.iLikeMeat().forEach(pizza -> System.out.println(
                pizza + " " +
                        pizza.getIngredients().size() + " " +
                        pizza.getIngredients().stream().filter(Ingredient::isMeat)
                                .count()
        ));
        System.out.println("Pizze pogrupowane po cenie");
       /*
        pc.groupByPrice()
                .forEach((key, value) -> System.out.printf
                ("%5.1f | %s \n", key, value));
                */
       /*
       TreeMap<Double, List<Pizza>> sortedMap = new TreeMap<>(pc.groupByPrice());
        System.out.println(sortedMap);
        */
        new TreeMap<>(pc.groupByPrice())
                .forEach((key, value) -> System.out.printf
                        ("%5.1f | %s \n", key, value));

        System.out.println("Pizze pogrupowane po ilości składników ostrych");
        pc.groupByNumberOfSpicyIngredients()
                .forEach((key, value) -> System.out.printf("%5d | %s \n", key, value));

        System.out.println("Pizze pogrupowane po ilości składników, ale tylko większe od 4 i mniejsze od 7");
        pc.groupByNumberOfIngredientsGreaterThan4()
                .forEach((key, value) -> System.out.printf("%5d | %s \n", key, value));
        System.out.println(pc.formatedMenu());


    }
}


