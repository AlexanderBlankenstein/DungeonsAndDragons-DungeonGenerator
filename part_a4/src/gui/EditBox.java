package gui;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditBox extends GridPane {

    private static Controller theController;

    /**
     * Create edit popup window.
     * @param spaceID
     */
    public static void display(int spaceID) {
        Stage window = new Stage();
        theController = GuiView.getController();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Monster and Treasure");
        window.setMinWidth(250);

        Button editMonstersBtn = new Button("Edit Monsters");
        Button editTreasureBtn = new Button("Edit Treasure");
        Button closeBtn = new Button("Close");

        editTreasureBtn.setOnAction((ActionEvent event) -> {
            EditTreasure.display(spaceID);
        });

        editMonstersBtn.setOnAction((ActionEvent event) -> {
            EditMonster.display(spaceID);
        });

        closeBtn.setOnAction((ActionEvent event) -> {
            GuiView.changeDescriptionText(theController.getNewDescription(spaceID));
            window.close();
        });

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(editMonstersBtn, editTreasureBtn, closeBtn);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
