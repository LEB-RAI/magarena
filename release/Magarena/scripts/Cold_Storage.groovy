[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Pump),
        "Return"
    ) {

        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicSacrificeEvent(source)
            ];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                this,
                "Return each creature card exiled with SN to the battlefield under your control."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.doAction(new ReturnLinkedExileAction(
                event.getPermanent(),
                MagicLocationType.Play,
                event.getPlayer()
            ));
        }
    }
]
