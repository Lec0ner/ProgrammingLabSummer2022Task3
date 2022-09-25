package core;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class Food {
    private Point coordinate;

    public void generateNewFood(List<Point> snake) {
        boolean fruitInSnake;
        do {
            coordinate = new Point((int) (Math.random() * Game.ROWS), (int) (Math.random() * Game.COLUMNS));
            fruitInSnake = false;
            for (Point snakeElement : snake) {
                if (snakeElement.equals(coordinate)) {
                    fruitInSnake = true;
                    break;
                }
            }
        } while (fruitInSnake);
    }

    public Point getCoordinate() {
        return coordinate;
    }
}
