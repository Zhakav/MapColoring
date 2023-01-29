import java.sql.*;
import java.util.LinkedList;

public class DataBase {

    private String mapName;
    //private String mapSaveName;
    private Connection con;

    DataBase(String mapName) {

        try {
            //setMapSaveName();
            this.mapName = mapName;
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:S:\\programing\\Java\\Udemy\\JavaFx\\MapColoring\\src\\Database\\MapColoringDB");

        } catch (Exception e){

            e.printStackTrace();

        }
    }

    public void saveToDatabase(LinkedList<Integer>[] adjacency) {

        try {

            PreparedStatement prepared = null;
            Statement statement = con.createStatement();
            statement.executeUpdate("DELETE FROM " + mapName);
            statement.close();

            for (int i = 0; i < adjacency.length; i++) {

                for (Integer X : adjacency[i]) {

                    prepared = con.prepareStatement("INSERT INTO  " + mapName +  " VALUES (?,?)");
                    prepared.setInt(1, i);
                    prepared.setInt(2, X);
                    prepared.executeUpdate();

                }

            }

            if(prepared!=null)

                prepared.close();

        } catch (Exception e){

            e.printStackTrace();

        }

    }

    public LinkedList<Integer>[] readFromDatabase(int countryCount){

        LinkedList<Integer>[] adjacency=new LinkedList[countryCount];

        for (int i = 0; i < countryCount; ++i) {

            adjacency[i] = new LinkedList<>();

        }

        try {

            int neighbor1;
            int neighbor2;

            Statement statement= con.createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT * FROM " + mapName );

            while(resultSet.next()){

                neighbor1=resultSet.getInt("Neighbor_id1");
                neighbor2=resultSet.getInt("Neighbor_id2");

                adjacency[neighbor1].add(neighbor2);
                adjacency[neighbor2].add(neighbor1);

            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adjacency;

    }

    public void saveColors(String name , int[] countryColor) {

        try {

            PreparedStatement preparedStatement = con.prepareStatement("CREATE TABLE " + name + " (" +
                    " countryID INTEGER," +
                    " colorId INTEGER);");

            preparedStatement.execute();


            for(int i=0 ; i<countryColor.length ; i++){

                preparedStatement = con.prepareStatement("INSERT INTO " + name + " VALUES (?,?);");
                preparedStatement.setInt(1,i);
                preparedStatement.setInt(2,countryColor[i]);
                preparedStatement.execute();
            }

            /*preparedStatement = con.prepareStatement("INSERT  INTO " + mapSaveName + " VALUES (?)");
            preparedStatement.setString(1,name);
            preparedStatement.execute();*/

            preparedStatement.close();

        } catch (Exception e){e.printStackTrace();};

    }

    /*private void setMapSaveName(){

       switch (mapName){

           case "IR" : mapSaveName="IrSave";
           break;
           case "US" : mapSaveName="UsSave";
           break;
           case "World": mapSaveName="WorldSave";
           break;

       };

    }*/

    public void closeConnection()  {

        try {

            con.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

}
