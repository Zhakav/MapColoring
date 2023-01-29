import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class MapFrame extends JFrame {

    private JMenu file,actions,color,open,adjacency;
    private JMenuItem save,close,greedyColoring,randomColoring,resetColors,defineAdjacency,saveAdjacency,back;
    private JMenuBar menuBar;
    private JRadioButtonMenuItem red,blue,green,magenta,yellow,pink,orange;
    private ButtonGroup buttonGroup;
    private MapGUI map;


    MapFrame(MapGUI map )  {

        super(map.getMapFullName());

        this.map=map;
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);

        initGUI();

        JPanel UI = new JPanel(new BorderLayout(4, 4));
        JLabel output = map.getOutput();

        Font font=new Font("Segoe Print",Font.BOLD,10);

        JButton saveColors= new JButton("Save Colors");
        saveColors.setFont(font);

        /*saveColors.addActionListener((ActionEvent ae)->{

            SaveColorFrame saveColorFrame=new SaveColorFrame(new DataBase(map.getMapName()), map.getCountryColor());
            saveColorFrame.setVisible(true);

        });*/



        UI.setBorder(new EmptyBorder(4, 4, 4, 4));
        UI.setLayout(new BorderLayout());
        UI.add(output, BorderLayout.CENTER);


        add(UI, BorderLayout.CENTER);
        setResizable(false);
        pack();


    }

    private void initGUI(){

        //Defining MenuItems

        ///File
        //open=new JMenu("Open");
        //save=new JMenuItem("Save");
        back=new JMenuItem("Restart");
        close=new JMenuItem("Close");

        ///Actions
        greedyColoring=new JMenuItem("Greedy Coloring");
        randomColoring=new JMenuItem("RandomColoring");
        resetColors=new JMenuItem("Reset Colors");

        ///Colors
        buttonGroup=new ButtonGroup();
        red=new JRadioButtonMenuItem("Red");
        blue=new JRadioButtonMenuItem("Blue");
        green=new JRadioButtonMenuItem("Green");
        magenta=new JRadioButtonMenuItem("Magenta");
        yellow=new JRadioButtonMenuItem("Yellow");
        pink=new JRadioButtonMenuItem("Pink");
        orange=new JRadioButtonMenuItem("Orange");

        ///adjacency
        defineAdjacency=new JMenuItem("Define Adjacency");
        saveAdjacency=new JMenuItem("Save Adjacency");


        //Adding Listeners
        greedyColoring.addActionListener(ae->{

            try {
                map.greedyColoringClicked();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        randomColoring.addActionListener(ae->{

            map.randomColoringClicked();

        });
        resetColors.addActionListener(ae->{

            map.resetAllClicked();

        });

        defineAdjacency.addActionListener(ae->{

            map.addAdjacencyClicked();

        });

        saveAdjacency.addActionListener(ae->{

            map.saveAdjacencyClicked();

        });

        back.addActionListener(ae->{

            WelcomeFrame welcomeFrame=new WelcomeFrame();
            welcomeFrame.setVisible(true);
            this.setVisible(false);

        });

        close.addActionListener(ae->{

            System.exit(0);

        });


        /*save.addActionListener(ae->{


            SaveColorFrame saveColorFrame=new SaveColorFrame(map.getCountries(),map.getCountryColor(),map.getSaveNames(),map.getAdjacency(), map.getMapName());
            saveColorFrame.setVisible(true);

        });*/


        ///Color Listeners
        red.addItemListener(l->{

            if(red.isSelected())

                map.colorClicked(red.getText());

        });
        blue.addItemListener(l->{

            if(blue.isSelected())

                map.colorClicked(blue.getText());

        });
        magenta.addItemListener(l->{

            if(magenta.isSelected())

                map.colorClicked(magenta.getText());

        });
        green.addItemListener(l->{

            if(green.isSelected())

                map.colorClicked(green.getText());

        });
        yellow.addItemListener(l->{

            if(yellow.isSelected())

                map.colorClicked(yellow.getText());

        });
        pink.addItemListener(l->{

            if(pink.isSelected())

                map.colorClicked(pink.getText());

        });
        orange.addItemListener(l->{

            if(orange.isSelected())

                map.colorClicked(orange.getText());

        });

        //Adding SaveMenuItems
        //createSaveMenuItems();


        //Defining Menus
        file=new JMenu("File");
        actions=new JMenu("Actions");
        color=new JMenu("Colors");
        adjacency=new JMenu("Adjacency");

        //Adding MenuItems

        ///File
        //file.add(open);
        //file.add(save);
        file.add(back);
        file.add(close);

        ///Actions
        actions.add(greedyColoring);
        actions.add(randomColoring);
        actions.add(resetColors);

        ///Colors
        buttonGroup.add(red);
        buttonGroup.add(blue);
        buttonGroup.add(green);
        buttonGroup.add(magenta);
        buttonGroup.add(yellow);
        buttonGroup.add(pink);
        buttonGroup.add(orange);

        color.add(red);
        color.add(blue);
        color.add(green);
        color.add(magenta);
        color.add(yellow);
        color.add(pink);
        color.add(orange);

        ///Adjacency
        adjacency.add(defineAdjacency);
        adjacency.add(saveAdjacency);


        //Defining MenuBar
        menuBar=new JMenuBar();
        menuBar.add(file);
        menuBar.add(actions);
        menuBar.add(color);
        menuBar.add(adjacency);

        //Setting MenuBar
        this.setJMenuBar(menuBar);

    }


    private void createSaveMenuItems(){

        ArrayList<JMenuItem> menuItems=map.createSaveItems();

        open.removeAll();

        for(JMenuItem menuItem: menuItems){

            open.add(menuItem);

        }

    }

}
