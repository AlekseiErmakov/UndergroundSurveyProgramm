class Count{
    private double R,a,b,c,d,e,f,g,akv,bkv,ckv,ekv,fkv,gkv,x1,y1,z1,PK,x2,y2,z2,x,y,z;

    int kv;
    public Count(){
        x1 = 3621.026;
        y1 = 961.205;
        z1 = 33.170;
        x2 = 3633.735;
        y2 = 973.952;
        z2 = 33.170;
        x = 3624.487;
        y = 967.205;
        z = 35.060;
        setPoint();
    }
    public void setPoint(){
        double a = x2 - x1;
        double b = y2 - y1;
        double c = z2 - z1;

        double e = x1 - x;
        double f = y1 - y;
        double g = z1 - z;
        double h = f*c - g*b;
        double i = e*c - g*a;
        double j = e*b - f*a;
        double kv = 2;
        double hkv = Math.pow(h,kv);
        double ikv = Math.pow(i,kv);
        double jkv = Math.pow(j,kv);
        double k = Math.sqrt(hkv + ikv + jkv);
        double akv = Math.pow(a,kv);
        double bkv = Math.pow(b,kv);
        double ckv = Math.pow(c,kv);
        double l = Math.sqrt(akv + bkv + ckv);
        R = k/l;
        System.out.println(R);

    }

    public double getRad(){
        return R;
    }

    public static void main(String[] args){
        new Count();
        System.out.println(new Count().getRad());
    }
}