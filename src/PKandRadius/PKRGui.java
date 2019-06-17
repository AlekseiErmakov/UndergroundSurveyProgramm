package PKandRadius;

import jdk.jshell.tool.JavaShellToolBuilder;

import javax.lang.model.element.Name;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.awt.FileDialog;

public class PKRGui<Shell> extends JFrame {
    int d = 12;
    int rows = 100;
    int c = 7;
    int b = 4;
    int lng = 40;
    int Tho = 1000;
    double X1, Y1, H1, PK1, X2, Y2, H2, PK2, X, Y, H, GorPK, PK, Rad, Dev, DeltaH,Prok;

    JPanel DataBaselines = new JPanel();

    JPanel MainPanel = new JPanel();
    JPanel PointBaseLabel = new JPanel();
    JPanel PointOneCoord = new JPanel();
    JPanel PointTwoCoord = new JPanel();


    String[] Labels = {"      ", "Х", "Y", "Н", "ПК"};

    JTextField labelPoint1 = new JTextField("Точка 1");
    JTextField labelPoint2 = new JTextField("Точка 2");

    JTextField X1text = new JTextField(d);
    JTextField X2text = new JTextField(d);
    JTextField Y1text = new JTextField(d);
    JTextField Y2text = new JTextField(d);
    JTextField H1text = new JTextField(d);
    JTextField H2text = new JTextField(d);
    JTextField PK1text = new JTextField(d);
    JTextField PK2text = new JTextField(d);


    JMenuItem Import = new JMenuItem("Импорт");
    JMenuItem Save = new JMenuItem("Сохранить");
    JMenuItem SaveBL = new JMenuItem("Сохранить БЛ");
    JMenuItem ImportBL = new JMenuItem("Импортировать БЛ");


    JMenu fileMenu = new JMenu("Файл");
    JMenu ViewMenu = new JMenu("Вид");

    JMenuItem View = new JMenuItem("Прямой");
    JMenuItem View2 = new JMenuItem("Косой");

    JButton Count = new JButton("Расчет");
    JButton CountProk = new JButton("Расчет прокладок");
    JButton SaveBut = new JButton("Сохранить");
    JButton Clear = new JButton("Очистить");


    JTextField[] textFields = {new JTextField("Номер", b), new JTextField("Название", b), new JTextField("X", b), new JTextField("Y", b), new JTextField("H", b),
            new JTextField("Горизонтальный ПК", c), new JTextField("Наклонный ПК", c), new JTextField("Радиус", c), new JTextField("Смещение", c), new JTextField("Превышение от Б.Л.", c), new JTextField("Прокладки", c)};


    JTextField[] NumberTextField = new JTextField[lng];
    JTextField[] NameTextField = new JTextField[lng];
    JTextField[] XTFields = new JTextField[lng];
    JTextField[] YTFields = new JTextField[lng];
    JTextField[] HTFields = new JTextField[lng];
    JTextField[] GORPktTFields = new JTextField[lng];
    JTextField[] PKTFields = new JTextField[lng];
    JTextField[] RadTFields = new JTextField[lng];
    JTextField[] DevTFields = new JTextField[lng];
    JTextField[] DeltaHFields = new JTextField[lng];
    JTextField[] ProkladesField = new JTextField[lng];


    double[] PKs = new double[lng];
    double min = PKs[0];

    int BNlang = 20;
    String[] BaseNames = new String[20];
    JComboBox<String> Bases = new JComboBox<>(BaseNames);


    String strNum;
    String strName;
    String strX;
    String strY;
    String strH;
    String strGorPK;
    String strPK;
    String strRad;
    String strDev;
    String strDeltaH;
    String strProklade;

    ArrayList<String[]> DataCoordin = new ArrayList<>();
    ArrayList<String[]> BaseLine = new ArrayList<>();

    ArrayList<String> NumArray = new ArrayList<>();
    ArrayList<String> NameArray = new ArrayList<>();
    ArrayList<String> Xarray = new ArrayList<>();
    ArrayList<String> Yarray = new ArrayList<>();
    ArrayList<String> Harray = new ArrayList<>();
    ArrayList<String> GorPKarray = new ArrayList<>();
    ArrayList<String> PKarray = new ArrayList<>();
    ArrayList<String> RadArray = new ArrayList<>();
    ArrayList<String> DevArray = new ArrayList<>();
    ArrayList<String> DeltaHArray = new ArrayList<>();
    ArrayList<String> ProklArray = new ArrayList<>();

