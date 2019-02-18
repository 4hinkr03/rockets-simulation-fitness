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
import java.util.Arrays;
import java.util.List;
import prins.simulator.Config;
import prins.simulator.model.Agent;
import prins.simulator.model.Environment;
import prins.simulator.model.Location;

/**
 *
 * @author Prins Butt
 */
public class Ocean extends Environment {

    Agent[][] world = new Agent[Config.WORLD_WIDTH][Config.WORLD_HEIGHT];

    @Override
    public void clear() {
        world = new Agent[Config.WORLD_WIDTH][Config.WORLD_HEIGHT];
    }

    public List<Location> findFreeLocations(Location location) {
        List<Location> freeLocations = new ArrayList<>();

        int rowStart = Math.max(location.getY() - 1, 0);
        int rowFinish = Math.min(location.getY() + 1, world.length - 1);
        int colStart = Math.max(location.getX() - 1, 0);
        int colFinish = Math.min(location.getX() + 1, world[0].length - 1);

        for (int row = rowStart; row <= rowFinish; row++) {
            for (int col = colStart; col <= colFinish; col++) {
                if (world[row][col] == null) {
                    freeLocations.add(new Location(row, col));
                }
            }
        }

        return freeLocations;
    }

    @Override
    public Agent getAgent(Location location) {
        return world[location.getY()][location.getX()];
    }

    @Override
    public int getHeight() {
        return Config.WORLD_WIDTH;
    }

    @Override
    public int getWidth() {
        return Config.WORLD_HEIGHT;
    }

    @Override
    public void setAgent(Agent agent, Location location) {
        world[location.getY()][location.getX()] = agent;
    }

}
