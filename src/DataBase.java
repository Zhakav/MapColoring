import java.sql.*;
import java.util.LinkedList;

public class DataBase {

    private String mapName;
    private Connection con;

    DataBase(String mapName) {

        try {


            this.mapName = mapName;
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:A:\\SQLight\\MapColoring");

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

                System.out.println(neighbor1 + " " +neighbor2);


                adjacency[neighbor1].add(neighbor2);
                adjacency[neighbor2].add(neighbor1);

            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adjacency;

    }

    public void closeConnection()  {

        try {

            con.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

}
