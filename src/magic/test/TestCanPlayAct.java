package magic.test;

import magic.model.MagicDuel;
import magic.model.MagicGame;
import magic.model.MagicPlayer;
import magic.model.phase.MagicMainPhase;

class TestCanPlayAct extends TestGameBuilder {
    public MagicGame getGame() {
        final MagicDuel duel=createDuel();
        final MagicGame game=duel.nextGame();
        game.setPhase(MagicMainPhase.getFirstInstance());
        final MagicPlayer player=game.getPlayer(0);
        final MagicPlayer opponent=game.getPlayer(1);

        MagicPlayer P = player;

        P.setLife(6);
        addToLibrary(P, "Forest", 20);
        addToLibrary(P, "Island", 20);
        addToLibrary(P, "Entreat the Angels", 20);
        addToLibrary(P, "Sliver Overlord", 1);
        addToGraveyard(P, "Ink-Eyes, Servant of Oni", 3);
        createPermanent(game,P, "Mountain", false, 1);
        createPermanent(game,P, "Kher Keep", false, 1);
        addToHand(P, "Lightning Bolt", 1);
        addToHand(P, "Mountain", 1);
        addToHand(P, "Argothian Wurm", 1);
        addToHand(P, "Geralf's Messenger", 1);
        addToHand(P, "Ancestral Recall", 1);

        P = opponent;

        P.setLife(6);
        addToLibrary(P, "Mountain", 20);
        createPermanent(game,P,"Rupture Spire",false,9);
        createPermanent(game,P, "Grizzly Bears", false, 1);
        addToHand(P, "Trained Jackal", 1);
        addToGraveyard(P, "Ink-Eyes, Servant of Oni", 1);

        return game;
    }
}
