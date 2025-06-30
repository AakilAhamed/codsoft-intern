import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class ATMAppFX extends Application {

    private double balance = 0;
    private TextField amountField;
    private Label balanceLabel;

    @Override
    public void start(Stage stage) {
        Label title = new Label("🖤 ByteBank ATM");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setTextFill(Color.web("#00e676"));

        amountField = new TextField();
        amountField.setPromptText("Enter Amount");
        amountField.setStyle("-fx-background-color: #121212; -fx-text-fill: white;");
        amountField.setMaxWidth(200);

        balanceLabel = new Label("Balance: ₹0.00");
        balanceLabel.setTextFill(Color.WHITE);
        balanceLabel.setFont(Font.font("Segoe UI", 14));

        Button depositBtn = actionButton("Deposit");
        depositBtn.setOnAction(e -> deposit());

        Button withdrawBtn = actionButton("Withdraw");
        withdrawBtn.setOnAction(e -> withdraw());

        Button checkBtn = actionButton("Check Balance");
        checkBtn.setOnAction(e -> updateBalanceLabel());

        VBox box = new VBox(15, title, amountField, depositBtn, withdrawBtn, checkBtn, balanceLabel);
        box.setPadding(new Insets(25));
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #0e0e0e;");

        Scene scene = new Scene(box, 350, 350);
        stage.setScene(scene);
        stage.setTitle("ATM Interface");
        stage.show();
    }

    private Button actionButton(String label) {
        Button btn = new Button(label);
        btn.setStyle("-fx-background-color: #00c853; -fx-text-fill: white; -fx-font-weight: bold;");
        btn.setMaxWidth(150);
        return btn;
    }

    private void deposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) throw new IllegalArgumentException();
            balance += amount;
            updateBalanceLabel();
            showSuccess("Deposited ₹" + String.format("%.2f", amount));
        } catch (Exception e) {
            showError("Invalid deposit amount.");
        }
    }

    private void withdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0 || amount > balance) {
                showError("Invalid or insufficient funds.");
                return;
            }
            balance -= amount;
            updateBalanceLabel();
            showSuccess("Withdrew ₹" + String.format("%.2f", amount));
        } catch (Exception e) {
            showError("Invalid withdrawal amount.");
        }
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Balance: ₹" + String.format("%.2f", balance));
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Transaction Failed");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showSuccess(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Transaction Successful");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
