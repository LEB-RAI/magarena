
package magic.test;

import magic.model.MagicDuel;
import magic.model.MagicGame;
import magic.model.MagicPlayer;
import magic.model.phase.MagicMainPhase;

class TestStatics extends TestGameBuilder {
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
        createPermanent(game,P,"Creeping Tar Pit",false,1);
        createPermanent(game,P,"Raging Ravine",false,1);
        createPermanent(game,P,"Phyrexian Crusader",false,3);
        addToHand(P,"Glorious Anthem",1);
        addToHand(P,"Godhead of Awe",1);
        addToHand(P,"Aven Mimeomancer",1);
        addToHand(P, "Mortivore", 1);
        addToHand(P, "Batterskull", 1);
        addToHand(P, "Rancor", 1);
        addToHand(P, "Angelic Destiny", 1);
        addToHand(P, "Master of Etherium", 1);
        addToHand(P, "Mortivore", 1);
        addToGraveyard(P,"Mortivore", 10);

        P = opponent;

        P.setLife(20);
        addToLibrary(P, "Plains", 10);
        createPermanent(game,P,"Rupture Spire",false,8);
        createPermanent(game,P,"Phyrexian Crusader",false,3);

        return game;
    }
}
