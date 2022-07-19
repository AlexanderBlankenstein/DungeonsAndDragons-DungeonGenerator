package gui;

import dnd.models.Monster;
import dnd.models.Treasure;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class EditTreasure {
    private static Controller theController;
    private static int newTreasureChoice;
    private static Treasure chosenTreasure;
    private static ArrayList<Treasure> treasureList;

    /**
     * create edit treasure popup window
     * @param spaceID
     */
    public static void display(int spaceID) {
        Stage window = new Stage();
        theController = GuiView.getController();
        makeTreasureList();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Treasure");
        window.setMinWidth(250);

        ListView<String> listView = new ListView<>();
        listView.setPrefSize(200, 200);
        listView.setItems(createList(spaceID));
        listView.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> observableValue, Number number, Number value) -> {
                    newTreasureChoice = value.intValue();
                });

        ComboBox comboBox = new ComboBox();
        comboBox.setPromptText("Select Treasure");
        comboBox.setItems(createComboBox());
        comboBox.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> observableValue, Number number, Number value) -> {
                    chosenTreasure = treasureList.get(value.intValue());
                });

        Button addBtn = new Button("Add");
        Button removeBtn = new Button("Remove");
        Button closeBtn = new Button("Close");

        addBtn.setOnAction((
                ActionEvent event) -> {
            try {
                theController.addTreasure(chosenTreasure);
                listView.setItems(createList(spaceID));
            } catch (NullPointerException e) {
                System.out.println("Must select a Treasure First");
            }
        });

        removeBtn.setOnAction((ActionEvent event) -> {
            try {
                theController.removeTreasure(newTreasureChoice);
                listView.setItems(createList(spaceID));
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Can't remove zero Treasure.");
            }
        });

        closeBtn.setOnAction((ActionEvent event) -> {
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(listView, comboBox, addBtn, removeBtn, closeBtn);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * create treasure list section
     * @param spaceID
     * @return
     */
    private static ObservableList<String> createList(int spaceID) {
        ArrayList<Treasure> treasureBox = theController.getTreasureList(spaceID);
        ObservableList<String> treasureData = FXCollections.observableArrayList();

        for (Treasure treasure: treasureBox) {
            int treasureIndex = treasureBox.indexOf(treasure);
            treasureData.add("Treasure " + (treasureIndex+1) + ": " + treasureBox.get(treasureIndex).getDescription());
        }
        return treasureData;
    }

    /**
     * create treasure dropdown menu
     * @return
     */
    private static ObservableList<String> createComboBox() {
        ArrayList<String> descriptionList = new ArrayList<>();
        for (Treasure t:treasureList) {
            descriptionList.add(t.getDescription());
        }
        return FXCollections.observableArrayList(descriptionList);
    }

    /**
     * create list of treasure.
     */
    private static void makeTreasureList() {
        treasureList = new ArrayList<>();
        int i = 1;
        int index = 0;
        do{
            Treasure treasure = new Treasure();
            treasure.chooseTreasure(i);
            if (i==1){
                treasureList.add(treasure);
            } else {
                if (!treasure.getDescription().contains(treasureList.get((index)).getDescription())){
                    treasureList.add(treasure);
                    index++;
                }
            }
            i++;
        } while (i<=100);
    }
}