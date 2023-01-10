package Unsused;

import java.util.Objects;
import java.util.Random;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

// 
// Decompiled by Procyon v0.5.36
// 

public class Map{
//{
//    private LinkedList<Integer>[] adjacency;
//    private int[] countryColor;
//    private boolean[] colorAvailable;
//    private int colorCount;
//    private int countryCount;
//    private HashMap<Integer, MyColor> colors;
//    private HashMap<Integer, Country> countries;
//
//    Map() {
//        this.colorCount = 0;
//        this.countryCount = 0;
//        this.colors = new HashMap<Integer, MyColor>();
//        this.countries = new HashMap<Integer, Country>();
//    }
//
//    public void createMap() {
//        this.adjacency = (LinkedList<Integer>[])new LinkedList[this.countryCount];
//        for (int i = 0; i < this.countryCount; ++i) {
//            this.adjacency[i] = new LinkedList<Integer>();
//        }
//        Arrays.fill(this.countryColor = new int[this.countryCount], -1);
//        this.colorAvailable = new boolean[this.colorCount];
//    }
//
//    public void addCountry(final Country country) {
//        this.countries.putIfAbsent(this.countryCount, country);
//        ++this.countryCount;
//    }
//
//    public void addColor(final MyColor color) {
//        this.colors.putIfAbsent(this.colorCount, color);
//        ++this.colorCount;
//    }
//
//    public void resetCountryColor(final Country country) {
//        final int countryKey = this.getCountryKey(country);
//        if (countryKey != -1) {
//            this.countryColor[countryKey] = -1;
//        }
//        else {
//            System.out.println("Country Doesn't Exist In Our Database\nPlease Add It First And Try Again");
//        }
//    }
//
//    public void resetAll() {
//        Arrays.fill(this.countryColor, -1);
//    }
//
//    public void addEdge(final Country c1, final Country c2) {
//        if (this.getCountryKey(c1) != -1 && this.getCountryKey(c2) != -1) {
//            this.adjacency[this.getCountryKey(c1)].offerFirst(this.getCountryKey(c2));
//            this.adjacency[this.getCountryKey(c2)].offerFirst(this.getCountryKey(c1));
//        }
//        else {
//            System.out.println("The country doesnt Exist in our database!!!!");
//        }
//    }
//
//    public void greedyColoring() throws InterruptedException {
//        this.countryColor[0] = 0;
//        Arrays.fill(this.colorAvailable, true);
//        for (int i = 1; i < this.countryCount; ++i) {
//            for (final int neighbor : this.adjacency[i]) {
//                if (this.countryColor[neighbor] != -1) {
//                    this.colorAvailable[this.countryColor[neighbor]] = false;
//                }
//            }
//            int color;
//            for (color = 0; color < this.colorCount && !this.colorAvailable[color]; ++color) {}
//
//            this.countryColor[i] = color;
//            Arrays.fill(this.colorAvailable, true);
//            for (final int neighbor2 : this.adjacency[i]) {
//                if (this.countryColor[neighbor2] != -1) {
//                    this.colorAvailable[this.countryColor[neighbor2]] = false;
//                }
//            }
//        }
//    }
//
//    public void colorTheCountry(final Country country, final MyColor color) {
//        Arrays.fill(this.colorAvailable, true);
//        final int colorKey = this.getColorKey(color);
//        final int countryKey = this.getCountryKey(country);
//        if (countryKey != -1 && colorKey != -1) {
//            for (final int neighbor : this.adjacency[countryKey]) {
//                if (this.countryColor[neighbor] != -1) {
//                    this.colorAvailable[this.countryColor[neighbor]] = false;
//                }
//            }
//            if (this.colorAvailable[colorKey]) {
//                this.countryColor[countryKey] = colorKey;
//            }
//            else {
//                System.out.println("You Can't Color The Country With The Same Color Of It's Neighbor!!!!");
//            }
//        }
//        else {
//            System.out.println("Color Or Country Doesn't Exist In Our Database Please\nPlease Add Them And Try Again.....");
//        }
//        Arrays.fill(this.colorAvailable, true);
//    }
//
//    public void randomColoring() {
//        Arrays.fill(this.colorAvailable, true);
//        final Random random = new Random();
//        for (int i = 0; i < this.countryCount; ++i) {
//            for (final int neighbor : this.adjacency[i]) {
//                if (this.countryColor[neighbor] != -1) {
//                    this.colorAvailable[this.countryColor[neighbor]] = false;
//                }
//            }
//            int color;
//            do {
//                color = random.nextInt(this.colorCount);
//                if (this.colorAvailable[color]) {
//                    this.countryColor[i] = color;
//                    break;
//                }
//            } while (!this.colorAvailable[color]);
//            Arrays.fill(this.colorAvailable, true);
//        }
//    }
//
//    public void printMap() {
//        for (int i = 0; i < this.countryCount; ++i) {
//            if (this.countryColor[i] != -1) {
//                System.out.println( this.countries.get(i).getName() + "----------> " + this.colors.get(this.countryColor[i]).getName());
//            }
//            else {
//                System.out.println( this.countries.get(i).getName() + "----------> Uncolored" );
//            }
//        }
//    }
//
//    private int getCountryKey(final Country country) {
//        int result = -1;
//        if (this.countries.containsValue(country)) {
//            for (final java.util.Map.Entry<Integer, Country> entry : this.countries.entrySet()) {
//                if (Objects.equals(entry.getValue(), country)) {
//                    result = entry.getKey();
//                    break;
//                }
//            }
//        }
//        return result;
//    }
//
//    private int getColorKey(final MyColor color) {
//        int result = -1;
//        if (this.colors.containsValue(color)) {
//            for (final java.util.Map.Entry<Integer, MyColor> entry : this.colors.entrySet()) {
//                if (Objects.equals(entry.getValue(), color)) {
//                    result = entry.getKey();
//                    break;
//                }
//            }
//        }
//        return result;
//    }
}
