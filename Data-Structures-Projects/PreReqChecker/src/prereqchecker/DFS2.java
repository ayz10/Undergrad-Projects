package prereqchecker;
import java.util.*;

public class DFS2 {

    public DFS2(Graph graph, int source,ArrayList<String> takenCourses){
        dfs2(graph, source, takenCourses);
    }



    private void dfs2(Graph graph, int source, ArrayList<String> takenCourses){
        String[] arr = graph.getCourses();
        String course = arr[source];
        if(!takenCourses.contains(course)){
            takenCourses.add(course);
        }

        for(String w: graph.adjCourse(course)){
            int index = findIndex(arr, w);
            dfs2(graph, index, takenCourses);
        }
    }

    public int findIndex(String arr[], String t){
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
    
}
