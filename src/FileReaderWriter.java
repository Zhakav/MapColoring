import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class FileReaderWriter {

    public void writeShapes(HashMap<Integer,Shape> countries , String filePath) {

        try {
            FileOutputStream output = new FileOutputStream(filePath);
            ObjectOutputStream objectOutput = new ObjectOutputStream(output);
            objectOutput.writeObject(countries);

            output.close();
            objectOutput.close();

        } catch (Exception e){e.printStackTrace();}


    }

    public HashMap<Integer,Shape> readShapes(String filePath){

        HashMap<Integer,Shape> countries=null;

        try {
            FileInputStream input = new FileInputStream(filePath);
            ObjectInputStream objectInput = new ObjectInputStream(input);
            countries =  (HashMap<Integer, Shape>) objectInput.readObject();

            input.close();
            objectInput.close();

        } catch (Exception e){e.printStackTrace();}

        return countries;

    }

    public void writeSaveNames(ArrayList<String> saveNAmes , String filePath){

        try {
            FileOutputStream output = new FileOutputStream(filePath);
            ObjectOutputStream objectOutput = new ObjectOutputStream(output);
            objectOutput.writeObject(saveNAmes);

            output.close();
            objectOutput.close();

        } catch (Exception e){e.printStackTrace();}

    }

    public ArrayList<String> readSaveNames(String filePath){

        ArrayList<String> saveNames=null;

        try {
            FileInputStream input = new FileInputStream(filePath);
            ObjectInputStream objectInput = new ObjectInputStream(input);
            saveNames = (ArrayList<String>) objectInput.readObject();

            input.close();
            objectInput.close();

        } catch (Exception e){e.printStackTrace();}

        return saveNames;

    }

    public void writeIntegers(int[] integers,String filePath){

        try {
            FileOutputStream output = new FileOutputStream(filePath);
            ObjectOutputStream objectOutput = new ObjectOutputStream(output);
            objectOutput.writeObject(integers);

            output.close();
            objectOutput.close();

        } catch (Exception e){e.printStackTrace();}

    }

    public int[] readIntegers(String filePath){

        int[] integers=null;

        try {
            FileInputStream input = new FileInputStream(filePath);
            ObjectInputStream objectInput = new ObjectInputStream(input);
            integers = (int[]) objectInput.readObject();

            input.close();
            objectInput.close();

        } catch (Exception e){e.printStackTrace();}

        return integers;

    }

    public void writeAdjacency(LinkedList<Integer>[] adjacency,String filePath){

        try {
            FileOutputStream output = new FileOutputStream(filePath);
            ObjectOutputStream objectOutput = new ObjectOutputStream(output);
            objectOutput.writeObject(adjacency);

            output.close();
            objectOutput.close();

        } catch (Exception e){e.printStackTrace();}

    }

    public LinkedList<Integer>[] readAdjacency(String filePath){

        LinkedList<Integer>[] adjacency =null;

        try {
            FileInputStream input = new FileInputStream(filePath);
            ObjectInputStream objectInput = new ObjectInputStream(input);
            adjacency = (LinkedList<Integer>[]) objectInput.readObject();

            input.close();
            objectInput.close();

        } catch (Exception e){e.printStackTrace();}

        return adjacency;

    }

}
