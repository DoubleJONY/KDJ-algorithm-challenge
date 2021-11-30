import java.util.*;

class Solution {
    public int solution(int n, int[][] edge) {
        Graph g = buildGraph(n, edge);
        Map<Integer, Integer> counts = countVerticesByDistance(g, 1);
        int maxDist = Collections.max(counts.keySet());
        int answer = counts.get(maxDist);
        return answer;
    }

    Graph buildGraph(int n, int[][] edge) {
        Graph g = new Graph(n);
        for (int[] e : edge) {
            g.addEdge(e[0], e[1]);
        }
        return g;
    }

    Map<Integer, Integer> countVerticesByDistance(Graph g, int startVertex) {
        Map<Integer, Integer> counter = new HashMap<>();

        Queue<List<Integer>> q = new LinkedList<>();
        boolean[] visited = new boolean[g.size + 1];

        List<Integer> firstState = List.of(0, startVertex);
        q.add(firstState);
        visited[startVertex] = true;

        while (!q.isEmpty()) {
            List<Integer> state = q.poll();
            int t = state.get(0);
            int currVertex = state.get(1);

            if (!counter.containsKey(t))
                counter.put(t, 0);
            counter.put(t, counter.get(t) + 1);

            for (int nextVertex : g.edges.get(currVertex)) {
                if (visited[nextVertex])
                    continue;

                List<Integer> nextState = List.of(t + 1, nextVertex);
                q.add(nextState);
                visited[nextVertex] = true;
            }
        }

        return counter;
    }
}


class Graph {
    Map<Integer, List<Integer>> edges;
    int size;

    public Graph(int nVertices) {
        size = nVertices;
        edges = new HashMap<>();
        for (int v = 1; v <= nVertices; v++) {
            edges.put(v, new ArrayList<>());
        }
    }

    public void addEdge(int vertexA, int vertexB) {
        edges.get(vertexA).add(vertexB);
        edges.get(vertexB).add(vertexA);
    }
}
