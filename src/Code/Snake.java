
package Code;

import java.util.ArrayList;
import processing.core.PVector;

public class Snake {
    //Aquí sera donde se almacenen las posiciones del snake
    public ArrayList<Integer> posX = new ArrayList<>();
    public ArrayList<Integer> posY = new ArrayList<>();
    
    public boolean alive = true;
    
    //Son las dos posiciones donde empieza
    private final PVector initialPosition1;
    private final PVector initialPosition2;
    
    public int r,g,b;
    
    public Snake(int r,int g,int b, PVector initial1, PVector initial2){
        this.r = r;
        this.g = g;
        this.b = b;
        this.initialPosition1 = initial1;
        this.initialPosition2 = initial2;
        
        restart();
    }
    
    //Mueve la serpiente humana añadiendo la posicion nueva al inicio y despues elimina la última
    //Lo que le pasamos son las coordenadas de un vector que solo pueda tener 1 o -1 de tal forma que solo se mueva una casilla al sumarla
    public void mover(int x,int y){
        posX.add(0,posX.get(0)+ x);
        posY.add(0,posY.get(0)+ y);
        
        posX.remove(posX.size()-1);
        posY.remove(posY.size()-1);
    }
    
    //Añade una nueva posicion
    public void comer(){
        posX.add(posX.get(posX.size() - 1));
        posY.add(posY.get(posY.size() - 1));
    }
    
    //Pone todo como al inicio
    public void restart(){
        posX.clear();
        posY.clear();
        alive = true;
        
        posX.add((int)initialPosition1.x);
        posY.add((int)initialPosition1.y);
        posX.add((int)initialPosition2.x);
        posY.add((int)initialPosition2.y);
    }
}
