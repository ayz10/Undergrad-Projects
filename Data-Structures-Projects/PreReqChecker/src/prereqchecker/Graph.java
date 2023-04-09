package prereqchecker; 
import java.util.*;

public class Graph{
    int V;
    private HashMap<String, ArrayList<String>> map = new HashMap<>();

    //graph given input file
    public Graph(String fileName){
        StdIn.setFile(fileName);
        V = StdIn.readInt();
        map = new HashMap<String, ArrayList<String>>(V);

        for(int i = 0; i < V; i++){
            String courseID = StdIn.readString();
            ArrayList<String> list = new ArrayList<>();
            map.put(courseID, list);
        }

        int P = StdIn.readInt();
        for(int j = 0; j<P; j++){
            String target = StdIn.readString();
            String preReq = StdIn.readString();
            map.get(target).add(preReq);
        }
    }

    //add edge
    public void addEdge(String course, String preReq){
        map.get(course).add(preReq);
    }

    //print all courses and their prereqs
    public void adj(){
        for(String s:map.keySet()){
            StdOut.print(s+" ");
            for(String w: map.get(s)){
                StdOut.print(w+" ");
            }
            StdOut.print("\n");
        }
    }

    //return adjlist of course s
    public ArrayList<String> adjCourse(String s){
        ArrayList<String> ans = map.get(s);
        return ans;
    }

    //# courses
    public int V(){
        return V;
    }

    //put all courses in an indexed array
    public String[] getCourses(){
        Set<String> s = map.keySet();
        int n = s.size();
        String arr[] = new String[n];

        int i =0;
        for(String x: s){
            arr[i++] = x;
        }
        return arr;
    }

    public Set<String> keys(){
        return map.keySet();
    }
}
