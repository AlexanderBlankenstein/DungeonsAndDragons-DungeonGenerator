package gui;

import dnd.models.Monster;
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

public class EditMonster {
    private static Controller theController;
    private static int newMonsterChoice;
    private static Monster chosenMonster;
    private static ArrayList<Monster> monsterList;

    /**
     * create edit monster popup window
     * @param spaceID
     */
    public static void display(int spaceID) {
        Stage window = new Stage();
        theController = GuiView.getController();
        makeMonsterList();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Monster");
        window.setMinWidth(250);

        ListView<String> listView = new ListView<>();
        listView.setPrefSize(200, 200);
        listView.setItems(createList(spaceID));
        listView.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> observableValue, Number number, Number value) -> {
                    newMonsterChoice = value.intValue();
                });

        ComboBox comboBox = new ComboBox();
        comboBox.setPromptText("Select Monster");
        comboBox.setItems(createComboBox());
        comboBox.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> observableValue, Number number, Number value) -> {
                    chosenMonster = monsterList.get(value.intValue());
                });

        Button addBtn = new Button("Add");
        Button removeBtn = new Button("Remove");
        Button closeBtn = new Button("Close");

        addBtn.setOnAction((
                ActionEvent event) -> {
            try{
                theController.addMonster(chosenMonster);
                listView.setItems(createList(spaceID));
            } catch (NullPointerException e) {
                System.out.println("Must select a monster first.");
            }
        });

        removeBtn.setOnAction((ActionEvent event) -> {
            try {
                theController.removeMonster(newMonsterChoice);
                listView.setItems(createList(spaceID));
            } catch (IndexOutOfBoundsException e){
                System.out.println("Cant remove zero monsters.");
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
     * create monster list
     * @param spaceID
     * @return
     */
    private static ObservableList<String> createList(int spaceID) {
        ArrayList<Monster> monsterBox = theController.getMonsterList(spaceID);
        ObservableList<String> monsterData = FXCollections.observableArrayList();

        for (Monster monster: monsterBox) {
            int monsterIndex = monsterBox.indexOf(monster);
            monsterData.add("Monster " + (monsterIndex+1) + ": " + monsterBox.get(monsterIndex).getDescription());
        }
        return monsterData;
    }

    /**
     * create monster dropdown menu
     * @return
     */
    private static ObservableList<String> createComboBox() {
        ArrayList<String> descriptionList = new ArrayList<>();
        for (Monster m:monsterList) {
            descriptionList.add(m.getDescription());
        }
        return FXCollections.observableArrayList(descriptionList);
    }

    /**
     * create monster list
     */
    private static void makeMonsterList() {
        monsterList = new ArrayList<>();
        int i = 1;
        int index = 0;
        do{
            Monster monster = new Monster();
            monster.setType(i);
            if (i==1){
                monsterList.add(monster);
            } else {
                if (!monster.getDescription().contains(monsterList.get((index)).getDescription())){
                    monsterList.add(monster);
                    index++;
                }
            }
            i++;
        } while (i<=100);
    }
}
