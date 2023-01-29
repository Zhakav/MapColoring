import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static javax.swing.JOptionPane.showMessageDialog;

public class SaveMapFrame extends JFrame {

    ArrayList<String> saveNames;
    HashMap<Integer,Shape> countries;
    int[] countryColor;
    private LinkedList<Integer>[] adjacency;
    String name;
    FileReaderWriter fileReaderWriter;

    SaveMapFrame(HashMap<Integer,Shape> countries , int[] countryColor, ArrayList<String> saveNames, LinkedList<Integer>[] adjacency){

        this.countries=countries;
        this.countryColor=countryColor;
        this.saveNames=saveNames;
        this.adjacency=adjacency;
        fileReaderWriter=new FileReaderWriter();

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

            this.name=saveName.getText();

            if(name.isBlank()){

                showMessageDialog(null, "Save Name Can't Be Empty!", "Error", JOptionPane.ERROR_MESSAGE);

            }else if(duplicateNames(name)){

                showMessageDialog(null, "Save Name Already Exists!", "Error", JOptionPane.ERROR_MESSAGE);

            }

            else{

                restoreSaveNames();
                fileReaderWriter.writeShapes(countries,"S:\\programing\\Java\\Udemy\\JavaFx\\MapColoring\\src\\Saves\\UserDefined\\Countries"+name+".data");
                fileReaderWriter.writeIntegers(countryColor,"S:\\programing\\Java\\Udemy\\JavaFx\\MapColoring\\src\\Saves\\UserDefined\\CountryColor"+name+".data");
                fileReaderWriter.writeAdjacency(adjacency,"S:\\programing\\Java\\Udemy\\JavaFx\\MapColoring\\src\\Saves\\UserDefined\\Adjacency"+name+".data");

                setVisible(false);
                showMessageDialog(null, "Map Saved Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);

            }

        });



        add(label);
        add(saveName);
        add(bContinue);

    }

    private boolean duplicateNames(String name){

        boolean result=false;



        for(String str:saveNames){

            if (str==null)
                break;

            else if (name.compareTo(str)==0){
                result=true;
                break;
            }
        }

        return result;

    }

    private boolean restoreSaveNames(){


        saveNames.add(name);

        fileReaderWriter.writeSaveNames(saveNames,"S:\\programing\\Java\\Udemy\\JavaFx\\MapColoring\\src\\Saves\\UserDefined\\SaveNames.data");

        return true;

    }


}
