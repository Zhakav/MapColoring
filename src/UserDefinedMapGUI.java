import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class UserDefinedMapGUI extends JFrame {

    private HashMap<Integer,Shape> countries;
    private HashMap<Integer,Color>  colors;
    private LinkedList<Integer>[] adjacency;
    private boolean[] colorAvailable;
    private ArrayList<String> saveNames;
    private int[] countryColor;

    private JMenu file,actions,color,open;
    private JMenuItem save,close,greedyColoring,randomColoring,resetColors,resetMap;
    private JMenuBar menuBar;
    private JRadioButtonMenuItem red,blue,green,magenta,yellow,pink,orange;
    private ButtonGroup buttonGroup;

    private Color fillColor=Color.RED;

    UserDefinedMapGUI(){

        this.setSize(1600,800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUnits();

        initGUI();

        createSaveItems();

//        JComboBox<String> comboBox =new ColorBox();
//        comboBox.setSize(100,50);
//        comboBox.addActionListener(ae-> fillColor=MyColor.colorStringToObject(String.valueOf(comboBox.getSelectedItem())));
//
//        JButton greedyColoring=new JButton("Greedy Coloring");
//        greedyColoring.addActionListener(ae->{
//
//            try {
//                MapLogic.greedyColoring(colorAvailable,adjacency,countries,colors,countryColor);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            repaint();
//
//        });
//
//        JButton randomColoring=new JButton("Random Coloring");
//        randomColoring.addActionListener(ae->{
//
//            MapLogic.randomColoring(colorAvailable,adjacency,countries,colors,countryColor);
//            repaint();
//
//        });
//
//        JButton resetAll=new JButton("Reset All");
//        resetAll.addActionListener(ae->{
//
//            MapLogic.resetColors(countryColor);
//            repaint();
//
//        });
//
//        JButton save=new JButton("Save");
//        save.addActionListener(ae->{
//
//            SaveMapFrame saveMapFrame=new SaveMapFrame(countries,countryColor,saveNames,adjacency);
//            saveMapFrame.setVisible(true);
//
//        });
//
//
//        JPanel controller=new JPanel(new FlowLayout());
//        controller.setSize(100,80);
//        controller.setBackground(new Color(133,182,176));
//        controller.add(comboBox);
//        controller.add(greedyColoring);
//        controller.add(randomColoring);
//        controller.add(save);

        this.add(new MapBackground(),BorderLayout.CENTER);
        //this.add(controller,BorderLayout.SOUTH);

        this.setResizable(false);

    }

    private class MapBackground extends JComponent{

        Point start,end;


        MapBackground(){


            this.addMouseListener(new MouseHandler());
            this.addMouseMotionListener(new MouseMotionHandler());


        }

        private class MouseHandler extends MouseAdapter {

            @Override
            public void mousePressed(MouseEvent e) {
                start=e.getPoint();
                end=start;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                Shape country = makeRectangle(start.x, start.y, e.getX(), e.getY());
                countries.putIfAbsent(countries.size(),country);

                adjacency[Country.getCountryKey(country,countries)]=new LinkedList<>();

                defineAdjacency(country);

                if(!MapLogic.colorTheCountry(country,fillColor,colorAvailable,adjacency,countries,colors,countryColor)){

                    System.out.println("Remove Shape Checkpoint------------------------------------------------------------------");
                    //MapLogic.removeShape(country,countries,adjacency);
                    countries.remove(Country.getCountryKey(country,countries));

                }

                start = null;
                end = null;
                repaint();
            }
        }

        private class MouseMotionHandler extends MouseMotionAdapter {


            @Override
            public void mouseDragged(MouseEvent e) {

                end=e.getPoint();
                repaint();

            }
        }

        public void paint(Graphics graphic){

            Graphics2D graphic2D=(Graphics2D) graphic;

            graphic2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphic2D.setPaint(Color.BLUE);

            graphic2D.setStroke(new BasicStroke(2));

            graphic2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));

            System.out.println("Colors:");

            for (Color color: colors.values()){

                System.out.println(color);

            }

            System.out.println("Countries:");

            for (Shape country:countries.values()){

                System.out.println(country);

            }

            /*System.out.println("CountryColor:");

            for(int i=0 ; i<countryColor.length;i++){

                System.out.println("Country " + i + " Color " + countryColor[i]);

            }*/

            if(!countries.values().isEmpty()) {

                for (int i = 0; i < countryColor.length; i++) {

                    int color = countryColor[i];
                    Shape country = countries.get(i);

                    if (country != null) {

                        graphic2D.setPaint(Color.BLACK);
                        graphic2D.draw(countries.get(i));

                        if (color != -1) {

                            graphic2D.setColor(colors.get(color));
                            graphic2D.fill(countries.get(i));

                        }

                    }
                }
            }

            /*for (Shape country : countries.values()) {

                graphic2D.setPaint(Color.BLACK);
                graphic2D.draw(country);
                graphic2D.setColor(colors.get(countryColor[(Country.getCountryKey(country,countries))]));
                graphic2D.fill(country);

            }*/

            if (start != null && end != null) {

                graphic2D.setPaint(Color.LIGHT_GRAY);
                Shape dragged = makeRectangle(start.x, start.y, end.x, end.y);
                graphic2D.draw(dragged);

            }
        }

        private Rectangle2D.Float makeRectangle(int startX, int startY, int endX, int endY) {

            return new Rectangle2D.Float(Math.min(startX, endX),
                    Math.min(startY, endY), Math.abs(startX - endX),
                    Math.abs(startY - endY));

        }

        private void defineAdjacency(Shape country){

            for(Shape neighbor: countries.values()){

                if (country.intersects((Rectangle2D) neighbor)){

                    MapLogic.addEdge(country,neighbor,countries,adjacency);

                }

            }

        }

    }

    private void initUnits(){

        saveNames=new FileReaderWriter().readSaveNames("S:\\programing\\Java\\Udemy\\JavaFx\\MapColoring\\src\\Saves\\UserDefined\\SaveNames.data");
        countries=new HashMap<>();
        colors=new HashMap<>();
        adjacency=new LinkedList[500];
        colorAvailable=new boolean[500];
        Arrays.fill(colorAvailable,true);
        countryColor=new int[500];
        Arrays.fill(countryColor,-1);
        MyColor.setColors(colors);

    }

    private void initGUI(){

        //Defining MenuItems

        ///File
        open=new JMenu("Open");
        save=new JMenuItem("Save");
        close=new JMenuItem("Close");

        ///Actions
        greedyColoring=new JMenuItem("Greedy Coloring");
        randomColoring=new JMenuItem("RandomColoring");
        resetColors=new JMenuItem("Reset Colors");
        resetMap=new JMenuItem("Reset Map");

        ///Colors
        buttonGroup=new ButtonGroup();
        red=new JRadioButtonMenuItem("Red");
        blue=new JRadioButtonMenuItem("Blue");
        green=new JRadioButtonMenuItem("Green");
        magenta=new JRadioButtonMenuItem("Magenta");
        yellow=new JRadioButtonMenuItem("Yellow");
        pink=new JRadioButtonMenuItem("Pink");
        orange=new JRadioButtonMenuItem("Orange");


        //Adding Listeners
        greedyColoring.addActionListener(ae->{

            try {
                MapLogic.greedyColoring(colorAvailable,adjacency,countries,colors,countryColor);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();

        });
        randomColoring.addActionListener(ae->{

            MapLogic.randomColoring(colorAvailable,adjacency,countries,colors,countryColor);
            repaint();

        });
        resetColors.addActionListener(ae->{

            MapLogic.resetColors(countryColor);
            repaint();

        });
        resetMap.addActionListener(ae->{

            countries=new HashMap<>();
            adjacency=new LinkedList[500];
            countryColor=new int[500];
            repaint();
        });
        save.addActionListener(ae->{

            SaveMapFrame saveMapFrame=new SaveMapFrame(countries,countryColor,saveNames,adjacency);
            saveMapFrame.setVisible(true);

        });

        ///Color Listeners
        red.addItemListener(l->{

            if(red.isSelected())

                fillColor=MyColor.colorStringToObject(red.getText());

        });
        blue.addItemListener(l->{

            if(blue.isSelected())

                fillColor=MyColor.colorStringToObject(blue.getText());

        });
        magenta.addItemListener(l->{

            if(magenta.isSelected())

                fillColor=MyColor.colorStringToObject(magenta.getText());

        });
        green.addItemListener(l->{

            if(green.isSelected())

                fillColor=MyColor.colorStringToObject(green.getText());

        });
        yellow.addItemListener(l->{

            if(yellow.isSelected())

                fillColor=MyColor.colorStringToObject(yellow.getText());

        });
        pink.addItemListener(l->{

            if(pink.isSelected())

                fillColor=MyColor.colorStringToObject(pink.getText());

        });
        orange.addItemListener(l->{

            if(orange.isSelected())

                fillColor=MyColor.colorStringToObject(orange.getText());

        });


        //Defining Menus
        file=new JMenu("File");
        actions=new JMenu("Actions");
        color=new JMenu("Colors");

        //Adding MenuItems

        ///File
        file.add(open);
        file.add(save);
        file.add(close);

        ///Actions
        actions.add(greedyColoring);
        actions.add(randomColoring);
        actions.add(resetColors);
        actions.add(resetMap);

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




        //Defining MenuBar
        menuBar=new JMenuBar();
        menuBar.add(file);
        menuBar.add(actions);
        menuBar.add(color);

        //Setting MenuBar
        this.setJMenuBar(menuBar);

    }

    private void createSaveItems(){

        if(saveNames!=null) {

            JMenuItem saveItem;
            open.removeAll();
            for (String save : saveNames) {

                if (save==null) {
                    System.out.println("Save is Null");
                    break;
                }

                saveItem = new JMenuItem(save);
                saveItem.addActionListener(new SaveHandler(save));
                open.add(saveItem);

            }
        }

    }

    private class SaveHandler implements ActionListener{

        String saveName;
        FileReaderWriter fileReaderWriter;

        SaveHandler(String saveName){

            this.saveName=saveName;
            this.fileReaderWriter=new FileReaderWriter();

        }

        @Override
        public void actionPerformed(ActionEvent e) {

            countries=fileReaderWriter.readShapes("S:\\programing\\Java\\Udemy\\JavaFx\\MapColoring\\src\\Saves\\UserDefined\\Countries" + saveName +  ".data");
            countryColor=fileReaderWriter.readIntegers("S:\\programing\\Java\\Udemy\\JavaFx\\MapColoring\\src\\Saves\\UserDefined\\CountryColor" + saveName +".data");
            adjacency=fileReaderWriter.readAdjacency("S:\\programing\\Java\\Udemy\\JavaFx\\MapColoring\\src\\Saves\\UserDefined\\Adjacency" + saveName +".data");
            repaint();

        }
    }

}
