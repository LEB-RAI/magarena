[
    new MagicAtYourUpkeepTrigger() {
        public boolean ifCondition(final MagicPermanent permanent, final MagicPlayer you) {
            final MagicTargetFilter<MagicPermanent> other = new MagicOtherPermanentTargetFilter(MagicTargetFilterFactory.PERMANENT_YOU_CONTROL, permanent);
            return you.controlsPermanent(other) == false && you.getHandSize() == 0;
        }
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPlayer upkeepPlayer) {
            return ifCondition(permanent, upkeepPlayer) ?
                new MagicEvent(
                    permanent,
                    this,
                    "PN wins the game."
                ):
                MagicEvent.NONE;
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            if (ifCondition(event.getPermanent(), event.getPlayer())) {
                game.doAction(new MagicLoseGameAction(event.getPlayer().getOpponent()));
            }
        }
    }
]