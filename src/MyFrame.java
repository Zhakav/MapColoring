import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MyFrame extends JFrame {


    MyFrame(MapGUI map){

        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);

        JPanel UI = new JPanel(new BorderLayout(4, 4));
        JLabel output = map.getOutput();
        JButton button= new JButton("OK");
        JComboBox<String> comboBox =new JComboBox<>();

        comboBox.addItem("Red");
        comboBox.addItem("Blue");
        comboBox.addItem("Green");
        comboBox.addItem("Magenta");
        comboBox.addItem("Yellow");
        comboBox.addItem("Pink");
        comboBox.addItem("Orange");

        JPanel controller = new JPanel(new FlowLayout());
        controller.add(button);
        controller.add(comboBox);

        button.setSize(50,50);
        output.setSize(20,20);
        UI.setBorder(new EmptyBorder(4, 4, 4, 4));
        UI.setLayout(new BorderLayout());
        UI.add(output, BorderLayout.CENTER);
        UI.add(controller,BorderLayout.SOUTH);


        add(UI, BorderLayout.CENTER);
        setResizable(false);
        pack();

    }

}
