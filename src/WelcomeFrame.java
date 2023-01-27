import javax.swing.*;
import java.awt.*;
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
        welcome.setText("Welcome. Please choose your map or create new one to continue.");
        welcome.setSize(500,100);
        add(welcome);

        String items[]={"IRAN","UNITED STATES","WORLD"};
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

        JButton bOpen= new JButton("Open map");
        bOpen.setFont(font);
        add(bOpen);

        JButton bCreate= new JButton("Create map");
        bCreate.setFont(font);
        add(bCreate);

        bCreate.addActionListener(ae->{

            UserDefinedMapGUI userDefined=new UserDefinedMapGUI();
            this.setVisible(false);
            userDefined.setVisible(true);

        });
    }

}
