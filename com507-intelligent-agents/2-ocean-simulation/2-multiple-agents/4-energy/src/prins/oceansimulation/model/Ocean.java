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
import prins.simulator.Config;
import prins.simulator.model.Agent;
import prins.simulator.model.Environment;
import prins.simulator.model.Location;

/**
 *
 * @author Prins Butt
 */
public class Ocean extends Environment {

    Agent[][] world = new Agent[Config.world_width][Config.world_height];

    @Override
    public void clear() {
        world = new Agent[Config.world_width][Config.world_height];
    }

    public List<Location> findFreeLocations(Location location) {
        List<Location> freeLocations = new ArrayList<>();

        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                int row = location.getY() + rowOffset;
                int col = location.getX() + colOffset;

                if (row >= 0 && row <= world.length - 1
                        && col >= 0 && col <= world[0].length - 1
                        && world[row][col] == null) {
                    freeLocations.add(new Location(col, row));
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
        return Config.world_height;
    }

    @Override
    public int getWidth() {
        return Config.world_width;
    }

    @Override

    public void setAgent(Agent agent, Location location) {
        world[location.getY()][location.getX()] = agent;
    }

}
