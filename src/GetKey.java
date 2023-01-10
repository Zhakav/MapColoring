import java.awt.*;
import java.util.HashMap;
import java.util.Objects;

public class GetKey {

    public   static int getColorKey(final Color color , HashMap<Integer, Color> colors) {

        int result = -1;

        if (colors.containsValue(color))
        {
            for (final java.util.Map.Entry<Integer, Color> entry : colors.entrySet())
            {
                if (Objects.equals(entry.getValue(), color)) {

                    result = entry.getKey();

                    break;
                }
            }
        }

        return result;
    }

    public static int getCountryKey(final Shape country , HashMap<Integer, Shape> countries) {
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

}
