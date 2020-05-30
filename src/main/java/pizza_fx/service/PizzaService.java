package pizza_fx.service;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import pizza_fx.model.Ingredient;
import pizza_fx.model.Pizza;
import pizza_fx.model.PizzaModel;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PizzaService {
    public ObservableList<PizzaModel> addPizzas(ObservableList<PizzaModel> pizzas)
    {
        for (Pizza pizza : Pizza.values()){
            pizzas.add(new PizzaModel(
                    pizza.getName(),

                    pizza.getIngredients().stream().map(Ingredient::getName)
                            .collect(Collectors.joining(", ")),

                    (pizza.getIngredients().stream()
                            .anyMatch(Ingredient::isSpicy) ? "ostra " : "")
                            +
                            (pizza.getIngredients().stream().noneMatch(Ingredient::isMeat) ? "wege " : ""),

                    pizza.getIngredients().stream().mapToDouble(Ingredient::getPrice).sum()
            ));
        }
        return pizzas;
    }
    // metoda konfigurujaca komlumny TableView i wprowadzajaca dane z ObservableList
    public void insertPizzasToTable(
            TableView<PizzaModel> tblPizza,
            TableColumn<PizzaModel, String> tcName,
            TableColumn<PizzaModel, String> tcIngredients,
            TableColumn<PizzaModel, String> tcType,
            TableColumn<PizzaModel, Double> tcPrice,
            ObservableList<PizzaModel>  pizzas
            )
        {
            tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tcIngredients.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
            tcType.setCellValueFactory(new PropertyValueFactory<>("type"));
            tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

            // ustawienie jezyka
            Locale locale = new Locale("pl", "PL");
            // obiekt do wartosci numerycznych
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

            tcPrice.setCellFactory(tc -> new TableCell<PizzaModel, Double>(){
              @Override
              protected  void updateItem(Double price, boolean empty){
                  super.updateItem(price, empty);
                      if(empty){
                          setText(null);
                      }
                      else{
                          setText(currencyFormat.format(price));
                      }
                  }
              }
            );
            tblPizza.setItems(pizzas);
        }
        //generator pizzy dnia -> 1.obnizenie ceny wylosowanej pizzy 2.przekazanie ppizzy dnia do labela
    public void pizzaOfTheDayGenerator(ObservableList<PizzaModel> pizzas, Label lbRandomPizza){
            }
                 // obiekt do przechwoywania aktualna kwtoe do zaplaty
    private double amount;

    private List<Integer> choices = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
    // metoda do wybierania i przenoszenia pizzy do koszyka
    public void addToBasket(TableView<PizzaModel> tblPizza, TextArea taBasket, Label lblSum){
        // odczyt, który wiersz w tabelce został zaznaczony
        PizzaModel selectedPizza = tblPizza.getSelectionModel().getSelectedItem();
        // utworzenie okna kontekstowego do zamówienia wybranej ilości pizzy
        ChoiceDialog<Integer> addToBasketDialog = new ChoiceDialog<>(1, choices);
        addToBasketDialog.setTitle("Wybierz ilość");
        addToBasketDialog.setHeaderText("Wybrałeś pizze " + selectedPizza.getName());
        addToBasketDialog.setContentText("Wybierz ilość zamawianej pizzy: ");
        // okno zostaje wyświetlone i utrzymane na ekranie i zwróci wartość po wciśnięciu przycisku
        Optional<Integer> result = addToBasketDialog.showAndWait();
        // gdy wybrano OK
        result.ifPresent(quantity -> taBasket.appendText(
                String.format("%-15s %5d szt. %10.2f zł\n",
                        selectedPizza.getName(),quantity, selectedPizza.getPrice() * quantity)));
        result.ifPresent(quantity -> amount = amount + (quantity * selectedPizza.getPrice()));
        lblSum.setText(String.format("KWOTA DO ZAPŁATY: %.2f ZŁ", amount));
    }
    public void clearOrder(TextArea taBasket, TextField tfAdress, TextField tfPhone, Label lblSum){
        taBasket.clear();
        tfAdress.clear();
        tfPhone.clear();
        lblSum.setText("KWOTA DO ZAPŁATY 0,00 ZŁ");
        amount =0;
    }

public boolean isPhoneValid(String phone){
        return Pattern.matches(
                "(^([0-9]{3}[-]{1}){2}[0-9]{3}$)|(^[0-9]{9}$)",
                phone);
}

public boolean isAdressValid(String address){
    return Pattern.matches("^[au][l][\\.]\\s{0,1}[A-Za-złąęśćźżóń\\d\\.\\s]{1,}\\s{1}\\d{1,}[A-Za-z]{0,}[\\/]{0,1}\\d{0,}[,]\\s{0,1}\\d{2}[-]\\d{3}\\s{1}[A-Za-złąęśćźżóń\\s\\-]{2,}$",
            address);

}

public void getOrder(TextField tfPhone, TextField tfAddress, TextArea taBasket, Label lblSum){
        if(isAdressValid(tfAddress.getText()) && isPhoneValid(tfPhone.getText()) && !taBasket.getText().equals("")){
            Alert.AlertType alertAlertType;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Zamówienie");
            alert.setHeaderText("Potwierdzenie zamówienia");
            alert.setContentText("Twoje zamówienie: \n" + taBasket.getText() + "\nDo zapłaty: "  + amount + "zł");
            alert.showAndWait();
            
            clearOrder(taBasket,tfAddress,tfPhone,lblSum);

        }
        else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Zamówienie");
            alert.setHeaderText("Błędne dane zamówienia");
            String validationResult = "Wprowadziłes niepoprawne dane w następujących polach: ";
            if(!isPhoneValid(tfPhone.getText())){
                validationResult += "telefon ";
            }
            if(!isAdressValid(tfAddress.getText())){
                validationResult += "adress dostawy ";
            }
            if(isPhoneValid(tfPhone.getText())&& isAdressValid(tfAddress.getText())){
                validationResult ="";
            }
            String emptyBasket = "";
            if(taBasket.getText().equals("")){
                emptyBasket = "\nTwój koszyk nie może byc pusty";
            }
            alert.setContentText(validationResult + emptyBasket);
            alert.showAndWait();
        }
}
public void saveDataToFile(TextField tfAddress, TextField tfPhone, TextArea taBasket) throws FileNotFoundException {
        //data i czas zamówienia
        // adres dostawy
        //telefon
        // czas dostawy to data icz as zamówienia + 45 minut
        //---------------------------------
        // zawartość koszyka
        // kwota do zapłaaty
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
            "Plik tekstowy (*.txt)", "*txt");
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showSaveDialog(null);
    Writer out;
    PrintWriter printWriter = new PrintWriter(file);
    printWriter.println("Potwierdzneie Zamówienia");
    LocalDateTime dateTime = LocalDateTime.now();
    printWriter.println("Data i czas:" + dateTime);
    printWriter.println("Adres dostawy: " + tfAddress.getText());
    printWriter.println("Telefon kontaktowy: " + tfPhone.getText());
    printWriter.println("Czas dostawyy: " + dateTime.plusMinutes(45));
    printWriter.println("Zamówienie: \n" + taBasket.getText());
    printWriter.println("Suma do zapłaty :" + amount + " zł");
    printWriter.close();


    }
}
