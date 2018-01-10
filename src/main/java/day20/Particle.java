package day20;

public class Particle {
    int positionX;
    int positionY;
    int positionZ;
    int velocityX;
    int velocityY;
    int velocityZ;
    int accelerationX;
    int accelerationY;
    int accelerationZ;

    private static final int X = 0;
    private static final int Y = 1;
    private static final int Z = 2;

    public Particle(String string) {
        String[] params = string.split(" ");

        String positionString = params[0].substring(params[0].indexOf('<') + 1, params[0].indexOf('>'));
        setPosition(positionString);

        String velocityString = params[1].substring(params[1].indexOf('<') + 1, params[1].indexOf('>'));
        setVelocity(velocityString);

        String accelerationString = params[2].substring(params[2].indexOf('<') + 1, params[2].indexOf('>'));
        setAcceleration(accelerationString);
    }

    private void setPosition(String positionString) {
        String[] coordinates = positionString.split(",");

        this.positionX = Integer.parseInt(coordinates[X]);
        this.positionY = Integer.parseInt(coordinates[Y]);
        this.positionZ = Integer.parseInt(coordinates[Z]);
    }

    private void setVelocity(String positionString) {
        String[] coordinates = positionString.split(",");

        this.velocityX = Integer.parseInt(coordinates[X]);
        this.velocityY = Integer.parseInt(coordinates[Y]);
        this.velocityZ = Integer.parseInt(coordinates[Z]);
    }

    private void setAcceleration(String positionString) {
        String[] coordinates = positionString.split(",");

        this.accelerationX = Integer.parseInt(coordinates[X]);
        this.accelerationY = Integer.parseInt(coordinates[Y]);
        this.accelerationZ = Integer.parseInt(coordinates[Z]);
    }

    public void move() {
        this.positionX += this.velocityX;
        this.positionY += this.velocityY;
        this.positionZ += this.velocityZ;

        this.velocityX += this.accelerationX;
        this.velocityY += this.accelerationY;
        this.velocityZ += this.accelerationZ;
    }

    public boolean samePosition(Particle particle) {
        return this.positionX == particle.positionX && this.positionY == particle.positionY && this.positionZ == particle.positionZ;
    }


    public int getDistance() {
        return Math.abs(this.positionX) + Math.abs(this.positionY) + Math.abs(this.positionZ);
    }
}
