package PKandRadius;

import javax.lang.model.element.Name;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;

public class StraightLane extends JFrame implements StringRes {
    private static int i = 0;
    private static int j = 0;
    private JFrame straightframe;
    private double forcheck;
    private JPanel ReasultPanel, inResultPanel, BaseLinePanel, inBaselinePan;

    private String[] ResTip= {"Номер точки","Название","X","Y","H","Длина по оси","Гор ПК","Накл ПК","Смещение от оси","Превыш. от Оси","Радиус"};
    private final String[] tip = {"X","Y","H","PK"};

    private  final int size1 = tip.length;
    private  final int size2 = 3;
    private JTextField[][] BaseText = new JTextField[size1][size2];
    private double[][] BaseForCount = new double[4][2];

    private final int textsize = 11,
    unedittextsize = 5,
    resLength = 41,
    LeftRows = size1,
    LeftCols = size2,
    LeftHgap = 8,
    LeftVgap = 8,
    XYH = 3,
    CentrRows = resLength,
    CentrCols = ResTip.length,
    CentrHgap = 3,
    CentrVgap = 3;

    private final JTextField[][] ResTab = new JTextField[resLength][ResTip.length];

    private JButton CountButton, ClearButton, ClearBlButton, SaveResultsButton,SaveBLButton,ImportCoordsButton,ImportBLButton;

   ControlAction controlAction;
   ArrayList<SurveyPoint> LofP;
   ArrayList<Result> LofR;

    private final String LS = System.lineSeparator();
    public StraightLane(){
        super(ProgName);
        straightframe = new JFrame();
        straightframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
        inBaselinePan = new JPanel();
        BaseLinePanel = new JPanel(new FlowLayout());
        ReasultPanel = new JPanel();
        inResultPanel = new JPanel();

        straightframe.getContentPane();
        straightframe.setLayout(new BorderLayout());
        straightframe.add(BaseLinePanel,BorderLayout.WEST);
        straightframe.add(ReasultPanel,BorderLayout.EAST);


        controlAction = new ControlAction();
        fillBaseLinePanel();
        fillResultPanel();
        addControlButtons();

        straightframe.setVisible(true);
        straightframe.setSize(1920,1080);

        LofP=new ArrayList<>();
        LofR=new ArrayList<>();

    }
    private void fillBaseLinePanel(){
        BaseLinePanel.setPreferredSize(new Dimension(400,80));
        fillthePanel(LeftRows,LeftCols,LeftHgap,LeftVgap,BaseLinePanel,inBaselinePan);
        addJTextFields(size1,size2,BaseText,inBaselinePan,textsize,unedittextsize);
        fillUneditableLeftFields(tip,BaseText,size1,size2);
    }
    private void fillResultPanel(){
        ReasultPanel.setPreferredSize(new Dimension(1300,80));
        fillthePanel(CentrRows,CentrCols,CentrHgap,CentrVgap,ReasultPanel,inResultPanel);
        addJTextFields(resLength,ResTip.length,ResTab,inResultPanel,textsize,unedittextsize);
        fillUneditableCentrFields(ResTip,ResTab,resLength,ResTip.length);
    }
    private void addControlButtons(){
        SaveBLButton = addButton(SAVEBL,new SaveAction(BaseText),BaseLinePanel);
        ClearBlButton = addButton(CLEARBL,new ClearAction(BaseText,0),BaseLinePanel);
        ImportBLButton = addButton(IMPORTBL,new ImportAction(BaseText,4,2,0,1),BaseLinePanel);

        CountButton = addButton(COUNT,controlAction,ReasultPanel);
        ClearButton = addButton(CLEARPOINTS,new ClearAction(ResTab,1),ReasultPanel);
        SaveResultsButton = addButton(SAVERS,new SaveAction(ResTab),ReasultPanel);
        ImportCoordsButton = addButton(IMPORTPOINTS,new ImportAction(ResTab,40,4,1,1),ReasultPanel);
    }

