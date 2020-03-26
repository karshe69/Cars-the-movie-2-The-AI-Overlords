import shapes.Point;

import java.awt.*;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;

public class SelfDrivingCar extends Car {
    private Sensor[] sensors = new Sensor[10];

    public SelfDrivingCar(double x, double y, int width, int height, double angle, BlockingQueue<DistRes> queue, HashSet<Point> hitMap) {
        super(x, y, width, height, angle);

        sensors[0] = new Sensor(0, 0, queue, hitMap);
        sensors[1] = new Sensor(Math.PI, 1, queue, hitMap);
        sensors[2] = new Sensor(Math.PI / 8, 2, queue, hitMap);
        sensors[3] = new Sensor(- Math.PI / 8, 3, queue, hitMap);
        sensors[4] = new Sensor(Math.PI / 5, 4, queue, hitMap);
        sensors[5] = new Sensor(- Math.PI / 5, 5, queue, hitMap);
        sensors[6] = new Sensor(Math.PI / 3, 6, queue, hitMap);
        sensors[7] = new Sensor(- Math.PI / 3, 7, queue, hitMap);
        sensors[8] = new Sensor(- Math.PI * 6 / 5, 8, queue, hitMap);
        sensors[9] = new Sensor(Math.PI * 6 / 5, 9, queue, hitMap);
        /*for(int i = 0; i < sensors.length; i ++)
            sensors[i].start();*/
        updatePointss();
    }

    public void drawSensors(Graphics2D g) {
        for (Sensor sensor : sensors) sensor.draw(g);
    }

    @Override
    public void  move() { // moves the car
        turn(); // turns the car
        x += speed.getX(); // moves it according to it's speed in the x axis
        y += speed.getY(); // moves it according to it's speed in the y axis
        updatePointss(); // updates the points to the new position
    }

    public void updatePointss() { // updates the positions of the points and the sensors
        super.updatePoints();
        double x1, y1, x2, y2;
        Point[] corners = new Point[4];
        for (int i = -1; i <= 1; i += 2){
            corners[i + 2] = new Point(x + i * (innerRadius * Math.cos(innerAngle + i * angle)), y + innerRadius * Math.sin(innerAngle + i * angle));
            corners[i + 1] = new Point(x - i * (innerRadius * Math.cos(innerAngle + i * angle)),y - innerRadius * Math.sin(innerAngle + i * angle));
        }
        x1 = corners[0].getX();
        y1 = corners[0].getY();
        x2 = corners[3].getX();
        y2 = corners[3].getY();
        sensors[0].update((x1 + x2) / 2, (y1 + y2) / 2, angle);
        sensors[2].update((x1 + x2 * 3) / 4, (y1 + y2 * 3) / 4, angle);
        sensors[3].update((x1 * 3 + x2) / 4, (y1 * 3 + y2) / 4, angle);

        x1 = corners[1].getX();
        y1 = corners[1].getY();
        x2 = corners[3].getX();
        y2 = corners[3].getY();
        sensors[4].update((x1 + x2 * 3) / 4, (y1 + y2 * 3) / 4, angle);
        sensors[6].update((x1 + x2) / 2, (y1 + y2) / 2, angle);
        sensors[8].update((x1 * 3 + x2) / 4, (y1 * 3 + y2) / 4, angle);

        x1 = corners[2].getX();
        y1 = corners[2].getY();
        x2 = corners[0].getX();
        y2 = corners[0].getY();
        sensors[5].update((x1 + x2 * 3) / 4, (y1 + y2 * 3) / 4, angle);
        sensors[7].update((x1 + x2) / 2, (y1 + y2) / 2, angle);
        sensors[9].update((x1 * 3 + x2) / 4, (y1 * 3 + y2) / 4, angle);

        x1 = corners[1].getX();
        y1 = corners[1].getY();
        x2 = corners[2].getX();
        y2 = corners[2].getY();
        sensors[1].update((x1 + x2) / 2, (y1 + y2) / 2, angle);
    }
}