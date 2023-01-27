import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

//Main JFrame
public class DrawingBoardWithMatrix extends JFrame {

    //Main Method
    public static void main(String[] args) {
        new DrawingBoardWithMatrix();
    }

    //Frame Settings
    public DrawingBoardWithMatrix() {
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Adding JComponent
        this.add(new PaintSurface(), BorderLayout.CENTER);
        this.setVisible(true);
    }

    //Setting The JComponent
    private class PaintSurface extends JComponent {

        //Creating ShapeList
        ArrayList<Shape> shapes = new ArrayList<Shape>();

        //Mouse Point
        Point startDrag, endDrag;

        public PaintSurface() {

            //MouseListener For Pressing And Releasing
            this.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent e) {
                    startDrag = new Point(e.getX(), e.getY());
                    endDrag = startDrag;
                    repaint();
                }

                public void mouseReleased(MouseEvent e) {

                    //Adding New Shape To The ShapeList
                    Shape r = makeRectangle(startDrag.x, startDrag.y, e.getX(), e.getY());
                    shapes.add(r);

                    startDrag = null;
                    endDrag = null;
                    repaint();

                }
            });

            //MouseListener For Mouse Dragging
            this.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {

                    endDrag = new Point(e.getX(), e.getY());
                    repaint();

                }
            });
        }

        //Setting The Background
        private void paintBackground(Graphics2D g2){

            g2.setPaint(Color.LIGHT_GRAY);

        }

        //Painting Method
        public void paint(Graphics g) {

            //Creating Graphic
            Graphics2D g2 = (Graphics2D) g;

            //Set RenderingHint
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            paintBackground(g2);

            // Creating List Of Colors
            Color[] colors = { Color.YELLOW, Color.MAGENTA, Color.CYAN , Color.RED, Color.BLUE, Color.PINK};
            int colorIndex = 0;

            //Setting Stroke
            g2.setStroke(new BasicStroke(3));

            //Setting Composite
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));

            //Drawing Shapes
            for (Shape s : shapes) {
                g2.setPaint(Color.BLACK);
                g2.draw(s);
                g2.setPaint(colors[(colorIndex++) % 6]);
                g2.fill(s);
            }

            //Drawing Dragging Rectangle
            if (startDrag != null && endDrag != null) {
                g2.setPaint(Color.LIGHT_GRAY);
                Shape r = makeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                g2.draw(r);
            }
        }

        private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
            return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
        }
    }
}