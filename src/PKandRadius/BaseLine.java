package PKandRadius;

public class BaseLine implements StringRes{
    private BasePoint Fpoint;
    private BasePoint Spoint;
    public BaseLine(BasePoint Fpoint, BasePoint Spoint){
        this.Fpoint=Fpoint;
        this.Spoint=Spoint;
    }

    public BasePoint getFp() {
        return Fpoint;
    }

    public BasePoint getSp() {
        return Spoint;
    }
}
