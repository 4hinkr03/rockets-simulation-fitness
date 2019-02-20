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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import prins.oceansimulation.model.Ocean;
import prins.oceansimulation.model.Shark;
import prins.simulator.Simulator;
import prins.simulator.view.Gui;

/**
 *
 * @author Prins Butt
 */
public class OceanSimulation extends Simulator {

    private final Gui gui;
    private final Ocean ocean;

    private Random random;
    private final List<Shark> sharks;

    public OceanSimulation() {
        ocean = new Ocean();
        gui = new Gui(ocean);
        gui.registerAgentColors(Shark.class, Color.red);

        sharks = new ArrayList<>();

        populate();
    }

    private void populate() {
        random = new Random(OceanConfig.SEED);

        for (int row = 0; row < ocean.getWidth(); row++) {
            for (int col = 0; col < ocean.getHeight(); col++) {

                if (random.nextDouble() < OceanConfig.SHARK_BREEDING_PROBABILITY) {
                    Shark shark = new Shark(col, row);
                    ocean.setAgent(shark, shark.getLocation());
                    sharks.add(shark);
                }
            }
        }
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
        sharks.clear();
        populate();
    }

    @Override
    protected void update() {
        Iterator<Shark> itr = sharks.iterator();
        
        while (itr.hasNext()) {
            Shark shark = itr.next();
            shark.act(ocean);

            if (!shark.isAlive()) {
                ocean.setAgent(null, shark.getLocation());
                itr.remove();
            }
        }
    }

}
