[
    new MagicAtUpkeepTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPlayer upkeepPlayer) {
            return permanent.isOpponent(upkeepPlayer) ?
                new MagicEvent(
                    permanent,
                    this,
                    "Untap all permanents you control."
                ):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicPlayer player = event.getPlayer();
            final Collection<MagicPermanent> targets =
                game.filterPermanents(player,PERMANENT_YOU_CONTROL);
            for (final MagicPermanent target : targets) {
                game.doAction(new UntapAction(target));
            }
        }
    }
]
