[
    new MagicComesIntoPlayWithCounterTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPayedCost payedCost) {
            if (payedCost.isKicked()) {
                game.doAction(new ChangeCountersAction(permanent,MagicCounterType.PlusOne,5));
            }
            return MagicEvent.NONE;
        }
    }
]
