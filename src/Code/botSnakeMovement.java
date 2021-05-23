
package Code;

import com.sun.org.apache.xml.internal.security.transforms.implementations.TransformXPointer;
import processing.core.*;

public class botSnakeMovement extends PApplet{
    final int filas = 25;
    int headNodo;
    int tailNodo;
    int snakeLength;
    boolean mapb[][];
    int counter;
    int[] longestRoad;
    int[][] grafo;
    PVector vHead = new PVector(0,0);
    PVector lastMove = new PVector(0,0);
    String direction = "";
    
    Dijkstra dij = new Dijkstra();
    
    
    public PVector getNewPosition(boolean map[][], PVector snakeHead,PVector snakeTail, PVector apple, int snakeLengh){
        this.snakeLength = snakeLengh;
        this.mapb = map;
        this.grafo = createGraph(map);
        vHead.set(snakeHead);
        int appleNodo = transformVectorIntoNodo(apple);
        headNodo = transformVectorIntoNodo(snakeHead);
        tailNodo = transformVectorIntoNodo(snakeTail);
        
        return getSolution(dij.dijkstra(grafo, appleNodo));
    }
    
    private PVector getSolution(int[] dist){
        //El nodo origen es la cabeza de la serpiente siempre
        int originNodo = headNodo;
        
        int i = (int) originNodo / filas;
        int j = originNodo % filas;
        
        int min = Integer.MAX_VALUE;
        int provisionalNodo = 0;
        
        //Comprobará todas las casillas a las que puede acceder en este movimiento, y se irá por aquella que 
        //que más rapido llegue hacia la manzana. También tiene en cuenta si al entrar a esa posición se quedará 
        //encerrada. Si es así, sencilla mente no escojera ese camino. 
        
        //Arriba
        if (i > 0 && !direction.equals("abajo")) {
            if (checkMove((i - 1) * filas + j, tailNodo)) {
                if (dist[(i - 1) * filas + j] < min) {
                    min = dist[(i - 1) * filas + j];
                    provisionalNodo = (i - 1) * filas + j;
                }
            }
        }
        //Abajo
        if (i < filas - 1 && !direction.equals("arriba")) {
            if (checkMove((i + 1) * filas + j, tailNodo)) {
                if (dist[(i + 1) * filas + j] < min) {
                    min = dist[(i + 1) * filas + j];
                    provisionalNodo = (i + 1) * filas + j;
                }
            }
        }
        //Izquierda
        if (j > 0 && !direction.equals("derecha")) {
            if (checkMove(i * filas + j - 1, tailNodo)) {
                if (dist[i * filas + j - 1] < min) {
                    min = dist[i * filas + j - 1];
                    provisionalNodo = i * filas + j - 1;
                }
            }

        }
        //Derecha
        if (j < filas - 1 && !direction.equals("izquierda")) {
            if (checkMove(i * filas + j + 1, tailNodo)) {
                if (dist[i * filas + j + 1] < min) {
                    min = dist[i * filas + j + 1];
                    provisionalNodo = i * filas + j + 1;
                }
            }

        }
        
        
        //Ya sabiendo a que nodo iremos, ahora le añadimos el movimeinto en sí.
        lastMove.set((provisionalNodo % filas)-vHead.x, (int) (provisionalNodo/filas) - vHead.y);
        
        //Aqui marcamos cual será la nueva direccion para que nunca pueda ir en contraria
        if(lastMove.x == 1){
            direction = "derecha";
        }
        else if(lastMove.x == -1){
            direction = "izquierda";
        }
        else if(lastMove.y == 1){
            direction = "abajo";
        }
        else if(lastMove.y == -1){
            direction = "arriba";
        }
        
        return lastMove;
    }
    
    //Metodo donde se comprueba si al entrar a esa casilla se va a encerrar el muy imbecil
    private boolean checkMove(int nextNodo, int tailNodo){
        boolean map[][] = createFutureMap();
        int[] dist = dij.dijkstra(createGraph(map), nextNodo);
        
        //Comprueba si puede acceder a su cola y si el recorrido que puede hacer sera mayor del que mida la serpiente
        if(dist[tailNodo] == Integer.MAX_VALUE && (numberOfPosibleMoves(dist)-5) < snakeLength){
            return false;
        }
        else{
            return true;
        }
    }
    //Creamos un mapa booleano con la posicion de la serpiente movida
    private boolean[][] createFutureMap() {
        boolean[][] newMap = new boolean[filas][filas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < filas; j++) {
                newMap[i][j] = mapb[i][j];
            }
        }

        PVector tail = transformIntoVector(tailNodo);
        PVector head = transformIntoVector(headNodo);

        //Ponemos la cola a negativo y buscamos su siguiente posicion en cola para tambien borrarla
        //Desgraciadamente por como esta heho no me queda mas opción que eliminar más de las que debo pero espero que esto no afecte mucho
        newMap[(int) tail.y][(int) tail.x] = true;

        if (tail.y > 0) {
            if (newMap[(int) tail.y - 1][(int) tail.x] == false) {
                newMap[(int) tail.y - 1][(int) tail.x] = true;
            }
        }
        if (tail.y < filas - 1) {
            if (newMap[(int) tail.y + 1][(int) tail.x] == false) {
                newMap[(int) tail.y + 1][(int) tail.x] = true;
            }
        }
        if (tail.x > 0) {
            if (newMap[(int) tail.y][(int) tail.x - 1] == false) {
                newMap[(int) tail.y][(int) tail.x - 1] = true;
            }
        }
        if (tail.x < filas - 1) {
            if (newMap[(int) tail.y][(int) tail.x + 1] == false) {
                newMap[(int) tail.y][(int) tail.x + 1] = true;
            }
        }

        //Ponemos la cabeza a false
        newMap[(int) head.y][(int) head.x] = false;

        return newMap;
    }
    
    //Cuenta todas las casillas a las que puedo acceder
    private int numberOfPosibleMoves(int[] dist){
        int cont = 0;
        for(int i = 0;i<dist.length;i++){
            if(dist[i] != Integer.MAX_VALUE){
                cont++;
            }
        }
        return cont;
    }
    
    public int [][] createGraph(boolean[][]matriz){
        int matrizSize = matriz.length;
        int graph[][] = new int[matrizSize][matrizSize];
        
        //Inicializamos la matriz con todo a 0, es decir, que nada puede pasar de momento
        for(int i = 0;i<matrizSize;i++){
            for(int j = 0;j<matrizSize;j++){
                graph[i][j] = 0;
            }
        }
        
        //Contruimos el graph. Para ello lo que hacemos recorrer cada casilla de nuestro mapa. En cada casilla mira
        //a ver si las casillas de su alrededor son true. Si es así, significa que por ahí puede pasar y por lo tanto lo marcamos
        //como un 1. 
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
    
    private int transformVectorIntoNodo(PVector p){
        int nodo = (int) (p.x + p.y * filas);
        return nodo;
    }
    private PVector transformIntoVector(int nodo){
        PVector v = new PVector((int)nodo%filas,(int)nodo/filas);
        return v;
    }
}
