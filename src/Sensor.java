import shapes.Point;
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
}