import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;

public class MapCreatingFrame extends JFrame {

    private JLabel label;
    private BufferedImage image;
    private LinkedHashMap<Integer,Shape> countries;
    private int countryCount=0;

    MapCreatingFrame() {

        countries=new LinkedHashMap<>();

        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);


        label=new JLabel();
        label.setSize(500,500);
        add(label,BorderLayout.CENTER);

        image =createImage();

        JPanel controller=new JPanel(new FlowLayout());
        add(controller,BorderLayout.SOUTH);

        JButton addShape=new JButton("Add Shape");
        addShape.setSize(50,50);
        controller.add(addShape);

        addShape.addActionListener(ae->addShapeClicked());

        setSize(1000,700);

        refresh();

    }

    private void refresh(){
        label.setIcon(new ImageIcon(image));
    }

    private BufferedImage createImage(){

        BufferedImage result=new BufferedImage(1000  , 700, BufferedImage.SCALE_DEFAULT);
        Graphics2D graphic= result.createGraphics();

        graphic.setColor(new Color(60,113,160));
        graphic.fillRect(0, 0, 1000, 700);
        graphic.setComposite(AlphaComposite.DstIn);
        graphic.drawImage(image, 0, 0, 1000, 700, 0, 0, 1000, 700, null);
        graphic.dispose();

        return result;

    }

    private void addShapeClicked(){

        Graphics2D graphic= image.createGraphics();
        Rectangle rectangle = new Rectangle(120,30);
        rectangle.setLocation(50,50);
        graphic.setColor(Color.BLACK);
        graphic.draw(rectangle);
        graphic.setColor(Color.WHITE);
        graphic.fill(rectangle);
        graphic.dispose();

        countries.put(countryCount++,rectangle);

        addMouseListener(new MouseHandler());

        refresh();

    }

    private void repaint(Point location , Rectangle country){

        Graphics2D graphic= image.createGraphics();

        graphic.setColor(new Color(60,113,160));
        graphic.draw(country);
        graphic.fill(country);
        country.setLocation((int) (location.getX() - country.getWidth()/2) , (int) location.getY() );
        graphic.setColor(Color.WHITE);
        graphic.fill(country);
        graphic.setColor(Color.BLACK);
        graphic.draw(country);
        graphic.dispose();

        refresh();

    }

    class MouseHandler extends MouseAdapter{

        private Rectangle selectedCountry=null;

        @Override
        public void mouseClicked(MouseEvent e) {


            Point point=e.getPoint();

            if(selectedCountry!=null){

                if(Country.findCountry((int) point.getX(), (int) point.getY(),countries)==null) {

                    repaint(point, selectedCountry);
                    selectedCountry = null;

                }

            } else{

                selectedCountry = (Rectangle) Country.findCountry((int) point.getX(), (int) point.getY(),countries);

            }



        }
    }



}
