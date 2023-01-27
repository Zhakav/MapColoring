import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MapFrame extends JFrame {


    MapFrame(MapGUI map)  {

        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);

        JPanel UI = new JPanel(new BorderLayout(4, 4));
        JLabel output = map.getOutput();

        Font font=new Font("Segoe Print",Font.BOLD,10);

        JButton color= new JButton("Color");
        color.setFont(font);

        JButton greedy= new JButton("Greedy Coloring");
        greedy.setFont(font);

        JButton random= new JButton("Random Coloring");
        random.setFont(font);

        JButton reset= new JButton("Reset All");
        reset.setFont(font);

        JButton addAdjacency= new JButton("Add Adjacency");
        addAdjacency.setFont(font);

        JButton saveAdjacency= new JButton("Save Adjacency");
        saveAdjacency.setFont(font);

        JButton saveColors= new JButton("Save Colors");
        saveColors.setFont(font);

        JButton openSavedColors= new JButton("Open Saved Colors");
        openSavedColors.setFont(font);

        JComboBox<String> comboBox =new ColorBox();

        comboBox.setFont(font);

        JPanel controller = new JPanel(new FlowLayout());

        controller.add(color);
        controller.add(greedy);
        controller.add(random);
        controller.add(reset);
        controller.add(comboBox);
        controller.add(saveColors);
        controller.add(openSavedColors);
        controller.add(addAdjacency);
        controller.add(saveAdjacency);

        color.setSize(50,50);
        greedy.setSize(50,50);
        random.setSize(50,50);
        reset.setSize(50,50);
        addAdjacency.setSize(50,50);
        saveAdjacency.setSize(50,50);
        output.setSize(20,20);

        greedy.addActionListener((ActionEvent ae)->{
            try {
                map.greedyColoringClicked();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        random.addActionListener((ActionEvent ae)-> map.randomColoringClicked() );

        reset.addActionListener((ActionEvent ae)-> map.resetAllClicked() );

        addAdjacency.addActionListener((ActionEvent ae)-> map.addAdjacencyClicked() );

        saveAdjacency.addActionListener((ActionEvent ae)-> map.saveAdjacencyClicked() );

        color.addActionListener((ActionEvent ae)-> map.colorClicked((String) comboBox.getSelectedItem()) );

        comboBox.addActionListener((ActionEvent ae)-> map.colorClicked((String) comboBox.getSelectedItem()) );

        saveColors.addActionListener((ActionEvent ae)->{

            SaveColorFrame saveColorFrame=new SaveColorFrame(new DataBase(map.getMapName()), map.getCountryColor());
            saveColorFrame.setVisible(true);

        });



        UI.setBorder(new EmptyBorder(4, 4, 4, 4));
        UI.setLayout(new BorderLayout());
        UI.add(output, BorderLayout.CENTER);
        UI.add(controller,BorderLayout.SOUTH);


        add(UI, BorderLayout.CENTER);
        setResizable(false);
        pack();


    }

}
