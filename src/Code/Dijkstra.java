
package Code;

public class Dijkstra {
    final int filas = 25;
    final int V = filas * filas;
    
    // Funcion utilitaria para encontrar el vertice con la distancia minima, 
    // a partir del conjunto de los vertices todavia no incluidos en el 
    // camino mas corto
    private int minDistance(int[] dist, boolean[] verticeYaProcesado) {
        // Initialize min value
        int min = Integer.MAX_VALUE;
        int min_index = 0;

        for (int v = 0; v < V; v++) {
            if (verticeYaProcesado[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        }

        return min_index;
    }
    
    public int[] dijkstra(int[][] grafo, int nodoDestino) {
        int[] dist = new int[V];
        // dist[i] guarda la distancia mas corta desde el nodo inicial hasta el vertice i

        boolean[] verticeYaProcesado = new boolean[V];
        //Este arreglo tiene true si el vertice i ya fue procesado

        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            verticeYaProcesado[i] = false;
        }
        // La distancia del vertice origen hacia el mismo es siempre 0
        dist[nodoDestino] = 0;

        //Encuentra el camino mas corto para todos los vertices
        for (int count = 0; count < V - 1; count++) {

            //Toma el vertice con la distancia minima del cojunto de vertices aun no procesados
            //En la primera iteracion siempre se devuelve src
            int u = minDistance(dist, verticeYaProcesado);

            // Se marca como ya procesado
            verticeYaProcesado[u] = true;
            // Update dist value of the adjacent vertices of the picked vertex.
            for (int v = 0; v < V; v++) //Se actualiza la dist[v] solo si no esta en verticeYaProcesado, hay un
            //arco desde u a v y el peso total del camino desde src hasta v a traves de u es 
            // mas pequeno que el valor actual de dist[v]
            {
                if (verticeYaProcesado[v] == false && grafo[u][v] > 0 && dist[u] != Integer.MAX_VALUE && dist[u] + grafo[u][v] < dist[v]) {
                    dist[v] = dist[u] + grafo[u][v];
                }
            }
        }

        // se imprime el arreglo con las distancias
        return dist;
    }
}
