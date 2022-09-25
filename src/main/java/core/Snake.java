package core;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Snake {
    private final List<Point> snake = new LinkedList<>();
    private Direction direction;

    public Snake() {
        for (int i = 0; i < 3; i++) {
            snake.add(new Point(Game.COLUMNS / 2 + i, Game.ROWS / 2));
        }
        direction = Direction.LEFT;
    }

    // Передвижение змейки
    public void move() {
        Point head = (Point) snake.get(0).clone();
        switch (direction) {
            case RIGHT:
                head.x++;
                break;
            case LEFT:
                head.x--;
                break;
            case UP:
                head.y--;
                break;
            case DOWN:
                head.y++;
                break;
        }
        snake.add(0, head);
        snake.remove(snake.size() - 1);
    }

    public void eatFood() {
        snake.add(snake.size() - 1, snake.get(snake.size() - 1));
    }

    public boolean checkGameOver() {
        Point head = snake.get(0);
        // Проверить удары об стены
        if (head.x < 0 || head.y < 0 || head.x >= Game.COLUMNS || head.y >= Game.ROWS) return true;
        // Проверка соприкосновения головы с телом
        Iterator<Point> iteratorSnake = snake.iterator();
        iteratorSnake.next();
        for (Point snakeBody = iteratorSnake.next(); iteratorSnake.hasNext(); snakeBody = iteratorSnake.next())
            if (snakeBody.equals(head)) return true;
        return false;
    }

    public void changeDirection(Direction direction) {
        if (direction == null) return;
        this.direction = direction;
    }

    public List<Point> getSnake() {
        return snake;
    }

    public Point getHead() {
        return snake.get(0);
    }
}
