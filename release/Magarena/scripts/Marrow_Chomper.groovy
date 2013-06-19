[
    new MagicWhenComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent, final MagicPayedCost payedCost) {
            final MagicTargetFilter<MagicPermanent> targetFilter=new MagicOtherPermanentTargetFilter(
                    MagicTargetFilter.TARGET_CREATURE_YOU_CONTROL,permanent);
            final MagicTargetChoice targetChoice=new MagicTargetChoice(
                    targetFilter,false,MagicTargetHint.None,"a creature other than "+permanent+" to sacrifice");
            return permanent.getController().getNrOfPermanentsWithType(MagicType.Creature) > 1 ?
                new MagicEvent(
                    permanent,
                    new MagicMayChoice(targetChoice),
                    MagicSacrificeTargetPicker.create(),
                    this,
                    "PN may\$ sacrifice a creature\$ to SN."
                ):
                MagicEvent.NONE;
        }

        @Override
        public boolean usesStack() {
            return false;
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            if (event.isYes()) {
                final MagicPermanent permanent=event.getPermanent();
                event.processTargetPermanent(game,new MagicPermanentAction() {
                    public void doAction(final MagicPermanent creature) {
                        game.doAction(new MagicSacrificeAction(creature));
                        game.doAction(new MagicChangeCountersAction(permanent,MagicCounterType.PlusOne,2,true));
                        game.doAction(new MagicChangeLifeAction(event.getPlayer(),2));
                        final MagicEvent newEvent=executeTrigger(game,permanent,permanent.getController());
                        if (newEvent.isValid()) {
                            game.addEvent(newEvent);
                        }
                    }
                });
            } 
        }
    }
]
