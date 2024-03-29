import java.sql.*;
import java.util.*;

public class Main {
    public static void performDFS(Dbfunction db, Connection conn) {
        try {
            // Retrieve vertices from the database
            ResultSet verticesRS = db.read_vertices(conn, "vertices");

            // Initialize set to keep track of visited vertices
            Set<Integer> visited = new HashSet<>();

            // Initialize map to store graph representation
            Map<Integer, List<Integer>> graph = new HashMap<>();

            // Constructing the graph from the database
            while (verticesRS.next()) {
                int vertex = verticesRS.getInt("vertice");
                if (!visited.contains(vertex)) {
                    // Perform DFS traversal from unvisited vertices
                    dfs(db, conn, visited, graph, vertex);
                }
            }
        } catch (SQLException e) {
            //System.out.println("Error performing DFS: " + e.getMessage());
        }
    }

    // Helper method for DFS traversal
    private static void dfs(Dbfunction db, Connection conn, Set<Integer> visited, Map<Integer, List<Integer>> graph, int startVertex) {
        try {
            // Mark the current vertex as visited
            visited.add(startVertex);

            // Initialize list to store vertices in current connected component
            List<Integer> connectedComponent = new ArrayList<>();
            connectedComponent.add(startVertex);

            // Retrieve neighbors for the current vertex from the database
            ResultSet edgesRS = db.read_edgesForVertex(conn, "edges", startVertex);
            while (edgesRS.next()) {
                int neighbor = edgesRS.getInt("t");
                if (!visited.contains(neighbor)) {
                    // Perform DFS traversal recursively for unvisited neighbors
                    dfs(db, conn, visited, graph, neighbor);
                    connectedComponent.add(neighbor); // Add neighbor to connected component
                }
            }

            // Store connected component in the graph
            graph.put(startVertex, connectedComponent);

            // Print DFS traversal for the connected component
            //System.out.println("DFS traversal starting from vertex " + startVertex + ": " + connectedComponent);
        } catch (SQLException e) {
            //System.out.println("Error performing DFS: " + e.getMessage());
        }
    }
    public static void performBFS(Dbfunction db, Connection conn) {
        try {
            // Retrieve vertices from the database
            ResultSet verticesRS = db.read_vertices(conn, "vertices");

            // Initialize set to keep track of visited vertices
            Set<Integer> visited = new HashSet<>();

            // Initialize map to store graph representation
            Map<Integer, List<Integer>> graph = new HashMap<>();

            // Constructing the graph from the database
            while (verticesRS.next()) {
                int vertex = verticesRS.getInt("vertice");
                if (!visited.contains(vertex)) {
                    // Perform BFS traversal from unvisited vertices
                    bfs(db, conn, visited, graph, vertex);
                }
            }
        } catch (SQLException e) {
            //System.out.println("Error performing BFS: " + e.getMessage());
        }
    }

    // Helper method for BFS traversal
    private static void bfs(Dbfunction db, Connection conn, Set<Integer> visited, Map<Integer, List<Integer>> graph, int startVertex) {
        try {
            // Initialize queue for BFS traversal
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(startVertex);
            visited.add(startVertex);

            // Initialize list to store vertices in current connected component
            List<Integer> connectedComponent = new ArrayList<>();
            connectedComponent.add(startVertex);

            // Perform BFS traversal
            while (!queue.isEmpty()) {
                int currentVertex = queue.poll();

                // Retrieve neighbors for the current vertex from the database
                ResultSet edgesRS = db.read_edgesForVertex(conn, "edges", currentVertex);
                while (edgesRS.next()) {
                    int neighbor = edgesRS.getInt("t");
                    if (!visited.contains(neighbor)) {
                        queue.offer(neighbor);
                        visited.add(neighbor);
                        connectedComponent.add(neighbor); // Add neighbor to connected component
                    }
                }
            }

            // Store connected component in the graph
            graph.put(startVertex, connectedComponent);

            // Print BFS traversal for the connected component
            //System.out.println("BFS traversal starting from vertex " + startVertex + ": " + connectedComponent);
        } catch (SQLException e) {
            //System.out.println("Error performing BFS: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Dbfunction db = new Dbfunction();
        Connection conn = db.connect_to_db("algo","postgres","admin");
//        db.createTableForVertice(conn , "vertices");
//        db.createTableForEdges(conn , "edges");

        // generate random vertices
        Random r = new Random();
        // first add five vertices to the vertices table
//        int temp = 15000;
//        for(int i=0; i<temp; i++) {
//            db.insertVertices(conn, "vertices" ,i);
//        }
//        // now add random edges
//        for(int i=0; i<temp; i++){
//            db.insertEdges(conn, "edges", r.nextInt(temp), r.nextInt(temp));
//        }
//        long startTime = System.currentTimeMillis(); // Record start time
//        performBFS(db, conn);
//        long endTime = System.currentTimeMillis(); // Record end time
//
//        long elapsedTime = endTime - startTime; // Calculate elapsed time
//        System.out.println("BFS traversal completed in " + elapsedTime + " milliseconds.");
        long startTime1 = System.currentTimeMillis(); // Record start time
        performDFS(db, conn);
        long endTime1 = System.currentTimeMillis(); // Record end time

        long elapsedTime1 = endTime1 - startTime1; // Calculate elapsed time
        System.out.println("DFS traversal completed in " + elapsedTime1 + " milliseconds.");
    }
}