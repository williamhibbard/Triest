import java.util.*;

public class TriestImpr implements DataStreamAlgo {

	private final boolean TEST = false;

	private Map<Integer, HashSet<Integer>> graph;
	private int memory;
	private int time;
	private int numTri;
	private Random random1;
	private Random random2;

    /*
     * Constructor.
     * The parameter samsize denotes the size of the sample, i.e., the number of
     * edges that the algorithm can store.
     */
	public TriestImpr(int samsize) {
		memory = samsize;
		graph = new HashMap<>();
		time = -1; // -1 so that it starts at 0
		numTri = 0;
		random1 = new Random();
		random2 = new Random();
	}

	public void handleEdge(Edge edge) {
		int u = edge.u;
		int v = edge.v;
		//System.out.println();
		//System.out.println(time);
		if (graph.containsKey(u) && graph.get(u).contains(v)){ return;}
		time++;

		if(time <= memory){

			if (graph.containsKey(u) && graph.containsKey(v)){ // Check case for if both nodes already exist

				HashSet<Integer> intersection = new HashSet<>(graph.get(u));
				intersection.retainAll(graph.get(v));
				numTri += intersection.size(); // the number of intersections is the number of triangles that will form

				graph.get(u).add(v); // complete the triangle
				graph.get(v).add(u); // complete the triangle
			}
			else { // no need to check for triangles if both nodes don't already exist in the graph
				if (graph.containsKey(u)) {
					graph.get(u).add(v);
				} else {
					graph.put(u, new HashSet<>());
					graph.get(u).add(v);
				}
				if (graph.containsKey(v)) {
					graph.get(v).add(u);
				} else {
					graph.put(v, new HashSet<>());
					graph.get(v).add(u);
				}
			}
		}

		else { // if time is greater than memory

			if (graph.containsKey(u) && graph.containsKey(v)) { // Check case for if both nodes already exist
				HashSet<Integer> intersection = new HashSet<>(graph.get(u));
				intersection.retainAll(graph.get(v));
				numTri += (int)Math.round(intersection.size() * getCoeff()); // the number of intersections is the number of triangles that will form * our predictive coefficient

				if (random1.nextDouble() < 1.0 * memory / time) {
					graph.get(u).add(v); // complete the triangle
					graph.get(v).add(u); // complete the triangle

					Edge e = getRandomEdge();
					int uReplace = e.u;
					int vReplace = e.v;

					graph.get(uReplace).remove(vReplace);
					graph.get(vReplace).remove(uReplace);

				}
			}
			else{
				if (random1.nextDouble() < 1.0 * memory / time) {
					if (graph.containsKey(u)) {
						graph.get(u).add(v);
					} else {
						graph.put(u, new HashSet<>());
						graph.get(u).add(v);
					}
					if (graph.containsKey(v)) {
						graph.get(v).add(u);
					} else {
						graph.put(v, new HashSet<>());
						graph.get(v).add(u);
					}

					Edge e = getRandomEdge();
					int uReplace = e.u;
					int vReplace = e.v;

					graph.get(uReplace).remove(vReplace);
					graph.get(vReplace).remove(uReplace);
				}
			}
		}

		if (TEST){
			System.out.println("Key: " + u + "  Values: " + graph.get(u).toString());
			System.out.println();
		}
	}

	public int getEstimate() { return numTri; } // You shouldn't return 0 ;-)

	private Edge getRandomEdge(){
		Set<Integer> keys = graph.keySet();
		int index = random2.nextInt(2*memory);
		if (TEST)
			System.out.println("Random " + index);
		for(Integer key : keys){
			if (index < graph.get(key).size() && graph.get(key).size() != 0){
				for (Integer value : graph.get(key)){
					if (index > 0) index--;
					else{
						return new Edge("" + key + " " + value);
					}
				}
			}
			else{
				index -= graph.get(key).size();
			}
		}
		return getRandomEdge();
	}

	private double getCoeff(){
		double coeff = 1.0 * (time - 1)*(time - 2) / (memory * (memory - 1));
		//System.out.println("coeff = " + (int)coeff);
		return coeff;
	}

}
