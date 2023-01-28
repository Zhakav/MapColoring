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

import static javax.swing.JOptionPane.showMessageDialog;

public class MapGUI {

    private BufferedImage image;
    private Area mapMainArea;
    private HashMap<Integer,Shape> countries;
    private HashMap<Integer,Color>  colors;
    private LinkedList<Integer>[] adjacency;
    private final JLabel output = new JLabel();
    private  String mapName;
    
    private boolean[] colorAvailable;
    private int[] countryColor;
    
    private Shape[] twoPoints;
    private int twoPointsCounter=0;

    private AdjacencyHandler adjacencyHandler;
    private ColorHandler colorHandler;

    public MapGUI(String mapName) {

        try {

            initUI(mapName);

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }

    public final void initUI(String mapName) throws Exception {


        String mapFileName;

        switch (mapName) {
            case "IRAN" -> {
                mapFileName = "IR.jpg";
                this.mapName = "IR";
            }
            case "UNITED STATES" -> {
                mapFileName = "US.jpeg";
                this.mapName = "US";
            }
            case "WORLD" -> {
                mapFileName = "World.png";
                this.mapName = "World";
            }
            default -> throw new Exception("Invalid Unused.Map!!!");
        }

        image=readImage("S:\\programing\\Java\\Udemy\\JavaFx\\MapColoring\\src\\Maps\\" + mapFileName);
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

    private void createMap(BufferedImage image) {

        mapMainArea = getMapMainArea(Color.WHITE, image, 12);
        countries = separateMapIntoRegions(mapMainArea);
        colors=new HashMap<>();
        MyColor.setColors(colors);

        DataBase dataBase=new DataBase(mapName);
        adjacency= dataBase.readFromDatabase(countries.size());
        dataBase.closeConnection();

        Arrays.fill(this.countryColor = new int[countries.size()], -1);
        colorAvailable = new boolean[colors.size()];

    }


    private void addAdjacency(Point mouseLocation) {

        Point labelPoint = output.getLocationOnScreen();

        int x = mouseLocation.x - labelPoint.x;
        int y = mouseLocation.y - labelPoint.y;

        Shape country = Country.findCountry(x,y,countries);

        if (country != null) {
            twoPoints[twoPointsCounter] = country;
            twoPointsCounter++;

            if (twoPointsCounter == 2) {

                if(MapLogic.addEdge(twoPoints[0], twoPoints[1],countries,adjacency))
                    showMessageDialog(null, "Edge Added Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);

                twoPointsCounter = 0;

            }
        }

    }

    public JLabel getOutput() {return output;}

    public void addAdjacencyClicked(){

        output.removeMouseListener(adjacencyHandler);
        output.removeMouseListener(colorHandler);
        adjacencyHandler=new AdjacencyHandler("Add Adjacency");
        output.addMouseListener(adjacencyHandler);

    }

    public void saveAdjacencyClicked(){

        output.removeMouseListener(colorHandler);
        output.removeMouseListener(adjacencyHandler);

        DataBase dataBase=new DataBase(mapName);
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

    public int[] getCountryColor() {
        return countryColor;
    }

    public String getMapName() {
        return mapName;
    }

    public void randomColoringClicked() {

        MapLogic.randomColoring(colorAvailable,adjacency,countries,colors,countryColor);
        refresh();
    }

    public void resetAllClicked() {

        MapLogic.resetColors(countryColor);
        refresh();

    }

    public void greedyColoringClicked() throws InterruptedException {

        MapLogic.greedyColoring(colorAvailable,adjacency,countries,colors,countryColor);
        refresh();

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

            if (Objects.equals(sourceName, "Add Adjacency")) {

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
            colorObject = MyColor.colorStringToObject(colorString);

        }

        @Override
        public void mouseClicked(MouseEvent e) {

            country=Country.findCountry(e.getX(),e.getY(),countries);
            MapLogic.colorTheCountry(country,colorObject,colorAvailable,adjacency,countries,colors,countryColor);
            refresh();

        }


    }

}
