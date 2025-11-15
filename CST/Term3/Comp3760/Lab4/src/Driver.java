public class Driver {
    public static void main(String[] args)
    {

        String[] labels = {"a", "b", "c", "d", "e", "f", "g", "h"};

        Graph G = new Graph(labels, false);

        G.addEdge("a", "b");
        G.addEdge("a", "e");
        G.addEdge("a", "f");
        G.addEdge("b", "f");
        G.addEdge("b", "g");
        G.addEdge("c", "d");
        G.addEdge("c", "g");
        G.addEdge("d", "h");
        G.addEdge("e", "f");
        G.addEdge("g", "h");

        System.out.println("Adjacency Matrix:");
        System.out.println(G);
        System.out.println();

        System.out.println("DFS:");
        G.runDFS(false);
        System.out.println("Last DFS order: " + G.getLastDFSOrder());
        System.out.println("Last DFS dead-end order: " + G.getLastDFSDeadEndOrder());
        System.out.println();

        System.out.println("BFS:");
        G.runBFS(false);
        System.out.println("Last BFS order: " + G.getLastBFSOrder());
    }
}
