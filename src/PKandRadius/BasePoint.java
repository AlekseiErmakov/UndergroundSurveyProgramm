package PKandRadius;

public class BasePoint extends Points {

    public BasePoint(double X,double Y,double H) {
        this.X = X;
        this.Y = Y;
        this.H = H;
        Name =DefaultPName;


    }
    public BasePoint(String Name, double X, double Y, double H){
        this.X = X;
        this.Y = Y;
        this.H = H;
        this.Name = Name;
    }
    public BasePoint( double X, double Y, double H, double PK){
        this.X = X;
        this.Y = Y;
        this.H = H;
        this.Name = Name;
        this.PK=PK;
    }
}
