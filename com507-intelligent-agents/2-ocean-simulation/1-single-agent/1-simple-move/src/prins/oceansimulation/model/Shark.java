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

import java.util.List;
import prins.simulator.model.Agent;
import prins.simulator.model.Location;

/**
 *
 * @author Prins Butt
 */
public class Shark extends Agent {
    
    public Shark(int x, int y) {
        super(new Location(x, y));
    }
    
    public void act(Ocean ocean) {
        List<Location> freeLocations = ocean.findFreeLocations(location);
        ocean.setAgent(null, location);
        ocean.setAgent(this, freeLocations.get(0));
        location = freeLocations.get(0);
    }
}
