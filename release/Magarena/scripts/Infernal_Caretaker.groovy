[
    new MagicWhenSelfTurnedFaceUpTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent faceUp) {
            return new MagicEvent(
                permanent,
                this,
                "Return all Zombie cards from all graveyards to their owners' hands."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final List<MagicCard> graveyard=
                game.filterCards(event.getPlayer(),ZOMBIE_CARD_FROM_ALL_GRAVEYARDS);
            for (final MagicCard card : graveyard) {
                game.doAction(new RemoveCardAction(card,MagicLocationType.Graveyard));
                game.doAction(new MoveCardAction(card,MagicLocationType.Graveyard,MagicLocationType.OwnersHand));
            }
        }
    }
]
