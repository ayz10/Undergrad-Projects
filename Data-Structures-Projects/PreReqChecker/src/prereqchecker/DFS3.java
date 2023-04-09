package prereqchecker;
import java.util.*;

public class DFS3 {
    public DFS3(Graph graph, int source,ArrayList<String> needToTake, ArrayList<String> takenCourses){
        dfs(graph, source, needToTake, takenCourses);
    }



    private void dfs(Graph graph, int source, ArrayList<String> needToTake, ArrayList<String> takenCourses){
        String[] arr = graph.getCourses();
        String course = arr[source];
        if(!needToTake.contains(course) && !takenCourses.contains(course)){
            needToTake.add(course);
        }

        for(String w: graph.adjCourse(course)){
            int index = findIndex(arr, w);
            dfs(graph, index, needToTake, takenCourses);
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


