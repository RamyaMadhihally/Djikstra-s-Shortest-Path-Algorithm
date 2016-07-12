// Implementation of Djikstra's on Fibonacci Heap

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import dataStructures.FibonacciHeap;
import dataStructures.FibonacciHeap.Node;




public class DjikstraFibonacciHeap {
	// Fibonacci heap to find the min distance adjacent vertex
	private FibonacciHeap<Integer> fHeap;
	@SuppressWarnings("unused")
	// Max number of vertices in the graph/network
	private int maxNumberOfVertices=0;
	//Map to store the distance to a node
	private Map<Integer,Double> shortestDistance=new HashMap<Integer, Double>();
	//Map to store the shortest path from a node
	private Map<Integer,LinkedList<Integer>> shortestPath=new HashMap<Integer, LinkedList<Integer>>();
	//Constructor to initialize 
	public DjikstraFibonacciHeap(GraphInput graph,int source) throws Exception{
		maxNumberOfVertices=graph.getNumberOfvertices();
		fHeap=new FibonacciHeap<Integer>();
		Map<Integer,LinkedList<Edge>> tempAdjList =graph.getAdjacencyList();
		
		Collection<LinkedList<Edge>> values=tempAdjList.values();
		//Putting all the vertices on the heap
		for(LinkedList<Edge> list: values){
			for(int i=0;i<list.size();i++){
				Edge e=list.get(i);
				shortestDistance.put(e.getDestination(), Double.POSITIVE_INFINITY);

				fHeap.insertNode(new Node<Integer>(e.getDestination(), Double.POSITIVE_INFINITY),Double.POSITIVE_INFINITY); 
				shortestDistance.put(e.getSource(),  Double.POSITIVE_INFINITY);

				fHeap.insertNode(new Node<Integer>(e.getSource(), Double.POSITIVE_INFINITY),Double.POSITIVE_INFINITY); 
			}
		}
		//Setting the source distance to zero
		shortestDistance.put(source,0.0);
		fHeap.decreaseKey(new Node<Integer>(source, 0.0),0.0);
        // Retrieve adjacent vertices from the heap with minimum distance(priority)
		while (!fHeap.isEmpty()) {
			Node<Integer> tempMin=fHeap.extractMin(fHeap);
			LinkedList<Edge> list=tempAdjList.get(tempMin.getNodeElement());
			if(list!=null){
				for(int i=0;i<list.size();i++){
					Edge e=list.get(i);

					Integer nextMin=e.getDestination();

					double tmpShort=shortestDistance.get(tempMin.getNodeElement()) + e.getCost();


					LinkedList<Integer> tmpPathList=new LinkedList<Integer>();
					
					// If a shorter path is found to a node,update the distance
					if(tmpShort< shortestDistance.get(nextMin)){

						shortestDistance.put(nextMin, tmpShort);
						Edge e1=new Edge(source,nextMin,(int)tmpShort);

						tmpPathList.add(e1.getDestination());
						shortestPath.put(nextMin,tmpPathList);


						fHeap.decreaseKey(new Node<Integer>(nextMin, tmpShort),tmpShort);

					}
				}

			}

		}

	}


	public static void main(String[] args) {
		In in = new In("test.txt");
		GraphInput G = new GraphInput(in);
		int s = 1;

		// compute shortest paths
		try {
			long time1=System.currentTimeMillis();
			//System.out.println("Time"+System.currentTimeMillis());
			DjikstraFibonacciHeap heap=new DjikstraFibonacciHeap(G,s);
			long time2=System.currentTimeMillis();
			System.out.println("Time "+(time2-time1));
			System.out.println("Shortest distance using Fibonacci Heap"+heap.shortestDistance.values());
			System.out.println("Shortest path using Fibonacci Heap"+heap.shortestPath.values());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}





	}
}
