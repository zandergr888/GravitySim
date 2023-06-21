# High-Performance N-Body Gravity Simulation with Barnes-Hut Algorithm

This project presents a high-performance gravity simulation that capitalizes on the power of the Barnes-Hut algorithm, an approximation algorithm renowned for optimizing the computational complexity of calculating gravitational forces within a large-scale system of celestial bodies.

## Overview

The gravity simulation employs the Barnes-Hut algorithm to accurately compute gravitational interactions with high computational efficiency. The algorithm operates by dividing the simulated space into a hierarchical, quadtree structure, which allows for the approximation of distant celestial clusters as singular entities. This mechanism significantly reduces the computational load in comparison to traditional, direct computation methods.

The simulation delivers a vivid, real-time visual representation of the motion and gravitational interactions of celestial bodies. Simulated bodies exhibit behaviors such as orbiting and interaction influenced by gravitational forces. On my machine, the simulation was able to fluidly handle as many as 100,000 celestial bodies compared to only about 2,000 without the algorithm, demonstrating the power and efficiency of the Barnes-Hut algorithm.
## Features

High-performance computation of gravitational forces utilizing the Barnes-Hut algorithm, facilitating the fluid simulation of large celestial systems.
Dynamic, real-time visualization of celestial bodies and their movements, providing an immersive experience for the user.
Flexibility in simulation parameters, allowing customization of mass, initial positions, and velocities of celestial bodies.
A user-friendly, interactive interface that allows users to explore and experiment with the simulation.


### Visualization
![](
https://github.com/zandergr888/GravitySim/blob/master/grav3000gif.gif
)
![](
https://github.com/zandergr888/GravitySim/blob/master/gifSolar.gif
)

## Requirements

- Java Development Kit (JDK) 8 or later
- JavaFX library

## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/zandergr888/GravitySim.git


2. Build the project using your preferred build tool or IDE.

3. Run the simulation by executing the main class.

4. Explore the simulation interface and observe the celestial bodies as they interact and move under the influence of gravity.

## Computational Complexity Reduction
The implementation of the Barnes-Hut algorithm has effectively optimized the simulation from a computational complexity of O(n²) to O(n log n). This improvement stems from the algorithm's ability to subdivide the space into regions and represent distant groups of bodies as a single body. The computation then, instead of calculating pairwise forces, computes forces between bodies and regions, significantly reducing the number of computations. This transformation empowers the simulator to handle tens of thousands of celestial bodies in real-time, offering an unparalleled performance gain over traditional brute-force methods.