    ArrayList<String[]> Results = new ArrayList<>();

    ArrayList<Double> PKForProk= new ArrayList<>();

    double minimalPK;

    JFileChooser jFileChooser = null;

    String[][] FileFilters ={{"Файлы Word(*.doc)","*.docx"},
            {"Файлы WordPad(*.txt)","*.txt"},
            {"Файлы Sokkia(*.sdr)","*.sdr"},
            {"Файлы Word(*.*)","*."}};




    public PKRGui() {
        super("Расчет пикетов радиусов и прокладок");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);


        JMenuBar jMenuBar = new JMenuBar();
        fileMenu.add(Import);
        Import.addActionListener(new ImportCoordAction());
        fileMenu.addSeparator();
        fileMenu.add(Save);
        fileMenu.addSeparator();
        fileMenu.add(ImportBL);
        ImportBL.addActionListener(new ImportBaseLineAction());
        fileMenu.addSeparator();
        fileMenu.add(SaveBL);
        SaveBL.addActionListener(new SaveBaselineAction());
        ViewMenu.add(View);
        ViewMenu.add(View2);

        jMenuBar.add(fileMenu);
        jMenuBar.add(ViewMenu);

        setJMenuBar(jMenuBar);

        setLayout(new BorderLayout());
        add(jMenuBar, BorderLayout.NORTH);


