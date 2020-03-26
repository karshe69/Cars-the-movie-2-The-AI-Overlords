import shapes.Point;

import java.awt.*;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;

public class Sensor extends Thread{
    private double carAngle;
    private double relativeAngle;
    private boolean changeflag = false;
    private boolean runflag = true;
    private HashSet<Point> hitMap;
    private int sign;
    private Point origin;
    private BlockingQueue<DistRes> queue;
    private Point pnt = new Point(0,0);
    private Color senseColor = new Color(255, 150, 255);
    private Color hitColor = new Color(190, 0, 10);

    public Sensor(double relAng, int sign, BlockingQueue<DistRes> queue, HashSet<Point> hitMap){
        relativeAngle = relAng;
        this.sign = sign;
        this.queue = queue;
        origin = new Point();
        this.hitMap = hitMap;
    }

    public synchronized void update(double x, double y, double carAng){
        origin.update(x, y);
        carAngle = carAng;
        changeflag = true;
        sense();
    }

    public void setRunflag(boolean runflag) {
        this.runflag = runflag;
    }

    public void draw(Graphics2D g){
        double x = pnt.getX() - origin.getX();
        double y = pnt.getY() - origin.getY();
        g.setColor(senseColor);
        g.drawLine((int)origin.getX(), (int)origin.getY(), (int)origin.getX() + (int)x*2, (int)origin.getY() + (int)y*2);
        g.setColor(hitColor);
        g.fillOval((int)pnt.getX() - 5, (int)pnt.getY() - 5, 10, 10);
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
        double x = Math.cos(carAngle + relativeAngle), y = Math.sin(carAngle + relativeAngle);
        int iter = 20;
        int i = iter;
        while (!hitMap.contains(new Point((int)Math.abs(x * i + origin.getX()), (int)Math.abs(y * i + origin.getY())))){
            i += iter;
        }
        for (; iter >= 0; iter--)
            if(hitMap.contains(new Point((int)Math.abs(x * (i - iter) + origin.getX()), (int)Math.abs(y * (i - iter) + origin.getY()))))
                break;
        pnt.update(x * (i - iter) + origin.getX(), y * (i - iter) + origin.getY());
        return new DistRes(Math.sqrt(x*x + y*y) * (i - iter), sign);
    }
}