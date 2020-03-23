public class SelfDrivingCar extends Car {
    private Censor[] censors = new Censor[9];
    public SelfDrivingCar(double x, double y, int width, int height, double angle) {
        super(x, y, width, height, angle);
    }
}
