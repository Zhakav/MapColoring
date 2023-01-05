import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;
import java.util.*;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class MapGUI {

    private BufferedImage image;
    private Area mapMainArea;
    private HashMap<Integer,Shape> countries;
    private HashMap<Integer,Color>  colors;
    private LinkedList<Integer>[] adjacency;
    private JLabel output = new JLabel();
    
    private boolean[] colorAvailable;
    private int[] countryColor;
    
    private Shape[] twoPoints;
    private int twoPointsCounter=0;

    private AdjacencyHandler adjacencyHandler;
    private ColorHandler colorHandler;

    public MapGUI() {

        try {

            initUI();

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }

    public final void initUI() throws Exception {

        image=readImage("S:\\programing\\Java\\Udemy\\JavaFx\\MapColoring\\src\\Maps\\World.png");
        createMap(image);
        output.addMouseMotionListener(new MotionHandler());
        twoPoints=new Shape[2];

        refresh();
        
    }

    private BufferedImage readImage(String imagePath) throws IOException {

        File file = new File(imagePath);
        image = ImageIO.read(file);
        image = resize(image , 750 * (image.getWidth() / image.getHeight()) , 750);

        return image;

    }

    private Area getMapMainArea(Color targetColor , BufferedImage map , int tolerance){

        GeneralPath generalPath=new GeneralPath();

        boolean flag = false;

        for (int x= 0 ; x<map.getWidth() ; x++){

            for (int y = 0 ; y < map.getHeight(); y++) {

                if (isIncludedColor(new Color(map.getRGB(x, y)), targetColor, tolerance)) {

                    if (flag) {

                        generalPath.lineTo(x, y);
                        generalPath.lineTo(x, y + 1);
                        generalPath.lineTo(x + 1, y + 1);
                        generalPath.lineTo(x + 1, y);
                        generalPath.lineTo(x, y);

                    } else { generalPath.moveTo(x, y); }
                    
                    flag = true;

                } else {flag = false;}

            }
                flag = false;
            }

        generalPath.closePath();
        
        return new Area(generalPath);
    }

    private static boolean isIncludedColor(Color targetColor , Color pixelColor , int tolerance){

        int rT = targetColor.getRed();
        int bT = targetColor.getBlue();
        int gT = targetColor.getGreen();

        int rP = pixelColor.getRed();
        int bP = pixelColor.getBlue();
        int gP = pixelColor.getGreen();

        return (rP - tolerance <= rT) && (rP + tolerance >= rT)
                && (bP - tolerance <= bT) && (bP + tolerance >= bT)
                && (gP - tolerance <= gT) && (gP + tolerance >= gT);

    }

    private void refresh() {
        output.setIcon(new ImageIcon(getImage()));
    }

    private BufferedImage getImage(){

        BufferedImage bufferedImage =new BufferedImage(image.getWidth()  , image.getHeight() , BufferedImage.SCALE_DEFAULT);
        Graphics2D graphic= bufferedImage.createGraphics();

        graphic.drawImage(image , 0 , 0 , output);
        graphic.setColor(Color.white);
        graphic.fill(mapMainArea);
        graphic.setColor(Color.BLACK);
        graphic.draw(mapMainArea);

        for(int i =0 ; i < countryColor.length ; i++){

            if(countryColor[i]!=-1){

                graphic.setColor(colors.get(countryColor[i]));
                graphic.fill(countries.get(i));
            }
        }
        
        try {

            Point mousePoint = MouseInfo.getPointerInfo().getLocation();
            Point labelPoint = output.getLocationOnScreen();

            int x = mousePoint.x - labelPoint.x;
            int y = mousePoint.y - labelPoint.y;

            Point pointOnImage = new Point(x, y);

            for (Shape country : countries.values()) {

                if (country.contains(pointOnImage)) {

                    graphic.setColor(Color.yellow.darker());
                    graphic.fill(country);
                    break;

                }

            }
        } catch (Exception ignored){}

        graphic.dispose();

        return bufferedImage;
    }

    private static HashMap<Integer,Shape> separateMapIntoRegions(Shape shape){

        HashMap<Integer,Shape> regions = new HashMap<>();

        PathIterator pathIterator= shape.getPathIterator(null);
        GeneralPath generalPath=new GeneralPath();

        int counter = 0;

        while (!pathIterator.isDone()){

            double[] coordinates = new double[6];

            int segmentType= pathIterator.currentSegment(coordinates);
            int windingRule = pathIterator.getWindingRule();

            generalPath.setWindingRule(windingRule);

            if(segmentType == pathIterator.SEG_MOVETO){

                generalPath =new GeneralPath();
                generalPath.setWindingRule(windingRule);
                generalPath.moveTo(coordinates[0] , coordinates[1]);

            } else if (segmentType == PathIterator.SEG_LINETO){

                generalPath.lineTo(coordinates[0] , coordinates[1]);

            } else if(segmentType == PathIterator.SEG_QUADTO){

                generalPath.quadTo( coordinates[0] , coordinates[1] , coordinates[2] ,coordinates[3]);

            } else if(segmentType == PathIterator.SEG_CUBICTO){

                generalPath.curveTo(coordinates[0] , coordinates[1] , coordinates[2] ,coordinates[3] ,coordinates[4] ,coordinates[5]);

            } else if(segmentType == PathIterator.SEG_CLOSE){

                generalPath.closePath();
                regions.putIfAbsent(counter++,new Area(generalPath));

            } else{

                System.err.println("Unexpected value! " + segmentType);

            }

            pathIterator.next();

        }

        return regions;

    }

    private static BufferedImage resize(BufferedImage image, int newWidth, int newHeight) {
        
        int width = image.getWidth();
        int height = image.getHeight();
        
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D graphic = newImage.createGraphics();
        
        graphic.drawImage(image, 0, 0, newWidth, newHeight, 0, 0, width, height, null);
        graphic.dispose();
        
        return newImage;
    }

    private Shape findCountry(int x , int y ){

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

    private void setColors(){

        colors.putIfAbsent(0,Color.RED);
        colors.putIfAbsent(1,Color.BLUE);
        colors.putIfAbsent(2,Color.GREEN);
        colors.putIfAbsent(3,Color.MAGENTA);
        colors.putIfAbsent(4,Color.YELLOW);
        colors.putIfAbsent(5,Color.PINK);
        colors.putIfAbsent(6,Color.ORANGE);

    }

    private Color colorStringToObject(String colorString){

        return switch (colorString) {
            case "Red" -> Color.RED;
            case "Blue" -> Color.BLUE;
            case "Green" -> Color.GREEN;
            case "Magenta" -> Color.MAGENTA;
            case "Yellow" -> Color.YELLOW;
            case "Pink" -> Color.PINK;
            case "Orange" -> Color.ORANGE;
            default -> null;
        };

    }

    private  static int getColorKey(final Color color , HashMap<Integer, Color> colors) {

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

    private static int getCountryKey(final Shape country , HashMap<Integer, Shape> countries) {
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

    private void createMap(BufferedImage image) {

        mapMainArea = getMapMainArea(Color.WHITE, image, 12);
        countries = separateMapIntoRegions(mapMainArea);
        colors=new HashMap<>();
        setColors();

        DataBase dataBase=new DataBase("World");
        adjacency= dataBase.readFromDatabase(countries.size());
        dataBase.closeConnection();

        Arrays.fill(this.countryColor = new int[countries.size()], -1);
        colorAvailable = new boolean[colors.size()];

    }

    private void addEdge(final Shape c1, final Shape c2) throws Exception {

        int country1Key=getCountryKey(c1 , countries);
        int country2Key=getCountryKey(c2 , countries);

        boolean condition=(country1Key != -1 &&
                country2Key != -1 &&
                country2Key != country1Key&&
                !this.adjacency[country1Key].contains(country2Key)
        );

        if ( condition){

            this.adjacency[country1Key].offerFirst(country2Key);
            this.adjacency[country2Key].offerFirst(country1Key);

            showMessageDialog(null, "Edge Added Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void colorTheCountry(final Shape country, final Color color) {

        Arrays.fill(colorAvailable, true);

        final int colorKey = getColorKey(color , colors);
        final int countryKey = getCountryKey(country , countries);

        if (countryKey != -1 && colorKey != -1) {

            for (final int neighbor :adjacency[countryKey]) {

                if (countryColor[neighbor] != -1) {

                    colorAvailable[countryColor[neighbor]] = false;

                }
            }

            if (colorAvailable[colorKey]) {

                countryColor[countryKey] = colorKey;

            }

            else { showMessageDialog(null, "You Can't Color The Country With The Same Color Of It's Neighbor!!!!", "Error", ERROR_MESSAGE);}

        }

        else {showMessageDialog(null, "Color Or Country Doesn't Exist In Our Database Please\\nPlease Add Them And Try Again.....", "Error", ERROR_MESSAGE);}

        Arrays.fill(colorAvailable, true);

    }

    private void addAdjacency(Point mouseLocation) throws Exception {

        Point labelPoint = output.getLocationOnScreen();

        int x = mouseLocation.x - labelPoint.x;
        int y = mouseLocation.y - labelPoint.y;

        Shape country = findCountry(x,y);

        if (country != null) {
            twoPoints[twoPointsCounter] = country;
            twoPointsCounter++;

            if (twoPointsCounter == 2) {

                addEdge(twoPoints[0], twoPoints[1]);
                twoPointsCounter = 0;

            }
        }

    }

    public JLabel getOutput() {return output;}

    public void resetAll() {

        Arrays.fill(this.countryColor, -1);

        refresh();

    }

    public void greedyColoring() throws InterruptedException {

        this.countryColor[0] = 0;
        Arrays.fill(colorAvailable, true);

        for (int i = 1; i < countries.size(); ++i) {

            for (final int neighbor : adjacency[i]) {

                if (countryColor[neighbor] != -1) {

                    colorAvailable[countryColor[neighbor]] = false;

                }
            }

            int color;

            for (color = 0; color <colors.size() && !colorAvailable[color]; ++color) {}

            this.countryColor[i] = color;
            Arrays.fill(colorAvailable, true);

            refresh();

        }
    }

    public void randomColoring() {

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

        refresh();

    }

    public void addAdjacencyClicked(){

        output.removeMouseListener(adjacencyHandler);
        output.removeMouseListener(colorHandler);
        adjacencyHandler=new AdjacencyHandler("Add Adjacency");
        output.addMouseListener(adjacencyHandler);

    }

    public void saveAdjacencyClicked(){

        output.removeMouseListener(colorHandler);
        output.removeMouseListener(adjacencyHandler);

        DataBase dataBase=new DataBase("World");
        dataBase.saveToDatabase(adjacency);
        dataBase.closeConnection();
        showMessageDialog(null, "Adjacency Saved Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);

    }

    public void colorClicked(String colorString){

        output.removeMouseListener(colorHandler);
        output.removeMouseListener(adjacencyHandler);
        colorHandler=new ColorHandler(colorString);
        output.addMouseListener(colorHandler);

    }


    class MotionHandler  implements MouseMotionListener  {

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

            refresh();

        }

    }

    class AdjacencyHandler extends MouseAdapter{

        String sourceName;

        AdjacencyHandler(String sourceName){
            this.sourceName=sourceName;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

            if (sourceName == "Add Adjacency") {

                try {

                    addAdjacency(e.getLocationOnScreen());

                } catch (Exception ex) {

                    ex.printStackTrace();

                }
            }


        }
    }

    class ColorHandler extends MouseAdapter {

        String colorString;
        Color colorObject;
        Shape country;

        ColorHandler(String colorString){

            this.colorString=colorString;
            colorObject = colorStringToObject(colorString);

        }

        @Override
        public void mouseClicked(MouseEvent e) {

            country=findCountry(e.getX(),e.getY());
            colorTheCountry(country,colorObject);
            refresh();

        }


    }

    ///Unused
    /*public LinkedList<Integer>[] defineAdjacency() throws Exception {

        LinkedList<Integer>[] adjacency=new LinkedList[countries.size()];

        PathIterator pathIterator=mapMainArea.getPathIterator(null);

        double previousX;
        double previousY;
        double currentX;
        double currentY;

        Shape country=null;
        Shape neighbor;

        while (!pathIterator.isDone()){

            double[] coordinates = new double[6];

            int segmentType= pathIterator.currentSegment(coordinates);

            if(segmentType == pathIterator.SEG_MOVETO){

                currentX = coordinates[0];
                currentY = coordinates[1];

                for (double x=currentX ; x<image.getWidth() ; x++ ) {

                    neighbor = findCountry((int) x, (int) currentY);

                    if(neighbor != null && !neighbor.equals(country)){

                        addEdge(neighbor,country);
                        break;
                    }

                }
                for (double x=currentX ; x>=0 ; x-- ) {

                    neighbor = findCountry((int) x, (int) currentY);

                    if(neighbor != null && !neighbor.equals(country)){

                        addEdge(neighbor,country);
                        break;
                    }

                }

                for (double y=currentY ; y<image.getHeight() ; y++ ) {

                    neighbor = findCountry((int) currentX, (int) y);

                    if(neighbor != null && !neighbor.equals(country)){

                        addEdge(neighbor,country);
                    }

                }
                for (double y=currentY ; y>=0 ; y-- ) {

                    neighbor = findCountry((int) currentX, (int) y);

                    if(neighbor != null && !neighbor.equals(country)){

                        addEdge(neighbor,country);
                        break;
                    }

                }

            } else{

                previousX = coordinates[0];
                previousY = coordinates[1];
                country=findCountry((int) previousX, (int) previousY);

            }

            pathIterator.next();

        }

        return adjacency;

    }*/


}
