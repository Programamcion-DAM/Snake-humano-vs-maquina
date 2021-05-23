
package Code;
import processing.core.*;

public class Apple extends PApplet{
    
    public PVector position = new PVector(0,0);
    
    public Apple(){
        
    }
    
    //Le da una nueva posicion a la manzana comproabdo que ahí no haya serpiente
    public void spawn(boolean[][] map){
        boolean done = false;
        while(done == false){
            int x = (int) random(0,24);
            int y = (int) random(0,24);
            
            if(map[y][x] == true){
                done = true;
                position.x = x;
                position.y = y;
            }
        }
    }
    
    public PVector getPosition(){
        return position;
    }
}
