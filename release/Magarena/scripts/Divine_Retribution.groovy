[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.NEG_TARGET_ATTACKING_CREATURE,
                this,
                "SN deals damage to target attacking creature\$ equal to the" +
                "number of attacking creatures."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final int amount = event.getPlayer().getNrOfPermanents(MagicTargetFilterFactory.ATTACKING_CREATURE);
            event.processTargetPermanent(game, {
                final MagicPermanent creature ->
                final MagicDamage damage = new MagicDamage(event.getSource(),creature,amount);
                game.doAction(new MagicDealDamageAction(damage));
            });
        }
    }
]