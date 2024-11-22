package com.example.dataentryform;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {
    public static ArrayList<Person> persons = new ArrayList<>();
    private File selectedFile;

    @Override
    public void start(Stage stage) throws IOException {
    GridPane gridPane = new GridPane();
    gridPane.setStyle("-fx-background-color: yellow;");
    Image banner = new Image(this.getClass().getResource("/Form2.png").toExternalForm());
    ImageView imageView = new ImageView(banner);
    ImageView selectedImageView = new ImageView();
    selectedImageView.setFitWidth(200); // Set a fixed width for the image
    selectedImageView.setFitHeight(200); // Set a fixed height for the image
    selectedImageView.setPreserveRatio(true);

    Label nameLabel = createStyledLabel("Name: ");
    Label fatherNameLabel = createStyledLabel("Father's Name: ");
    Label cnicLabel = createStyledLabel("CNIC No: ");
    Label dateLabel = createStyledLabel("Date of Birth: ");
    Label genderLabel = createStyledLabel("Gender: ");
    Label cityLabel = createStyledLabel("City: ");
    Label fileLabel = createStyledLabel("File: ");
    Label selectedFileLabel = new Label("No File Selected");


    TextField nameText = new TextField();
    nameText.setPromptText("Enter your name");
    TextField fatherNameText = new TextField();
    fatherNameText.setPromptText("Enter your father's name");
    TextField cnicText = new TextField();
    cnicText.setPromptText("Enter your CNIC number");

    DatePicker datePicker = new DatePicker();
    datePicker.setPrefWidth(600);

    ComboBox<String> cityComboBox = new ComboBox<>();
    cityComboBox.getItems().addAll("Lahore","Karachi","Multan", "Peshawar","Quetta","Islamabad","Faisalabad");
    cityComboBox.setPromptText("Select your city");

    RadioButton maleButton = new RadioButton("Male");
    RadioButton femaleButton = new RadioButton("Female");
    ToggleGroup toggleGroup = new ToggleGroup();
    maleButton.setToggleGroup(toggleGroup);
    femaleButton.setToggleGroup(toggleGroup);
    Button fileChooserButton = new Button("Choose File");
    fileChooserButton.setOnAction(e->{
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpeg","*.jpg"));

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            selectedFile=file;
            selectedFileLabel.setText(selectedFile.getName());
            try {
                Image selectedImage = new Image(selectedFile.toURI().toString());
                selectedImageView.setImage(selectedImage); // Update the ImageView with the new image
            } catch (Exception ex) {
                selectedFileLabel.setText("Invalid file selected");
            }
        }
    });

    Button saveButton = new Button("Save");
    saveButton.setOnAction(e -> {
                String name = nameText.getText().trim();
                String fatherName = fatherNameText.getText().trim();
                String cnic = cnicText.getText().trim();
                String date = (datePicker.getValue() != null) ? datePicker.getValue().toString() : "";
                String gender = (maleButton.isSelected()) ? "Male" : (femaleButton.isSelected() ? "Female" : "");
                String city = cityComboBox.getValue();
                // Validate required fields
                if (name.isEmpty() || fatherName.isEmpty() || cnic.isEmpty() || gender.isEmpty() || date.isEmpty()|| city.isEmpty()||selectedFile==null) {
                    showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all required fields.");
                    return;
                }
                persons.add(new Person(name, fatherName, cnic, date, gender,city,selectedFile));
                showAlert(Alert.AlertType.INFORMATION, "Success", "Data saved successfully!");
        // Clear input fields for the next entry
        nameText.clear();
        fatherNameText.clear();
        cnicText.clear();
        datePicker.setValue(null);
        cityComboBox.setValue(null);
        maleButton.setSelected(false);
        femaleButton.setSelected(false);
        selectedFile = null;
        selectedFileLabel.setText("No File Selected");
        selectedImageView.setImage(null);  // Clear the image view

            });
        // Cancel button to hide the stage
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            stage.hide(); // Hides the current stage
            for(Person person: persons)
                System.out.println(person);
        });

        // Styling for Cancel button
        cancelButton.setPadding(new Insets(10, 10, 10, 10));
        cancelButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: black; -fx-text-fill: gold; -fx-font-style: italic;");


    saveButton.setPadding(new Insets(10,10,10,10));
    saveButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: black; -fx-text-fill: gold; -fx-font-style: italic;");


     VBox genderBox = new VBox(10);
     genderBox.getChildren().addAll(maleButton,femaleButton);
     HBox imageBox = new HBox();
     imageBox.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-padding: 10,10,10,10; -fx-background-color: #f9f9f9;");
     imageBox.setPrefSize(200, 200);// Set fixed size
     imageBox.setMaxSize(200, 200); // Ensure it doesn't grow
     imageBox.setAlignment(Pos.CENTER); // Center
     //imageBox.setPadding(new Insets(10,10,10,10));
     imageBox.getChildren().add(selectedImageView);
        HBox saveCancelBox = new HBox(10); // Space of 10px between buttons
        saveCancelBox.setAlignment(Pos.BOTTOM_RIGHT);
        saveCancelBox.setPadding(new Insets(10, 50, 0, 50));
        saveCancelBox.getChildren().addAll(cancelButton, saveButton);

        nameText.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                fatherNameText.requestFocus(); // Move to the father's name field
            }
        });

        fatherNameText.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                cnicText.requestFocus(); // Move to the CNIC field
            }
        });

        cnicText.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                datePicker.requestFocus(); // Move to the date picker
            }
        });

        datePicker.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                maleButton.requestFocus(); // Move to the gender radio buttons
            }
        });

    gridPane.add(imageView,0,0,2,1);
    gridPane.setPadding(new Insets(20, 10,80,10));
    gridPane.setVgap(15);
    gridPane.setHgap(15);
    gridPane.add(nameLabel,0,1);
    gridPane.add(nameText,1,1);
    gridPane.add(fatherNameLabel,0,2);
    gridPane.add(fatherNameText,1,2);
    gridPane.add(cnicLabel,0,3);
    gridPane.add(cnicText,1,3);
    gridPane.add(dateLabel,0,4);
    gridPane.add(datePicker,1,4);
    gridPane.add(genderLabel,0,5);
    gridPane.add(genderBox,1,5);
    gridPane.add(cityLabel,0,6);
    gridPane.add(cityComboBox,1,6);
    gridPane.add(fileLabel,0, 7);
    gridPane.add(fileChooserButton,1,7);
    gridPane.add(imageBox,1,8);
    gridPane.add(saveCancelBox, 1, 10); // Add the HBox to the grid

    Scene scene = new Scene(gridPane, 800, 800);
    stage.setScene(scene);
    stage.show();
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-font-style: italic;");
        return label;
    }

    public static void main(String[] args) {
        launch();

    }
}