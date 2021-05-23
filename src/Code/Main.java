
package Code;

import processing.core.*;
public class Main extends PApplet{

    public static void main(String[] args) {
        PApplet.main(new String[]{Code.Main.class.getName()});
    }
    
    int filas = 25;
    int columnas = 25;
    int bs = 20;
    
    boolean map[][] = new boolean[filas][columnas];
    PVector direction = new PVector(1,0);
    
    boolean greenBox = true;
    boolean purpleBox = true;
    
    Apple apple = new Apple();
    Snake humanSnake = new Snake(100,200,100,new PVector(2,2), new PVector(2,1));
    Snake botSnake = new Snake(100,100,200, new PVector(18,18), new PVector(18,19));
    
    @Override
    public void settings(){
        size(500,540);
    }
    
    @Override
    public void setup(){
        frameRate(25);
        initGame();
    }
    
    @Override
    public void draw(){
        background(25);
        drawMap();
        drawApple();
        
        playHumanSnake();
        playBotSnake(botSnake);
    }
    
    void initGame(){
        updateMap();
        apple.spawn(map);
    }
    
    void updateMap(){
        //Asignamos todo el mapa a true primero
        for(int i = 0;i<filas;i++){
            for(int j = 0;j<columnas;j++){
                map[i][j] = true;
            }
        }
        
        //Asignamos las posiciones de la serpiente humana como ocupadas
        for(int i = 1; i < humanSnake.posX.size();i++){
            map[humanSnake.posY.get(i)][humanSnake.posX.get(i)] = false;
        }
        
        //Asignamos las posiciones de la serpiente bot como ocupadas
        for(int i = 1; i < botSnake.posX.size();i++){
            map[botSnake.posY.get(i)][botSnake.posX.get(i)] = false;
        }
    }
    
    void drawMap(){
        //Dibujamos el cuadrado gris de abajo
        fill(100,100,100);
        rect(0,500,width,40);
        
        //Dibujamos las dos casillas del marcador
        fill(100,200,100);
        rect(30,510,210,20);
        
        fill(100,100,200);
        rect(270,510,210,20);
        
        if(greenBox == false){
            fill(250,50,50);
            rect(30,510,210,20);
        }
        if(purpleBox == false){
            fill(250,50,50);
            rect(270,510,210,20);
        }
    }
    
    void drawApple(){
        fill(215,0,75);
        rect(apple.position.x * bs, apple.position.y * bs, bs, bs);
    }
    
    void playHumanSnake(){
        //Si esta viva que juegue
        if(humanSnake.alive == true){
            moveHumanSnake();
            drawSnake(humanSnake);
            detectBorder(humanSnake);
            snakeCrash(humanSnake,botSnake);
        }
    }
    
    void moveHumanSnake(){
        humanSnake.mover((int)direction.x, (int)direction.y);
        eat(humanSnake);
    }
    
    void eat(Snake snake){
        //Comprueba si la cabeza de la serpiente (es decir, la posicion 0) es la misma posicion que la de la manzana
        if(snake.posX.get(0) == apple.position.x && snake.posY.get(0) == apple.position.y){
            apple.spawn(map);
            snake.comer();
        }
    }
    
    void drawSnake(Snake snake){
        //Rellenamos con el color asignado y despues recorremos todas sus posiciones dibujando en pantalla
        fill(snake.r,snake.g,snake.b);
        for(int i = 0;i<snake.posX.size();i++){
            rect(snake.posX.get(i) * bs, snake.posY.get(i) * bs,bs,bs);
        }
    }
    
    void detectBorder(Snake s){
        //Detecta si se sale del mapa la cabeza de la serpiente
        if(s.posX.get(0) < 0 || s.posX.get(0) > (columnas-1) || s.posY.get(0) < 0 || s.posY.get(0) > (filas-1)){
            s.restart();
        }
    }
    
    void snakeCrash(Snake s1, Snake s2){
        //Comprueba si se choca consigo misma, para ello recorre todas sus posiciones comprobando que no sean iguales a las de su cabeza
        if(s1.alive == true){
            for(int i = 2;i<s1.posX.size();i++){
                if(s1.posX.get(0) == s1.posX.get(i) && s1.posY.get(0) == s1.posY.get(i)){
                    s1.restart();
                }
            }
        }
        
        //Comprueba si se la s1 se choca con la serpiente s2. Metodo igual que el anterior pero con la otra serpiente
        if(s1.alive == true && s2.alive == true){
            for(int i = 0;i<s2.posX.size();i++){
                if(s1.posX.get(0) == s2.posX.get(i) && s1.posY.get(0) == s2.posY.get(i)){
                    s1.restart();
                }
            }
        }
    }
    
    void playBotSnake(Snake bot){
        if(bot.alive == true){
            moveBotSnake(bot);
            drawSnake(bot);
            detectBorder(bot);
            snakeCrash(bot,humanSnake);
        }
    }
    
    void moveBotSnake(Snake s1){
        s1.mover(apple.getPosition(), map);
        eat(s1);
    }
    
    void deleteSanke(Snake s){
        s.posX.clear();
        s.posY.clear();
        s.alive = false;
    }
            
    void restartGame(){
        //Comienza la partida de 0
        humanSnake.restart();
        initGame();
    }
    
    @Override
    public void keyPressed(){
        if(key == 'w' || keyCode == UP){
            if(direction.y != 1){
                direction.set(0,-1);
            }
        }
        if(key == 's' || keyCode == DOWN){
            if(direction.y != -1){
                direction.set(0,1);
            }
        }
        if(key == 'a' || keyCode == LEFT){
            if(direction.x != 1){
                direction.set(-1,0);
            }
        }
        if(key == 'd' || keyCode == RIGHT){
            if(direction.x != -1){
                direction.set(1,0);
            }
        }
        //Reinicia el juego
        if(key == ' '){
            restartGame();
        }
    }
    
    @Override
    public void mouseClicked(){
        //Comprueba si esta en el cuadrado verde el click. Si es así cambiara el estado del snake muerto-vivo
        if(mouseX >= 30 && mouseX <= 240 && mouseY >= 510 && mouseY <= 530){
            greenBox = !greenBox;
            
            if(humanSnake.alive == true){
                deleteSanke(humanSnake);
            }
            else{
                humanSnake.restart();
            }
        }
        //Comprueba si esta en el cuadrado morado el click.
        if(mouseX >= 270 && mouseX <= 480 && mouseY >= 510 && mouseY <= 530){
            purpleBox = !purpleBox;
            
            if(botSnake.alive == true){
                deleteSanke(botSnake);
            }
            else{
                botSnake.restart();
            }
        }
    }
    
    
}
