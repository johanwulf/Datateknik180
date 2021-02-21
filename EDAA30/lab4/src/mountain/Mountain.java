package mountain;

import java.util.HashMap;
import java.util.Map;

import fractal.Fractal;
import fractal.TurtleGraphics;

public class Mountain extends Fractal {
    private int length;
    private Map<Side, Point> triangelMap = new HashMap<Side, Point>();

    public Mountain(int length) {
        super();
        this.length = length;
    }

    @Override
    public String getTitle() {
        return "Bergsmassiv";
    }

    @Override
    public void draw(TurtleGraphics turtle) {
        turtle.moveTo(turtle.getWidth() / 2.0 - length / 2.0, turtle.getHeight() / 2.0 + Math.sqrt(3.0) * length / 4.0);
        Point a = new Point((int) turtle.getX(), (int) turtle.getY());
        Point b = new Point(295, 222);
        Point c = new Point(445, 480);
        fractalMountain(turtle, order, length, a, b, c, 30);
    }

    public void fractalMountain(TurtleGraphics turtle, int order, double length, Point a, Point b, Point c, double dev) {
        if (order == 0) {
            turtle.moveTo(a.getX(), a.getY());
            turtle.forwardTo(c.getX(), c.getY());
            turtle.forwardTo(b.getX(), b.getY());
            turtle.forwardTo(a.getX(), a.getY());
        } else {
            int yShift = (int) RandomUtilities.randFunc(dev);

            Point AB = createPoint(a, b, yShift);
            Point AC = createPoint(a, c, yShift);
            Point BC = createPoint(b, c, yShift);

            fractalMountain(turtle, order-1, length/2, AB, AC, BC, dev/2);
            fractalMountain(turtle, order-1, length/2, AB, BC, b, dev/2);
            fractalMountain(turtle, order-1, length/2, AB, a, AC, dev/2);
            fractalMountain(turtle, order-1, length/2, AC, c, BC, dev/2);
        }
    }

    private Point createPoint(Point a, Point b, int yShift) {
        Side tempSide = new Side(a, b);

        if(triangelMap.containsKey(tempSide)) {
			Point excistingPoint = triangelMap.get(tempSide);
			triangelMap.remove(tempSide);
			return excistingPoint;
		} else {
            int x = ((a.getX() + b.getX())/2);
            int y = ((a.getY()+b.getY())/2) + yShift;

            Point newPoint = new Point(x,y);
            triangelMap.put(tempSide, newPoint);
            return newPoint;
        } 
    }
}
