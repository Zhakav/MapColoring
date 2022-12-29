import java.awt.*;
import java.util.*;

public class MapLogic {

    private LinkedList<Integer>[] adjacency;
    private int[] countryColor;
    private boolean[] colorAvailable;
    private HashMap<Integer, Color> colors;
    private HashMap<Integer, Shape> countries;
    private int colorCount;
    private int countryCount;

    MapLogic(MapGUI map){

        countries = map.getCountries();
        setColors();
        countryCount = countries.size();
        colorCount = colors.size();

    }

    private void setColors(){

        colors.putIfAbsent(0,Color.RED);
        colors.putIfAbsent(1,Color.BLUE);
        colors.putIfAbsent(2,Color.GREEN);
        colors.putIfAbsent(3,Color.MAGENTA);
        colors.putIfAbsent(4,Color.YELLOW);
        colors.putIfAbsent(5,Color.PINK);
        colors.putIfAbsent(6,Color.ORANGE);

    }

    private int getColorKey(final Color color) {
        int result = -1;
        if (this.colors.containsValue(color)) {
            for (final java.util.Map.Entry<Integer, Color> entry : this.colors.entrySet()) {
                if (Objects.equals(entry.getValue(), color)) {
                    result = entry.getKey();
                    break;
                }
            }
        }
        return result;
    }

    private int getCountryKey(final Shape country) {
        int result = -1;
        if (this.colors.containsValue(country)) {
            for (final java.util.Map.Entry<Integer, Shape> entry : this.countries.entrySet()) {
                if (Objects.equals(entry.getValue(), country)) {
                    result = entry.getKey();
                    break;
                }
            }
        }
        return result;
    }

    public void createMap() {
        this.adjacency = (LinkedList<Integer>[])new LinkedList[this.countryCount];
        for (int i = 0; i < this.countryCount; ++i) {
            this.adjacency[i] = new LinkedList<Integer>();
        }
        Arrays.fill(this.countryColor = new int[this.countryCount], -1);
        this.colorAvailable = new boolean[this.colorCount];
    }

    public void resetAll() {
        Arrays.fill(this.countryColor, -1);
    }

    public void addEdge(final Shape c1, final Shape c2) {
        if (this.getCountryKey(c1) != -1 && this.getCountryKey(c2) != -1) {
            this.adjacency[this.getCountryKey(c1)].offerFirst(this.getCountryKey(c2));
            this.adjacency[this.getCountryKey(c2)].offerFirst(this.getCountryKey(c1));
        }
        else {
            System.out.println("The country doesnt Exist in our database!!!!");
        }
    }

    public void greedyColoring() throws InterruptedException {
        this.countryColor[0] = 0;
        Arrays.fill(this.colorAvailable, true);
        for (int i = 1; i < this.countryCount; ++i) {
            for (final int neighbor : this.adjacency[i]) {
                if (this.countryColor[neighbor] != -1) {
                    this.colorAvailable[this.countryColor[neighbor]] = false;
                }
            }
            int color;
            for (color = 0; color < this.colorCount && !this.colorAvailable[color]; ++color) {}
            if (this.colorCount <= color) {
                System.out.println("Not enough colors to coloring the.....\nPlease add more colors and try again");
                Thread.sleep(1000L);
                this.resetAll();
                break;
            }
            this.countryColor[i] = color;
            Arrays.fill(this.colorAvailable, true);
        }
    }

    public void colorTheCountry(final Shape country, final Color color) {
        Arrays.fill(this.colorAvailable, true);
        final int colorKey = this.getColorKey(color);
        final int countryKey = this.getCountryKey(country);
        if (countryKey != -1 && colorKey != -1) {
            for (final int neighbor : this.adjacency[countryKey]) {
                if (this.countryColor[neighbor] != -1) {
                    this.colorAvailable[this.countryColor[neighbor]] = false;
                }
            }
            if (this.colorAvailable[colorKey]) {
                this.countryColor[countryKey] = colorKey;
            }
            else {
                System.out.println("You Can't Color The Country With The Same Color Of It's Neighbor!!!!");
            }
        }
        else {
            System.out.println("Color Or Country Doesn't Exist In Our Database Please\nPlease Add Them And Try Again.....");
        }
        Arrays.fill(this.colorAvailable, true);
    }

    public void randomColoring() {
        Arrays.fill(this.colorAvailable, true);
        final Random random = new Random();
        for (int i = 0; i < this.countryCount; ++i) {
            for (final int neighbor : this.adjacency[i]) {
                if (this.countryColor[neighbor] != -1) {
                    this.colorAvailable[this.countryColor[neighbor]] = false;
                }
            }
            int color;
            do {
                color = random.nextInt(this.colorCount);
                if (this.colorAvailable[color]) {
                    this.countryColor[i] = color;
                    break;
                }
            } while (!this.colorAvailable[color]);
            Arrays.fill(this.colorAvailable, true);
        }
    }

}
