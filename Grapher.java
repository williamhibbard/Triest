import java.io.*;

public class Grapher {

    public static void main(String[] args) {
        // Create a reader for the input file.
        BufferedReader br = null;


        int[] sizes = {5000,10000,20000,30000,40000};

        try {
            for (int j = 0; j < 5; j++) {
                File file = new File(args[1] + "Base" + sizes[j] + ".txt");
                FileOutputStream outputStream = new FileOutputStream(file);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                for (int i = 0; i < 20; i++) {

                    DataStreamAlgo algo;
                    algo = new TriestBase(sizes[j]);
                    try {
                        br = new BufferedReader(new FileReader(new File(args[0])));
                    } catch(IOException e) {
                        System.err.println("File '" + args[args.length - 1] +
                                "' not found or not readable.");
                        System.exit(1);
                    }
                    // Iterate over the lines of the input file: read the edge on the line,
                    // pass the edge to the algorithm to handle it, and print the new
                    // estimation of the number of triangles.
                    try {
                        for (String line = br.readLine(); line != null; line = br.readLine()) {
                            algo.handleEdge(new Edge(line.trim()));
                            writer.write(algo.getEstimate() + ",");
                        }
                        writer.newLine();
                    } catch (IOException io) {
                        System.err.println("Error reading the file:" + io);
                        System.exit(1);
                    }
                }
                writer.close();
            }
            for (int j = 0; j < 5; j++) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(args[1] + "Impr" + sizes[j] + ".txt"));
                for (int i = 0; i < 20; i++) {

                    DataStreamAlgo algo;
                    algo = new TriestImpr(sizes[j]);
                    try {
                        br = new BufferedReader(new FileReader(new File(args[0])));
                    } catch(IOException e) {
                        System.err.println("File '" + args[args.length - 1] +
                                "' not found or not readable.");
                        System.exit(1);
                    }
                    // Iterate over the lines of the input file: read the edge on the line,
                    // pass the edge to the algorithm to handle it, and print the new
                    // estimation of the number of triangles.
                    try {
                        for (String line = br.readLine(); line != null; line = br.readLine()) {
                            algo.handleEdge(new Edge(line.trim()));
                            writer.write(algo.getEstimate() + ",");
                        }
                        writer.newLine();
                    } catch (IOException io) {
                        System.err.println("Error reading the file:" + io);
                        System.exit(1);
                    }
                }
                writer.close();
            }
        }catch(Exception e){
            System.out.println("Error");
        }
    }
}