        addPanel();
        setSize(1920, 1080);


    }

    class ImportBaseLineAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            JFileChooser fileopen = new JFileChooser();
            int ret = fileopen.showDialog(null, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File MyBasefile = fileopen.getSelectedFile();
                try {



                    FileReader filereader = new FileReader(MyBasefile);
                    BufferedReader reader = new BufferedReader(filereader);
                    String line = null;
                    X1text.setText("");
                    X2text.setText("");
                    Y1text.setText("");
                    Y2text.setText("");
                    H1text.setText("");
                    H2text.setText("");
                    PK1text.setText("");
                    PK2text.setText("");
                    BaseLine.clear();
                    while ((line = reader.readLine()) != null) {
                        String[] base = line.split("\t");
                        BaseLine.add(base);

                    }
                    X1text.setText(BaseLine.get(0)[0]);
                    X2text.setText(BaseLine.get(0)[1]);
                    Y1text.setText(BaseLine.get(1)[0]);
                    Y2text.setText(BaseLine.get(1)[1]);
                    H1text.setText(BaseLine.get(2)[0]);
                    H2text.setText(BaseLine.get(2)[1]);
                    PK1text.setText(BaseLine.get(3)[0]);
                    PK2text.setText(BaseLine.get(3)[1]);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    class ImportCoordAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {



            JFileChooser fileopen = new JFileChooser();
            int ret = fileopen.showDialog(null, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File Myfile = fileopen.getSelectedFile();


                try {


                    FileReader filereader = new FileReader(Myfile);
                    BufferedReader reader = new BufferedReader(filereader);
                    String line = null;
                    DataCoordin.clear();
                    for (int i = 0; i < 40; i++) {

                        NameTextField[i].setText("");
                        XTFields[i].setText("");
                        YTFields[i].setText("");
                        HTFields[i].setText("");

                    }
                    while ((line = reader.readLine()) != null) {
                        String[] point = line.split("\t");
                        DataCoordin.add(point);

                    }

                    reader.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            for (int i = 0;i<DataCoordin.size(); i++){
                System.out.println(DataCoordin.get(i)[2]);
            }

            for (int i = 0; i < DataCoordin.size(); i++) {

                NameTextField[i].setText(DataCoordin.get(i)[0]);
                XTFields[i].setText(DataCoordin.get(i)[1]);
                YTFields[i].setText(DataCoordin.get(i)[2]);
                HTFields[i].setText(DataCoordin.get(i)[3]);

            }
        }
    }
    public void addPanel() {
        MainPanel.setLayout(new BorderLayout());
        add(MainPanel);
        MainPanel.add(new PanelTop(), BorderLayout.NORTH);
        MainPanel.add(new PanelBottom(), BorderLayout.SOUTH);
        MainPanel.add(new PanelLeft(), BorderLayout.WEST);
        MainPanel.add(new PanelCenter(), BorderLayout.CENTER);
        MainPanel.add(new PanelEast(), BorderLayout.EAST);
    }

    class PanelTop extends JPanel {
        public PanelTop() {


        }
    }
    class SaveBaselineAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            JFileChooser fileopen = new JFileChooser();
            int ret = fileopen.showSaveDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File Myfile = fileopen.getSelectedFile();

                BaseLine.clear();

                String point1[] = {X1text.getText(),X2text.getText()};
                BaseLine.add(0,point1);
                String point2[] = {Y1text.getText(),Y2text.getText()};
                BaseLine.add(1,point2);
                String point3[] = {H1text.getText(),H2text.getText()};
                BaseLine.add(2,point3);
                String point4[] = {PK1text.getText(),PK2text.getText()};
                BaseLine.add(3,point4);


                int i;
                int j;
                final String LS = System.lineSeparator();
                try {
                    FileWriter fileWriter = new FileWriter(Myfile);
                    for (i = 0; i < BaseLine.size(); i++) {


                        for (j = 0; j < 2; j++) {

                            fileWriter.write(BaseLine.get(i)[j] + "\t");
                        }
                        fileWriter.write(LS);
                    }
                    fileWriter.flush();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    class PanelCenter extends JPanel {


        JPanel Xpanel = new JPanel();
        JPanel Ypanel = new JPanel();
        JPanel Hpanel = new JPanel();
        JPanel GorPKpanel = new JPanel();
        JPanel PKpanel = new JPanel();
        JPanel Radpanel = new JPanel();
        JPanel Devpanel = new JPanel();
        JPanel DeltaHPanel = new JPanel();
        JPanel NumberPanel = new JPanel();
        JPanel NamePanel = new JPanel();
        JPanel ProkladesPanel = new JPanel();

        JPanel[] panels = {NumberPanel, NamePanel, Xpanel, Ypanel, Hpanel, GorPKpanel, PKpanel, Radpanel, Devpanel, DeltaHPanel, ProkladesPanel};
        JTextField[] textFields = {new JTextField("Номер", b), new JTextField("Название", b), new JTextField("X", b), new JTextField("Y", b), new JTextField("H", b),
                new JTextField("Горизонтальный ПК", c), new JTextField("Наклонный ПК", c), new JTextField("Радиус", c), new JTextField("Смещение", c), new JTextField("Превышение от Б.Л.", c), new JTextField("Прокладки", c)};


        public PanelCenter() {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

            for (int i = 0; i < panels.length; i++) {

                add(panels[i]);
                panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.Y_AXIS));
                textFields[i].setEditable(false);
                textFields[i].setHorizontalAlignment(JTextField.CENTER);
                panels[i].add(textFields[i]);


            }
            for (int j = 0; j < XTFields.length; j++) {
                NumberTextField[j] = new JTextField();
                NumberTextField[j].setHorizontalAlignment(JTextField.CENTER);
                NameTextField[j] = new JTextField();
                NameTextField[j].setHorizontalAlignment(JTextField.CENTER);
                NumberTextField[j].setEditable(false);
                NumberTextField[j].setText(String.format("№ %d", j + 1));

                XTFields[j] = new JTextField();
                XTFields[j].setHorizontalAlignment(JTextField.CENTER);
                YTFields[j] = new JTextField();
                YTFields[j].setHorizontalAlignment(JTextField.CENTER);
                HTFields[j] = new JTextField();
                HTFields[j].setHorizontalAlignment(JTextField.CENTER);
                GORPktTFields[j] = new JTextField();
                GORPktTFields[j].setHorizontalAlignment(JTextField.CENTER);
                PKTFields[j] = new JTextField();
                PKTFields[j].setHorizontalAlignment(JTextField.CENTER);
                RadTFields[j] = new JTextField();
                RadTFields[j].setHorizontalAlignment(JTextField.CENTER);
                DevTFields[j] = new JTextField();
                DevTFields[j].setHorizontalAlignment(JTextField.CENTER);
                DeltaHFields[j] = new JTextField();
                DeltaHFields[j].setHorizontalAlignment(JTextField.CENTER);
                ProkladesField[j] = new JTextField();
                ProkladesField[j].setHorizontalAlignment(JTextField.CENTER);

                panels[0].add(NumberTextField[j]);
                panels[1].add(NameTextField[j]);
                panels[2].add(XTFields[j]);
                panels[3].add(YTFields[j]);
                panels[4].add(HTFields[j]);
                panels[5].add(GORPktTFields[j]);
                panels[6].add(PKTFields[j]);
                panels[7].add(RadTFields[j]);
                panels[8].add(DevTFields[j]);
                panels[9].add(DeltaHFields[j]);
                panels[10].add(ProkladesField[j]);
            }
        }
    }


    class PanelEast extends JPanel {
        public PanelEast() {
            JTextField field = new JTextField("                                      " +
                    "         Поперечный разрез", 40);
            add(field);
            field.setEditable(false);
            field.setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);
            g.setColor(Color.BLACK);
            int d1 = 250;
            int d2 = 246;
            int d3 = 20;
            int c1 = 200;
            int c2 = 230;
            int q = 50;
            int mas;
            g.fillOval(c1 - d1 / 2, c2 - d1 / 2, d1, d1);
            g.setColor(Color.WHITE);
            g.fillOval(c1 - d2 / 2, c2 - d2 / 2, d2, d2);
            g.setColor(Color.BLACK);
            g.drawLine(c1, d + c2 - d1 / 2 - q, c1, q + c2 + d1 / 2);
            g.drawLine(c1 - d1 / 2 - q, c2, q + c1 + d1 / 2, c2);
            g.drawLine(c1 - d1 / 2, c2 - d1 / 2, c1 + d1 / 2, c2 + d1 / 2);
            g.drawLine(c1 - d1 / 2, c2 + d1 / 2, c1 + d1 / 2, c2 - d1 / 2);


        }
    }

    class PanelBottom extends JPanel {
        JPanel grid = new JPanel();
        JPanel flow = new JPanel();

        public PanelBottom() {
            add(grid);
            grid.setLayout(new GridLayout(1, 2, 20, 15));
            grid.add(flow);
            flow.setLayout(new FlowLayout(FlowLayout.RIGHT));
            flow.add(Count);
            flow.add(CountProk);
            flow.add(SaveBut);
            flow.add(Clear);
            Count.addActionListener(new CountAction());
            CountProk.addActionListener(new CountProkAction());
            SaveBut.addActionListener(new SaveAction());
            Clear.addActionListener(new ClearAction());
        }
        class CountProkAction implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                minimalPK=PKForProk.get(1);
                for (int i=0; i<PKForProk.size(); i++){
                    if (PKForProk.get(i) < minimalPK){
                        minimalPK = PKForProk.get(i);
                    }
                }
                System.out.println(minimalPK);
                for (int i = 0; i<PKForProk.size(); i++ ){
                    System.out.println(PKForProk.get(i));
                    System.out.println(minimalPK);
                    Prok = PKForProk.get(i)-minimalPK;


                    ProkladesField[i].setText(String.format("%.3f ",Prok));
                    strProklade = String.format("%.3f ", Prok);
                    ProklArray.add(strProklade);
                }
                System.out.println(ProklArray);
            }
        }

        class ClearAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < XTFields.length; i++) {
                    XTFields[i].setText("");
                    YTFields[i].setText("");
                    HTFields[i].setText("");
                    GORPktTFields[i].setText("");
                    PKTFields[i].setText("");
                    DevTFields[i].setText("");
                    DeltaHFields[i].setText("");
                    ProkladesField[i].setText("");
                    RadTFields[i].setText("");
                    NameTextField[i].setText("");
                }
            }
        }

        class SaveAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {


                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showSaveDialog(null);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File Myfile = fileopen.getSelectedFile();

                    for (int i = 0; i < RadArray.size(); i++) {

                        String point[] = {NumArray.get(i), NameArray.get(i), Xarray.get(i), Yarray.get(i), Harray.get(i), GorPKarray.get(i), PKarray.get(i), RadArray.get(i), DeltaHArray.get(i), DevArray.get(i), ProklArray.get(i)};

                        Results.add(i, point);
                    }
                    int i;
                    int j;
                    final String LS = System.lineSeparator();
                    try {
                        FileWriter fileWriter = new FileWriter(Myfile);
                        for (i = 0; i < Results.size(); i++) {
                            System.out.println();
                            fileWriter.write(LS);
                            for (j = 0; j < 11; j++) {
                                System.out.print(Results.get(i)[j] + "\t");
                                fileWriter.write(Results.get(i)[j] + "\t");
                            }
                        }
                        fileWriter.flush();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }


        }


        class CountAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    X1 = Double.parseDouble(X1text.getText());
                } catch (Exception ex) {
                    X1text.setText("Неверный формат");
                }
                try {
                    X2 = Double.parseDouble(X2text.getText());
                } catch (Exception ex) {
                    X2text.setText("Неверный формат");
                }
                try {
                    Y1 = Double.parseDouble(Y1text.getText());
                } catch (Exception ex) {
                    Y1text.setText("Неверный формат");
                }
                try {
                    Y2 = Double.parseDouble(Y2text.getText());
                } catch (Exception ex) {
                    Y2text.setText("Неверный формат");
                }
                try {
                    H1 = Double.parseDouble(H1text.getText());
                } catch (Exception ex) {
                    H1text.setText("Неверный формат");
                }
                try {
                    H2 = Double.parseDouble(H2text.getText());
                } catch (Exception ex) {
                    H2text.setText("Неверный формат");
                }
                try {
                    PK1 = Double.parseDouble(PK1text.getText());
                } catch (Exception ex) {
                    PK1text.setText("Неверный формат");
                }
                try {
                    PK2 = Double.parseDouble(PK2text.getText());
                } catch (Exception ex) {
                    PK2text.setText("Неверный формат");
                }
                for (int i = 0; i < lng; i++) {
                    try {
                        X = Double.parseDouble(XTFields[i].getText());
                        Y = Double.parseDouble(YTFields[i].getText());
                        H = Double.parseDouble(HTFields[i].getText());
                        GorPK = new Count(X1, Y1, H1, PK1, X2, Y2, H2, PK2, X, Y, H).getGorPK();
                        PK = new Count(X1, Y1, H1, PK1, X2, Y2, H2, PK2, X, Y, H).getPK();
                        Rad = new Count(X1, Y1, H1, PK1, X2, Y2, H2, PK2, X, Y, H).getRad();
                        Dev = new Count(X1, Y1, H1, PK1, X2, Y2, H2, PK2, X, Y, H).getDev();
                        DeltaH = new Count(X1, Y1, H1, PK1, X2, Y2, H2, PK2, X, Y, H).getDeltaH();
                        PKs[i] = PK;

                        PKForProk.add(PK);

                        strGorPK = String.format("%.3f ", GorPK);
                        strPK = String.format("%.3f ", PK);
                        strRad = String.format("%.3f ", Rad);
                        strDev = String.format("%.3f ", Dev);
                        strDeltaH = String.format(String.format("%.3f ", DeltaH));


                        GORPktTFields[i].setText(String.format("%.3f ", GorPK));
                        PKTFields[i].setText(String.format("%.3f ", PK));
                        RadTFields[i].setText(String.format("%.3f ", Rad));
                        DeltaHFields[i].setText(String.format("%.3f ", DeltaH));
                        DevTFields[i].setText(String.format("%.3f ", Dev));

                        GorPKarray.add(strGorPK);
                        PKarray.add(strPK);
                        RadArray.add(strRad);
                        DevArray.add(strDev);
                        DeltaHArray.add(strDeltaH);




                    } catch (Exception ex) {
                        GORPktTFields[i].setText("");
                        PKTFields[i].setText("");
                        RadTFields[i].setText("");
                        DevTFields[i].setText("");
                        DeltaHFields[i].setText("");

                    }
                }

                for (int i = 0; i < GorPKarray.size(); i++) {
                    NumArray.add(NumberTextField[i].getText());
                    NameArray.add(NameTextField[i].getText());
                    Xarray.add(XTFields[i].getText());
                    Yarray.add(YTFields[i].getText());
                    Harray.add(HTFields[i].getText());

                }




            }
        }
    }

    class PanelLeft extends JPanel {

        Dimension dimension = new Dimension(1100, 30);
        Dimension panelDim = new Dimension(2000, 600);

        public PanelLeft() {
            setLayout(new BorderLayout());
            leftPanel();


        }


        public void leftPanel() {
            add(PointBaseLabel, BorderLayout.WEST);
            add(PointOneCoord, BorderLayout.CENTER);
            add(PointTwoCoord, BorderLayout.EAST);

            setMinimumSize(panelDim);


            PointBaseLabel.setLayout(new BoxLayout(PointBaseLabel, BoxLayout.Y_AXIS));

            for (int i = 0; i < Labels.length; i++) {
                JTextField label = new JTextField(Labels[i]);
                label.setEditable(false);
                label.setMaximumSize(dimension);
                PointBaseLabel.add(label);
            }

            labelPoint1.setEditable(false);
            labelPoint2.setEditable(false);
            labelPoint2.setMaximumSize(dimension);
            labelPoint1.setMaximumSize(dimension);

            X1text.setMaximumSize(dimension);
            Y1text.setMaximumSize(dimension);
            H1text.setMaximumSize(dimension);
            PK1text.setMaximumSize(dimension);
            X2text.setMaximumSize(dimension);
            Y2text.setMaximumSize(dimension);
            H2text.setMaximumSize(dimension);
            PK2text.setMaximumSize(dimension);


            PointOneCoord.setLayout(new BoxLayout(PointOneCoord, BoxLayout.Y_AXIS));
            PointOneCoord.add(labelPoint1);
            PointOneCoord.add(X1text);
            PointOneCoord.add(Y1text);
            PointOneCoord.add(H1text);
            PointOneCoord.add(PK1text);

            PointTwoCoord.setLayout(new BoxLayout(PointTwoCoord, BoxLayout.Y_AXIS));
            PointTwoCoord.add(labelPoint2);
            PointTwoCoord.add(X2text);
            PointTwoCoord.add(Y2text);
            PointTwoCoord.add(H2text);
            PointTwoCoord.add(PK2text);
        }


    }
}


