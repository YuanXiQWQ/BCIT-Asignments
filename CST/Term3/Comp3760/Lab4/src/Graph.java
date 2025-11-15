import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Adjacency-matrix graph implementation supporting directed or undirected edges and
 * recording DFS/BFS traversal orders.
 *
 * @author Jerry Xing | Student ID: A01354731
 */
public class Graph {
    /** Labels associated with each vertex index. */
    private final String[] vertexLabels;
    /** Adjacency matrix storing edge presence between vertices. */
    private final int[][] adjacencyMatrix;
    /** Indicates whether edges are treated as directed. */
    private final boolean directed;
    /** Most recent DFS visitation order as labels. */
    private String lastDFSOrder;
    /** Most recent DFS dead-end (post-order) sequence as labels. */
    private String lastDFSDeadEndOrder;
    /** Most recent BFS visitation order as labels. */
    private String lastBFSOrder;

    /**
     * Creates a graph backed by an adjacency matrix and copies the supplied vertex
     * labels.
     *
     * @param vertexLabels labels indexed by vertex position
     * @param isDirected   {@code true} for a directed graph, {@code false} for an
     *                     undirected graph
     * @throws IllegalArgumentException if {@code vertexLabels} is {@code null}
     */
    public Graph(String[] vertexLabels, boolean isDirected)
    {
        this.vertexLabels = new String[vertexLabels.length];
        System.arraycopy(vertexLabels, 0, this.vertexLabels, 0, vertexLabels.length);

        int n = vertexLabels.length;
        this.adjacencyMatrix = new int[n][n];
        this.directed = isDirected;
        this.lastDFSOrder = null;
        this.lastDFSDeadEndOrder = null;
        this.lastBFSOrder = null;
    }

    /**
     * Indicates whether this graph treats edges as directed.
     *
     * @return {@code true} if directed, otherwise {@code false}
     */
    public boolean isDirected()
    {
        return directed;
    }

    /**
     * Adds an edge between two labeled vertices if both labels exist. For undirected
     * graphs the reciprocal edge is also created.
     *
     * @param A label of the source vertex
     * @param B label of the destination vertex
     */
    public void addEdge(String A, String B)
    {
        int indexA = indexOfLabel(A);
        int indexB = indexOfLabel(B);

        if(indexA == -1 || indexB == -1)
        {
            return;
        }

        adjacencyMatrix[indexA][indexB] = 1;

        if(!directed)
        {
            adjacencyMatrix[indexB][indexA] = 1;
        }
    }

    /**
     * Reports the number of vertices in the graph.
     *
     * @return vertex count
     */
    public int size()
    {
        return vertexLabels.length;
    }

    /**
     * Retrieves the label associated with a vertex index.
     *
     * @param v vertex index
     * @return vertex label at the given index
     */
    public String getLabel(int v)
    {
        return vertexLabels[v];
    }

    /**
     * Builds a string view of the adjacency matrix with each row prefixed by its vertex
     * label.
     *
     * @return adjacency matrix representation
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        int n = size();

        for(int i = 0; i < n; i++)
        {
            sb.append(vertexLabels[i]).append(":");
            for(int j = 0; j < n; j++)
            {
                sb.append(" ").append(adjacencyMatrix[i][j]);
            }
            if(i < n - 1)
            {
                sb.append(System.lineSeparator());
            }
        }

        return sb.toString();
    }

    /**
     * Executes a depth-first search starting from every unvisited vertex to cover all
     * components.
     *
     * @param quiet when {@code false}, traversal progress is printed to standard output
     */
    public void runDFS(boolean quiet)
    {
        int n = size();
        boolean[] visited = new boolean[n];
        List<Integer> dfsOrder = new ArrayList<>();
        List<Integer> deadEndOrder = new ArrayList<>();

        for(int i = 0; i < n; i++)
        {
            if(!visited[i])
            {
                dfsFrom(i, visited, dfsOrder, deadEndOrder, quiet);
            }
        }

        lastDFSOrder = buildOrderString(dfsOrder);
        lastDFSDeadEndOrder = buildOrderString(deadEndOrder);
    }

    /**
     * Runs a depth-first search beginning at the specified vertex label.
     *
     * @param v     starting vertex label
     * @param quiet when {@code false}, traversal progress is printed to standard output
     */
    public void runDFS(String v, boolean quiet)
    {
        int startIndex = indexOfLabel(v);
        if(startIndex == -1)
        {
            return;
        }

        int n = size();
        boolean[] visited = new boolean[n];
        List<Integer> dfsOrder = new ArrayList<>();
        List<Integer> deadEndOrder = new ArrayList<>();

        dfsFrom(startIndex, visited, dfsOrder, deadEndOrder, quiet);

        lastDFSOrder = buildOrderString(dfsOrder);
        lastDFSDeadEndOrder = buildOrderString(deadEndOrder);
    }

