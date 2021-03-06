package magic.test;

import magic.model.MagicDuel;
import magic.model.MagicGame;
import magic.model.MagicPlayer;
import magic.model.phase.MagicMainPhase;

class TestSliver extends TestGameBuilder {
    public MagicGame getGame() {
        final MagicDuel duel=createDuel();
        final MagicGame game=duel.nextGame();
        game.setPhase(MagicMainPhase.getFirstInstance());
        final MagicPlayer player=game.getPlayer(0);
        final MagicPlayer opponent=game.getPlayer(1);

        MagicPlayer P = player;

        P.setLife(1);
        addToLibrary(P, "Island", 10);
        createPermanent(game,P,"Island",false,20);
        createPermanent(game,P,"Sliver Queen",false,1);
        createPermanent(game,P,"Heart Sliver",false,1);
        createPermanent(game,P,"Magma Sliver",false,1);


        P = opponent;

        P.setLife(20);
        addToLibrary(P, "Plains", 10);
        createPermanent(game,P,"Plains",false,10);
        createPermanent(game,P,"Honor Guard",false,4);

        return game;
    }
}
