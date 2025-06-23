package MeuRoboVencedorV4;
import robocode.*;
import robocode.util.Utils;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.*;

public class MeuRoboVencedorV4 extends AdvancedRobot {
    // Informações do inimigo
    private double inimigoX, inimigoY;
    private long ultimaLeituraRadar = 0;
    private int direcaoMovimento = 1;

    // Wave surfing
    private List<BulletWave> waves = new ArrayList<>();

    // Guess factor targeting
    private static final int BINS = 31;
    private int[] stats = new int[BINS];
    private double maxEscapeAngle;

    public void run() {
        setColors(Color.black, Color.blue, Color.red, Color.yellow, Color.green);
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        maxEscapeAngle = Rules.getBulletSpeed(3) / 8; // escape angle

        while (true) {
            if (getTime() - ultimaLeituraRadar > 4) {
                setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
            }

            moverComWaveSurfing();
            execute();
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        ultimaLeituraRadar = getTime();

        double absBearing = getHeadingRadians() + e.getBearingRadians();
        double dist = e.getDistance();
        inimigoX = getX() + dist * Math.sin(absBearing);
        inimigoY = getY() + dist * Math.cos(absBearing);

        double radarTurn = Utils.normalRelativeAngle(absBearing - getRadarHeadingRadians());
        setTurnRadarRightRadians(radarTurn * 2);

        // Registrar wave inimiga
        if (e.getEnergy() < getEnergy()) {
            double bulletPower = getEnergy() - e.getEnergy();
            waves.add(new BulletWave(getX(), getY(), getTime(), absBearing, Rules.getBulletSpeed(bulletPower), direcaoMovimento));
        }

        // Guess factor targeting: escolha bin mais provável
        int best = BINS / 2;
        for (int i = 0; i < BINS; i++)
            if (stats[i] > stats[best]) best = i;
        double guessFactor = (best - (BINS - 1) / 2) / ((BINS - 1) / 2);
        double targetAngle = absBearing + guessFactor * maxEscapeAngle;

        // Mira
        double bulletPower = Math.min(3, Math.min(getEnergy()/4, 400/dist));
        bulletPower = Math.max(0.1, bulletPower);
        setTurnGunRightRadians(Utils.normalRelativeAngle(targetAngle - getGunHeadingRadians()));
        if (getGunHeat() == 0 && Math.abs(getGunTurnRemainingRadians()) < 0.2) {
            setFire(bulletPower);
        }

        // Atualiza estatísticas ao impactar
        Iterator<BulletWave> it = waves.iterator();
        while (it.hasNext()) {
            BulletWave w = it.next();
            if (w.checkHit(getTime(), inimigoX, inimigoY)) {
                int idx = w.getFactorIndex(inimigoX, inimigoY);
                stats[idx]++;
                it.remove();
            }
        }
    }

    public void onHitByBullet(HitByBulletEvent e) {
        direcaoMovimento *= -1;
    }

    public void onHitWall(HitWallEvent e) {
        direcaoMovimento *= -1;
    }

    private void moverComWaveSurfing() {
        // Simular custo dos dois lados e escolher movimento mais seguro
        double angleToEnemy = Utils.normalAbsoluteAngle(Math.atan2(inimigoX - getX(), inimigoY - getY()));
        double[] angles = { angleToEnemy + maxEscapeAngle, angleToEnemy - maxEscapeAngle };
        double bestDanger = Double.MAX_VALUE;
        double bestAngle = angles[0];

        for (double ang : angles) {
            double danger = calcDanger(getX(), getY(), ang);
            if (danger < bestDanger) { bestDanger = danger; bestAngle = ang; }
        }

        double turn = Utils.normalRelativeAngle(bestAngle - getHeadingRadians());
        setTurnRightRadians(turn);
        setAhead(100 * direcaoMovimento);
    }

    private double calcDanger(double x, double y, double ang) {
        double res = 0;
        for (BulletWave w : waves) {
            double predictedX = x + Math.sin(ang) * 100;
            double predictedY = y + Math.cos(ang) * 100;
            int bin = w.getFactorIndex(predictedX, predictedY);
            double density = stats[bin] + 0.1;
            res += density;
        }
        return res;
    }

    // Classe para modelar onda de bala inimiga (bullet wave)
    private class BulletWave {
        double x, y, startTime, angle;
        double speed;
        int direction;
        BulletWave(double x, double y, double t, double a, double s, int dir) {
            this.x = x; this.y = y; startTime = t;
            angle = a; speed = s; direction = dir;
        }
        boolean checkHit(long now, double ex, double ey) {
            return (now - startTime) * speed > Point2D.distance(x, y, ex, ey) - speed;
        }
        int getFactorIndex(double ex, double ey) {
            double offset = Utils.normalRelativeAngle(Math.atan2(ex - x, ey - y) - angle);
            double factor = Math.sin(offset) / maxEscapeAngle * direction;
            int idx = (int) Math.round((factor + 1) * (BINS - 1) / 2.0);
            return Math.max(0, Math.min(BINS - 1, idx));
        }
    }
}