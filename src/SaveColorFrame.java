import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static javax.swing.JOptionPane.showMessageDialog;

public class SaveColorFrame extends JFrame {

    ArrayList<String> saveNames;
    FileReaderWriter fileReaderWriter;
    String mapName;
    String name;

    SaveColorFrame(HashMap<Integer,Shape> countries , int[] countryColor, ArrayList<String> saveNames, LinkedList<Integer>[] adjacency,String mapName){

        this.mapName=mapName;
        this.saveNames=saveNames;
        this.fileReaderWriter=new FileReaderWriter();

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

            name=saveName.getText();

            if(name.isBlank()){

                showMessageDialog(null, "Save Name Cant Be Empty!", "Error", JOptionPane.ERROR_MESSAGE);

            }
            else if(duplicateNames(name)){

                showMessageDialog(null, "Save Name Already Exists!", "Error", JOptionPane.ERROR_MESSAGE);

            }else{

                restoreSaveNames();
                fileReaderWriter.writeShapes(countries,"S:\\programing\\Java\\Udemy\\JavaFx\\MapColoring\\src\\Saves\\"+ mapName + "\\Countries"+name+".data");
                fileReaderWriter.writeIntegers(countryColor,"S:\\programing\\Java\\Udemy\\JavaFx\\MapColoring\\src\\Saves\\"+ mapName +"\\CountryColor"+name+".data");
                fileReaderWriter.writeAdjacency(adjacency,"S:\\programing\\Java\\Udemy\\JavaFx\\MapColoring\\src\\Saves\\"+ mapName +"\\Adjacency"+name+".data");

                setVisible(false);
                showMessageDialog(null, "Colors Saved Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);

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

        fileReaderWriter.writeSaveNames(saveNames,"S:\\programing\\Java\\Udemy\\JavaFx\\MapColoring\\src\\Saves\\"+ mapName +"\\SaveNames.data");

        return true;

    }

}
