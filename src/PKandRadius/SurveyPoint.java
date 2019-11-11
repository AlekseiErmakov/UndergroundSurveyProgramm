package PKandRadius;

public class SurveyPoint extends Points {
    public SurveyPoint(double X,double Y,double H) {
        this.X = X;
        this.Y = Y;
        this.H = H;
        Name =DefaultPName;


    }
    public SurveyPoint(String Name, double X, double Y, double H){
        this.X = X;
        this.Y = Y;
        this.H = H;
        this.Name = Name;
    }
    public SurveyPoint(String Name, double X, double Y, double H, double PK){
        this.X = X;
        this.Y = Y;
        this.H = H;
        this.Name = Name;
        this.PK=PK;
    }
}
