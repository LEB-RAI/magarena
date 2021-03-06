package magic.ui.screen;

import magic.ui.IconImages;
import magic.model.MagicCardList;
import magic.model.MagicGame;
import magic.model.MagicPlayer;
import magic.ui.canvas.cards.CardsCanvas;
import magic.ui.canvas.cards.CardsCanvas.LayoutMode;
import magic.ui.duel.choice.MulliganChoicePanel;
import magic.ui.screen.interfaces.IActionBar;
import magic.ui.screen.interfaces.IStatusBar;
import magic.ui.screen.widget.ActionBarButton;
import magic.ui.screen.widget.MenuButton;
import net.miginfocom.swing.MigLayout;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import magic.data.MagicIcon;
import magic.ui.CardImagesProvider;
import magic.ui.ScreenController;

@SuppressWarnings("serial")
public class MulliganScreen
    extends AbstractScreen
    implements IStatusBar, IActionBar {

    private final static Dimension cardSize = CardImagesProvider.PREFERRED_CARD_SIZE;

    private volatile static boolean isActive = false;

    private CardsCanvas cardsCanvas;
    private MulliganChoicePanel choicePanel;
    private final AbstractAction takeMulliganAction = new TakeMulliganAction();
    private final StatusPanel statusPanel;
    private final MagicCardList hand;

    public MulliganScreen(final MulliganChoicePanel choicePanel, final MagicCardList hand) {
        this.choicePanel = choicePanel;
        this.hand = hand;
        isActive = true;
        statusPanel = new StatusPanel(choicePanel.getGameController().getGame());
        setContent(getScreenContent(hand));
        setEscapeKeyInputMap();
    }

    public static boolean isActive() {
        return isActive;
    }

    /**
     * Override standard behaviour of the Escape key as defined by AbstractScreen.
     * In this case close screen and set flag so that it is not re-opened should the
     * user subsequently take a mulligan from the main game screen.
     */
    private void setEscapeKeyInputMap() {
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "EscapeKeyAction");
        getActionMap().put("EscapeKeyAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!cardsCanvas.isBusy()) {
                    isActive = false;
                    ScreenController.closeActiveScreen(false);
                }
            }
        });
    }

    private JPanel getScreenContent(final MagicCardList hand) {
        cardsCanvas = new CardsCanvas(cardSize);
        cardsCanvas.setAnimationEnabled(true);
        cardsCanvas.setAnimationDelay(50, 20);
        cardsCanvas.setLayoutMode(LayoutMode.SCALE_TO_FIT);
        refreshCardsDisplay(hand);
        return cardsCanvas;
    }

    public void dealNewHand(final MulliganChoicePanel choicePanel, final MagicCardList hand) {
        this.choicePanel = choicePanel;
        refreshCardsDisplay(hand);
    }

    private void refreshCardsDisplay(final MagicCardList hand) {
        // Important: uses Runnable so painting works properly.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                cardsCanvas.refresh(hand, cardSize);
            }
        });
    }

    /* (non-Javadoc)
     * @see magic.ui.IMagStatusBar#getScreenCaption()
     */
    @Override
    public String getScreenCaption() {
        return "Mulligan?";
    }

    /* (non-Javadoc)
     * @see magic.ui.IMagActionBar#getLeftAction()
     */
    @Override
    public MenuButton getLeftAction() {
        return new MenuButton("Close", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (!cardsCanvas.isBusy()) {
                    isActive = false;
                    ScreenController.closeActiveScreen(false);
                }
            }
        });
    }

    /* (non-Javadoc)
     * @see magic.ui.IMagActionBar#getRightAction()
     */
    @Override
    public MenuButton getRightAction() {
        return new MenuButton("Play this hand", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (!cardsCanvas.isBusy()) {
                    choicePanel.doMulliganAction(false);
                    isActive = false;
                    ScreenController.closeActiveScreen(false);
                }
            }
        });
    }

    /* (non-Javadoc)
     * @see magic.ui.IMagActionBar#getMiddleActions()
     */
    @Override
    public List<MenuButton> getMiddleActions() {
        final List<MenuButton> buttons = new ArrayList<>();
        buttons.add(
                new ActionBarButton(
                        IconImages.getIcon(MagicIcon.MULLIGAN_ICON),
                        "Mulligan", "Draw a new hand with one less card.",
                        takeMulliganAction)
                );
        return buttons;
    }

    /* (non-Javadoc)
     * @see magic.ui.MagScreen#canScreenClose()
     */
    @Override
    public boolean isScreenReadyToClose(final AbstractScreen nextScreen) {
        return true;
    }

    private final class TakeMulliganAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!cardsCanvas.isBusy()) {
                choicePanel.doMulliganAction(true);
                if (hand.size() <= 2) {
                    isActive = false;
                    ScreenController.closeActiveScreen(false);
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see magic.ui.screen.interfaces.IStatusBar#getStatusPanel()
     */
    @Override
    public JPanel getStatusPanel() {
        return statusPanel;
    }

    private class StatusPanel extends JPanel {

        // ui
        private final MigLayout migLayout = new MigLayout();
        private final JLabel playingFirstLabel = new JLabel();

        public StatusPanel(final MagicGame game) {
            setLookAndFeel();
            setContent(game);
        }

        private void setLookAndFeel() {
            setOpaque(false);
            setLayout(migLayout);
            // playing first label
            playingFirstLabel.setForeground(Color.WHITE);
            playingFirstLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
            playingFirstLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        private void refreshLayout() {
            removeAll();
            migLayout.setLayoutConstraints("insets 0, gap 2, flowy");
            add(playingFirstLabel, "w 100%");
        }

        private void setContent(final MagicGame game) {
            final MagicPlayer turnPlayer = game.getTurnPlayer();
            final MagicPlayer humanPlayer = game.getPlayer(0);
            playingFirstLabel.setText("You play " + (turnPlayer == humanPlayer ? "first." : "second."));
            refreshLayout();
        }

    }

}
