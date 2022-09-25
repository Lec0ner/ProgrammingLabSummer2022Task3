package ui;

import core.Direction;
import core.Game;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.awt.Point;

import static java.lang.Math.*;

public class SnakeGame extends Application {
    // Настройки окна
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private GraphicsContext graphicsContext;
    // Размер одной ячейки
    private static final int squareSize = WIDTH / max(Game.ROWS, Game.COLUMNS);
    // Картинка еды
    private static final Image foodImage = new Image("food.png");
    // Для воспроизведения цикла игры
    private final Timeline timeLine = new Timeline(new KeyFrame(Duration.millis(250), e -> run()));
    // Сама змейка
    private Game game;
    // Направление текущее и новое
    private Direction directionNow;
    private Direction directionNew;

    @Override
    public void start(Stage primaryStage) {
        // Создаем окно игры
        primaryStage.setTitle("Snake");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        graphicsContext = canvas.getGraphicsContext2D();
        primaryStage.setResizable(false);
        // Создаем змейку
        game = new Game();
        directionNew = null;
        directionNow = null;
        // Устанавливаем настройки управления
        controlGame(scene, primaryStage);
        // Задаем параметры функции времени
        timeLine.setCycleCount(Animation.INDEFINITE);
        // Отрисовываем основные элементы
        drawGame();
    }

    private void run() {
        // Игра проиграна
        if (game.gameIsOver()) {
            timeLine.stop();
            graphicsContext.setFill(Color.web("ea3700"));
            graphicsContext.setFont(new Font("Arial", 70));
            graphicsContext.fillText("Game Over", 200, 400);
            return;
        }
        if (game.gameIsWin()){
            timeLine.stop();
            graphicsContext.setFill(Color.web("4DFF00"));
            graphicsContext.setFont(new Font("Arial", 70));
            graphicsContext.fillText("Game Win", 200, 400);
            return;
        }
        // Коррекция таймера относительно очков
        timeLine.setRate(pow(game.getScore(), 2) / 20000.0 + 1);
        // Движение змейки
        game.doIteration(directionNew);
        directionNow = directionNew;
        drawGame();
    }

    private void controlGame(Scene scene, Stage primaryStage) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if ((code == KeyCode.RIGHT || code == KeyCode.D) && directionNow != Direction.LEFT)
                directionNew = Direction.RIGHT;
            else if ((code == KeyCode.LEFT || code == KeyCode.A) && directionNow != Direction.RIGHT)
                directionNew = Direction.LEFT;
            else if ((code == KeyCode.UP || code == KeyCode.W) && directionNow != Direction.DOWN)
                directionNew = Direction.UP;
            else if ((code == KeyCode.DOWN || code == KeyCode.S) && directionNow != Direction.UP)
                directionNew = Direction.DOWN;
            else if (code == KeyCode.R)
                start(primaryStage);
            else if (code == KeyCode.ESCAPE)
                primaryStage.close();
            // Запускаем цикл игры
            timeLine.play();
        });
    }

    private void drawGame() {
        drawBackground();
        drawSnake();
        drawFood();
        drawScore();
    }

    private void drawBackground() {
        for (int i = 0; i < Game.ROWS; i++) {
            for (int j = 0; j < Game.COLUMNS; j++) {
                graphicsContext.setFill(Color.web((i + j) % 2 == 0 ? "6cc94b" : "61bf40"));
                graphicsContext.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
            }
        }
    }

    private void drawSnake() {
        graphicsContext.setFill(Color.web("8a7698"));
        for (Point body : game.getSnake()) {
            graphicsContext.fillRoundRect(body.getX() * squareSize, body.getY() * squareSize,
                    squareSize - 1, squareSize - 1, 40, 40);
        }
        graphicsContext.setFill(Color.web("8c40cf"));
        graphicsContext.fillRoundRect(game.getHead().getX() * squareSize, game.getHead().getY() * squareSize,
                squareSize - 1, squareSize - 1, 30, 30);
    }

    public void drawFood() {
        graphicsContext.drawImage(foodImage, game.getFoodCoordinate().x * squareSize,
                game.getFoodCoordinate().y * squareSize, squareSize, squareSize);
    }

    public void drawScore() {
        graphicsContext.setFill(Color.web("eaffbf"));
        graphicsContext.setFont(new Font("Arial", 40));
        graphicsContext.fillText("Score  " + game.getScore(), 5, 35);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
