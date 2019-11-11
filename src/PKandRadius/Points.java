package PKandRadius;

public abstract class Points implements StringRes{
    double X, Y, H, PK;
    String Name;



    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getH() {
        return H;
    }

    public double getPK() {
        return PK;
    }

    public String getName() {
        return Name;
    }

    @Override
    public String toString() {
        return Name + "\t" + X + "\t" + Y + "\t" + H;
    }
}
