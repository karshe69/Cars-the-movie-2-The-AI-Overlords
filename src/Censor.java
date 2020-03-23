public class Censor extends Point{
    private double angle;

    public Censor(){
        angle = 0;
    }

    public Censor(double x, double y, double angle){
        super(x, y);
        this.angle = angle;
    }

    public void pUpdate(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void fUpdate(double x, double y, double angle){
        this.x = x;
        this.y = y;
        this.angle = angle;
    }
}
