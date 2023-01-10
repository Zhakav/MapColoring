import java.awt.*;
import java.util.HashMap;

public class Country
{

    public static Shape findCountry(int x , int y , HashMap<Integer,Shape> countries){

        Shape country=null;

        Point point=new Point(x,y);

        for (Shape X : countries.values()) {

            if (X.contains(point)) {

                country = X;
                break;
            }

        }

        return country;
    }

}
