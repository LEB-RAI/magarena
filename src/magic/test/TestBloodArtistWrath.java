package magic.test;

import magic.model.MagicDuel;
import magic.model.MagicGame;
import magic.model.MagicPlayer;
import magic.model.phase.MagicMainPhase;

class TestBloodArtistWrath extends TestGameBuilder {
    public MagicGame getGame() {
        final MagicDuel duel=createDuel();
        final MagicGame game=duel.nextGame();
        game.setPhase(MagicMainPhase.getFirstInstance());
        final MagicPlayer player=game.getPlayer(0);
        final MagicPlayer opponent=game.getPlayer(1);

        MagicPlayer P = player;

        P.setLife(20);
        addToLibrary(P, "Plains", 10);
        createPermanent(game,P,"Rupture Spire",false,8);
        createPermanent(game,P,"Avacynian Priest",false,1);
        createPermanent(game,P,"Avacynian Priest",false,1);
        createPermanent(game,P,"Blood Artist",false,1);
        createPermanent(game,P,"Grizzly Bears",false,1);
        createPermanent(game,P,"Grizzly Bears",false,1);
        addToHand(P, "Wrath of God", 1);

        P = opponent;

        P.setLife(5);
        addToLibrary(P, "Plains", 10);
        createPermanent(game,P,"Rupture Spire",false,8);

        return game;
    }
}
