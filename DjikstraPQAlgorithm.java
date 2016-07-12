
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class DijkstraPQAlgrithm {
	private int maxNumberOfVertices;
	private IndexMinPQ<Integer> minHeap;
	public Map<Integer,Double> shortestDistance=new HashMap<Integer, Double>();
	public Map<Integer,LinkedList<Edge>> shortestPath=new HashMap<Integer, LinkedList<Edge>>();
	public DijkstraPQAlgrithm(GraphInput graph,int source){
		maxNumberOfVertices=graph.getNumberOfvertices();
		minHeap=new IndexMinPQ<Integer>(maxNumberOfVertices);
		Map<Integer,LinkedList<Edge>> tempAdjList =graph.getAdjacencyList();
		Set<Integer> tempVerticesList=tempAdjList.keySet();
		Collection<LinkedList<Edge>> values=tempAdjList.values();
		//System.out.println(values);
		for(LinkedList<Edge> list: values){
			for(int i=0;i<list.size();i++){
				Edge e=list.get(i);
				shortestDistance.put(e.getDestination(), Double.POSITIVE_INFINITY);
				shortestDistance.put(e.getSource(),  Double.POSITIVE_INFINITY);
				if(!minHeap.contains(e.getDestination()))
					minHeap.insert(e.getDestination(),Integer.MAX_VALUE);
				if(!minHeap.contains(e.getSource()))
					minHeap.insert(e.getSource(),Integer.MAX_VALUE);
			}
		}
		shortestDistance.put(source,0.0);
		//System.out.println("Shortest distance"+shortestDistance);


		minHeap.decreaseKey(source, 0);
		//System.out.println(minHeap);

		while(minHeap.isEmpty()==false){
			int tempMin=minHeap.delMin();
			//System.out.println("tempMin"+tempMin);
			LinkedList<Edge> list=tempAdjList.get(tempMin);
			if(list!=null){
				for(int i=0;i<list.size();i++){
					Edge e=list.get(i);
					Integer nextMin=e.getDestination();
					double tmpShort=shortestDistance.get(tempMin) + e.getCost();

					LinkedList<Edge> tmpPathList=new LinkedList<Edge>();
					if(tmpShort< shortestDistance.get(nextMin)){

						shortestDistance.put(nextMin, tmpShort);

						Edge e1=new Edge(source,nextMin,(int)tmpShort);



						tmpPathList.add(e1);
						shortestPath.put(nextMin,tmpPathList);

						if(minHeap.contains(nextMin))
						{
							minHeap.decreaseKey(nextMin, (int)tmpShort);	
						}
						else
							minHeap.insert(nextMin, (int)tmpShort);
					}
				}

			}

		}
	}

}

