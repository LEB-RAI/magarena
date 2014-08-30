[
    new MagicCardActivation(
        [
            MagicConditionFactory.OpponentGainLifeOrMore(1),
            MagicCondition.CARD_CONDITION
        ],
        new MagicActivationHints(MagicTiming.Removal),
        "Alt"
    ) {
        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicCard source) {
            return [new MagicPayManaCostEvent(source, "{B}")];
        }
    }
]
