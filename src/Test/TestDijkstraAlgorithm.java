package Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//import org.junit.Test;
import org.junit.*;

import Dijkstra.Edge;
import Dijkstra.Graph;
import Dijkstra.Vertex;
import Engine.DijkstraAlgorithm;
import Engine.DijkstraCar;

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
            	location.addItem("Item_" + j, (float) (j + 5)*5 - i*2);
            }
            nodes.add(location);
        }

        // All nodes have edges with each other
        addLane("Edge_0", 0, 1, 85);
        addLane("Edge_1", 0, 2, 217);
        addLane("Edge_2", 0, 4, 173);
        addLane("Edge_3", 2, 6, 186);
        addLane("Edge_4", 2, 7, 103);
        addLane("Edge_5", 3, 7, 183);
        addLane("Edge_6", 5, 8, 250);
        addLane("Edge_7", 8, 9, 84);
        addLane("Edge_8", 7, 9, 167);
        addLane("Edge_8", 7, 5, 167);
        addLane("Edge_9", 4, 9, 502);
        addLane("Edge_10", 9, 10, 40);
        addLane("Edge_11", 1, 10, 600);
        addLane("Edge_12", 2, 3, 186);
        addLane("Edge_13", 10, 0, 186);
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
    public void testDijkstra() {
    	
    	System.out.println("testDijkstra");
    	
    	for(int startNode = 0; startNode < 5; startNode++) {
    		for(int destinationNode = 3; destinationNode < 8; destinationNode++) {
    			if(startNode != destinationNode) {
			        // Lets check from location Loc_1 to Loc_10
			        Graph graph = new Graph(nodes, edges);
			        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
			        dijkstra.execute(nodes.get(startNode));
			        LinkedList<Vertex> path = dijkstra.getPath(nodes.get(destinationNode));
			
			        assertNotNull(path);
			        assertTrue(path.size() > 0);
			        assertEquals(" Visit all nodes", 11, dijkstra.getDistances().size());
			
			        //for (Vertex vertex : path) {
			            //System.out.println(vertex);
			        //}
    			}
    		}
    	}

    }
    
    @Test
    public void testCar() {
    	System.out.println("testCar");
    	
    	for(int startNode = 0; startNode < 5; startNode++) {
    		for(int testedItem = 3; testedItem < 8; testedItem++) {
    			String testDescriptor = "Test Node " + startNode + ", Item " + testedItem;
    			System.out.println(testDescriptor);

    			Graph graph = new Graph(nodes, edges);
    	        DijkstraCar dijkstra = new DijkstraCar(graph);
    	        dijkstra.execute(nodes.get(startNode));
    	        Map<Vertex, Float> priceList = dijkstra.findMinimalPrices(0.1f,"Item_" + testedItem);
    	        
    	        assertNotNull(testDescriptor, priceList);
    	        assertTrue(testDescriptor, priceList.size()>0);
    	        assertEquals(testDescriptor + " Visit all nodes", 11, dijkstra.getDistances().size());
    	        assertEquals(testDescriptor + " Test all prices", 11, priceList.size());
    	        /*
    	        System.out.println("Distâncias:");
    	        for(Map.Entry<Vertex, Integer> entry : dijkstra.getDistances().entrySet()) {
    	        	System.out.println(entry.toString() );
    	        }
    	        System.out.println("Preços:");
    	        for(Map.Entry<Vertex, Float> entry : priceList.entrySet()) {
    	        	System.out.println(entry.toString() );
    	        }
    	        //*/
    		}
    	}
    	

    }

    private static void addLane(String laneId, int sourceLocNo, int destLocNo,
            int duration) {
        Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration );
        edges.add(lane);
    }
}