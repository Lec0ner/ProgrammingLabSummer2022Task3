package core;

import java.awt.*;
import java.util.List;

public class Game {

    public static int SCORE_PER_FOOD = 10;
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
            score += SCORE_PER_FOOD;
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

    public boolean gameIsWin() {
        return snake.getSize() > COLUMNS * ROWS - 1;
    }


    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;

        Game game = (Game) o;

        if (score != game.score) return false;
        if (!snake.equals(game.snake)) return false;
        return food.equals(game.food);
    }

    @Override
    public int hashCode() {
        int result = snake.hashCode();
        result = 31 * result + food.hashCode();
        result = 31 * result + score;
        return result;
    }

    @Override
    public String toString() {
        return "Game{" +
                "snake=" + snake +
                ", food=" + food +
                ", score=" + score +
                '}';
    }
}
