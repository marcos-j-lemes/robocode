package c11b15;
import robocode.*;
import java.awt.Color;
import static robocode.util.Utils.normalRelativeAngleDegrees;

public class Spire extends AdvancedRobot {
    private int moveDirection = 1;
    private double lastEnemyEnergy = 100;
    
    public void run() {
        // Configuração inicial
        setColors(Color.pink, Color.black, Color.red);
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        
        // Radar girando continuamente
        setTurnRadarRight(Double.POSITIVE_INFINITY);
        
        // Comportamento principal
        while (true) {
            // Movimento em padrão mais dinâmico
            moveDirection *= -1;
            setAhead(100 * moveDirection);
            
            // Movimento lateral para evitar tiros
            if (Math.random() > 0.7) {
                setTurnRight(90 - getHeading() + (Math.random() * 60 - 30));
            }
            
            execute();
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        // Foco no inimigo escaneado
        double absoluteBearing = getHeading() + e.getBearing();
        setTurnRadarLeft(getRadarTurnRemaining());
        
        // Mira simplificada mas eficaz
        double gunTurn = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
        setTurnGunRight(gunTurn);
        
        // Disparo inteligente baseado na mudança de energia
        if (getGunHeat() == 0) {
            double firePower = Math.min(3, Math.max(1, 3 - e.getDistance()/200));
            if (e.getEnergy() < lastEnemyEnergy) {
                firePower = Math.min(firePower + 0.5, 3);
            }
            setFire(firePower);
        }
        lastEnemyEnergy = e.getEnergy();
        
        // Movimento evasivo
        if (e.getDistance() < 200) {
            setBack(100);
            setTurnRight(normalRelativeAngleDegrees(e.getBearing() + 90));
        } else {
            setAhead(80);
        }
        
        execute();
    }

    public void onHitByBullet(HitByBulletEvent e) {
        // Reação evasiva
        setBack(100);
        setTurnRight(normalRelativeAngleDegrees(e.getBearing() + 90));
        execute();
    }
    
    public void onHitWall(HitWallEvent e) {
        // Melhor tratamento de colisão com paredes
        setBack(100);
        setTurnRight(90);
        setAhead(50);
        execute();
    }
    
    public void onWin(WinEvent e) {
        // Animação de vitória
        for (int i = 0; i < 10; i++) {
            turnRight(36);
        }
    }
}