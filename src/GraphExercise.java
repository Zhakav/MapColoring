import java.util.Iterator;
import java.util.Arrays;
import java.util.LinkedList;

// 
// Decompiled by Procyon v0.5.36
// 

public class GraphExercise
{
    private final int V;
    private LinkedList<Integer>[] adjacency;
    
    GraphExercise(final int v) {
        this.V = v;
        this.adjacency = (LinkedList<Integer>[])new LinkedList[this.V];
        for (int i = 0; i < this.V; ++i) {
            this.adjacency[i] = new LinkedList<Integer>();
        }
    }
    
    public void addEdge(final int v1, final int v2) {
        this.adjacency[v1].addFirst(v2);
        this.adjacency[v2].addFirst(v1);
    }
    
    public void greedyColoring() {
        final int[] result = new int[this.V];
        Arrays.fill(result, -1);
        result[0] = 0;
        final boolean[] available = new boolean[this.V];
        Arrays.fill(available, true);
        for (int i = 1; i < this.V; ++i) {
            for (final int X : this.adjacency[i]) {
                if (result[X] != -1) {
                    available[result[X]] = false;
                }
            }
            int color;
            for (color = 0; color < this.V && !available[color]; ++color) {}
            result[i] = color;
            Arrays.fill(available, true);
        }
        for (int u = 0; u < this.V; ++u) {
            //System.out.println(invokedynamic(makeConcatWithConstants:(II)Ljava/lang/String;, u, result[u]));
        }
    }
    
    public static void main(final String[] args) {
        final GraphExercise g1 = new GraphExercise(5);
        g1.addEdge(0, 1);
        g1.addEdge(0, 2);
        g1.addEdge(1, 2);
        g1.addEdge(1, 3);
        g1.addEdge(2, 3);
        g1.addEdge(3, 4);
        System.out.println("Coloring of graph 1");
        g1.greedyColoring();
        System.out.println();
        final GraphExercise g2 = new GraphExercise(5);
        g2.addEdge(0, 1);
        g2.addEdge(0, 2);
        g2.addEdge(1, 2);
        g2.addEdge(1, 4);
        g2.addEdge(2, 4);
        g2.addEdge(4, 3);
        System.out.println("Coloring of graph 2 ");
        g2.greedyColoring();
    }
}
