#include <stdio.h>
#include <limits.h>

#define MAX 100
#define INF INT_MAX

// Function to find the vertex with the minimum distance value
int findMinVertex(int dist[], int visited[], int V) {
    int min = INF, minIndex;
    
    for (int v = 0; v < V; v++) {
        if (!visited[v] && dist[v] <= min) {
            min = dist[v];
            minIndex = v;
        }
    }
    return minIndex;
}

// Dijkstra's Algorithm using Adjacency Matrix and Array as Priority Queue
void dijkstra(int graph[MAX][MAX], int V, int src) {
    int dist[V];        // dist[i] will hold the shortest distance from src to i
    int visited[V];     // visited[i] will be true if vertex i is included in shortest path tree

    // Initialize all distances as INFINITE and visited[] as false
    for (int i = 0; i < V; i++) {
        dist[i] = INF;
        visited[i] = 0;
    }

    // Distance of source vertex from itself is always 0
    dist[src] = 0;

    // Find shortest path for all vertices
    for (int count = 0; count < V - 1; count++) {
        // Pick the minimum distance vertex from the set of vertices not yet processed
        int u = findMinVertex(dist, visited, V);

        // Mark the picked vertex as processed
        visited[u] = 1;

        // Update dist value of the adjacent vertices of the picked vertex
        for (int v = 0; v < V; v++) {
            // Update dist[v] only if it is not visited, there is an edge from u to v,
            // and total weight of path from src to v through u is smaller than current value of dist[v]
            if (!visited[v] && graph[u][v] != INF && dist[u] != INF && dist[u] + graph[u][v] < dist[v]) {
                dist[v] = dist[u] + graph[u][v];
            }
        }
    }

    // Print the calculated shortest distances
    printf("Vertex \t\t Distance from Source\n");
    for (int i = 0; i < V; i++) {
        printf("%d \t\t %d\n", i, dist[i]);
    }
}

int main() {
    int V = 5;  // Number of vertices in the graph
    int graph[MAX][MAX] ={
                    {0, 4, 2, 6, 8},
                    {INF, 0, INF, 4, 3},
                    {INF, INF, 0, 1, INF},
                    {INF, 1, INF, 0, 3},
                    {INF, INF, INF, INF, 0}
                    };

    int src = 0;  // Source vertex
    dijkstra(graph, V, src);

    return 0;
}
