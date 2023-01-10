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

        JButton color= new JButton("Color");
        JButton greedy= new JButton("Greedy Coloring");
        JButton random= new JButton("Random Coloring");
        JButton reset= new JButton("Reset All");
        JButton addAdjacency= new JButton("Add Adjacency");
        JButton saveAdjacency= new JButton("Save Adjacency");

        JComboBox<String> comboBox =new JComboBox<>();

        comboBox.addItem("Red");
        comboBox.addItem("Blue");
        comboBox.addItem("Green");
        comboBox.addItem("Magenta");
        comboBox.addItem("Yellow");
        comboBox.addItem("Pink");
        comboBox.addItem("Orange");

        JPanel controller = new JPanel(new FlowLayout());

        controller.add(color);
        controller.add(greedy);
        controller.add(random);
        controller.add(reset);
        controller.add(comboBox);
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
                map.greedyColoring();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        random.addActionListener((ActionEvent ae)-> map.randomColoring() );

        reset.addActionListener((ActionEvent ae)-> map.resetAll() );

        addAdjacency.addActionListener((ActionEvent ae)-> map.addAdjacencyClicked() );

        saveAdjacency.addActionListener((ActionEvent ae)-> map.saveAdjacencyClicked() );

        color.addActionListener((ActionEvent ae)-> map.colorClicked((String) comboBox.getSelectedItem()) );

        comboBox.addActionListener((ActionEvent ae)-> map.colorClicked((String) comboBox.getSelectedItem()) );



        UI.setBorder(new EmptyBorder(4, 4, 4, 4));
        UI.setLayout(new BorderLayout());
        UI.add(output, BorderLayout.CENTER);
        UI.add(controller,BorderLayout.SOUTH);


        add(UI, BorderLayout.CENTER);
        setResizable(false);
        pack();


    }

}
