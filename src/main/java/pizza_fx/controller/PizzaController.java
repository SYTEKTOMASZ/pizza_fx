package pizza_fx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import pizza_fx.model.Ingredient;
import pizza_fx.model.Pizza;
import pizza_fx.model.PizzaModel;
import pizza_fx.service.PizzaService;

import javax.swing.*;
import java.util.stream.Collectors;

public class PizzaController {
// Aby dodać kolekcje  obiektów do kontroek FXML korzystamy z ObservableList
    private ObservableList<PizzaModel> pizzas = FXCollections.observableArrayList();
// Objekt klasy Pizza service
    private PizzaService pizzaService = new PizzaService();


    @FXML
    private Label lblSum;

    @FXML
    private TableView<PizzaModel> tblPizza;

    @FXML
    private TableColumn<PizzaModel, String> tcName;

    @FXML
    private TableColumn<PizzaModel, String> tcIngredients;

    @FXML
    private TableColumn<PizzaModel, String> tcType;

    @FXML
    private TableColumn<PizzaModel, Double> tcPrice;

    @FXML
    private Label lbRandomPizza;

    @FXML
    private TextArea taBasket;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfPhone;

    @FXML
    void clearAction(MouseEvent event) {
        pizzaService.clearOrder(taBasket, tfAddress, tfPhone, lblSum);
    }

    @FXML
    void orderAction(MouseEvent event) {
        System.out.println("112");
    }

    @FXML
    void selectPizzaAction(MouseEvent mouseEvent){
        //odczyt, który wiersz w tabelce został zaznaczony
        pizzaService.addToBasket(tblPizza, taBasket);
        // utworzenie okna kontekstowego do zamowienia wybranej ilosci pizzy

    }

    // konstruktor -> incjalizacja
public void initialize(){
        // wywołanie metody zaimplementowanych w logice biznesowej aplikacji
       pizzas =  pizzaService.addPizzas(pizzas);
       pizzaService.insertPizzasToTable(
               tblPizza, tcName, tcIngredients, tcType,tcPrice, pizzas);
       pizzaService.pizzaOfTheDayGenerator(pizzas, lbRandomPizza);
}


}
