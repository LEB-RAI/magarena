[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                this,
                "Each player shuffles his or her hand, graveyard, and permanents he or she owns into his or her library, then draws seven cards. Each player's life total becomes 7."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            for (final MagicPlayer player : game.getAPNAP()) {
                final Collection<MagicPermanent> permanents = player.filterPermanents(PERMANENT_YOU_OWN);
                final MagicCardList hand = new MagicCardList(player.getHand());
                final MagicCardList graveyard = new MagicCardList(player.getGraveyard());
                for (final MagicPermanent permanent : permanents) {
                    game.doAction(new RemoveFromPlayAction(permanent,MagicLocationType.OwnersLibrary));
                }
                for (final MagicCard cardHand : hand) {
                    game.doAction(new RemoveCardAction(cardHand,MagicLocationType.OwnersHand));
                    game.doAction(new MoveCardAction(cardHand,MagicLocationType.OwnersHand,MagicLocationType.OwnersLibrary));
                }
                for (final MagicCard cardGraveyard : graveyard) {
                    game.doAction(new RemoveCardAction(cardGraveyard,MagicLocationType.Graveyard));
                    game.doAction(new MoveCardAction(cardGraveyard,MagicLocationType.Graveyard,MagicLocationType.OwnersLibrary));
                }
            }
            for (final MagicPlayer player : game.getAPNAP()) {
                game.doAction(new DrawAction(player,7));
            }
            for (final MagicPlayer player : game.getAPNAP()) {
                final int changeLife = 7 - player.getLife();
                game.doAction(new ChangeLifeAction(player,changeLife));
            }
        }
    }
]
