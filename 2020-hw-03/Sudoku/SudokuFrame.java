import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;


public class SudokuFrame extends JFrame {
    private JTextArea puzzle;
    private JTextArea solution;
	private JCheckBox autoCheck;
    private String solutionString;
    private int solutionCount;
    private long elapsedTime;

    public SudokuFrame() {
        super("Sudoku Solver");

        setLayout(new BorderLayout(4, 4));
        // YOUR CODE HERE
        puzzle = new JTextArea(15, 20);
        puzzle.setBorder(new TitledBorder("Puzzle"));
        puzzle.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                if (autoCheck.isSelected()) {
                    solveSudoku();
                    draw();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                if (autoCheck.isSelected()) {
                    solveSudoku();
                    draw();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                if (autoCheck.isSelected()) {
                    solveSudoku();
                    draw();
                }
            }
        });

		solution = new JTextArea(15, 20);
		solution.setBorder(new TitledBorder("Solution"));
		solution.setEditable(false);

		Box box = Box.createHorizontalBox();
		JButton check = new JButton("Check");
		autoCheck = new JCheckBox("Auto Check");
		autoCheck.setSelected(true);


		check.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				solveSudoku();
				draw();
			}
		});
		box.add(check);
		box.add(autoCheck);
		add(puzzle, BorderLayout.CENTER);
		add(box, BorderLayout.SOUTH);
		add(solution, BorderLayout.EAST);
		// Could do this:
        setLocationByPlatform(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void draw() {
        solutionString += "\nsolutions:" + solutionCount + "\n";
        solutionString += "elapsed:" + elapsedTime + "ms" + "\n";
        solution.setText(solutionString);
    }

    private void solveSudoku() {
        try {
            Sudoku sudoku = new Sudoku(puzzle.getText());
            solutionCount = sudoku.solve();
            if (solutionCount > 0) {
                solutionString = sudoku.getSolutionText();
            } else {
                solutionString = "";
            }
            elapsedTime = sudoku.getElapsed();
        } catch (Exception ex) {
            solutionString = "Parsing Problem";
        }
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
        SudokuFrame frame = new SudokuFrame();
    }

}
