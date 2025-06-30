import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import java.util.*;

public class GuessGameFX extends Application {

    private int numberToGuess, attemptsLeft;
    private int round = 1;
    private final int maxAttempts = 7;
    private Label feedbackLabel;
    private TextField guessInput;
    private Button guessButton, playAgainButton;
    private List<Integer> leaderboard = new ArrayList<>();
    private VBox leaderboardBox;

    @Override
    public void start(Stage primaryStage) {
        newGame();

        // 🎯 Matte Black Streamlit-Style UI
        feedbackLabel = new Label("🎯 Round " + round + ": Guess a number between 1 and 100");
        feedbackLabel.setTextFill(Color.web("#e0e0e0"));
        feedbackLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 16));

        guessInput = new TextField();
        guessInput.setPromptText("Enter your guess...");
        guessInput.setMaxWidth(200);
        guessInput.setStyle("-fx-background-color: #121212; -fx-text-fill: white; -fx-border-color: #333;");

        guessButton = new Button("Guess");
        guessButton.setStyle("-fx-background-color: #00c853; -fx-text-fill: white; -fx-font-weight: bold;");

        playAgainButton = new Button("Play Again");
        playAgainButton.setStyle("-fx-background-color: #2979ff; -fx-text-fill: white; -fx-font-weight: bold;");
        playAgainButton.setDisable(true);

        leaderboardBox = new VBox(5);
        updateLeaderboard();

        guessButton.setOnAction(e -> checkGuess());
        playAgainButton.setOnAction(e -> {
            round++;
            newGame();
            feedbackLabel.setText("🎯 Round " + round + ": Guess a number between 1 and 100");
            guessButton.setDisable(false);
            playAgainButton.setDisable(true);
            guessInput.clear();
        });

        VBox gameBox = new VBox(15, feedbackLabel, guessInput, guessButton, playAgainButton, leaderboardBox);
        gameBox.setAlignment(Pos.CENTER);
        gameBox.setPadding(new Insets(25));
        gameBox.setStyle("-fx-background-color: #0e0e0e;");

        Scene scene = new Scene(gameBox, 400, 380);
        primaryStage.setTitle("🎮 Guess The Number");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void newGame() {
        numberToGuess = new Random().nextInt(100) + 1;
        attemptsLeft = maxAttempts;
    }

    private void checkGuess() {
        try {
            int guess = Integer.parseInt(guessInput.getText());
            attemptsLeft--;

            if (guess == numberToGuess) {
                showSuccessPopup();
                int score = attemptsLeft + 1;
                leaderboard.add(score);
                leaderboard.sort(Collections.reverseOrder());
                if (leaderboard.size() > 3) leaderboard = leaderboard.subList(0, 3);
                updateLeaderboard();
                guessButton.setDisable(true);
                playAgainButton.setDisable(true);
                playAgainButton.setDisable(false);
            } else if (attemptsLeft == 0) {
                feedbackLabel.setText("❌ Out of attempts! It was: " + numberToGuess);
                guessButton.setDisable(true);
                playAgainButton.setDisable(false);
            } else if (guess < numberToGuess) {
                feedbackLabel.setText("🔻 Too low! Attempts left: " + attemptsLeft);
            } else {
                feedbackLabel.setText("🔺 Too high! Attempts left: " + attemptsLeft);
            }
        } catch (NumberFormatException e) {
            feedbackLabel.setText("❌ Please enter a valid number!");
        }

        guessInput.clear();
    }

    private void showSuccessPopup() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("🎉 You Guessed It!");
        alert.setHeaderText(null);
        alert.setContentText("✅ Correct! You guessed the number.\nAttempts left: " + (attemptsLeft + 1));
        alert.showAndWait();
    }

    private void updateLeaderboard() {
        leaderboardBox.getChildren().clear();
        Label title = new Label("🏆 Top Scores This Session");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        title.setTextFill(Color.web("#eeeeee"));
        leaderboardBox.getChildren().add(title);

        if (leaderboard.isEmpty()) {
            Label empty = new Label("No scores yet.");
            empty.setTextFill(Color.GRAY);
            leaderboardBox.getChildren().add(empty);
        } else {
            for (int i = 0; i < leaderboard.size(); i++) {
                Label scoreLabel = new Label((i + 1) + ". Score: " + leaderboard.get(i));
                scoreLabel.setTextFill(Color.web("#b9f6ca")); // light neon green
                leaderboardBox.getChildren().add(scoreLabel);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
