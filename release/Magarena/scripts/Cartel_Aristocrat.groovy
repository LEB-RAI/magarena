[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Pump),
        "Protection"
    ) {
        @Override
        public MagicEvent[] getCostEvent(final MagicPermanent source) {
            final MagicTargetFilter<MagicPermanent> targetFilter = new MagicOtherPermanentTargetFilter(
                MagicTargetFilter.TARGET_CREATURE_YOU_CONTROL,
                source
            );
            final MagicTargetChoice targetChoice = new MagicTargetChoice(
                targetFilter,
                false,
                MagicTargetHint.None,
                "a creature other than " + source + " to sacrifice"
            );
            return [
                new MagicSacrificePermanentEvent(
                    source,
                    targetChoice
                )
            ];
        }
        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source, final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                MagicColorChoice.ALL_INSTANCE,
                this,
                "SN gains protection from color\$ of your choice until end of turn."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.doAction(new MagicSetAbilityAction(
                event.getPermanent(),
                event.getChosenColor().getProtectionAbility()
            ));
        }
    }
]
