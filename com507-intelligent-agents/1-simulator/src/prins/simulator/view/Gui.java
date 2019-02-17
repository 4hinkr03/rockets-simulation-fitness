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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import prins.simulator.Constants;
import prins.simulator.model.Agent;
import prins.simulator.model.Environment;

/**
 *
 * @author Prins Butt
 */
public class Gui extends JFrame {

    private final Map<Class, Color> agentTypesMap;
    private PropertyChangeSupport changes;
    private JPanel legendPanel;
    private final Environment model;
    private JButton resetButton;
    private JSlider speedSlider;
    private JButton startButton;
    private JButton stepButton;
    private JTextField stepTextField;
    private JButton stopButton;
    private JTable world;

    public Gui(Environment model) {
        this.model = model;
        agentTypesMap = new HashMap<>();

        changes = new PropertyChangeSupport(this);

        initGui();
        initComponents();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    public void registerAgentColors(Class agent, Color color) {
        if (!agentTypesMap.containsKey(agent)) {
            agentTypesMap.put(agent, color);

            JLabel colorLabel = new JLabel();
            colorLabel.setBackground(color);
            colorLabel.setOpaque(true);
            colorLabel.setPreferredSize(new Dimension(15, 15));
            legendPanel.add(colorLabel);
            legendPanel.add(new JLabel(agent.getSimpleName()));
        }
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }

    public void render() {
        if (!isVisible()) {
            pack();
            setVisible(true);
        }
        
        world.repaint();
    }
    
    public void setStep(int step) {
        stepTextField.setText("" + step);
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
        speedSlider.addChangeListener((ChangeEvent e) -> {
            slide();
        });

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
        legendPanel = new JPanel();
        add(legendPanel, BorderLayout.NORTH);
    }

    private void initWorld() {
        DefaultTableModel tableModel = new DefaultTableModel(
                (model.getWidth() > 0)
                ? model.getWidth() : Constants.WORLD_WIDTH,
                (model.getHeight() > 0)
                ? model.getHeight() : Constants.WORLD_WIDTH
        );

        world = new JTable(tableModel);
        world.setEnabled(false);

        for (int col = 0; col < model.getWidth(); col++) {
            TableColumn column = world.getColumnModel().getColumn(col);
            column.setMinWidth(Constants.WORLD_CELL_SIZE);
            column.setMaxWidth(Constants.WORLD_CELL_SIZE);
            world.setDefaultRenderer(world.getColumnClass(col), new CellRenderer());
        }

        JPanel worldPanel = new JPanel();
        worldPanel.add(world);
        add(worldPanel, BorderLayout.CENTER);
    }

    private void reset() {
        resetButton.setEnabled(false);

        changes.firePropertyChange("reset", false, true);
    }

    private void slide() {
        changes.firePropertyChange("speed", 0, speedSlider.getValue());
    }

    private void start() {
        stopButton.setEnabled(true);
        startButton.setEnabled(false);
        stepButton.setEnabled(false);
        resetButton.setEnabled(false);

        changes.firePropertyChange("start", false, true);
    }

    private void step() {
        resetButton.setEnabled(true);

        changes.firePropertyChange("step", false, true);
    }

    private void stop() {
        stopButton.setEnabled(false);
        startButton.setEnabled(true);
        stepButton.setEnabled(true);

        if (Integer.parseInt(stepTextField.getText()) > 0) {
            resetButton.setEnabled(true);
        }

        changes.firePropertyChange("stop", true, false);
    }

    private class CellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {

            Component component = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            Agent agent = model.getAgent(row, column);

            if (agent != null && agentTypesMap.containsKey(agent.getClass())) {
                component.setBackground(agentTypesMap.get(agent.getClass()));
            } else {
                component.setBackground(null);
            }

            return component;
        }
    }
}
