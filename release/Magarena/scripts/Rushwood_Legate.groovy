def FOREST_AND_ISLAND_CONDITION = new MagicCondition() {
    public boolean accept(final MagicSource source) {
        return source.getOpponent().controlsPermanent(MagicSubType.Island) &&
             source.getController().controlsPermanent(MagicSubType.Forest);
    }
};

[
     new MagicCardActivation(
        [FOREST_AND_ISLAND_CONDITION, MagicCondition.CARD_CONDITION],
        new MagicActivationHints(MagicTiming.Main, true),
        "Free"
    ) {

        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicCard source) {
            return [];
        }
    }
]
