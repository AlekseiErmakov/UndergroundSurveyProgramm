package PKandRadius;


import javax.swing.plaf.*;

import javax.lang.model.element.Name;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
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

    private String[] ResTip = {"Номер точки", "Название", "X", "Y", "H", "Длина по оси", "Гор ПК", "Накл ПК", "Смещение от оси", "Превыш. от Оси", "Радиус"};
    private final String[] tip = {"X", "Y", "H", "PK"};

    private final int size1 = tip.length;
    private final int size2 = 3;
    private JTextField[][] BaseText = new JTextField[size1][size2];
    private JPanel bottomPanel;

    private final int textsize = 11,
            unedittextsize = 5,
            resLength = 301,
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

    private JButton CountButton, ClearButton, ClearBlButton, SaveResultsButton, SaveBLButton, ImportCoordsButton, ImportBLButton;

    ControlAction controlAction;
    ArrayList<SurveyPoint> LofP;
    ArrayList<Result> LofR;

    private final String LS = System.lineSeparator();
    final JScrollPane scrollPane;

    public StraightLane() {
        super(ProgName);
        straightframe = new JFrame();
        straightframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
        inBaselinePan = new JPanel();
        BaseLinePanel = new JPanel(new FlowLayout());
        ReasultPanel = new JPanel();
        inResultPanel = new JPanel();
        bottomPanel = new JPanel();
        scrollPane = new JScrollPane(ReasultPanel);
        scrollPane.createHorizontalScrollBar();
        scrollPane.createHorizontalScrollBar();
        initMetalLookAndFeel();

        straightframe.getContentPane();
        straightframe.setLayout(new BorderLayout());
        straightframe.add(BaseLinePanel, BorderLayout.WEST);
        straightframe.add(scrollPane,BorderLayout.CENTER);
        straightframe.add(bottomPanel,BorderLayout.SOUTH);
        bottomPanel.setLayout(new FlowLayout());

        controlAction = new ControlAction();
        fillBaseLinePanel();
        fillResultPanel();
        addControlButtons();

        straightframe.setVisible(true);
        straightframe.setSize(1920, 1080);

        LofP = new ArrayList<>();
        LofR = new ArrayList<>();

    }
    public void initMetalLookAndFeel() {
        try {
            MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Can't use the specified look and feel on this platform.");
        } catch (Exception e) {
            System.err.println("Couldn't get specified look and feel, for some reason.");
        }
    }

    private void fillBaseLinePanel() {
        BaseLinePanel.setPreferredSize(new Dimension(400, 80));
        fillthePanel(LeftRows, LeftCols, LeftHgap, LeftVgap, BaseLinePanel, inBaselinePan);
        addJTextFields(size1, size2, BaseText, inBaselinePan, textsize, unedittextsize);
        fillUneditableLeftFields(tip, BaseText, size1, size2);
    }

    private void fillResultPanel() {

        fillthePanel(CentrRows, CentrCols, CentrHgap, CentrVgap, ReasultPanel, inResultPanel);
        addJTextFields(resLength, ResTip.length, ResTab, inResultPanel, textsize, unedittextsize);
        fillUneditableCentrFields(ResTip, ResTab, resLength, ResTip.length);
    }

    private void addControlButtons() {
        SaveBLButton = addButton(SAVEBL, controlAction, BaseLinePanel);
        ClearBlButton = addButton(CLEARBL, controlAction, BaseLinePanel);
        ImportBLButton = addButton(IMPORTBL, controlAction, BaseLinePanel);

        CountButton = addButton(COUNT, controlAction, bottomPanel);
        ClearButton = addButton(CLEARPOINTS, controlAction, bottomPanel);
        SaveResultsButton = addButton(SAVERS, controlAction, bottomPanel);
        ImportCoordsButton = addButton(IMPORTPOINTS, controlAction, bottomPanel);
    }

    private void fillthePanel(int rows, int cols, int hgap, int vgap, JPanel FlowPanel, JPanel GridPanel) {

        FlowPanel.setLayout(new FlowLayout());
        GridPanel.setLayout(new GridLayout(rows, cols, hgap, vgap));
        FlowPanel.add(GridPanel);

    }

    private void addJTextFields(int S1, int S2, JTextField[][] jTextField, JPanel panel, int ttsize, int unedtxsize) {

        for (i = 0; i < S1; i++) {
            for (j = 0; j < S2; j++) {
                jTextField[i][j] = new JTextField(ttsize);
                panel.add(jTextField[i][j]);
                jTextField[i][0].setColumns(unedtxsize);
            }
        }
    }

    private void fillUneditableLeftFields(String[] field, JTextField[][] jTextField, int S1, int S2) {
        for (i = 0; i < S1; i++) {
            for (j = 0; j < S2; j++) {
                jTextField[i][0].setText(field[i]);
                jTextField[i][0].setEditable(false);
            }
        }
    }

    private void fillUneditableCentrFields(String[] field, JTextField[][] jTextField, int S1, int S2) {
        for (i = 0; i <= S1; i++) {
            for (j = 0; j < S2; j++) {
                jTextField[0][j].setText(field[j]);
                jTextField[0][j].setEditable(false);

            }
        }
        for (i = 1; i < S1; i++) {
            jTextField[i][0].setText(String.format("№ %d", i));
            jTextField[i][0].setEditable(false);
            for (int j=5;j<jTextField[0].length;j++){
                jTextField[i][j].setEditable(false);
            }
        }
    }

    private JButton addButton(String name, ActionListener listener, JPanel panel) {
        JButton button = new JButton(name);
        panel.add(button, BorderLayout.SOUTH);
        button.addActionListener(listener);
        return button;

    }

    class ControlAction implements ActionListener {
        BaseLine BL;
        BasePoint Fp;
        BasePoint Sp;

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case COUNT:
                    count();
                    break;
                case IMPORTPOINTS:
                    importSDR();
                    break;
                case CLEARPOINTS:
                    clearRes();
                    break;
                case CLEARBL:
                    clearBL();
                    break;
                case SAVERS:
                    savePoints();
                    break;
                case SAVEBL:
                    saveBL();
                    break;
                case IMPORTBL:
                    importBL();
                    break;
            }

        }

        private void count() {
            LofP.add(0, null);
            LofR.add(0, null);
            try {
                Fp = new BasePoint(Double.parseDouble(BaseText[0][1].getText()),
                        Double.parseDouble(BaseText[1][1].getText()),
                        Double.parseDouble(BaseText[2][1].getText()),
                        Double.parseDouble(BaseText[3][1].getText()));

                Sp = new BasePoint(Double.parseDouble(BaseText[0][2].getText()),
                        Double.parseDouble(BaseText[1][2].getText()),
                        Double.parseDouble(BaseText[2][2].getText()),
                        Double.parseDouble(BaseText[3][2].getText()));

                BL = new BaseLine(Fp, Sp);
            } catch (Exception ex) {
                chekAlllane();
            }
            for (int i = 1; i < resLength; i++) {
                try {
                    LofP.add(i, new SurveyPoint(ResTab[i][1].getText(),
                            Double.parseDouble(ResTab[i][2].getText()),
                            Double.parseDouble(ResTab[i][3].getText()),
                            Double.parseDouble(ResTab[i][4].getText())));
                } catch (Exception ex) {
                    LofP.add(i, null);
                }
            }
            for (int i = 1; i < resLength; i++) {
                if (LofP.get(i) == null) {
                    LofR.add(i, null);
                } else {
                    LofR.add(i, new Result(BL, LofP.get(i)));
                    ResTab[i][5].setText(String.format("%.3f", LofR.get(i).getLength()));
                    ResTab[i][6].setText(String.format("%.3f", LofR.get(i).getGorPiket()));
                    ResTab[i][7].setText(String.format("%.3f", LofR.get(i).getPK()));
                    ResTab[i][8].setText(String.format("%.3f", LofR.get(i).getDev()));
                    ResTab[i][9].setText(String.format("%.3f", LofR.get(i).getDeltaH()));
                    ResTab[i][10].setText(String.format("%.3f", LofR.get(i).getRadius()));
                }
            }
        }

        private void chekAlllane() {
            for (int i = 0; i < BaseText.length; i++) {
                for (int j = 1; j < BaseText[0].length; j++) {
                    try {
                        double a = Double.parseDouble(BaseText[i][j].getText());
                    } catch (Exception myEx) {
                        BaseText[i][j].setText(ALERT);
                    }
                }
            }
        }

        private void importSDR() {
            ArrayList<String> Pspisok = new ArrayList<>();
            MyFilechooser fileopen = new MyFilechooser();
            FileFilter fileFilter = new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return true;
                }

                @Override
                public String getDescription() {
                    return ".txt";
                }
            };
            fileopen.addChoosableFileFilter(fileFilter);
            Pspisok.clear();
            int ret = fileopen.showDialog(null, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File Myfile = fileopen.getSelectedFile();

                    try {
                        FileReader filereader = new FileReader(Myfile);
                        BufferedReader reader = new BufferedReader(filereader);
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            Pspisok.add(line);

                        }
                        reader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                try{
                    for (int i = 4; i >= 0; i--) {

                        Pspisok.remove(i);
                    }
                }catch(IndexOutOfBoundsException ie){
                    ie.printStackTrace();
                }
            }
            for (int j = 0; j < Pspisok.size(); j++) {
                char[] temp = Pspisok.get(j).toCharArray();
                String temp1 = "";
                String temp2 = "";
                for (int i = 0; i <= 19; i++) {
                    temp1 = temp1 + temp[i];
                }
                for (int i = 20; i < temp.length; i++) {
                    temp2 = temp2 + temp[i];
                }
                String temp3 = temp1 + " " + temp2;
                String[] point = temp3.split("\\s+");

                int b=4;
                for (int i = point.length-1; i >= point.length-4; i--) {
                    ResTab[j + 1][b].setText(point[i]);
                    b--;
                }
                b =1;
                String tempo ="";

                for (int i =1;i<=point.length-4;i++){

                    tempo = tempo+point[i]+" ";
                    ResTab[j+1][b].setText(tempo);
                }
            }
        }

        private String getFileExtension(File file) {
            String fileName = file.getName();
            if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
                return fileName.substring(fileName.lastIndexOf(".") + 1);
            else return "";
        }

        private void clearRes() {
            for (i = 1; i < ResTab.length; i++) {
                for (j = 1; j < ResTab[0].length; j++) {
                    ResTab[i][j].setText(" ");
                }
            }
        }

        private void clearBL() {
            for (i = 0; i < BaseText.length; i++) {
                for (j = 1; j < BaseText[0].length; j++) {
                    BaseText[i][j].setText(" ");
                }
            }
        }
        public void savePoints() {
            int ret;
            MyFilechooser myFilechooser = new MyFilechooser();
            ret = myFilechooser.showSaveDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File myfile = myFilechooser.getSelectedFile();
                try {
                    FileWriter fileWriter = new FileWriter(myfile);
                    for (i = 1; i <LofR.size(); i++) {
                        if (LofR.get(i)==null);
                        else{
                            fileWriter.write(String.valueOf(LofR.get(i)));
                            fileWriter.write(LS);
                        }
                    }
                    fileWriter.flush();
                } catch (IOException ioex) {
                    ioex.printStackTrace();
                }
            }
        }
        public void saveBL() {
            int ret;
            MyFilechooser myFilechooser = new MyFilechooser();
            ret = myFilechooser.showSaveDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File myfile = myFilechooser.getSelectedFile();
                try {
                    FileWriter fileWriter = new FileWriter(myfile);
                    for (i = 0; i <4; i++) {
                      for (int j=1; j<3; j++ ){
                          fileWriter.write(BaseText[i][j].getText()+"\t");
                      }
                      fileWriter.write(LS);
                    }
                    fileWriter.flush();
                } catch (IOException ioex) {
                    ioex.printStackTrace();
                }
            }
        }
        public void importBL(){
            MyFilechooser fileopen = new MyFilechooser();
            ArrayList<String> BLspisok = new ArrayList<>();
            BLspisok.clear();
            int ret = fileopen.showDialog(null, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File Myfile = fileopen.getSelectedFile();
                if (getFileExtension(Myfile).equals(FileFormatTXT)) {
                    try {
                        FileReader filereader = new FileReader(Myfile);
                        BufferedReader reader = new BufferedReader(filereader);
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                           BLspisok.add(line);
                        }
                        reader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            for (int i =0 ; i<BLspisok.size(); i++){
                String[] temp = BLspisok.get(i).split("\\s+");
                for (int j =1;j<3;j++){
                    BaseText[i][j].setText(temp[j-1]);
                }
            }
        }
    }
}

class MyFilechooser extends JFileChooser{

}
class MyJOptionPane extends JOptionPane{

}






