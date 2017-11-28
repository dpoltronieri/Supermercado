package Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//import org.junit.Test;
import org.junit.*;

import Dijkstra.Edge;
import Dijkstra.Graph;
import Dijkstra.Vertex;
import Engine.DijkstraAlgorithm;

import static org.junit.Assert.*;

public class TestDijkstraAlgorithm {
    private static List<Vertex> nodes;
    private static List<Edge> edges;
    
    @BeforeClass
    public static void testSetData() {
    	nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        for (int i = 0; i < 11; i++) {
            Vertex location = new Vertex("Node_" + i, "Node_" + i);
            for (int j = 0; j < 10; j++) {
            	location.addItem("Item_" + j, (float) j*5 + i*2);
            }
            nodes.add(location);
        }

        addLane("Edge_0", 0, 1, 85);
        addLane("Edge_1", 0, 2, 217);
        addLane("Edge_2", 0, 4, 173);
        addLane("Edge_3", 2, 6, 186);
        addLane("Edge_4", 2, 7, 103);
        addLane("Edge_5", 3, 7, 183);
        addLane("Edge_6", 5, 8, 250);
        addLane("Edge_7", 8, 9, 84);
        addLane("Edge_8", 7, 9, 167);
        addLane("Edge_9", 4, 9, 502);
        addLane("Edge_10", 9, 10, 40);
        addLane("Edge_11", 1, 10, 600);
    }
    
    @Before
    public void testIntegrity() {
    	assertEquals(11, nodes.size());
    	for	(Vertex node : nodes) {
    		assertEquals(10, node.numberItems());
    	}
    	for (Edge edge : edges) {
    		assertNotNull(edge.getSource());
    		assertNotNull(edge.getDestination());
    		assertNotNull(edge.getWeight());
    	}
    }
    
    @Test
    public void testExcute() {

        // Lets check from location Loc_1 to Loc_10
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.execute(nodes.get(0));
        LinkedList<Vertex> path = dijkstra.getPath(nodes.get(10));

        assertNotNull(path);
        assertTrue(path.size() > 0);

        for (Vertex vertex : path) {
            System.out.println(vertex);
        }

    }

    private static void addLane(String laneId, int sourceLocNo, int destLocNo,
            int duration) {
        Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration );
        edges.add(lane);
    }
}