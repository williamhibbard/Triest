author: William Hibbard
        whibbard20@amherst.edu

There are two testing classes.  One is the main class which operates exactly as described
in the project description.

The classes with the bulk of the work are TriestBase and TriestImprv.  These classes are used
to predict the number of total triangles in a streamed graph, simulating different amounts of
available memory.  This means that not every edge can be stored and considered when calculating
the total number of triangles.

java Main
As described in the project description:
"By running java Main with dierent arguments, you can select at runtime which variant of
the algorithm you want to use. Run java Main -h (even without any modication to the les)
for details"

java Grapher
I created this for graph analysis.
To run this code, Run java Grapher yourGraphStream.txt desiredOutputDirectory/
This will take yourGraphStream.txt and run it through TriestBase and TriestImpr for 
sample sizes 5000, 10000, 20000, 30000, and 40000.  For each of these sample sizes,
it will run it 20 times, saving the output to a file BaseX.txt or ImprX.txt where X is the
sample size.  Each of these texts files will have 20 lines, where each line indicates one
run, and each prediction is separated by a comma.
I created a script in Python that runs through these files and graphs them to the images
that you will find in the Results directory.  This script only runs on a single .txt file,
so to get all resulting graphs, you will have to run it on every result file.
