import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class MapLogic {

    protected static boolean addEdge(final Shape c1, final Shape c2, HashMap<Integer,Shape> countries , LinkedList<Integer>[] adjacency)  {

        int country1Key=Country.getCountryKey(c1 , countries);
        int country2Key=Country.getCountryKey(c2 , countries);

        boolean condition=(country1Key != -1 &&
                country2Key != -1 &&
                country2Key != country1Key&&
                !adjacency[country1Key].contains(country2Key)
        );

        if ( condition){

            adjacency[country1Key].offerFirst(country2Key);
            adjacency[country2Key].offerFirst(country1Key);

            return true;

        } else{ return false;}

    }

    protected static boolean colorTheCountry(final Shape country, final Color color ,
                                          boolean[] colorAvailable , LinkedList<Integer>[] adjacency,
                                          HashMap<Integer,Shape> countries,HashMap<Integer,Color> colors,
                                          int[] countryColor) {

        Arrays.fill(colorAvailable, true);

        boolean result=false;

        final int colorKey = MyColor.getColorKey(color , colors);
        final int countryKey = Country.getCountryKey(country , countries);

        if (countryKey != -1 && colorKey != -1 && adjacency[countryKey]!=null) {

            for (final int neighbor :adjacency[countryKey]) {

                if (countryColor[neighbor] != -1) {

                    colorAvailable[countryColor[neighbor]] = false;

                }
            }

            if (colorAvailable[colorKey]) {

                countryColor[countryKey] = colorKey;

                result=true;

            }

            else { showMessageDialog(null, "You Can't Color The Country With The Same Color Of It's Neighbor!!!!", "Error", ERROR_MESSAGE);}

        }

        else {showMessageDialog(null, "Color Or Country Doesn't Exist In Our Database Please\\nPlease Add Them And Try Again.....", "Error", ERROR_MESSAGE);}

        Arrays.fill(colorAvailable, true);

        return result;

    }


    protected static void resetColors(int[] countryColor) {

        Arrays.fill(countryColor, -1);

    }

//    protected static void resetAll( LinkedList<Integer>[] adjacency, HashMap<Integer,Shape> countries
//            ,HashMap<Integer,Color> colors,int[] countryColor){
//
//
//
//
//    }

    protected static void randomColoring(boolean[] colorAvailable , LinkedList<Integer>[] adjacency,
                                         HashMap<Integer,Shape> countries,HashMap<Integer,Color> colors,
                                         int[] countryColor) {

        Arrays.fill(colorAvailable, true);
        final Random random = new Random();

        for (int i = 0; i < countries.size(); ++i) {

            for (final int neighbor : adjacency[i]) {

                if (countryColor[neighbor] != -1) {

                    colorAvailable[countryColor[neighbor]] = false;

                }
            }

            int color;

            do {

                color = random.nextInt(colors.size());

                if (colorAvailable[color]) {

                    countryColor[i] = color;
                    break;

                }

            } while (!colorAvailable[color]);

            Arrays.fill(colorAvailable, true);

        }

    }

    protected static boolean removeEdge(final Shape c1, final Shape c2, HashMap<Integer,Shape> countries , LinkedList<Integer>[] adjacency)  {

        int country1Key=Country.getCountryKey(c1 , countries);
        int country2Key=Country.getCountryKey(c2 , countries);

        boolean condition=(country1Key != -1 &&
                country2Key != -1 &&
                country2Key != country1Key&&
                adjacency[country1Key].contains(country2Key)
        );

        if ( condition){

            adjacency[country1Key].remove(country2Key);
            adjacency[country2Key].remove(country1Key);


            return true;

        } else{ return false;}

    }

    protected static boolean removeShape(final Shape country,HashMap<Integer,Shape> countries , LinkedList<Integer>[] adjacency){

        boolean result=false;

        countries.remove(country);

        for (Integer neighbor:adjacency[Country.getCountryKey(country,countries)]){

            removeEdge(country,countries.get(neighbor),countries,adjacency);

        }

        return result;

    }

    protected static void greedyColoring(boolean[] colorAvailable , LinkedList<Integer>[] adjacency,
                                         HashMap<Integer,Shape> countries,HashMap<Integer,Color> colors,
                                         int[] countryColor) throws InterruptedException {

        countryColor[0] = 0;
        Arrays.fill(colorAvailable, true);

        for (int i = 1; i < countries.size(); ++i) {

            for (final int neighbor : adjacency[i]) {

                if (countryColor[neighbor] != -1) {

                    colorAvailable[countryColor[neighbor]] = false;

                }
            }

            int color;

            for (color = 0; color <colors.size() && !colorAvailable[color]; ++color) {}

            countryColor[i] = color;
            Arrays.fill(colorAvailable, true);

        }
    }

}
