import random
import matplotlib.pyplot as plt

# i = infinity
i = float('inf')

# Function to generate a random adjacency matrix for a graph with a fixed number of vertices and edges
def generate_random_adj_matrix(num_of_vertices, num_of_edges):
    # Create a zero matrix for the graph
    adj_matrix = [[i] * num_of_vertices for _ in range(num_of_vertices)]

    # Populate the diagonal with zero (no self-loops)
    for v in range(num_of_vertices):
        adj_matrix[v][v] = 0

    edges_added = 0
    while edges_added < num_of_edges:
        # Randomly choose a pair of distinct vertices (u, v)
        u = random.randint(0, num_of_vertices - 1)
        v = random.randint(0, num_of_vertices - 1)

        # Ensure u and v are distinct and there's no existing edge
        if u != v and adj_matrix[u][v] == i:
            # Assign a random weight between 1 and 10
            weight = random.randint(1, 10)
            adj_matrix[u][v] = weight
            adj_matrix[v][u] = weight  # For undirected graph

            edges_added += 1

    return adj_matrix

# Dijkstra's algorithm that returns the number of key comparisons
def dijkstra(graph, source):
    n = len(graph)

    # Initialize distances, predecessors, and visited set
    d = [i] * n
    pi = [None] * n
    S = [False] * n
    d[source] = 0

    Q = list(range(n))
    key_comparisons = 0

    while Q:
        u = Q[0]  # Start by assuming the first element is the minimum

        # Find the minimum distance vertex in Q
        for vertex in Q:
            key_comparisons += 1  # Increment key comparison count
            if d[vertex] < d[u]:
                u = vertex

        Q.remove(u)  # Remove u from Q as it's now finalized
        S[u] = True

        # Relax adjacent vertices of u
        for v in range(n):
            if not S[v] and d[v] > d[u] + graph[u][v]:
                key_comparisons += 1  # Increment key comparison count for relaxation
                d[v] = d[u] + graph[u][v]
                pi[v] = u

    return key_comparisons

# Experiment parameters
fixed_edges = 10000
step_size = 500
max_vertices = 5000
num_iterations = 50

# Lists to store results for plotting
vertex_counts = []
average_comparisons = []

# Run the experiment
for v in range(100, max_vertices + 1, step_size):
    total_comparisons = 0

    # Generate a random graph with v vertices and fixed number of edges
    graph = generate_random_adj_matrix(v, fixed_edges)

    # Run Dijkstra's algorithm multiple times and accumulate comparisons
    for _ in range(num_iterations):
        source_vertex = random.randint(0, v - 1)  # Random source vertex
        comparisons = dijkstra(graph, source_vertex)
        total_comparisons += comparisons

    # Calculate the average number of key comparisons for this graph
    avg_comparisons = total_comparisons / num_iterations

    # Store the number of vertices and the corresponding average comparisons
    vertex_counts.append(v)
    average_comparisons.append(avg_comparisons)

    print(f"Vertices: {v}, Average Key Comparisons: {avg_comparisons}")

# Plot the results
plt.figure(figsize=(10, 6))
plt.plot(vertex_counts, average_comparisons, marker='o', linestyle='-')
plt.title("Average Key Comparisons vs Number of Vertices")
plt.xlabel("Number of Vertices")
plt.ylabel("Average Key Comparisons")
plt.grid(True)
plt.show()
