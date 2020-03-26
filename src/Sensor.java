import shapes.Point;

import java.awt.*;
import java.util.concurrent.BlockingQueue;

public class Sensor extends Thread{
    private double carAngle;
    private double relativeAngle;
    private boolean changeflag = false;
    private boolean runflag = true;
    private int sign;
    private Point origin;
    private BlockingQueue<DistRes> queue;

    public Sensor(double relAng, int sign, BlockingQueue<DistRes> queue){
        relativeAngle = relAng;
        this.sign = sign;
        this.queue = queue;
        origin = new Point();
    }

    public synchronized void update(double x, double y, double carAng){
        origin.update(x, y);
        carAngle = carAng;
        changeflag = true;
    }

    public void setRunflag(boolean runflag) {
        this.runflag = runflag;
    }

    public void draw(Graphics2D g){
        double x = Math.cos(carAngle + relativeAngle) * 1920 + origin.getX();
        double y = Math.sin(carAngle + relativeAngle) * 1920 + origin.getY();
        g.drawLine((int)origin.getX(), (int)origin.getY(), (int)x, (int)y);
    }

    public void run(){
        while (runflag){
            while (changeflag){
                try {
                    queue.put(sense());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                changeflag = false;
            }
        }
    }

    public DistRes sense(){
        return new DistRes(0, sign);
    }

    public double getCarAngle() {
        return carAngle;
    }

    public double getRelativeAngle() {
        return relativeAngle;
    }

    public Point getOrigin() {
        return origin;
    }
}