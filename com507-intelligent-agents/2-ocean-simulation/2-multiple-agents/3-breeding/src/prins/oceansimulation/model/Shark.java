/*
 * Copyright (C) 2019 Prins Butt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package prins.oceansimulation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import prins.oceansimulation.OceanConfig;
import prins.simulator.model.Agent;
import prins.simulator.model.Location;

/**
 *
 * @author Prins Butt
 */
public class Shark extends Agent {

    private static final Random RANDOM_GENERATOR = new Random(OceanConfig.SEED);
    private final int MAX_AGE;

    private int age;
    private boolean isAlive;

    public Shark(int x, int y) {
        super(new Location(x, y));
        MAX_AGE = RANDOM_GENERATOR.nextInt(OceanConfig.SHARK_MAXIMUM_AGE);
        isAlive = true;
    }

    public Shark(Location location) {
        this(location.getX(), location.getY());
    }

    public List<Shark> act(Ocean ocean) {
        List<Shark> descendants = new ArrayList<>();
        
        age();

        if (isAlive) {
            swim(ocean);
            descendants = breed(ocean);
        }
        
        return descendants;
    }

    public boolean isAlive() {
        return isAlive;
    }

    private void age() {
        age++;

        if (age > MAX_AGE) {
            isAlive = false;
        }
    }

    private List<Shark> breed(Ocean ocean) {

        List<Shark> descendants = new ArrayList<>();
        List<Location> freeLocations = ocean.findFreeLocations(location);

        if (freeLocations.size() > 0) {
            if (RANDOM_GENERATOR.nextDouble()
                    < OceanConfig.SHARK_BREEDING_PROBABILITY) {

                int index = RANDOM_GENERATOR.nextInt(freeLocations.size());
                Shark shark = new Shark(freeLocations.get(index));
                ocean.setAgent(shark, shark.getLocation());
                descendants.add(shark);
            }
        }

        return descendants;
    }

    private void swim(Ocean ocean) {
        List<Location> freeLocations = ocean.findFreeLocations(location);

        if (freeLocations.size() > 0) {
            ocean.setAgent(null, location);

            int index = RANDOM_GENERATOR.nextInt(freeLocations.size());
            location = freeLocations.get(index);

            ocean.setAgent(this, location);
        }
    }
}
