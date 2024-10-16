package it.unibo.cluedo.view.dice;

import java.util.Random;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Image;
import it.unibo.cluedo.application.Cluedo;
import it.unibo.cluedo.controller.dicecontroller.api.DiceController;

/**
 * This class is used to show the dice roll animation.
 */
public class DiceView extends JPanel {

    private final DiceController controller = Cluedo.CONTROLLER.getDiceController();

    private static final int ANIMATION_DURATION = 2000;
    private static final int ANIMATION_INTERVAL = 300;
    private static final int DICE_SIDES = 6;
    private static final int TEXT_SIZE = 32;
    private static final long serialVersionUID = 1L;

    private final JLabel diceLabel;
    private final JLabel diceImageLabel;
    private final String initialText;
    private final Random random = new Random();
    private final JButton rollButton;

    /**
     * Class constructor.
     */
    public DiceView() {
        this.diceLabel = new JLabel("Click the button to roll the dice ! ", SwingConstants.CENTER);
        this.diceImageLabel = new JLabel();
        this.rollButton = new JButton("Roll Dice");
        this.initialText = diceLabel.getText();

        setLayout(new BorderLayout());
        diceLabel.setFont(new Font("Arial", Font.BOLD, TEXT_SIZE));

        final JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.add(diceLabel, BorderLayout.CENTER);
        resultPanel.add(rollButton, BorderLayout.SOUTH);
        add(resultPanel, BorderLayout.CENTER);

        add(diceImageLabel, BorderLayout.EAST);

        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                rollButton.setEnabled(false);
                startDiceRollAnimation();
            }
        });
    }

    /**
     * This method starts the dice roll animation.
     */
    private void startDiceRollAnimation() {
        final Timer animationTimer = new Timer(ANIMATION_INTERVAL, new ActionListener() {
            private final long startTime = System.currentTimeMillis();
            @Override
            public void actionPerformed(final ActionEvent e) {
                final long elapsed = System.currentTimeMillis() - startTime;
                if (elapsed < ANIMATION_DURATION) {
                    final int rollingResult = random.nextInt(DICE_SIDES) + 1;
                    diceLabel.setText("Rolling dice..." + rollingResult);
                    updateDiceImage(rollingResult);
                } else {
                    ((Timer) e.getSource()).stop();
                    showFinalDiceResult();
                }
            }
        });
        animationTimer.start();
    }

    /**
     * This method updates the dice image basing on the result of the dice roll.
     * @param result the result of the dice roll
     */
    private void updateDiceImage(final int result) {
        final String imagePath = "dice" + result + ".png";
        final ImageIcon diceIcon = new ImageIcon(ClassLoader.getSystemResource(imagePath));
        final int newWidth = 80;
        final int newHeight = 80;
        final Image scaledImage = diceIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        diceImageLabel.setIcon(new ImageIcon(scaledImage));
    }

    /**
     * This method shows the final dice result with a message.
     */
    private void showFinalDiceResult() {
        final int result = controller.getResult();
        diceLabel.setText("Congratulations ! You rolled : " + result);
        updateDiceImage(result);
        if (!Cluedo.CONTROLLER.getUnforeseenController().hasUnforeseenBeenShown()) {
            Cluedo.CONTROLLER.getUnforeseenController().showUnforeseen();
        }
        Cluedo.CONTROLLER.updateInformations();
        Cluedo.CONTROLLER.updateBoard();
        Cluedo.CONTROLLER.updateButtons();
    }

    /**
     * This method updates the dice view.
     */
    public void updateDiceView() {
        diceLabel.setText(this.initialText);
        diceImageLabel.setIcon(null);
    }

    /**
     * This method enables the roll dice button.
     */
    public void enableButton() {
        this.rollButton.setEnabled(true);
    }

    /**
     * This method disables the roll dice button.
     */
    public void disableButton() {
        this.rollButton.setEnabled(false);
    }
}
