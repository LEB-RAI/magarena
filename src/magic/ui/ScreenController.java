package magic.ui;

import java.util.Stack;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import magic.data.GeneralConfig;
import magic.model.IUIGameController;
import magic.model.MagicCardDefinition;
import magic.model.MagicCardList;
import magic.model.MagicDeck;
import magic.model.MagicDuel;
import magic.model.MagicGame;
import magic.model.player.IPlayerProfileListener;
import magic.model.player.PlayerProfile;
import magic.ui.dialog.AboutDialog;
import magic.ui.dialog.DuelSidebarLayoutDialog;
import magic.ui.dialog.ImportDialog;
import magic.ui.dialog.PreferencesDialog;
import magic.ui.duel.choice.MulliganChoicePanel;
import magic.ui.screen.AbstractScreen;
import magic.ui.screen.AvatarImagesScreen;
import magic.ui.screen.CardExplorerScreen;
import magic.ui.screen.CardScriptScreen;
import magic.ui.screen.CardZoneScreen;
import magic.ui.screen.DeckEditorSplitScreen;
import magic.ui.screen.DeckEditorTabbedScreen;
import magic.ui.screen.DeckViewScreen;
import magic.ui.screen.DecksScreen;
import magic.ui.screen.DuelDecksScreen;
import magic.ui.screen.DuelGameScreen;
import magic.ui.screen.GameLogScreen;
import magic.ui.screen.HelpMenuScreen;
import magic.ui.screen.KeywordsScreen;
import magic.ui.screen.MainMenuScreen;
import magic.ui.screen.MulliganScreen;
import magic.ui.screen.NewDuelSettingsScreen;
import magic.ui.screen.ReadmeScreen;
import magic.ui.screen.SampleHandScreen;
import magic.ui.screen.SelectAiPlayerScreen;
import magic.ui.screen.SelectHumanPlayerScreen;
import magic.ui.screen.SettingsMenuScreen;
import magic.ui.screen.interfaces.IAvatarImageConsumer;
import magic.ui.screen.interfaces.IDeckConsumer;

public final class ScreenController {

    private static MagicFrame mainFrame = null;
    private static final Stack<AbstractScreen> screens = new Stack<>();

    public static MagicFrame getMainFrame() {
        if (mainFrame == null && java.awt.GraphicsEnvironment.isHeadless() == false) {
            mainFrame = new MagicFrame(GeneralConfig.SOFTWARE_TITLE);
        }
        return mainFrame;
    }

    public static void showDuelDecksScreen(final MagicDuel duel) {
        if (screens.peek() instanceof DuelDecksScreen) {
            screens.pop();
        }
        showScreen(new DuelDecksScreen(duel));
    }

    public static void showMainMenuScreen() {
        screens.clear();
        showScreen(new MainMenuScreen());
    }

    public static void showReadMeScreen() {
        showScreen(new ReadmeScreen());
    }
    
    public static void showKeywordsScreen() {
        showScreen(new KeywordsScreen());
    }

    public static void showHelpMenuScreen() {
        showScreen(new HelpMenuScreen());
    }

    public static void showSettingsMenuScreen() {
        showScreen(new SettingsMenuScreen());
    }

    public static void showCardExplorerScreen() {
        showScreen(new CardExplorerScreen());
    }

    public static void showDeckEditor(final MagicDeck deck) {
        if (GeneralConfig.getInstance().isSplitViewDeckEditor()) {
            showScreen(new DeckEditorSplitScreen(deck));
        } else {
            showScreen(new DeckEditorTabbedScreen(deck));
        }
    }
    public static void showDeckEditor() {
        if (GeneralConfig.getInstance().isSplitViewDeckEditor()) {
            showScreen(new DeckEditorSplitScreen());
        } else {
            showScreen(new DeckEditorTabbedScreen());
        }
    }

    public static void showSampleHandScreen(final MagicDeck deck) {
        showScreen(new SampleHandScreen(deck));
    }

