package simulator;

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
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Prins
 */
public class SimulatorGui extends JFrame {

    public SimulatorGui() {
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
        startButton.addActionListener((ActionEvent e) -> { start(); });
        controlsPanel.add(startButton);

        stepButton = new JButton("Step");
        stepButton.addActionListener((ActionEvent e) -> { step(); });
        controlsPanel.add(stepButton);

        stopButton = new JButton("Stop");
        stopButton.addActionListener((ActionEvent e) -> { stop(); });
        controlsPanel.add(stopButton);

        resetButton = new JButton("Reset");
        resetButton.addActionListener((ActionEvent e) -> { reset(); });
        controlsPanel.add(resetButton);
        
        controlsPanel.add(new JSeparator(SwingConstants.VERTICAL));

        controlsPanel.add(new JLabel("Slow"));

        speedSlider = new JSlider(JSlider.HORIZONTAL,
                SimulatorConstants.MIN_SIM_SPEED,
                SimulatorConstants.MAX_SIM_SPEED,
                SimulatorConstants.INIT_SIM_SPEED);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.addChangeListener((ChangeEvent e) -> { slide(); });
        controlsPanel.add(speedSlider);

        controlsPanel.add(new JLabel("Fast"));

        this.add(controlsPanel, BorderLayout.SOUTH);
    }

    private void initGui() {
        setTitle(SimulatorConstants.SIM_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }
    
    private void initInfo() {
        JPanel infoPanel = new JPanel();
        infoPanel.add(new JLabel("Step"));
        
        JTextField speedTextField = new JTextField();
        speedTextField.setEnabled(false);
        speedTextField.setText("" + speed);
        infoPanel.add(speedTextField);
        
        add(infoPanel, BorderLayout.NORTH);
    }
    
    private void initWorld() {
        DefaultTableModel model = new DefaultTableModel(SimulatorConstants.WORLD_WIDTH, SimulatorConstants.WORLD_HEIGHT);
        world = new JTable(model);
        world.setEnabled(false);

        for (int col = 0; col < SimulatorConstants.WORLD_WIDTH; col++) {
            TableColumn column = world.getColumnModel().getColumn(col);
            column.setMinWidth(SimulatorConstants.WORLD_CELL_SIZE);
            column.setMaxWidth(SimulatorConstants.WORLD_CELL_SIZE);
        }

        JPanel worldPanel = new JPanel();
        worldPanel.add(world);
        this.add(worldPanel, BorderLayout.CENTER);
    }
    
    private void reset() {
        speed = SimulatorConstants.MIN_SIM_SPEED;
        speedSlider.setValue(speed);
    }
    
    private void slide() {
        
    }
    
    private void start() {
        
    }
    
    private void step() {
        
    }
    
    private void stop() {
        
    }

    private JButton startButton;
    private JButton stepButton;
    private JButton stopButton;
    private JButton resetButton;
    private JSlider speedSlider;
    private JTable world;
    
    private int speed;
}
