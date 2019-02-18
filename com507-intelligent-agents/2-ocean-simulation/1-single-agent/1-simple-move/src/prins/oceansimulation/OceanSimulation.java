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
package prins.oceansimulation;

import java.awt.Color;
import prins.oceansimulation.model.Ocean;
import prins.oceansimulation.model.Shark;
import prins.simulator.Simulator;
import prins.simulator.model.Location;
import prins.simulator.view.Gui;

/**
 *
 * @author Prins Butt
 */
public class OceanSimulation extends Simulator {

    private final Gui gui;
    private final Ocean ocean;

    private final Shark shark;
    
    public OceanSimulation() {
        ocean = new Ocean();
        gui = new Gui(ocean);
        gui.registerAgentColors(Shark.class, Color.red);
        
        shark = new Shark(15, 15);
        ocean.setAgent(shark, shark.getLocation());
    }

    @Override
    protected void prepare() {
        gui.addPropertyChangeListener(this);
    }

    @Override
    protected void render() {
        gui.setStep(step);
        gui.render();
    }

    @Override
    protected void reset() {
        ocean.clear();
        shark.setLocation(new Location(15, 15));
        ocean.setAgent(shark, shark.getLocation());
    }

    @Override
    protected void update() {
        shark.act(ocean);
    }
}
