package PKandRadius;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    JToolBar Programs = new JToolBar();
    String firstPR= "Параметры съемочных точек на прямой";
    String secPR = "Параметры съемочных точек на кривой";
    String[] ChooseProgramName ={firstPR,secPR};
    JButton[] ChooseProgram = new JButton[ChooseProgramName.length];

    public MainFrame(){
        super("UnderGround Survey");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());
        add(new MainTopPanel(),BorderLayout.NORTH);
        setSize(1920,1080);
    }
    class MainTopPanel extends JPanel{
        public MainTopPanel(){
            setLayout(new FlowLayout());
            addJradioButtonGroup();
        }
        public void addJradioButtonGroup(){
            for (int i=0; i<ChooseProgramName.length; i++){
                ChooseProgram[i] = new JButton();
                ChooseProgram[i].setText(ChooseProgramName[i]);
                Programs.add(ChooseProgram[i]);
                ChooseProgram[i].addActionListener(new ChooseProgramAction());
            }
            add(Programs);

        }
    }
    class ChooseProgramAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command){
                case ("Параметры съемочных точек на прямой"): new PKRGui().setVisible(true);
                    break;
                case ("Параметры съемочных точек на кривой"): new RoundPKR().setVisible(true);
            }
        }
    }
}