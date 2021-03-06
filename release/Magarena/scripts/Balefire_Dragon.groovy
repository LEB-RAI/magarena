[
    new MagicWhenSelfCombatDamagePlayerTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicDamage damage) {
            return new MagicEvent(
                permanent,
                damage.getTargetPlayer(),
                damage.getDealtAmount(),
                this,
                "SN deals RN damage to each creature PN controls."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.filterPermanents(event.getPlayer(),CREATURE_YOU_CONTROL) each {
                game.doAction(new DealDamageAction(event.getSource(),it,event.getRefInt()));
            }
        }
    }
]
