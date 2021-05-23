
package Code;

import java.util.ArrayList;

public class LongRoad {
private boolean[][] mapb;
    private int nodoOrigen;
    private int nodoDestino;
    private final int filas = 25;
    private int nodoP;
    private ArrayList<Integer> caminoNodos = new ArrayList<Integer>();
    private final int V;
    Dijkstra d = new Dijkstra();
    
    public LongRoad (boolean[][] map,int nodoOrigen,int nodoDestino){
        mapb = map;
        this.nodoOrigen = nodoOrigen;
        this.nodoDestino = nodoDestino;
        nodoP = nodoOrigen;
        V = filas * filas;
    }
       
    public int[] getLongestRoad(){
        int[] dist = getCaminos(mapb, nodoDestino);
        createShortestRoadInMatrix(dist);        
        return alargarMatriz(nodoOrigen);
    }

    private void createShortestRoadInMatrix(int dist[]) {

        for (int x = 0; x < dist[nodoOrigen]; x++) {

            int i = (int) nodoP / filas;
            int j = nodoP % filas;

            int min = Integer.MAX_VALUE;
            int nodoProvisional = 0;
            
            if (i > 0) {
                if (dist[(i - 1) * filas + j] < min) {
                    min = dist[(i - 1) * filas + j];
                    nodoProvisional = (i - 1) * filas + j;
                }
            }
            if (i < filas - 1) {
                if (dist[(i + 1) * filas + j] < min) {
                    min = dist[(i + 1) * filas + j];
                    nodoProvisional = (i + 1) * filas + j;
                }
            }
            if (j > 0) {
                if (dist[i * filas + j - 1] < min) {
                    min = dist[i * filas + j - 1];
                    nodoProvisional = i * filas + j - 1;
                }
            }
            if (j < filas - 1) {
                if (dist[i * filas + j + 1] < min) {
                    min = dist[i * filas + j + 1];
                    nodoProvisional = i * filas + j + 1;
                }
            }

            int ip = (int) nodoProvisional / filas;
            int jp = nodoProvisional % filas;
            mapb[ip][jp] = false;
            
            nodoP = nodoProvisional;
            caminoNodos.add(nodoProvisional);
            
        }

    }


    private int[] alargarMatriz(int nodoInicial) {
        caminoNodos.add(0, nodoInicial);
        for (int i = 0; i < caminoNodos.size()-1; i++) {

            int nodo1 = caminoNodos.get(i);
            int nodo2 = caminoNodos.get(i + 1);

            int xNodo1 = (int) nodo1 / filas;
            int yNodo1 = nodo1 % filas;

            int xNodo2 = (int) nodo2 / filas;
            int yNodo2 = nodo2 % filas;

            alargarNodo(xNodo1, yNodo1, xNodo2, yNodo2);

        }
        //Convertimos el array list en un array de int
        int[] nodosArecorrer = new int[caminoNodos.size()];
        
        for(int i = 0;i<caminoNodos.size();i++){
            nodosArecorrer[i] = caminoNodos.get(i);
        }

        return nodosArecorrer;
    }

