package RoboV1;
import robocode.*;

public class RoboV1 extends Robot {
    double tamanho;
    
    public void run() {
        double alturaMapa = getBattleFieldHeight();
        double larguraMapa = getBattleFieldWidth();

        if(getHeading()>=180){
            turnLeft(getHeading()-180);
        }else{
            turnRight(-getHeading());
        }

        while(true) {
            ahead(alturaMapa/4);
            turnGunRight(360);
            turnLeft(90);
            ahead(larguraMapa/4);
            turnGunRight(360);
            turnRight(90);
            ahead(alturaMapa/4);
            turnGunRight(360);
            turnLeft(90);
        }
    }
}