    private void fillthePanel(int rows,int cols, int hgap, int vgap, JPanel FlowPanel,JPanel GridPanel){

        FlowPanel.setLayout(new FlowLayout());
        GridPanel.setLayout(new GridLayout(rows,cols,hgap,vgap));
        FlowPanel.add(GridPanel);

    }
    private void addJTextFields(int S1,int S2,JTextField[][] jTextField,JPanel panel,int ttsize,int unedtxsize){

        for( i= 0; i<S1; i++){
            for( j=0; j<S2; j++){
                jTextField[i][j] = new JTextField(ttsize);
                panel.add(jTextField[i][j]);
                jTextField[i][0].setColumns(unedtxsize);
            }
        }
    }
    private void fillUneditableLeftFields(String[] field,JTextField[][] jTextField,int S1,int S2){
        for ( i = 0; i<S1; i++ ){
            for(j = 0; j<S2; j++){
                jTextField[i][0].setText(field[i]);
                jTextField[i][0].setEditable(false);
            }
        }
    }
    private void fillUneditableCentrFields(String[] field,JTextField[][] jTextField,int S1,int S2){
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
    private JButton addButton(String name,ActionListener listener,JPanel panel){
        JButton button = new JButton(name);
        panel.add(button,BorderLayout.SOUTH);
        button.addActionListener(listener);
        return button;

    }
    public boolean chek(String S) {
        try {
            forcheck = Double.parseDouble(S);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    class ControlAction implements ActionListener{
        BaseLine BL;
        BasePoint Fp;
        BasePoint Sp;

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command){
                case COUNT:
                    count();
                    break;
            }

        }
        private void count(){
                LofP.add(0,null);
                LofR.add(0,null);
            try{
                Fp=new BasePoint(Double.parseDouble(BaseText[0][1].getText()),Double.parseDouble(BaseText[1][1].getText()),Double.parseDouble(BaseText[2][1].getText()),Double.parseDouble(BaseText[3][1].getText()));
                System.out.println(Fp.toString());
                Sp=new BasePoint(Double.parseDouble(BaseText[1][2].getText()),Double.parseDouble(BaseText[2][2].getText()),Double.parseDouble(BaseText[3][2].getText()),Double.parseDouble(BaseText[3][2].getText()));
                System.out.println(Sp.toString());
                BL=new BaseLine(Fp,Sp);
            }catch (Exception ex){
                chekAlllane();
            }
            for (int i = 1; i<41;i++){
                try {
                    LofP.add(i, new SurveyPoint(ResTab[i][1].getText(), Double.parseDouble(ResTab[i][2].getText()), Double.parseDouble(ResTab[i][3].getText()), Double.parseDouble(ResTab[i][4].getText())));
                }catch (Exception ex){
                    LofP.add(i, null);
                }


            }
            System.out.println(LofP.get(2));
            for (int i =1;i<41;i++) {
                if (LofP.get(i)==null){
                }else {
                LofR.add(i,new Result(BL,LofP.get(i)));
                ResTab[i][5].setText(String.format("%3s",LofR.get(i).getLength()));
                ResTab[i][6].setText(String.format("%3s",LofR.get(i).getGorPiket()));
                ResTab[i][7].setText(String.format("%3s",LofR.get(i).getPK()));
                ResTab[i][8].setText(String.format("%3s",LofR.get(i).getDev()));
                ResTab[i][9].setText(String.format("%3s",LofR.get(i).getDeltaH()));
                ResTab[i][10].setText(String.format("%3s",LofR.get(i).getRadius()));
                }
                //String result = point.toString() + "\t" + getLength() + "\t" + getGorPiket() + "\t" + getPK() +  "\t" + getDev() + "\t" + getDeltaH() + "\t" + getRadius();
            }







        }
        private void chekAlllane(){
            for(int i = 0;i<BaseText.length;i++){
                for (int j=1;j<BaseText[0].length;j++){
                    try{
                        double a =Double.parseDouble(BaseText[i][j].getText());
                    } catch (Exception myEx){
                        BaseText[i][j].setText(ALERT);
                    }
                }
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






