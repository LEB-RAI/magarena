[
    new MagicAtUpkeepTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game, final MagicPermanent permanent, final MagicPlayer upkeepPlayer) {
           return new MagicEvent(
                permanent,
                upkeepPlayer,
                this,
                "Return to its owner's hand each creature PN controls " +
                "with power greater than the number of cards in PN's hand."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final int amount = event.getPlayer().getHandSize();
            CREATURE_YOU_CONTROL.filter(event.getPlayer()) each {
                if (it.getPower() > amount){
                    game.doAction(new RemoveFromPlayAction(it,MagicLocationType.OwnersHand));
                }
            }
        }
    }
]
