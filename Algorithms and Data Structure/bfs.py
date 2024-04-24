from collections import deque
from typing import List


AdjacencyList = List[List[int]]

# Function to perform "Breadth First Search" 
# on a graph represented using adjacency list
def bfs(*, adjacencyList: AdjacencyList, initialNode: int, visidedNodes: List[bool]) -> None:
    # Queue for BFS
    queue = deque()
    
    # Mark the current node as visited and enqueue it
    queue.append(initialNode)
    visidedNodes[initialNode]

    # Iterate over the queue
    while queue:
        # Dequeue a node from queue
        currentNode = queue.popleft()

        # Get all adjacent nodes of the dequeued node
        # If an adjacent has not been visited, then mark it as visited and enqueue it
        for neighbour in adjacencyList[currentNode]:
            if not visidedNodes[neighbour]:
                visidedNodes[neighbour] = True
                queue.append(neighbour)
    

# Function to add an edge to the graph
def addEdge(*, adjacencyList: AdjacencyList, currentNode: int, adjacentNode: int) -> None:
    adjacencyList[currentNode].append(adjacentNode)


def  main() -> None:
    # Number of nodes in the graph
    nodes = 5
    
    # Adjacency List as representation of the graph
    adjacencyList = [[] for _ in range(nodes)]

    addEdge(adjacencyList=adjacencyList, currentNode=0, adjacentNode=1)
    addEdge(adjacencyList=adjacencyList, currentNode=0, adjacentNode=2)
    addEdge(adjacencyList=adjacencyList, currentNode=1, adjacentNode=3)
    addEdge(adjacencyList=adjacencyList, currentNode=1, adjacentNode=4)
    addEdge(adjacencyList=adjacencyList, currentNode=2, adjacentNode=4)

    # Mark all visited nodes as not visited
    visitedNodes = [False] * nodes
   
    bfs(adjacencyList=adjacencyList, initialNode=0, visidedNodes=visitedNodes)


if __name__ == "__main__":
    main()

