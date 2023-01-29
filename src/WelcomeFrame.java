import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class WelcomeFrame extends JFrame {

    WelcomeFrame(){

        FlowLayout layout=new FlowLayout();
        layout.setHgap(30);
        layout.setVgap(20);

        setResizable(false);

        Font font=new Font("Segoe Print",Font.BOLD,12);

        setLayout(layout);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);

        setSize(600,150);

        JLabel welcome=new JLabel();
        welcome.setFont(new Font("Segoe Print",Font.BOLD,17));
        welcome.setText("Welcome. Please Choose A Map Or Create New One To Continue.");
        welcome.setSize(500,100);
        add(welcome);

        String items[]={"IRAN","UNITED STATES","FRANCE","GERMANY","EUROPE"};
        JComboBox<String> mapsName=new JComboBox<>(items);
        mapsName.setFont(font);
        add(mapsName);

        JButton bContinue= new JButton("Continue");
        bContinue.setFont(font);
        add(bContinue);
        
        bContinue.addActionListener(ae ->{

            MapFrame frame=new MapFrame(new MapGUI((String) mapsName.getSelectedItem()));
            this.setVisible(false);
            frame.setVisible(true);


        });

        JButton bCreate= new JButton("Create map");
        bCreate.setFont(font);
        add(bCreate);

        bCreate.addActionListener(ae->{

            UserDefinedMapGUI userDefined=new UserDefinedMapGUI("User Defined Map");
            this.setVisible(false);
            userDefined.setVisible(true);
            showMessageDialog(null, "Create The Shapes By Clicking And Dragging The Mouse.\nYou Can Perform Your Desired Actions In MenuBar.", "Information", JOptionPane.INFORMATION_MESSAGE);

        });
    }

}
