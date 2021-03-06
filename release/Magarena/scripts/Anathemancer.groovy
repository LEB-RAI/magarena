[
    new MagicWhenComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPayedCost payedCost) {      
            return new MagicEvent(
                permanent,
                NEG_TARGET_PLAYER,
                this,
                "When SN enters the battlefield, it deals damage to target player\$ equal to "+
                "the number of nonbasic lands that player controls."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPlayer(game, {
                final int amount = it.getNrOfPermanents(NONBASIC_LAND_YOU_CONTROL)
                game.doAction(new DealDamageAction(event.getSource(),it,amount));
                game.logAppendMessage(event.getPlayer(),"("+amount+")");
            });
        }
    }
]
