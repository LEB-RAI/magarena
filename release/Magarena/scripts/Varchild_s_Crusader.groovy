def Unblockable = MagicAbility.getAbilityList("SN can't be blocked except by Walls");

[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Pump),
        "Unblockable"
    ) {
        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicPayManaCostEvent(source,"{0}")
            ];
        }
        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source, final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                this,
                "SN can't be blocked this turn except by Walls. Sacrifice SN at the beginning of the next end step."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.doAction(new GainAbilityAction(event.getPermanent(),Unblockable));
            game.doAction(new AddTriggerAction(event.getPermanent(), MagicAtEndOfTurnTrigger.Sacrifice));
        }
    }
]
