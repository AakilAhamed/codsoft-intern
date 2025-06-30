import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class GradeCalculatorFX extends Application {

    private TextField rollField, sub1, sub2, sub3, sub4, sub5;
    private ComboBox<String> classDropdown;
    private Button calcButton;

    @Override
    public void start(Stage primaryStage) {
        Label title = new Label("📘 Grade Calculator");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        title.setTextFill(Color.web("#eeeeee"));

        rollField = new TextField();
        rollField.setPromptText("Enter Roll No");
        rollField.setStyle("-fx-background-color: #121212; -fx-text-fill: white;");

        classDropdown = new ComboBox<>();
        classDropdown.getItems().addAll("Class 10", "Class 11", "Class 12");
        classDropdown.setValue("Class 10");
        classDropdown.setStyle("-fx-background-color: #121212; -fx-text-fill: white;");

        sub1 = subjectField("Subject 1 Marks");
        sub2 = subjectField("Subject 2 Marks");
        sub3 = subjectField("Subject 3 Marks");
        sub4 = subjectField("Subject 4 Marks");
        sub5 = subjectField("Subject 5 Marks");

        calcButton = new Button("Generate Report");
        calcButton.setStyle("-fx-background-color: #00c853; -fx-text-fill: white; -fx-font-weight: bold;");
        calcButton.setOnAction(e -> showReportCard());

        VBox layout = new VBox(15, title, rollField, classDropdown, sub1, sub2, sub3, sub4, sub5, calcButton);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #0e0e0e;");

        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setTitle("Grade Calculator - Matte Black UI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TextField subjectField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setStyle("-fx-background-color: #121212; -fx-text-fill: white;");
        tf.setMaxWidth(200);
        return tf;
    }

    private void showReportCard() {
        try {
            int m1 = Integer.parseInt(sub1.getText());
            int m2 = Integer.parseInt(sub2.getText());
            int m3 = Integer.parseInt(sub3.getText());
            int m4 = Integer.parseInt(sub4.getText());
            int m5 = Integer.parseInt(sub5.getText());

            for (int mark : new int[]{m1, m2, m3, m4, m5}) {
                if (mark < 0 || mark > 100)
                    throw new IllegalArgumentException("Marks must be 0-100");
            }

            int total = m1 + m2 + m3 + m4 + m5;
            double percentage = total / 5.0;
            String grade;

            if (percentage >= 90) grade = "A+";
            else if (percentage >= 75) grade = "A";
            else if (percentage >= 60) grade = "B";
            else if (percentage >= 50) grade = "C";
            else if (percentage >= 35) grade = "D";
            else grade = "F (Fail)";

            Stage reportStage = new Stage();
            VBox reportBox = new VBox(12);
            reportBox.setAlignment(Pos.CENTER);
            reportBox.setPadding(new Insets(20));
            reportBox.setStyle("-fx-background-color: #121212;");

            Label heading = new Label("📄 Report Card");
            heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
            heading.setTextFill(Color.web("#00e676"));

            reportBox.getChildren().addAll(
                    heading,
                    infoLine("Roll No:", rollField.getText()),
                    infoLine("Class:", classDropdown.getValue()),
                    infoLine("Subject 1:", m1 + " / 100"),
                    infoLine("Subject 2:", m2 + " / 100"),
                    infoLine("Subject 3:", m3 + " / 100"),
                    infoLine("Subject 4:", m4 + " / 100"),
                    infoLine("Subject 5:", m5 + " / 100"),
                    infoLine("Total Marks:", String.valueOf(total)+" / 500"),
                    infoLine("Percentage:", String.format("%.2f%%", percentage)),
                    infoLine("Grade:", grade)
            );

            Scene reportScene = new Scene(reportBox, 350, 450);
            reportStage.setTitle("Result Summary");
            reportStage.setScene(reportScene);
            reportStage.show();

        } catch (NumberFormatException e) {
            showError("Please enter valid numbers for all subjects.");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    private Label infoLine(String key, String value) {
        Label label = new Label(key + " " + value);
        label.setTextFill(Color.web("#eeeeee"));
        label.setFont(Font.font("Segoe UI", 14));
        return label;
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("❌ Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
