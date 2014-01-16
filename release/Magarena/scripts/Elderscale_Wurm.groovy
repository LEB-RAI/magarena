[
    new MagicWhenComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPayedCost payedCost) {      
            return (permanent.getController().getLife() < 7) ?
                new MagicEvent(
                    permanent,
                    this,
                    "If your life total is less than 7, your life total becomes 7."
                ):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicPlayer player = event.getPlayer();
            final int life = player.getLife();
            if (life < 7) {
                game.doAction(new MagicChangeLifeAction(player, 7 - life)) 
            }
        }
    },
    new MagicWhenDamageIsDealtTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicDamage damage) {
            final MagicPlayer player = permanent.getController();
            final MagicTarget target = damage.getTarget();
            if (player == target && player.getLife() < 7) {
                player.setLife(7);
            }
            return MagicEvent.NONE;
        }
    }
]
