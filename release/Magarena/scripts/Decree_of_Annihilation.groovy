[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                this,
                "Exile all artifacts, creatures, and lands from the battlefield, all cards from all graveyards, and all cards from all hands."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final Collection<MagicPermanent> artifacts = game.filterPermanents(ARTIFACT);
            for (final MagicPermanent artifact : artifacts) {
                game.doAction(new RemoveFromPlayAction(artifact,MagicLocationType.Exile));
            }
            final Collection<MagicPermanent> creatures = game.filterPermanents(CREATURE);
            for (final MagicPermanent creature : creatures) {
                game.doAction(new RemoveFromPlayAction(creature,MagicLocationType.Exile));
            }
            final Collection<MagicPermanent> lands = game.filterPermanents(LAND);
            for (final MagicPermanent land : lands) {
                game.doAction(new RemoveFromPlayAction(land,MagicLocationType.Exile));
            }
            for (final MagicPlayer player : game.getAPNAP()) {
                final MagicCardList graveyard = new MagicCardList(player.getGraveyard());
                for (final MagicCard cardGraveyard : graveyard) {
                    game.doAction(new RemoveCardAction(cardGraveyard,MagicLocationType.Graveyard));
                    game.doAction(new MoveCardAction(cardGraveyard,MagicLocationType.Graveyard,MagicLocationType.Exile));
                }
            }
            for (final MagicPlayer player : game.getAPNAP()) {
                final MagicCardList hand = new MagicCardList(player.getHand());
                for (final MagicCard cardHand : hand) {
                    game.doAction(new RemoveCardAction(cardHand,MagicLocationType.OwnersHand));
                    game.doAction(new MoveCardAction(cardHand,MagicLocationType.OwnersHand,MagicLocationType.Exile));
                }
            }
        }
    }
]
