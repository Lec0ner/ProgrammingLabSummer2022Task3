package core;

import java.awt.*;
import java.util.List;

public class Game {
    // Размеры поля
    public static int COLUMNS = 20;
    public static int ROWS = 20;
    // Змея
    private final Snake snake = new Snake();
    // Еда
    private final Food food = new Food();
    private int score = 0;

    public Game() {
        food.generateNewFood(snake.getSnake());
    }

    public void doIteration(Direction direction) {
        // Сменили направление движения
        snake.changeDirection(direction);
        // Произвели движение
        snake.move();
        // Проверили на наличие еды
        if (snake.getHead().equals(food.getCoordinate())) {
            // Поели
            snake.eatFood();
            // Создали еду
            food.generateNewFood(snake.getSnake());
            score += 10;
        }
    }

    public List<Point> getSnake() {
        return snake.getSnake();
    }

    public Point getHead() {
        return snake.getHead();
    }

    public Point getFoodCoordinate() {
        return food.getCoordinate();
    }

    public boolean gameIsOver() {
        return snake.checkGameOver();
    }

    public int getScore() {
        return score;
    }
}
