import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GravitySimulation extends JPanel {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 900;
    private Planet[] planets;

    public GravitySimulation(Planet[] planets) {
        this.planets = planets;
    }

    private void update() {
        // Update the positions and velocities of all planets
        QuadTree qt = new QuadTree(WIDTH / 2, HEIGHT / 2, Math.max(WIDTH, HEIGHT));

        // Insert all planets into the QuadTree
        for (Planet planet : planets) {
            qt.insert(planet);
        }

        // For each planet, calculate the total force acting on it and update its velocity and position
        for (Planet planet : planets) {
            if (planet.getName().equals("Sun")) continue; // The sun does not move
            double[] force = qt.calculateForce(planet, 0.5); // You can adjust the theta value as needed

            planet.updateVelocity(force[0], force[1]);
            planet.updatePosition();
        }

        // Remove null planets from the array
        planets = Arrays.stream(planets)
                .filter(Objects::nonNull)
                .toArray(Planet[]::new);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set the background color
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw all the planets
        for (Planet planet : planets) {
            g.setColor(planet.getColor());
            g.fillOval((int) planet.getXPos(), (int) planet.getYPos(), (int) (planet.getDiameter()), (int) (planet.getDiameter()));
        }
    }

    public static void main(String[] args) {
        // Create an array of planets
        Random rand = new Random();

        Planet[] planets = new Planet[100000];
        for (int i = 0; i < 100000; i++) {
            int x, y;
            do {
                // Generate random positions within the width and height of your space
                x = rand.nextInt(WIDTH);
                y = rand.nextInt(HEIGHT);
            } while (positionOverlaps(x, y, planets)); // Checks if this position overlaps with any existing planet

            // Generate a random color for the planet
            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();
            Color randomColor = new Color(r, g, b);

            // Use the generated position and color to create the new planet

            planets[i] = new Planet("Planet" + (i + 1), 2,1, x, y, 0, 0, randomColor);
        }

        JFrame frame = new JFrame("Gravity Simulation");
        GravitySimulation simulation = new GravitySimulation(planets);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.add(simulation);
        frame.setVisible(true);

        while (true) {
            simulation.update();
            simulation.repaint();

            try {
                Thread.sleep(1); // Adjust the sleep time to change the simulation speed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean positionOverlaps(int x, int y, Planet[] planets) {
        for (Planet planet : planets) {
            if (planet != null && Math.abs(planet.getXPos() - x) < planet.getDiameter() && Math.abs(planet.getYPos() - y) < planet.getDiameter()) {
                return true;
            }
        }
        return false;
    }
}