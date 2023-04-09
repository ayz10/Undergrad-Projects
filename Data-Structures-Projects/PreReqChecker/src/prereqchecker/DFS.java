package prereqchecker;

public class DFS {
    private boolean[] onStack;
    private boolean[] marked;
    private boolean hasCycle;

    //public call method
    public DFS(Graph graph, int source){
        onStack = new boolean[graph.V()];
        marked = new boolean[graph.V()];
        dfs(graph, source);
    }

    //recursive cycle finder
    private void dfs(Graph graph, int source){
        String[] arr = graph.getCourses();
        marked[source] = true;
        onStack[source]= true;
        String course = arr[source];

        for(String w: graph.adjCourse(course)){
            int index = findIndex(arr, w);
            if(!marked[index]){
                dfs(graph, index);
            }
            else if(onStack[index]){
                hasCycle = true;
                return;
            }
        }
        onStack[source] = false;
    }


    //hasCycle boolean
    public boolean hasCycle(){
        return hasCycle;
    }

    //find index given courseID
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
