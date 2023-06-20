public class QuadTree {
    private static final double G = 1;  // Gravitational constant
    private static final int MAX_CAPACITY = 1;  // Maximum bodies a node can hold before partitioning
    private double xCenter, yCenter, length;  // Center and length of this quad
    private Planet body;  // Body held by this quad
    private QuadTree NW, NE, SW, SE;  // Subquads
    private double totalMass;  // Total mass of bodies in this quad
    private double xCenterOfMass, yCenterOfMass;  // Position of center of mass in this quad

    public QuadTree(double xCenter, double yCenter, double length) {
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.length = length;
    }


    // Returns the total gravitational force exerted on the given body by the bodies in this quad
    public void insert(Planet body) {
        // If the body is not in this quad, return
        if (!inQuad(body)) return;

        // If this quad does not contain a body, add the body to this quad
        if (this.body == null) {
            this.body = body;
            this.totalMass = body.getMass();
            this.xCenterOfMass = body.getXPos();
            this.yCenterOfMass = body.getYPos();
            return;
        }

        // If this quad does contain a body, partition this quad if not already partitioned
        if (NW == null) {
            partition();
        }

        // Update the total mass and center of mass
        double totalPrevMass = this.totalMass;
        this.totalMass += body.getMass();
        this.xCenterOfMass = (this.xCenterOfMass * totalPrevMass + body.getXPos() * body.getMass()) / this.totalMass;
        this.yCenterOfMass = (this.yCenterOfMass * totalPrevMass + body.getYPos() * body.getMass()) / this.totalMass;

        // Insert the body into the appropriate subquad
        NW.insert(this.body);
        NE.insert(this.body);
        SW.insert(this.body);
        SE.insert(this.body);
        NW.insert(body);
        NE.insert(body);
        SW.insert(body);
        SE.insert(body);

        // Remove the body from this quad
        this.body = null;
    }

    private void partition() {
        NW = new QuadTree(xCenter - length / 4, yCenter + length / 4, length / 2);
        NE = new QuadTree(xCenter + length / 4, yCenter + length / 4, length / 2);
        SW = new QuadTree(xCenter - length / 4, yCenter - length / 4, length / 2);
        SE = new QuadTree(xCenter + length / 4, yCenter - length / 4, length / 2);
    }

    public double[] calculateForce(Planet body, double theta) {
        double dx = xCenterOfMass - body.getXPos();
        double dy = yCenterOfMass - body.getYPos();
        double softening = 1e2;
        double distance = Math.sqrt(dx * dx + dy * dy);
        distance += softening;
        if (this.body != null && this.body != body) {
            // If this quad contains a body and it's not the same as the input body, calculate force
            double force = (G * totalMass * body.getMass()) / (distance * distance);
            return new double[]{force * dx / distance, force * dy / distance};
        } else if (this.body == null && (length / distance) < theta) {
            // If this quad does not contain a body and is sufficiently far away (s/d < theta), calculate force
            double force = (G * totalMass * body.getMass()) / (distance * distance);
            return new double[]{force * dx / distance, force * dy / distance};
        } else if (NW == null) {
            // If this quad does not contain a body and is not partitioned, no force
            return new double[]{0, 0};
        } else {
            // If this quad does not contain a body and is partitioned, recursively calculate force
            double[] NWForce = NW.calculateForce(body, theta);
            double[] NEForce = NE.calculateForce(body, theta);
            double[] SWForce = SW.calculateForce(body, theta);
            double[] SEForce = SE.calculateForce(body, theta);
            return new double[]{NWForce[0] + NEForce[0] + SWForce[0] + SEForce[0], NWForce[1] + NEForce[1] + SWForce[1] + SEForce[1]};
        }
    }

    // Checks if a body is in a quad
    private boolean inQuad(Planet body) {
        return body.getXPos() >= this.xCenter - this.length / 2 &&
                body.getXPos() <  this.xCenter + this.length / 2 &&
                body.getYPos() >= this.yCenter - this.length / 2 &&
                body.getYPos() <  this.yCenter + this.length / 2;
    }
}