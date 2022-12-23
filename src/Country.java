import java.util.HashMap;

// 
// Decompiled by Procyon v0.5.36
// 

public class Country
{
    private static int count;
    private final int id;
    private String name;
    private static HashMap<Integer, String> countries;
    
    Country(final String name) {
        this.id = Country.count;
        this.name = name;
        Country.countries.put(this.id, name);
        ++Country.count;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getId() {
        return this.id;
    }
    
    public static int getCount() {
        return Country.count;
    }
    
    static {
        Country.count = 0;
        Country.countries = new HashMap<Integer, String>();
    }
}
