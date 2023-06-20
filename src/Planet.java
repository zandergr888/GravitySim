import java.awt.*;

public class Planet {
    private String name;
    private double mass;
    private double diameter;
    private double xPos;
    private double yPos;
    private double xVel;
    private double yVel;
    private Color color;

    public Planet(String name, double mass, double diameter, double xPos, double yPos, double xVel, double yVel, Color color) {
        this.name = name;
        this.mass = mass;
        this.diameter = diameter;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVel = xVel;
        this.yVel = yVel;
        this.color = color;
    }



    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public double getMass() {
        return mass;
    }

    public double getDiameter() {
        return diameter;
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public double getXVel() {
        return xVel;
    }

    public double getYVel() {
        return yVel;
    }

    public void updatePosition() {
        xPos += xVel;
        yPos += yVel;
    }

    public void updatePosition(double velocityX, double velocityY) {
        xPos += velocityX;
        yPos += velocityY;
    }

    public void updateVelocity(double accelerationX, double accelerationY) {
        xVel += accelerationX;
        yVel += accelerationY;
    }
}




