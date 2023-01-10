import javax.swing.*;
import java.awt.*;
public class WelcomeFrame extends JFrame {

    WelcomeFrame(){

        FlowLayout layout=new FlowLayout();
        layout.setHgap(10);
        layout.setVgap(30);

        setLayout(layout);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);

        setSize(500,200);

        JLabel welcome=new JLabel();
        Font font=new Font("Bahnschrift SemiLight Condensed",Font.BOLD,13);
        welcome.setFont(font);
        welcome.setText("Welcome. Please choose your map or create new one to continue.");
        welcome.setSize(500,100);
        add(welcome);

        String items[]={"IRAN","UNITED STATES","WORLD"};
        JComboBox<String> mapsName=new JComboBox<>(items);
        add(mapsName);

        JButton bContinue= new JButton("Continue");
        add(bContinue);
        
        bContinue.addActionListener(ae ->{

            MapFrame frame=new MapFrame(new MapGUI((String) mapsName.getSelectedItem()));
            this.setVisible(false);
            frame.setVisible(true);

        });

        JButton bOpen= new JButton("Open map");
        add(bOpen);

        JButton bCreate= new JButton("Create map");
        add(bCreate);
    }

}
