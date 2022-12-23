import java.util.HashMap;

// 
// Decompiled by Procyon v0.5.36
// 

public class Color
{
    private static int count;
    private String name;
    private final int id;
    private static HashMap<Integer, String> colors;
    
    Color(final String name) {
        this.id = Color.count;
        this.name = name;
        Color.colors.put(this.id, name);
        ++Color.count;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public static HashMap<Integer, String> getColors() {
        return Color.colors;
    }
    
    static {
        Color.count = 0;
        Color.colors = new HashMap<Integer, String>();
    }
}
