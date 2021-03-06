[
    new MagicComesIntoPlayWithCounterTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game, final MagicPermanent permanent, final MagicPayedCost payedCost) {
            final int amount = permanent.getController().filterCards(CREATURE_CARD_FROM_GRAVEYARD).size();
            if (amount>0) {
                game.doAction(new ChangeCountersAction(permanent, MagicCounterType.PlusOne, amount));
            } 
            return MagicEvent.NONE;
        }
    },
    new MagicWhenComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game, final MagicPermanent permanent, final MagicPayedCost payedCost) {
            return new MagicEvent(
                permanent,
                this,
                "Exile all creature cards from PN's graveyard."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicPlayer player = event.getPlayer();
            game.filterCards(player, CREATURE_CARD_FROM_GRAVEYARD) each {
                game.doAction(new RemoveCardAction(it, MagicLocationType.Graveyard));
                game.doAction(new MoveCardAction(it, MagicLocationType.Graveyard, MagicLocationType.Exile));
            }
        }
    }
]
