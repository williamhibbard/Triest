import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TriestBase implements DataStreamAlgo {

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
	public TriestBase(int samsize) {
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

		else{ // if time is greater than memory
			if (random1.nextDouble() < memory * 1.0 / time){
				// This block gets rid of an edge and subtracts the number of triangles that exist because of them
				Edge e = getRandomEdge();
				int uReplace = e.u;
				int vReplace = e.v;

				if (TEST){
					System.out.println("uReplace is " + uReplace);
					System.out.println("vReplace is " + vReplace);
					if (!graph.containsKey(uReplace)) System.out.println("no such u edge");
					if (!graph.containsKey(vReplace)) System.out.println("no such v edge");
				}

				graph.get(uReplace).remove(vReplace);
				//if (graph.get(uReplace).isEmpty()) graph.remove(uReplace);

				graph.get(vReplace).remove(uReplace);
				//if (graph.get(vReplace).isEmpty()) graph.remove(vReplace);

				if (graph.containsKey(uReplace) && graph.containsKey(vReplace)) {
					HashSet<Integer> intersection1 = new HashSet<>(graph.get(uReplace));
					intersection1.retainAll(graph.get(vReplace));
					numTri -= intersection1.size(); // the number of intersections is the number of triangles that exist because of u and v
				}

				if (graph.containsKey(u) && graph.containsKey(v)){ // Check case for if both nodes already exist
					HashSet<Integer> intersection2 = new HashSet<>(graph.get(u));
					intersection2.retainAll(graph.get(v));
					numTri += intersection2.size(); // the number of intersections is the number of triangles that will form

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
			else return;
		}

		if (TEST){
			System.out.println("Key: " + u + "  Values: " + graph.get(u).toString());
			System.out.println();
		}
	}

	public int getEstimate() {
		double numerator1 = 1.0 * (memory - 2);
		double denominator1 = 1.0 * (time - 2);
		double numerator2 = 1.0 * (memory - 1);
		double denominator2 = 1.0 * (time - 1);
		double numerator3 = 1.0 * memory;
		double denominator3 = 1.0 * time;
		double pi1 = numerator1/denominator1;
		double pi2 = numerator2/denominator2;
		double pi3 = numerator3/denominator3;
		if (TEST) {
			System.out.println("numTri " + numTri);
			System.out.println();
		}
		if (time <= memory) {
			return numTri;
		} else if (time != 0){
			return (int)(numTri / pi1 / pi2 / pi3);
		}

		return 0;
	} // You shouldn't return 0 ;-)

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

}
