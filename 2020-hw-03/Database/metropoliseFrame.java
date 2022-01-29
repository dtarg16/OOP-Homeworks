import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class metropoliseFrame extends JFrame {
    //  2 drop-down  for search option;
    private JComboBox<String> populationCombo;
    private JComboBox<String> matchCombo;
    //Text Fields for entering data
    private JTextField Metropolise;
    private JTextField Continent;
    private JTextField Population;

    private metropoliseModel model;

    public metropoliseFrame() {
        super("metropolis viewer");
        setLayout(new BorderLayout(4, 4));
        model = new metropoliseModel();
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        Box topBox = Box.createHorizontalBox();

        Metropolise = new JTextField(16);
        add(Metropolise);
        topBox.add(new Label("Metropolise:"));
        topBox.add(Metropolise);

        Continent = new JTextField(16);
        add(Continent);
        topBox.add(new Label("Continent:"));
        topBox.add(Continent);


        Population = new JTextField(16);
        add(Population);
        topBox.add(new Label("Population:"));
        topBox.add(Population);

        //create side box
        // Side box for 4 options
        Box sideBox = Box.createVerticalBox();
        sideBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        sideBox.setMaximumSize( new Dimension(  10, 10) );
        //add Add button on boc
        //  2 buttons;
        JButton add = new JButton("Add");
        sideBox.add(add);
        add.setAlignmentX(LEFT_ALIGNMENT);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                model.add(Metropolise.getText(),Continent.getText(),Population.getText());
            }
        });

        JButton search = new JButton("Search");
        search.setAlignmentX(LEFT_ALIGNMENT);
        sideBox.add(search);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                model.search(Metropolise.getText(),Continent.getText(),Population.getText(),
                        (String) Objects.requireNonNull(populationCombo.getSelectedItem()), (String) matchCombo.getSelectedItem());
            }
        });

        Box innerSideBox = Box.createVerticalBox();
        innerSideBox.setBorder(new TitledBorder("Search options"));

        String[] populationString = {"Population Larger Than", "Population Fewer Than"};
        populationCombo = new JComboBox<>(populationString);
        populationCombo.setMaximumSize( populationCombo.getPreferredSize());
        populationCombo.setAlignmentX(LEFT_ALIGNMENT);
        innerSideBox.add(populationCombo);

        String[] matchString = {"Exact Match", "Partial Match"};
        matchCombo = new JComboBox<>(matchString);
        matchCombo.setMaximumSize( populationCombo.getPreferredSize() );
        matchCombo.setAlignmentX(LEFT_ALIGNMENT);
        innerSideBox.add(matchCombo);

        sideBox.add(innerSideBox);

        add(scrollPane,BorderLayout.CENTER);
        add(topBox,BorderLayout.NORTH);
        add(sideBox,BorderLayout.EAST);



        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }


    public static void main(String[] args) {
        // GUI Look And Feel
        // Do this incantation at the start of main() to tell Swing
        // to use the GUI LookAndFeel of the native platform. It's ok
        // to ignore the exception.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        metropoliseFrame frame = new metropoliseFrame();
    }
}