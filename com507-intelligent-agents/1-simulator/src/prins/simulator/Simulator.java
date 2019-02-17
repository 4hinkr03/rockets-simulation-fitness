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
package prins.simulator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author Prins Butt
 */
public abstract class Simulator implements PropertyChangeListener {
    
    protected boolean isRunning;
    protected int speed;
    protected int step;

    public Simulator() {
        step = 0;
        speed = Constants.INIT_SIM_SPEED;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        switch (event.getPropertyName()) {

            case "reset":
                isRunning = false;
                step = 0;
                render();
                break;

            case "speed":
                speed = (int) event.getNewValue();

            case "start":
                isRunning = true;
                break;

            case "step":
                step++;
                update();
                render();
                break;

            case "stop":
                isRunning = false;
                break;
        }
    }

    public void run() {
        while (true) {

            try {
                Thread.sleep((Constants.MAX_SIM_SPEED / speed) * 100);

                if (isRunning) {
                    step++;

                    update();
                    render();
                }
            } catch (InterruptedException e) {
            }
        }
    }

    protected abstract void render();

    protected abstract void update();
}
