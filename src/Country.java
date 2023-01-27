import java.awt.*;
import java.util.HashMap;
import java.util.Objects;

public class Country
{
    protected static int getCountryKey(final Shape country , HashMap<Integer, Shape> countries) {
        int result = -1;

        if (countries.containsValue(country)) {

            for (final java.util.Map.Entry<Integer, Shape> entry : countries.entrySet()) {

                if (Objects.equals(entry.getValue(), country)) {

                    result = entry.getKey();

                    break;

                }
            }
        }

        return result;

    }

    protected static Shape findCountry(int x , int y , HashMap<Integer,Shape> countries){

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
