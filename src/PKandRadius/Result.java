package PKandRadius;

public class Result {
    private BaseLine line;
    private SurveyPoint point;
    private double radius;
    private double dev;
    private double gorLenght;
    private double gorPiket;
    private double length;
    private double deltaH;
    private String result;

    public Result(BaseLine line,SurveyPoint point){
        this.line = line;
        this.point = point;
        radius = Count.Rad(line, point);
        dev= Count.Dev(line, point);
        gorLenght = Count.gorLength(line, point);
        gorPiket = Count.GorPK(line,point);
        length = Count.Length(line, point);
        deltaH = Count.DeltaH(line,point);
        result = baseLineResult();

    }

    public double getRadius() {
        return radius;
    }

    public double getDev() {
        return dev;
    }

    public double getGorLenght() {
        return gorLenght;
    }

    public double getGorPiket() {
        return gorPiket;
    }

    public double getLength() {
        return length;
    }

    public double getDeltaH() {
        return deltaH;
    }

    public String baseLineResult(){
       String result = point.toString() + "\t" + getRadius() + "\t" + getDev() + "\t" + getGorLenght() +  "\t" + getGorPiket() + "\t" + getLength() + "\t" + getDeltaH();
       return result;
    }

    @Override
    public String toString() {
        return result;
    }

    public static class Count{
        private static double x1;
        private static double y1;
        private static double h1;
        private static double pk1;
        private static double x2;
        private static double y2;
        private static double h2;
        private static double pk2;
        private static double x;
        private static double y;
        private static double h;
        private static final int kv=2;

        public static void coords(BaseLine LN,SurveyPoint P){
            x1=LN.getFp().getX();
            y1=LN.getFp().getY();
            h1=LN.getFp().getH();
            pk1=LN.getFp().getPK();
            x1=LN.getSp().getX();
            y1=LN.getSp().getY();
            h1=LN.getSp().getH();
            pk2=LN.getSp().getPK();
        }

        public static double Rad(BaseLine LN,SurveyPoint P) {
            coords(LN,P);
            return Math.sqrt(Math.pow(((y1 - y) * (h2 - h1) - (h1 - h) * (y2 - y1)), kv)
                    + Math.pow(((x1 - x) * (h2 - h1) - (h1 - h) * (x2 - x1)), kv)
                    + Math.pow(((x1 - x) * (y2 - y1) - (y1 - y) * (x2 - x1)), kv))
                    / Math.sqrt(Math.pow((x2 - x1), kv) + Math.pow((y2 - y1), kv) + Math.pow((h2 - h1), kv));
        }

        public static double Dev(BaseLine LN,SurveyPoint P) {
            coords(LN,P);
            return ((y2 - y1) * x - (x2 - x1) * y + x2 * y1 - y2 * x1) / Math.sqrt(Math.pow(y2 - y1, kv) + Math.pow(x2 - x1, kv));
        }

        public static double gorLength(BaseLine LN,SurveyPoint P) {
            coords(LN,P);
            return Math.sqrt(Math.pow(x - x1, kv) + Math.pow(y - y1, kv) - Math.pow(Dev(LN,P), kv));
        }

        public static double GorPK(BaseLine LN,SurveyPoint P) {
            coords(LN,P);
            if (pk1 == 0) {
                return gorLength(LN,P);
            } else {
                if (pk2 == 0) {
                    return pk1 + gorLength(LN,P);
                } else {
                    if (pk2 > pk1) {
                        return pk1 + gorLength(LN,P);
                    } else if (pk2 == pk1) {
                        return 0;
                    } else {
                        return pk1 - gorLength(LN,P);
                    }
                }
            }
        }
        public static double Length(BaseLine LN,SurveyPoint P){
            coords(LN,P);
            return Math.sqrt(Math.pow(x-x1,kv)+Math.pow(y-y1,kv)+Math.pow(h-h1,kv)-Math.pow(Rad(LN,P),kv));
        }
        public static double PK(BaseLine LN,SurveyPoint P){
            coords(LN,P);
            if (pk1 == 0) {
                return Length(LN,P);
            } else {
                if (pk2 == 0) {
                    return pk1 + Length(LN,P);
                } else {
                    if (pk2 > pk1) {
                        return pk1 + Length(LN,P);
                    } else if (pk2 == pk1) {
                        return 0;
                    } else {
                        return pk1 - Length(LN,P);
                    }
                }
            }
        }
        public static double DeltaH(BaseLine LN,SurveyPoint P){
            coords(LN,P);
            return  h -(h1 + (h2-h1)*Math.sqrt(Math.pow(x-x1,kv)+Math.pow(y-y1,kv)-Math.pow(Dev(LN,P),kv))/Math.sqrt(Math.pow(x-x1,kv)+Math.pow(y-y1,kv)));
        }

    }
}
