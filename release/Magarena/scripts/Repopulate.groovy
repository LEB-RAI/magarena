[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                TARGET_PLAYER,
                this,
                "Shuffle all creature cards from target player's\$ graveyard into that player's library."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPlayer(game, {
                final List<MagicCard> graveyard = game.filterCards(it,CREATURE_CARD_FROM_GRAVEYARD);
                for (final MagicCard card : graveyard) {
                    game.doAction(new RemoveCardAction(card,MagicLocationType.Graveyard));
                    game.doAction(new MoveCardAction(card,MagicLocationType.Graveyard,MagicLocationType.OwnersLibrary));
                }
            });
        }
    }
]
