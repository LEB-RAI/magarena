[
   new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                NEG_TARGET_SPELL,
                this,
                "Counter target spell\$ unless its controller pays {2} "+
                "plus an additional {2} for each card named Rune Snag in each graveyard."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final int amount = (game.filterCards(
                cardName("Rune Snag")
                .from(MagicTargetType.Graveyard)
                .from(MagicTargetType.OpponentsGraveyard)
            ).size()*2)+2;
            event.processTargetCardOnStack(game, {
                game.logAppendMessage(event.getPlayer()," (X="+amount+")");
                game.addEvent(new MagicCounterUnlessEvent(
                    event.getSource(),
                    it,
                    MagicManaCost.create("{"+amount+"}")
                ));
            });
        }
    }
]
