import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;

public class MapGUI {

    private JComponent UI =null;
    File file;
    BufferedImage image;
    Area mapMainArea;
    HashMap<Integer,Shape> countries;
    JLabel output = new JLabel();
    private int height;
    private int width;

    public MapGUI() {

        try {

            initUI();

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }

    public final void initUI() throws Exception {


        file = new File("S:\\programing\\Java\\Udemy\\JavaFx\\MapColoring\\src\\Maps\\World.png");
        image = ImageIO.read(file);
        width = image.getWidth();
        height = image.getHeight();
        image = resize(image , 750 * (width / height) , 750);
        width = image.getWidth();
        height = image.getHeight();
        mapMainArea = getMapMainArea(Color.WHITE, image, 12);
        countries = separateMapIntoRegions(mapMainArea);

        output.addMouseMotionListener(new Handler());

        refresh();



    }

    public Area getMapMainArea(Color targetColor , BufferedImage map , int tolerance){

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

                    } else {

                        generalPath.moveTo(x, y);

                    }

                    flag = true;

                } else {
                    flag = false;
                }

            }

                flag = false;

            }

        generalPath.closePath();


        return new Area(generalPath);

    }

    public static boolean isIncludedColor(Color targetColor , Color pixelColor , int tolerance){

        int rT = targetColor.getRed();
        int bT = targetColor.getBlue();
        int gT = targetColor.getGreen();

        int rP = pixelColor.getRed();
        int bP = pixelColor.getBlue();
        int gP = pixelColor.getGreen();

        boolean result = (rP - tolerance <= rT) && (rP + tolerance >= rT)
                && (bP - tolerance <= bT) && (bP + tolerance >= bT)
                && (gP - tolerance <= gT) && (gP + tolerance >= gT);

        return result;

    }

    private void refresh() {
        output.setIcon(new ImageIcon(getImage()));
    }

    private BufferedImage getImage(){

        BufferedImage bufferedImage =new BufferedImage(width  , height , BufferedImage.SCALE_DEFAULT);

        Graphics2D graphic= bufferedImage.createGraphics();

        graphic.drawImage(image , 0 , 0 , output);
        graphic.setColor(Color.RED.brighter());
        graphic.fill(mapMainArea);
        graphic.setColor(Color.BLACK);
        graphic.draw(mapMainArea);

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
        } catch (Exception e){}

        graphic.dispose();

        return bufferedImage;
    }

    public static HashMap<Integer,Shape> separateMapIntoRegions(Shape shape){

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

    class Handler extends MouseMotionAdapter{

        @Override
        public void mouseMoved(MouseEvent e) {

            refresh();

        }
    }

    public JLabel getOutput() {
        return output;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }

    public HashMap<Integer, Shape> getCountries() {
        return countries;
    }
}