    /**
     * Executes a breadth-first search from every unvisited vertex to cover all
     * components.
     *
     * @param quiet when {@code false}, traversal progress is printed to standard output
     */
    public void runBFS(boolean quiet)
    {
        int n = size();
        boolean[] visited = new boolean[n];
        List<Integer> bfsOrder = new ArrayList<>();

        for(int i = 0; i < n; i++)
        {
            if(!visited[i])
            {
                bfsFrom(i, visited, bfsOrder, quiet);
            }
        }

        lastBFSOrder = buildOrderString(bfsOrder);
    }

    /**
     * Runs a breadth-first search beginning at the specified vertex label.
     *
     * @param v     starting vertex label
     * @param quiet when {@code false}, traversal progress is printed to standard output
     */
    public void runBFS(String v, boolean quiet)
    {
        int startIndex = indexOfLabel(v);
        if(startIndex == -1)
        {
            return;
        }

        int n = size();
        boolean[] visited = new boolean[n];
        List<Integer> bfsOrder = new ArrayList<>();

        bfsFrom(startIndex, visited, bfsOrder, quiet);

        lastBFSOrder = buildOrderString(bfsOrder);
    }

    /**
     * Retrieves the most recent DFS visit order across all components.
     *
     * @return DFS visit order or an explanatory message if DFS has not run
     */
    public String getLastDFSOrder()
    {
        if(lastDFSOrder == null)
        {
            return "No DFS has been run yet.";
        }
        return lastDFSOrder;
    }

    /**
     * Retrieves the most recent DFS dead-end (post-order) sequence.
     *
     * @return DFS dead-end order or a default message if DFS has not run
     */
    public String getLastDFSDeadEndOrder()
    {
        return Objects.requireNonNullElse(lastDFSDeadEndOrder,
                "No DFS has been run yet.");
    }

    /**
     * Retrieves the most recent BFS visit order.
     *
     * @return BFS visit order or an explanatory message if BFS has not run
     */
    public String getLastBFSOrder()
    {
        if(lastBFSOrder == null)
        {
            return "No BFS has been run yet.";
        }
        return lastBFSOrder;
    }

    /**
     * Looks up the index of a vertex label.
     *
     * @param label vertex label to locate
     * @return index of the label or {@code -1} if it is absent
     */
    private int indexOfLabel(String label)
    {
        if(label == null)
        {
            return -1;
        }

        for(int i = 0; i < vertexLabels.length; i++)
        {
            if(label.equals(vertexLabels[i]))
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * Recursive helper that performs DFS from a starting vertex.
     *
     * @param start        starting vertex index
     * @param visited      visitation flags shared across the traversal
     * @param dfsOrder     sequence collecting visit order
     * @param deadEndOrder sequence collecting vertices as recursion unwinds
     * @param quiet        when {@code false}, traversal progress is printed to standard
     *                     output
     */
    private void dfsFrom(int start,
                         boolean[] visited,
                         List<Integer> dfsOrder,
                         List<Integer> deadEndOrder,
                         boolean quiet)
    {

        visited[start] = true;
        dfsOrder.add(start);
        if(!quiet)
        {
            System.out.println("Visiting vertex " + vertexLabels[start]);
        }

        int n = size();
        for(int i = 0; i < n; i++)
        {
            if(adjacencyMatrix[start][i] == 1 && !visited[i])
            {
                dfsFrom(i, visited, dfsOrder, deadEndOrder, quiet);
            }
        }

        deadEndOrder.add(start);
    }

    /**
     * Iterative helper that performs BFS from a starting vertex using a simple queue.
     *
     * @param start    starting vertex index
     * @param visited  visitation flags shared across the traversal
     * @param bfsOrder sequence collecting visit order
     * @param quiet    when {@code false}, traversal progress is printed to standard
     *                 output
     */
    private void bfsFrom(int start,
                         boolean[] visited,
                         List<Integer> bfsOrder,
                         boolean quiet)
    {

        List<Integer> queue = new ArrayList<>();
        visited[start] = true;
        queue.add(start);

        while(!queue.isEmpty())
        {
            int current = queue.remove(0);
            bfsOrder.add(current);
            if(!quiet)
            {
                System.out.println("BFS visiting vertex " + vertexLabels[current]);
            }

            int n = size();
            for(int i = 0; i < n; i++)
            {
                if(adjacencyMatrix[current][i] == 1 && !visited[i])
                {
                    visited[i] = true;
                    queue.add(i);
                }
            }
        }
    }

    /**
     * Converts a list of vertex indices into a space-separated label string.
     *
     * @param order sequence of vertex indices
     * @return labels joined with single spaces in the order provided
     */
    private String buildOrderString(List<Integer> order)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < order.size(); i++)
        {
            int index = order.get(i);
            sb.append(vertexLabels[index]);
            if(i < order.size() - 1)
            {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
