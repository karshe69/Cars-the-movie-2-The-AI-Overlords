import shapes.Point;

import java.util.concurrent.BlockingQueue;

public class SelfDrivingCar extends Car {
    private Sensor[] sensors = new Sensor[9];

    public SelfDrivingCar(double x, double y, int width, int height, double angle, BlockingQueue<DistRes> queue) {
        super(x, y, width, height, angle);

        sensors[0] = new Sensor(0, 0, queue);
        sensors[1] = new Sensor(0, 1, queue);
        sensors[2] = new Sensor(0, 2, queue);
        sensors[3] = new Sensor(0, 3, queue);
        sensors[4] = new Sensor(0, 4, queue);
        sensors[5] = new Sensor(0, 5, queue);
        sensors[6] = new Sensor(0, 6, queue);
        sensors[7] = new Sensor(0, 7, queue);
        sensors[8] = new Sensor(0, 8, queue);
        updatePointss();
    }


    public void updatePointss() {
        super.updatePoints();
        double x1, y1, x2, y2;
        Point[] corners = new Point[4];
        for (int i = -1; i <= 1; i += 2){
            corners[i + 2] = new Point(x + i * (innerRadius * Math.cos(innerAngle + i * angle)), y + innerRadius * Math.sin(innerAngle + i * angle));
            corners[i + 1] = new Point(x - i * (innerRadius * Math.cos(innerAngle + i * angle)),y - innerRadius * Math.sin(innerAngle + i * angle));
        }
        x1 = corners[1].getX();
        y1 = corners[1].getY();
        x2 = corners[2].getX();
        y2 = corners[2].getY();
        sensors[0].update(x1, (y1 + y2) / 2, angle);
    }
}