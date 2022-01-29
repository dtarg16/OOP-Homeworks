import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JBrainTetris extends JTetris {

    private DefaultBrain brain;
    private Brain.Move move;
    private int current_count;

    private JLabel okLabel;
    private  JCheckBox brainMode;
    private JSlider adversary;
    private JPanel little;


    JBrainTetris(int pixels) {
        super(pixels);
        brain = new DefaultBrain();
        current_count = 0;
        move=null;
    }

    // Over ride of JTetris control Panel;

    public JComponent createControlPanel() {
        JComponent panel = super.createControlPanel();
        // brain
        panel.add(new JLabel("Brain:"));
        brainMode = new JCheckBox("Brain active");
        panel.add(brainMode);

        little = new JPanel();
        little.add(new JLabel("Adversary:"));
        adversary = new JSlider(0, 100, 0);// min, max, current
        adversary.setPreferredSize(new Dimension(100,15));
        little.add(adversary);

        okLabel = new JLabel("ok");

        little.add(okLabel);

        panel.add(little);

        return panel;
    }

    public void tick(int verb) {
        if(verb == DOWN && brainMode.isSelected()){
            if (count != current_count){
                board.undo();
                move = brain.bestMove(board,currentPiece,board.getHeight(),move);
                current_count = count;
            }
            if(move!=null){
                if(!move.piece.equals(currentPiece)) {
                    tick(ROTATE);
                }
                if(move.x>currentX) {
                    tick(RIGHT);
                }else if(move.x<currentX) {
                    tick(LEFT);
                }
            }
        }
        super.tick(verb);

    }
    public Piece pickNextPiece() {
        int randAdv = random.nextInt(99);
        if (randAdv<adversary.getValue()){
            okLabel.setText("*ok*");
            return worstPiece();
        }else{
            okLabel.setText("ok");
           return  super.pickNextPiece();
        }

    }
    private Piece worstPiece(){
        double score = 0;
        Piece worstPiece = null;
        Brain.Move worstMove = null;
        for(Piece wPiece : pieces){
            worstMove = brain.bestMove(board,wPiece,board.getHeight(),worstMove);
            if(worstMove!=null){
                if(worstMove.score>score){
                    worstPiece = wPiece;
                    score = worstMove.score;
                }
            }
        }
        if(worstPiece == null) return super.pickNextPiece();
        return worstPiece;
    }

    public static void main(String[] args) {
        // Set GUI Look And Feel Boilerplate.
        // Do this incantation at the start of main() to tell Swing
        // to use the GUI LookAndFeel of the native platform. It's ok
        // to ignore the exception.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        JBrainTetris tetris = new JBrainTetris(16);
        JFrame frame = JBrainTetris.createFrame(tetris);
        frame.setVisible(true);

    }
}