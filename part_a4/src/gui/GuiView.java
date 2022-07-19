package gui;

import dungeon.Chamber;
import dungeon.Passage;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.event.TreeModelEvent;
import java.io.File;
import java.util.ArrayList;


public class GuiView<toReturn> extends Application {

    private static Controller theController;
    private static BorderPane root;
    private Stage primaryStage;

    @Override
    public void start(Stage assignedStage) {
        theController = new Controller(this);
        primaryStage = assignedStage;
        root = setUpRoot(primaryStage);
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("DnD Level Generator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * set up program GUI
     * @param primaryStage
     * @return
     */
    private BorderPane setUpRoot(Stage primaryStage) {
        BorderPane temp = new BorderPane();

        MenuBar menuBar = setMenuBar(primaryStage);
        menuBar.prefWidthProperty().bind(this.primaryStage.widthProperty());
        Node left = setLeftPanel();
        Node center = setCenterPanel();
        Node right = setRightPanel();
        Node bottom = setBottomPanel();

        temp.setTop(menuBar);
        temp.setLeft(left);
        temp.setCenter(center);
        temp.setRight(right);
        temp.setBottom(bottom);
        return temp;
    }

    /**
     * create menu section at top of program.
     * @param primaryStage
     * @return
     */
    private MenuBar setMenuBar(Stage primaryStage){
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem loadMenuItem = new MenuItem("Load");
        loadMenuItem.setOnAction(actionEvent -> {
            theController.load(loadFileChoice(primaryStage));
            changeSpaceList(getListData());
        });

        MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.setOnAction(actionEvent -> theController.save(saveFileChoice(primaryStage)));

        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(actionEvent -> theController.exit(this.primaryStage));

        fileMenu.getItems().addAll(loadMenuItem, saveMenuItem, new SeparatorMenuItem(), exitMenuItem);
        menuBar.getMenus().addAll(fileMenu);
        return menuBar;
    }

    /**
     * load file location, user defind
     * @param parent
     * @return
     */
    private File loadFileChoice(Stage parent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a Save File");
        return fileChooser.showOpenDialog(parent);
    }

    /**
     * save file location, user defind
     * @param parent
     * @return
     */
    private File saveFileChoice(Stage parent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Create a Save File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showSaveDialog(parent);
    }

    /**
     * create center section for label text
     * @return
     */
    private Node setCenterPanel() {
        VBox temp = new VBox();
        temp.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: gray;");

        Label label = new Label("Please Select a Space \nFrom the list on the left to begin.\n<------- ");
        label.setWrapText(true);
        label.setMaxWidth(500);
        label.setMaxHeight(500);
        temp.getChildren().add(label);

        return temp;
    }

    /**
     * create left section for space list
     * @return
     */
    private Node setLeftPanel() {
        VBox temp = new VBox();
        temp.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: gray;");

        ListView<String> listView = new ListView<>();
        listView.setPrefSize(200, 500);

        listView.setItems(getListData());
        listView.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> observableValue, Number number, Number value) -> {
                    theController.setSpaceID(value.intValue());
                    changeDescriptionText(theController.getNewDescription(value.intValue()));
                    changeDoorDescriptionText("");
                    changeDoorList(theController.getNewDoorList(value.intValue()));
                });
        temp.getChildren().add(listView);
        return temp;
    }

    /**
     * create right section for door list
     * @return
     */
    private Node setRightPanel() {
        VBox temp = new VBox();
        temp.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: gray;");

        ComboBox comboBox = new ComboBox();
        comboBox.setPromptText("Select Door");
        temp.getChildren().add(comboBox);

        Label label = new Label();
        label.setWrapText(true);
        label.setMaxWidth(180);
        temp.getChildren().add(label);

        comboBox.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> observableValue, Number number, Number value) -> {
                    int doorID = 0;
                    if (value.intValue()>=0){
                        doorID = value.intValue();
                    }
                    changeDoorDescriptionText(theController.getNewDoorDescription(theController.getSpaceID(), doorID));
                });
        return temp;
    }

    /**
     * create bottom section for edit button
     * @return
     */
    private Node setBottomPanel() {
        VBox temp = new VBox();
        temp.setStyle("-fx-padding: 10;");

        Button editBtn = createButton("Edit", "-fx-background-color: #ff0000; -fx-background-radius: 10, 10, 10, 10;");
        editBtn.setOnAction((ActionEvent event) -> {
            createPopUp(theController.getSpaceID());
        });
        temp.getChildren().add(editBtn);
        return temp;
    }

    /**
     * create popup window
     * @param spaceID
     */
    public void createPopUp(int spaceID) {
        EditBox.display(spaceID);
    }

    /**
     * generic button creation method ensure that all buttons will have a
     * similar style and means that the style only need to be in one place
     * @param text
     * @param format
     * @return
     */
    private Button createButton(String text, String format) {
        Button btn = new Button();
        btn.setText(text);
        btn.setStyle("");
        return btn;
    }

    /**
     * update the text for label
     * @param text
     */
    public static void changeDescriptionText(String text) {
        ObservableList<Node> list = root.getChildren();
        Label myLabel = ((Label)(((VBox)(list.get(2))).getChildren()).get(0));
        myLabel.setText(text);
    }

    /**
     * update the space list view
     * @param data
     */
    private void changeSpaceList(ObservableList<String> data) {
        ObservableList<Node> list = root.getChildren();
        ListView myListView = ((ListView)(((VBox)(list.get(1))).getChildren()).get(0));
        myListView.setItems(data);
    }

    /**
     * get the data for list view
     * @return
     */
    private ObservableList<String> getListData() {
        ObservableList<String> data = FXCollections.observableArrayList();
        ArrayList<Passage> passages = theController.getPassageList();
        ArrayList<Chamber> chambers = theController.getChamberList();

        for (Chamber chamber:chambers) {
            data.add("Chamber " + (chambers.indexOf(chamber)+1));
        }

        for (Passage passage:passages) {
            data.add("Passage " + (passages.indexOf(passage)+1));
        }

        return data;
    }

    /**
     * update the door description
     * @param text
     */
    private void changeDoorDescriptionText(String text) {
        ObservableList<Node> list2 = root.getChildren();
        Label doorLabel = ((Label)(((VBox)(list2.get(3))).getChildren()).get(1));
        doorLabel.setText(text);
    }

    /**
     * updated the door list
     * @param doorList
     */
    private void changeDoorList(ArrayList<String> doorList) {
        ObservableList<Node> list = root.getChildren();
        ComboBox myComboBox = ((ComboBox)(((VBox)(list.get(3))).getChildren()).get(0));
        myComboBox.setItems(FXCollections.observableArrayList(doorList));
    }

    /**
     * retreive the controller
     * @return
     */
    public static Controller getController(){
        return theController;
    }

    /**
     * launch main program
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
