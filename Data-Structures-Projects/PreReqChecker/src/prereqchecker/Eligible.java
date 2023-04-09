package prereqchecker;
import java.util.*;

/**
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {

    private static int findIndex(String arr[], String t){
        if (arr == null) {
            return -1;
        }
        int len = arr.length;
        int i = 0;
        while (i < len) {
            if (arr[i].equals(t)) {
                return i;
            }
            else {
                i = i + 1;
            }
        }
        return -1;
    } 

    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
            return;
        }

	// WRITE YOUR CODE HERE
        Graph graph = new Graph(args[0]);
        ArrayList<String> takenCourses = new ArrayList<String>();
        ArrayList<String> eligible = new ArrayList<String>();
        StdIn.setFile(args[1]);

        //track courses taken
        int n = StdIn.readInt();
        String[] arr = graph.getCourses();
        for(int i = 0; i< n; i++){
            String course = StdIn.readString();
            takenCourses.add(course);
            int index = findIndex(arr, course);
            DFS2 dfs = new DFS2(graph, index, takenCourses);
        }
        
        //iterate over keys
        for(String w: graph.keys()){
            //get adjlist for each key
            ArrayList<String> prereqList = graph.adjCourse(w);
            //if no list but course is not marked as taken
            if(prereqList == null){
                if(!takenCourses.contains(w)){
                    eligible.add((w));
                }
                continue;
            }
            //if key is already marked as taken
            if(takenCourses.contains(w)){
                continue;
            }
            //iterate over adjlist
            int size = prereqList.size();
            for(int j = 0; j<size;j++){
                String prereq = prereqList.get(j);
                //if prereq is not taken, exit loop
                if(!takenCourses.contains(prereq)){
                    break;
                }
                if(j!=size-1){
                    continue;
                }
                eligible.add(w);
            }
        }

        StdOut.setFile(args[2]);
        for(String w: eligible){
            StdOut.println(w);
        }
    }
}
