import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static javax.swing.JOptionPane.showMessageDialog;

public class SaveColorFrame extends JFrame {

    SaveColorFrame(DataBase dataBase,int[] countryColor){



        setLayout(new FlowLayout());
        setSize(400,150);
        setResizable(false);

        JLabel label=new JLabel("Enter Your Save Name");
        label.setFont(new Font("Segoe Print",0,17));

        Font font=new Font("Segoe Print",0,12);

        JTextArea saveName=new JTextArea(1,20);
        saveName.setFont(font);

        JButton bContinue =new JButton("Continue");
        bContinue.setFont(font);

        bContinue.addActionListener((ActionEvent ae)->{

            String name=saveName.getText();

            if(name.isBlank()){

                showMessageDialog(null, "Save Name Cant Be Empty!", "Error", JOptionPane.ERROR_MESSAGE);

            }else{

                dataBase.saveColors(saveName.getText(),countryColor);
                setVisible(false);
                showMessageDialog(null, "Colors Saved Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);

            }

        });

        add(label);
        add(saveName);
        add(bContinue);

    }

}
