# i = infinity
i = float('inf')

adj_matrix = [
    [0, 4, 2, 8, 6],
    [i, 0, i, 4, 3],
    [i, i, 0, 1, i],
    [i, 1, i, 0, 3],
    [i, i, i, i, 0]
]

def dijkstra(graph, source):
    # Number of vertices n
    n = len(graph)
    
    d = [i] * n       
    pi = [None] * n   
    S = [False] * n   
    d[source] = 0

    Q = list(range(n))  

    while Q: 
        
        u = Q[0]

        for vertex in Q:
            if d[vertex] < u:
                u = vertex

        Q.remove(u)
        
        S[u] = True

        for v in range(n):
            if S[v]!=True and d[v] > d[u] + graph[u][v]:
                d[v] = d[u] + graph[u][v]
                pi[v] = u

    return d

source_vertex = 0
d = dijkstra(adj_matrix, source_vertex)

# Output results
print(d)