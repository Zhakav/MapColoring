
public class Main
{
    public static void main(final String[] args) throws InterruptedException {

        final Color cl1 = new Color("Red");
        final Color cl2 = new Color("Green");
        final Color cl3 = new Color("Blue");
        final Color cl4 = new Color("Yellow");
        final Color cl5 = new Color("Purple");

        final Country ct1 = new Country("Portugal");
        final Country ct2 = new Country("Spain");
        final Country ct3 = new Country("France");
        final Country ct4 = new Country("Belgium");
        final Country ct5 = new Country("Netherlands");
        final Country ct6 = new Country("Germany");
        final Country ct7 = new Country("Switzerland");
        final Country ct8 = new Country("Italy");
        final Country ct9 = new Country("Denmark");
        final Country ct10 = new Country("Austria");

        final Map map = new Map();

        map.addColor(cl1);
        map.addColor(cl2);
        map.addColor(cl3);
        map.addColor(cl4);
        map.addColor(cl5);

        map.addCountry(ct1);
        map.addCountry(ct2);
        map.addCountry(ct3);
        map.addCountry(ct4);
        map.addCountry(ct5);
        map.addCountry(ct6);
        map.addCountry(ct7);
        map.addCountry(ct8);
        map.addCountry(ct9);
        map.addCountry(ct10);

        map.createMap();

        map.addEdge(ct1, ct2);
        map.addEdge(ct2, ct3);
        map.addEdge(ct3, ct8);
        map.addEdge(ct3, ct7);
        map.addEdge(ct3, ct4);
        map.addEdge(ct3, ct6);
        map.addEdge(ct4, ct5);
        map.addEdge(ct4, ct6);
        map.addEdge(ct5, ct6);
        map.addEdge(ct6, ct7);
        map.addEdge(ct6, ct9);
        map.addEdge(ct6, ct10);
        map.addEdge(ct7, ct8);
        map.addEdge(ct7, ct10);
        map.addEdge(ct8, ct10);

        map.greedyColoring();

        map.resetCountryColor(ct3);

        map.resetAll();

        map.randomColoring();

        map.printMap();
    }
}