    public static void showCardZoneScreen(final MagicCardList cards, final String zoneName, final boolean animateCards) {
        showScreen(new CardZoneScreen(cards, zoneName, animateCards));
    }

    public static void showMulliganScreen(final MulliganChoicePanel choicePanel, final MagicCardList hand) {
        if (screens.peek() instanceof MulliganScreen) {
            final MulliganScreen screen = (MulliganScreen)screens.peek();
            screen.dealNewHand(choicePanel, hand);
        } else {
            showScreen(new MulliganScreen(choicePanel, hand));
        }
    }

    public static void showDeckView(final MagicDeck deck) {
        showScreen(new DeckViewScreen(deck));
    }

    public static void showSelectAiProfileScreen(final IPlayerProfileListener listener, final PlayerProfile profile) {
        showScreen(new SelectAiPlayerScreen(listener, profile));
    }

    public static void showSelectHumanPlayerScreen(final IPlayerProfileListener listener, final PlayerProfile profile) {
        showScreen(new SelectHumanPlayerScreen(listener, profile));
    }

    public static void showAvatarImagesScreen(final IAvatarImageConsumer consumer) {
        showScreen(new AvatarImagesScreen(consumer));
    }

    public static void showDuelPlayersScreen() {
        showScreen(new NewDuelSettingsScreen());
    }

    public static void showGameLogScreen() {
        showScreen(new GameLogScreen());
    }

    public static void showCardScriptScreen(final MagicCardDefinition card) {
        showScreen(new CardScriptScreen(card));
    }

    public static void showDeckChooserScreen(final IDeckConsumer deckConsumer) {
        showScreen(new DecksScreen(deckConsumer));
    }

    public static void showDuelGameScreen(final MagicGame game) {
        showScreen(new DuelGameScreen(game));
    }
    public static void showDuelGameScreen(final MagicDuel duel) {
        showScreen(new DuelGameScreen(duel));
    }

    public static void showAboutDialog() {
        new AboutDialog(getMainFrame());
    }

    public static void showImportDialog() {
        new ImportDialog(getMainFrame());
    }

    public static void showPreferencesDialog() {
        new PreferencesDialog(getMainFrame());
    }

    private static void closeActiveScreen() {
        final AbstractScreen activeScreen = screens.pop();
        final AbstractScreen nextScreen = screens.peek();
        if (activeScreen.isScreenReadyToClose(nextScreen)) {
            showScreen(screens.pop());
            if (nextScreen instanceof DuelGameScreen) {
                ((DuelGameScreen) nextScreen).updateView();
            } else if (nextScreen instanceof MainMenuScreen) {
                ((MainMenuScreen) nextScreen).updateMissingImagesNotification();
            }
        } else {
            screens.push(activeScreen);
        }
    }

    public static void closeActiveScreen(final boolean isEscapeKeyAction) {
        if (getScreensStackSize() == 1) {
            mainFrame.quitToDesktop(isEscapeKeyAction);
        } else {
            closeActiveScreen();
        }
    }    

    private static void showScreen(final AbstractScreen screen) {
        setMainFrameScreen(screen);
        screens.push(screen);
        screen.requestFocus();
    }

    private static void setMainFrameScreen(final AbstractScreen screen) {
        final JComponent contentPane = (JComponent) getMainFrame().getContentPane();
        contentPane.removeAll();
        contentPane.add(screen, "w 100%, h 100%");
        contentPane.revalidate();
        contentPane.repaint();
    }

    public static int getScreensStackSize() {
        return screens.size();
    }

    public static AbstractScreen getActiveScreen() {
        return screens.peek();
    }

    public static void refreshStyle() {
        for (AbstractScreen screen : screens) {
            MagicStyle.refreshComponentStyle(screen);
        }        
    }

    public static void showInfoMessage(final String message) {
        JOptionPane.showMessageDialog(getMainFrame(), message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showWarningMessage(final String message) {
        JOptionPane.showMessageDialog(getMainFrame(), message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public static void showDuelSidebarDialog(final IUIGameController controller) {
        new DuelSidebarLayoutDialog(getMainFrame(), controller);
    }

}