    private void alargarNodo(int xNodoInicio, int yNodoInicio, int xNodoPareja, int yNodoPareja) {

        boolean repeat;
        boolean inside;

        int xNI = xNodoInicio;
        int yNI = yNodoInicio;
        int xNP = xNodoPareja;
        int yNP = yNodoPareja;

        //Aqui una vez que tenemos ya el nodo pareja y el normal pues tratamos de alargarlos si es posible 
        inside = false;
        do {
            repeat = false;
            if (xNI > 0 && xNP > 0) {
                if (mapb[xNI - 1][yNI] == true && mapb[xNP - 1][yNP] == true) {
                    int nodoInicio = xNI * filas + yNI;
                    int nodoInicioNext = (xNI - 1) * filas + yNI;
                    insertAnextToB(nodoInicio, nodoInicioNext);

                    int nodoPareja = xNP * filas + yNP;
                    int nodoParejaAfter = (xNP - 1) * filas + yNP;
                    insertAafterB(nodoPareja, nodoParejaAfter);

                    repeat = true;
                    inside = true;
                    mapb[xNI - 1][yNI] = false;
                    mapb[xNP - 1][yNP] = false;
                    xNI -= 1;
                    xNP -= 1;
                }
            }

        } while (repeat == true);

        do {
            repeat = false;
            if (xNI < filas - 1 && xNP < filas - 1 && inside == false) {
                if (mapb[xNI + 1][yNI] == true && mapb[xNP + 1][yNP] == true) {
                    int nodoInicio = xNI * filas + yNI;
                    int nodoInicioNext = (xNI + 1) * filas + yNI;
                    insertAnextToB(nodoInicio, nodoInicioNext);

                    int nodoPareja = xNP * filas + yNP;
                    int nodoParejaAfter = (xNP + 1) * filas + yNP;
                    insertAafterB(nodoPareja, nodoParejaAfter);

                    repeat = true;
                    mapb[xNI + 1][yNI] = false;
                    mapb[xNP + 1][yNP] = false;
                    xNI += 1;
                    xNP += 1;
                }
            }
        } while (repeat == true);

        inside = false;
        xNI = xNodoInicio;
        yNI = yNodoInicio;
        xNP = xNodoPareja;
        yNP = yNodoPareja;

        do {
            repeat = false;
            if (yNI > 0 && yNP > 0) {
                if (mapb[xNI][yNI - 1] == true && mapb[xNP][yNP - 1] == true) {
                    int nodoInicio = xNI * filas + yNI;
                    int nodoInicioNext = xNI * filas + yNI - 1;
                    insertAnextToB(nodoInicio, nodoInicioNext);

                    int nodoPareja = xNP * filas + yNP;
                    int nodoParejaAfter = xNP * filas + yNP - 1;
                    insertAafterB(nodoPareja, nodoParejaAfter);

                    repeat = true;
                    inside = true;
                    mapb[xNI][yNI - 1] = false;
                    mapb[xNP][yNP - 1] = false;
                    yNI -= 1;
                    yNP -= 1;

                }
            }
        } while (repeat == true);

        do {
            repeat = false;
            if (yNI < filas - 1 && yNP < filas - 1 && inside == false) {
                if (mapb[xNI][yNI + 1] == true && mapb[xNP][yNP + 1] == true) {
                    int nodoInicio = xNI * filas + yNI;
                    int nodoInicioNext = xNI * filas + yNI + 1;
                    insertAnextToB(nodoInicio, nodoInicioNext);

                    int nodoPareja = xNP * filas + yNP;
                    int nodoParejaAfter = xNP * filas + yNP + 1;
                    insertAafterB(nodoPareja, nodoParejaAfter);

                    repeat = true;
                    mapb[xNI][yNI + 1] = false;
                    mapb[xNP][yNP + 1] = false;
                    yNI += 1;
                    yNP += 1;

                }
            }

        } while (repeat == true);
    }

    private  void insertAnextToB(int a, int b) {
        for (int i = 0; i < caminoNodos.size(); i++) {
            if (caminoNodos.get(i) == a) {
                caminoNodos.add(i + 1, b);
                break;
            }
        }
    }

    private  void insertAafterB(int a, int b) {
        for (int i = 0; i < caminoNodos.size(); i++) {
            if (caminoNodos.get(i) == a) {
                caminoNodos.add(i, b);
                break;
            }
        }
    }    
    
    public int[][] createGraph(boolean[][] matriz) {
        int matrizSize = matriz.length;
        int totalSize = matrizSize * matrizSize;
        int[][] graph = new int[totalSize][totalSize];

        //Inizializamos la matriz con todo a 0        
        for (int i = 0; i < totalSize; i++) {
            for (int j = 0; j < totalSize; j++) {
                graph[i][j] = 0;
            }
        }

        //Construimos el graph
        for (int i = 0; i < matrizSize; i++) {
            for (int j = 0; j < matrizSize; j++) {
                if (i > 0) {
                    if (matriz[i - 1][j] == true) {
                        graph[i * matrizSize + j][(i - 1) * matrizSize + j] = 1;
                    }
                }
                if (i < matrizSize - 1) {
                    if (matriz[i + 1][j] == true) {
                        graph[i * matrizSize + j][(i + 1) * matrizSize + j] = 1;
                    }
                }
                if (j > 0) {
                    if (matriz[i][j - 1] == true) {
                        graph[i * matrizSize + j][i * matrizSize + (j - 1)] = 1;
                    }
                }
                if (j < matrizSize - 1) {
                    if (matriz[i][j + 1] == true) {
                        graph[i * matrizSize + j][i * matrizSize + (j + 1)] = 1;
                    }
                }
            }
        }

        return graph;
    }
    
    private int[] getCaminos(boolean [][] map,int nodoDestino){
        return d.dijkstra(createGraph(map), nodoDestino);
    }
}
