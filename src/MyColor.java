import java.util.HashMap;

// 
// Decompiled by Procyon v0.5.36
// 

public class MyColor
{
    private static int count;
    private String name;
    private final int id;
    private static HashMap<Integer, String> colors;
    
    MyColor(final String name) {
        this.id = MyColor.count;
        this.name = name;
        MyColor.colors.put(this.id, name);
        ++MyColor.count;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public static HashMap<Integer, String> getColors() {
        return MyColor.colors;
    }
    
    static {
        MyColor.count = 0;
        MyColor.colors = new HashMap<Integer, String>();
    }
}