class Count{
    private double X1,Y1,H1,PK1,X2,Y2,H2,PK2,X,Y,H,Rad,GorPK,PK,Dev,a,b,c,d,e,f,g,Uklon,DeltaH;
    int kv=2;
    public Count(double X1,double Y1, double H1, double PK1, double X2, double Y2, double H2, double PK2, double X, double Y, double H){
        this.X1=X1;this.Y1=Y1;this.H1=H1;this.PK1=PK1;this.X2=X2;this.Y2=Y2;this.H2=H2;this.PK2=PK2;this.X=X;this.Y=Y;this.H=H;

        setPoint();
    }
    public void setPoint(){
        Uklon = Math.sqrt(Math.pow((X-X1),kv)+Math.pow((Y-Y1),kv)+Math.pow((H-H1),kv));////////
        a = (X2 - X1);
        b = (Y2 - Y1);
        c = (H2 - H1);

        e = (Y1 - Y)*(H2 - H1) - (H1 - H)*(Y2 - Y1);
        f = (X1 - X)*(H2 - H1) - (H1 - H)*(X2 - X1);
        g = (X1 - X)*(Y2 - Y1) - (Y1 - Y)*(X2 - X1);
        // считаем перпендикляр от точки к прямой(радиус)
        Rad = Math.sqrt(Math.pow(e,kv) + Math.pow(f,kv) + Math.pow(g,kv))/Math.sqrt(Math.pow(a,kv) + Math.pow(b,kv) + Math.pow(c,kv));
        if(PK2>PK1){
            // считаем ПК точки съемки
            PK = PK1 + Math.sqrt(Math.pow((X-X1),kv)+Math.pow((Y-Y1),kv)+Math.pow((H-H1),kv)+Math.pow(Rad,kv));
            // считаем ПИКЕТ как горизонтальное проложение
            GorPK = PK1 + Math.sqrt(Math.pow((X-X1),kv)+Math.pow((Y-Y1),kv)-Math.pow((Dev),kv));
        }else{
            PK = PK1 - Math.sqrt(Math.pow((X-X1),kv)+Math.pow((Y-Y1),kv)+Math.pow((H-H1),kv)+Math.pow(Rad,kv));
            // считаем ПИКЕТ как горизонтальное проложение
            GorPK = PK1 - Math.sqrt(Math.pow((X-X1),kv)+Math.pow((Y-Y1),kv)-Math.pow((Dev),kv));
        }
        // считаем отступ
        Dev = Math.abs((Y2-Y1)*X-(X2-X1)*Y+X2*Y1-Y2*X1)/Math.sqrt(Math.pow((Y2-Y1),kv)+Math.pow((X2-X1),kv));

        // считаем превышение от базовой линии
        DeltaH = H - ((H2-H1)*GorPK/Math.sqrt(Math.pow((X-X1),kv)+Math.pow((Y-Y1),kv))+H1);
    }
    public double getGorPK(){
        return GorPK;
    }
    public double getPK(){
        return PK;
    }
    public double getRad(){
        return Rad;
    }
    public double getDev(){
        return Dev;
    }
    public double getDeltaH(){
        return DeltaH;
    }
}