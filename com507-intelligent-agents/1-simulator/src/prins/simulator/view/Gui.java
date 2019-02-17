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
package prins.simulator.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import prins.simulator.model.Model;

/**
 *
 * @author Prins Butt
 */
public class Gui extends JFrame {

    private JButton resetButton;
    private int speed;
    private JSlider speedSlider;
    private JButton startButton;
    private int step;
    private JButton stepButton;
    private JTextField stepTextField;
    private JButton stopButton;
    private JTable world;

    public Gui(Model model) {
        initGui();
        initComponents();
    }

    public void showGui() {
        pack();
        setVisible(true);
    }

    private void initComponents() {
        initInfo();
        initWorld();
        initControls();
    }

    private void initControls() {
        JPanel controlsPanel = new JPanel();

        startButton = new JButton("Start");
        startButton.addActionListener((ActionEvent e) -> {
            start();
        });
        controlsPanel.add(startButton);

        stepButton = new JButton("Step");
        stepButton.addActionListener((ActionEvent e) -> {
            step();
        });
        controlsPanel.add(stepButton);

        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        stopButton.addActionListener((ActionEvent e) -> {
            stop();
        });
        controlsPanel.add(stopButton);

        resetButton = new JButton("Reset");
        resetButton.setEnabled(false);
        resetButton.addActionListener((ActionEvent e) -> {
            reset();
        });
        controlsPanel.add(resetButton);

        controlsPanel.add(new JSeparator(SwingConstants.VERTICAL));

        controlsPanel.add(new JLabel("Step"));

        stepTextField = new JTextField();
        stepTextField.setEnabled(false);
        stepTextField.setText("" + step);
        stepTextField.setColumns(5);
        controlsPanel.add(stepTextField);

        controlsPanel.add(new JSeparator(SwingConstants.VERTICAL));

        controlsPanel.add(new JLabel("Speed"));

        speedSlider = new JSlider(JSlider.HORIZONTAL,
                Constants.MIN_SIM_SPEED,
                Constants.MAX_SIM_SPEED,
                Constants.INIT_SIM_SPEED);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setPaintTicks(true);

        controlsPanel.add(speedSlider);

        add(controlsPanel, BorderLayout.SOUTH);
    }

    private void initGui() {
        setTitle(Constants.SIM_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        setLayout(new BorderLayout());
    }

    private void initInfo() {
        JPanel infoPanel = new JPanel();
        infoPanel.add(new JLabel("Simulator"));
        add(infoPanel, BorderLayout.NORTH);
    }

    private void initWorld() {
        DefaultTableModel model = new DefaultTableModel(
                Constants.WORLD_WIDTH,
                Constants.WORLD_HEIGHT);
        world = new JTable(model);
        world.setEnabled(false);

        for (int col = 0; col < Constants.WORLD_WIDTH; col++) {
            TableColumn column = world.getColumnModel().getColumn(col);
            column.setMinWidth(Constants.WORLD_CELL_SIZE);
            column.setMaxWidth(Constants.WORLD_CELL_SIZE);
        }

        JPanel worldPanel = new JPanel();
        worldPanel.add(world);
        add(worldPanel, BorderLayout.CENTER);
    }

    private void reset() {
        resetButton.setEnabled(false);
        step = 0;
        stepTextField.setText("" + step);
        speed = Constants.MIN_SIM_SPEED;
        speedSlider.setValue(speed);
    }

    private void start() {
        stopButton.setEnabled(true);
        startButton.setEnabled(false);
        stepButton.setEnabled(false);
        resetButton.setEnabled(false);
    }

    private void step() {
        step += speedSlider.getValue();
        stepTextField.setText("" + step);
        resetButton.setEnabled(true);
    }

    private void stop() {
        stopButton.setEnabled(false);
        startButton.setEnabled(true);
        stepButton.setEnabled(true);
        if (step > 0) {
            resetButton.setEnabled(true);
        }
    }
}
