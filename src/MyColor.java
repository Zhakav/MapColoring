import java.awt.*;
import java.util.HashMap;

// 
// Decompiled by Procyon v0.5.36
// 

public class MyColor
{

    public static void setColors(HashMap<Integer,Color> colors){

        colors.putIfAbsent(0, Color.RED);
        colors.putIfAbsent(1,Color.BLUE);
        colors.putIfAbsent(2,Color.GREEN);
        colors.putIfAbsent(3,Color.MAGENTA);
        colors.putIfAbsent(4,Color.YELLOW);
        colors.putIfAbsent(5,Color.PINK);
        colors.putIfAbsent(6,Color.ORANGE);

    }

    public static Color colorStringToObject(String colorString){

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

}
