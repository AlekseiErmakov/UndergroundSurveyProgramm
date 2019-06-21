package PKandRadius;

import javax.lang.model.element.Name;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RoundPKR extends JFrame {
    //перемееные левой панели "Исходные данные"
    JPanel test = new JPanel();
    int Pan = 4;
    String[] CoordRound = {"Название", "X", "Y", "H"};
    String[] NameForPoints = {"Название","Центр кривой","Начало кривой","Конец кривой"};
    JTextField[] TextRoundPoint;
    ArrayList<JTextField[]> CoordAll = new ArrayList<>();
    JPanel[] ForCoords = new JPanel[Pan];
    JPanel LeftBox = new JPanel();
    JTextField RoundParamName = new JTextField("Исходные данные",10);
    JTextField textRoundCoord;
    JPanel ForCoord;
    //перемеенные веохней панели
    JTextField prostotak = new JTextField("просто так",10);
    //переменные центральной панели
    String[] ColName ={"Название","Номер","Х","Y","Н","Гор. ПК", "Наклонный ПК","Радиус","Смещение","Превышние","Радиус от Ц.К."};
    JTextField [] ResultRow;
    ArrayList<JTextField[]> ResultTable= new ArrayList<>();
    JPanel[] CentrPanels;
    JTextField Result;
    int Rows=45;
    JPanel test2 = new JPanel();

    public RoundPKR() {
        super("Расчет параметров тоннеля на круговой кривой");
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        setVisible(false);
        setLayout(new BorderLayout());
        add(new RcentrPanel(),BorderLayout.CENTER);
        add(new RleftPanel(),BorderLayout.WEST);
        add(new RrightPanel(),BorderLayout.EAST);
        add(new RtopPanel(),BorderLayout.NORTH);
        add(new RbottomPanel(),BorderLayout.SOUTH);
        pack();

    }
    class RleftPanel extends JPanel{

        public RleftPanel(){

            setLayout(new BorderLayout());
            add(test,BorderLayout.NORTH);
            RoundParamName.setEditable(false);
            RoundParamName.setHorizontalAlignment(JTextField.CENTER);

            test.setLayout(new BoxLayout(test,BoxLayout.X_AXIS));

            AddJPanels();
        }
        public void AddJPanels(){
            for (int i =0; i<Pan; i++){

                TextRoundPoint = new JTextField[NameForPoints.length];

                for (int j=0; j<NameForPoints.length; j++){
                    TextRoundPoint[j] = new JTextField(10);


                }
                CoordAll.add(i,TextRoundPoint);

            }



            for (int i =0; i<NameForPoints.length;i++){
                ForCoords[i]=new JPanel();
                ForCoords[i].setLayout(new BoxLayout(ForCoords[i],BoxLayout.Y_AXIS));


                for (int j = 0; j<Pan; j++){
                    ForCoords[i].add(CoordAll.get(j)[i]);
                    CoordAll.get(0)[i].setEditable(false);
                    CoordAll.get(0)[i].setText(CoordRound[i]);
                    CoordAll.get(j)[i].setHorizontalAlignment(JTextField.CENTER);
                    CoordAll.get(j)[i].setMaximumSize(new Dimension(125,20));


                }

                CoordAll.get(i)[0].setText(NameForPoints[i]);
                CoordAll.get(i)[0].setEditable(false);
                test.add(ForCoords[i]);
            }


        }
    }

    class RrightPanel extends JPanel{
        public RrightPanel(){
            setLayout(new FlowLayout());
            JTextField fieldTest = new JTextField("Привет",8);
            add(fieldTest);
        }
    }
    class RtopPanel extends JPanel{
        public RtopPanel(){
            add(prostotak);
            prostotak.setEditable(false);

        }

    }
    class RbottomPanel extends JPanel{
        public RbottomPanel(){

        }
    }
    class RcentrPanel extends JPanel{
        public RcentrPanel(){
            setLayout(new BorderLayout());

            add(test2,BorderLayout.NORTH);
            test2.setLayout(new BoxLayout(test2,BoxLayout.X_AXIS));
            addResultTable();
        }
        public void addResultTable(){
            for (int i =0; i<Rows; i++){

                ResultRow = new JTextField[ColName.length];

                for (int j=0; j<ColName.length; j++){
                    ResultRow[j] = new JTextField(9);


                }
                ResultTable.add(i,ResultRow);

            }


            for (int i = 0; i <ColName.length; i++){
                CentrPanels=  new JPanel[ColName.length];
                CentrPanels[i] = new JPanel();
                CentrPanels[i].setLayout(new BoxLayout(CentrPanels[i],BoxLayout.Y_AXIS));
                ResultTable.get(0)[i].setText(ColName[i]);
                ResultTable.get(0)[i].setEditable(false);
                for (int j=0;j<Rows; j++){

                    CentrPanels[i].add(ResultTable.get(j)[i]);
                    ResultTable.get(j)[i].setMinimumSize(new Dimension(20,5));
                    ResultTable.get(j)[i].setMaximumSize(new Dimension(100,80));
                    ResultTable.get(j)[i].setHorizontalAlignment(JTextField.CENTER);

                }

                test2.add(CentrPanels[i]);
            }
            for (int i = 1; i<Rows; i++){
                ResultTable.get(i)[0].setText(String.format("№ %d",i));
                ResultTable.get(i)[0].setEditable(false);


            }

        }
    }


}