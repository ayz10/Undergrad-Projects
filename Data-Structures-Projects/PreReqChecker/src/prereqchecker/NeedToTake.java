package prereqchecker;

import java.util.*;

/**
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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {

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
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
            return;
        }

	// WRITE YOUR CODE HERE
        Graph graph = new Graph(args[0]);
        ArrayList<String> takenCourses = new ArrayList<String>();
        ArrayList<String> needToTake = new ArrayList<String>();
        StdIn.setFile(args[1]);

        String target = StdIn.readString();
        //track taken courses
        int c = StdIn.readInt();
        String[] arr = graph.getCourses();
        for(int i = 0; i< c; i++){
            String course = StdIn.readString();
            takenCourses.add(course);
            int index = findIndex(arr, course);
            DFS2 dfs = new DFS2(graph, index, takenCourses);
        }

        ArrayList<String> prereqs = graph.adjCourse(target);
        if(prereqs.size()<1){
            return;
        }
        for(int i = 0; i<prereqs.size();i++){
            String prereq = prereqs.get(i);
            if(!takenCourses.contains(prereq)){
                needToTake.add(prereq);
                int index = findIndex(arr, prereq);
                DFS3 dfs = new DFS3(graph, index, needToTake, takenCourses);
            }
        }

        StdOut.setFile(args[2]);
        for(String w: needToTake){
            StdOut.println(w);
        }
    }
}
