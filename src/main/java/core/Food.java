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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Food)) return false;

        Food food = (Food) o;

        return Objects.equals(coordinate, food.coordinate);
    }

    @Override
    public int hashCode() {
        return coordinate != null ? coordinate.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Food{" +
                "coordinate=" + coordinate +
                '}';
    }
}
