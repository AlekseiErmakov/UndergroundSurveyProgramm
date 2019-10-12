package PKandRadius;

import javax.lang.model.element.Name;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class StraightLane extends JFrame {
    private static int i = 0;
    private static int j = 0;
    private JFrame straightframe = new JFrame();
    private double forcheck;


    private JPanel BaseLinePanel = new JPanel(new FlowLayout());
    private JPanel InBaselinePan = new JPanel();


    private JPanel inBaselinePan = new JPanel();
    private final String[] tip = {"X","Y","H","PK"};
    private  final int size1 = tip.length;
    private  final int size2 = 3;
    private JTextField[][] BaseText = new JTextField[size1][size2];
    private double[][] BaseForCount = new double[4][2];
    private String[][] Results = new String[40][10];
    private double[][] CountedResults = new double[40][6];

    private final int textsize = 11;
    private final int unedittextsize = 5;
    private final int LeftRows = size1;
    private final int LeftCols = size2;
    private final int LeftHgap = 8;
    private final int LeftVgap = 8;
    private final int XYH = 3;

    private JPanel ReasultPanel = new JPanel();
    private JPanel inResultPanel = new JPanel();
    private final String[] ResTip= {"Номер точки","Название","X","Y","H","Длина по оси","Гор ПК","Накл ПК","Смещение от оси","Превыш. от Оси","Радиус"};
    private final int resLength = 41;
    private final JTextField[][] ResultTable = new JTextField[resLength][ResTip.length];
    private String NameOfTakenPoints[] = new String[resLength-1];
    private double CoordsOfPoints[][] = new double[resLength-1][XYH];

    private Point Points[] = new Point[CoordsOfPoints.length];
    private final int CentrRows = resLength;
    private final int CentrCols = ResTip.length;
    private final int CentrHgap = 3;
    private final int CentrVgap = 3;

    JButton CountButton = new JButton("Расчет");
    JButton ClearButton = new JButton("Очистить");
    JButton ClearBlButton = new JButton("Очистить БЛ");
    JButton SaveResultsButton = new JButton("Сохранить результат");
    JButton SaveBLButton = new JButton("Сохранить Базовую Линию");
    JButton ImportCoordsButton = new JButton("Импортировать точки");
    JButton ImportBLButton = new JButton("Импортировать Ось");

    private final String LS = System.lineSeparator();

    public StraightLane(){
        super("Расчет параметров съемочных точек на прямой");
        straightframe.setDefaultCloseOperation(EXIT_ON_CLOSE);


        straightframe.getContentPane();
        straightframe.setLayout(new BorderLayout());
        straightframe.add(BaseLinePanel,BorderLayout.WEST);
        straightframe.add(ReasultPanel,BorderLayout.EAST);


        BaseLinePanel.setPreferredSize(new Dimension(400,80));
        fillthePanel(LeftRows,LeftCols,LeftHgap,LeftVgap,BaseLinePanel,inBaselinePan);
        addJTextFields(size1,size2,BaseText,inBaselinePan,textsize,unedittextsize);
        fillUneditableLeftFields(tip,BaseText,size1,size2);
        addButton(SaveBLButton,new SaveAction(BaseText),BaseLinePanel);
        addButton(ClearBlButton,new ClearAction(BaseText,0),BaseLinePanel);
        addButton(ImportBLButton, new ImportAction(BaseText,4,2,0,1),BaseLinePanel);

        ReasultPanel.setPreferredSize(new Dimension(1300,80));
        fillthePanel(CentrRows,CentrCols,CentrHgap,CentrVgap,ReasultPanel,inResultPanel);
        addJTextFields(resLength,ResTip.length,ResultTable,inResultPanel,textsize,unedittextsize);
        fillUneditableCentrFields(ResTip,ResultTable,resLength,ResTip.length);
        addButton(CountButton,new CountAction(),ReasultPanel);
        addButton(ClearButton,new ClearAction(ResultTable,1),ReasultPanel);
        addButton(SaveResultsButton,new SaveAction(ResultTable),ReasultPanel);
        addButton(ImportCoordsButton,new ImportAction(ResultTable,40,4,1,1),ReasultPanel);
        straightframe.setVisible(true);
        straightframe.setSize(1920,1080);


    }


    public void fillthePanel(int rows,int cols, int hgap, int vgap, JPanel FlowPanel,JPanel GridPanel){

        FlowPanel.setLayout(new FlowLayout());
        GridPanel.setLayout(new GridLayout(rows,cols,hgap,vgap));
        FlowPanel.add(GridPanel);

    }
    public void addJTextFields(int S1,int S2,JTextField[][] jTextField,JPanel panel,int ttsize,int unedtxsize){

        for( i= 0; i<S1; i++){
            for( j=0; j<S2; j++){
                jTextField[i][j] = new JTextField(ttsize);
                panel.add(jTextField[i][j]);
                jTextField[i][0].setColumns(unedtxsize);
            }
        }
    }
    public void fillUneditableLeftFields(String[] field,JTextField[][] jTextField,int S1,int S2){
        for ( i = 0; i<S1; i++ ){
            for(j = 0; j<S2; j++){
                jTextField[i][0].setText(field[i]);
                jTextField[i][0].setEditable(false);
            }
        }
    }
    public void fillUneditableCentrFields(String[] field,JTextField[][] jTextField,int S1,int S2){
        for ( i = 0; i<=S1; i++ ){
            for(j = 0; j<S2; j++){
                jTextField[0][j].setText(field[j]);
                jTextField[0][j].setEditable(false);

            }
        }
        for( i = 1; i<S1; i++){
            jTextField[i][0].setText(String.format("№ %d", i ));
            jTextField[i][0].setEditable(false);
        }
    }
    public void addButton(JButton button,ActionListener listener,JPanel panel){
        panel.add(button,BorderLayout.SOUTH);
        button.addActionListener(listener);


    }
    public boolean chek(String S) {
        try {
            forcheck = Double.parseDouble(S);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    public void countCoordsOfPoints() {
        for (i = 0; i < CoordsOfPoints.length; i++) {
            for (j = 0; j < XYH; j++) {
                if (chek(ResultTable[i + 1][j + 2].getText())) {
                    CoordsOfPoints[i][j] = Double.parseDouble(ResultTable[i + 1][j + 2].getText());
                } else {
                    CoordsOfPoints[i][j] = 0;
                }
                NameOfTakenPoints[i] = ResultTable[i + 1][2].getText();

            }
        }
        for (i = 0; i < resLength - 1; i++) {
            for (j = 0; j < ResTip.length - 5; j++) {
                Points[i] = new Point(BaseForCount[0][0], BaseForCount[1][0], BaseForCount[2][0], BaseForCount[0][1], BaseForCount[1][1], BaseForCount[2][1], CoordsOfPoints[i][0], CoordsOfPoints[i][1], CoordsOfPoints[i][2], BaseForCount[3][0], BaseForCount[3][1]);
                if (CoordsOfPoints[i][0] == 0 || CoordsOfPoints[i][1] == 0) {
                    ResultTable[i + 1][j + 5].setText(" ");
                } else if (CoordsOfPoints[i][2] == 0) {
                    ResultTable[i + 1][5].setText(String.format("%.3f ",Points[i].countgorLength()));
                    ResultTable[i + 1][6].setText(String.format("%.3f ",Points[i].countGorPK()));
                    ResultTable[i + 1][7].setText(" ");
                    ResultTable[i + 1][8].setText(String.format("%.3f ",Points[i].countDev()));
                    ResultTable[i + 1][9].setText("");
                    ResultTable[i + 1][10].setText(String.format("%.3f ",Points[i].countRad()));

                } else {
                    ResultTable[i + 1][5].setText(String.format("%.3f ",Points[i].countgorLength()));
                    ResultTable[i + 1][6].setText(String.format("%.3f ",Points[i].countGorPK()));
                    ResultTable[i + 1][7].setText(String.format("%.3f ",Points[i].countPK()));
                    ResultTable[i + 1][8].setText(String.format("%.3f ",Points[i].countDev()));
                    ResultTable[i + 1][9].setText(String.format("%.3f ",Points[i].countDeltaH()));
                    ResultTable[i + 1][10].setText(String.format("%.3f ",Points[i].countRad()));
                }
            }
        }

    }

    class CountAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
             boolean BaseLine = false;
            for (i=0; i< BaseForCount.length; i++) {
                for (j = 0; j < 2; j++) {
                    if (chek(BaseText[i][j + 1].getText())) {
                        BaseForCount[i][j] = Double.parseDouble(BaseText[i][j + 1].getText());
                        BaseLine = true;
                    } else {
                        BaseLine=false;
                        JOptionPane.showMessageDialog(new MyJOptionPane(), "Неправильный ввод в Б.Л. в ячейке " + BaseText[i][j + 1].getText());
                        break;
                    }
                }
            }
            if(BaseLine){
                countCoordsOfPoints();
            }

        }
    }
    class ClearAction implements ActionListener{
        JTextField[][] ClearTextfield;
        int ii;

        public ClearAction(JTextField[][] ClearTextfield, int ii){
        this.ii=ii;
        this.ClearTextfield = ClearTextfield;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            for (i = ii; i<ClearTextfield.length; i++ ){
                for (j = 1;j<ClearTextfield[0].length;j++ ){
                    ClearTextfield[i][j].setText(" ");
                }
            }
        }
    }
    class ImportAction implements ActionListener{
        JTextField[][] impJtextField;
        String[] point;
        ArrayList<String[]> Import = new ArrayList<String[]>();
        int length1;
        int length2;
        int jjj;
        int iii;

        public ImportAction(JTextField[][] impJtextField,int length1,int length2,int iii,int jjj){
            this.impJtextField=impJtextField;
            this.length1=length1;
            this.length2=length2;
            this.iii=iii;
            this.jjj=jjj;

        }
        @Override
        public void actionPerformed(ActionEvent e) {
            Import.clear();
            MyFilechooser fileopen = new MyFilechooser();
            int ret = fileopen.showDialog(null, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File Myfile = fileopen.getSelectedFile();
                try {
                    FileReader filereader = new FileReader(Myfile);
                    BufferedReader reader = new BufferedReader(filereader);
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        point = line.split("\t");
                        Import.add(point);

                    }
                    if (Import.size()>length1){

                    }else{
                        length1=Import.size();
                    }
                    System.out.println(Import.get(2)[1]);
                for (i=0;i<length1;i++){
                    for(j=0;j<length2;j++){
                        impJtextField[i+iii][j+jjj].setText(Import.get(i)[j]);
                    }

                }

                    reader.close();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    class SaveAction implements ActionListener{
        JTextField[][] saveTextfield;
        SaveAction(JTextField[][] saveTextField){
            this.saveTextfield = saveTextField;

        }
        @Override
        public void actionPerformed(ActionEvent e) {
            int ret;

            MyFilechooser myFilechooser = new MyFilechooser();
            ret = myFilechooser.showSaveDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File myfile = myFilechooser.getSelectedFile();
                try {
                    FileWriter fileWriter = new FileWriter(myfile);
                    for(i=0; i<saveTextfield.length;i++){
                        fileWriter.write(LS);
                        for (j=0; j<saveTextfield[0].length; j++){
                            fileWriter.write(saveTextfield[i][j].getText());
                        }
                    }
                    fileWriter.flush();
                }catch (IOException ioex){
                    ioex.printStackTrace();
                }
            }
        }
    }

}
class MyFilechooser extends JFileChooser{

}
class MyJOptionPane extends JOptionPane{

    }


class Point {
    private double x1;
    private double y1;
    private double h1;
    private double pk1;
    private double x2;
    private double y2;
    private double h2;
    private double pk2;
    private double x;
    private double y;
    private double h;
    private final int kv=2;



    public Point(double x1, double y1, double h1,  double x2, double y2, double h2, double x, double y, double h,double pk1, double pk2) {
        this.x1 = x1;
        this.y1 = y1;
        this.h1 = h1;
        this.pk1 = pk1;
        this.x2 = x2;
        this.y2 = y2;
        this.h2 = h2;
        this.x = x;
        this.y = y;
        this.h = h;
        pk2 = 0;
    }

    public double countRad() {
        return Math.sqrt(Math.pow(((y1 - y) * (h2 - h1) - (h1 - h) * (y2 - y1)), kv) + Math.pow(((x1 - x) * (h2 - h1) - (h1 - h) * (x2 - x1)), kv) + Math.pow(((x1 - x) * (y2 - y1) - (y1 - y) * (x2 - x1)), kv)) / Math.sqrt(Math.pow((x2 - x1), kv) + Math.pow((y2 - y1), kv) + Math.pow((h2 - h1), kv));
    }

    public double countDev() {
        return ((y2 - y1) * x - (x2 - x1) * y + x2 * y1 - y2 * x1) / Math.sqrt(Math.pow(y2 - y1, kv) + Math.pow(x2 - x1, kv));
    }

    public double countgorLength() {
        return Math.sqrt(Math.pow(x - x1, kv) + Math.pow(y - y1, kv) - Math.pow(countDev(), kv));
    }

    public double countGorPK() {
        if (pk1 == 0) {
            return countgorLength();
        } else {
            if (pk2 == 0) {
                return pk1 + countgorLength();
            } else {
                if (pk2 > pk1) {
                    return pk1 + countgorLength();
                } else if (pk2 == pk1) {
                    return 0;
                } else {
                    return pk1 - countgorLength();
                }
            }
        }
    }
    public double countLength(){
        return Math.sqrt(Math.pow(x-x1,kv)+Math.pow(y-y1,kv)+Math.pow(h-h1,kv)-Math.pow(countRad(),kv));
    }
    public double countPK(){
        if (pk1 == 0) {
            return countLength();
        } else {
            if (pk2 == 0) {
                return pk1 + countLength();
            } else {
                if (pk2 > pk1) {
                    return pk1 + countLength();
                } else if (pk2 == pk1) {
                    return 0;
                } else {
                    return pk1 - countLength();
                }
            }
        }
    }
    public double countDeltaH(){
        return  h -(h1 + (h2-h1)*Math.sqrt(Math.pow(x-x1,kv)+Math.pow(y-y1,kv)-Math.pow(countDev(),kv))/Math.sqrt(Math.pow(x-x1,kv)+Math.pow(y-y1,kv)));
    }

}



