# Python3 program to print DFS traversal
# from a given path
from collections import defaultdict


# This class represents a directed graph using 
# adjacency list representation
class Graph:

    def __init__(self):
        # Default dictionary to store graph
        self.graph = defaultdict(list)

    def add_edge(self, u: int, v: int):
        self.graph[u].append(v)

    def depth_first_search(self, *, node: int, visited=None):
        if visited is None:
            visited = set()
        visited.add(node)
        
        for neighbour in self.graph[node]:
            if neighbour not in visited:
                self.depth_first_search(node=neighbour, visited=visited)


if __name__ == "__main__":
    g = Graph()
    g.add_edge(0, 1)
    g.add_edge(0, 2)
    g.add_edge(1, 2)
    g.add_edge(2, 0)
    g.add_edge(2, 3)
    g.add_edge(3, 3)

    g.depth_first_search(node=2)