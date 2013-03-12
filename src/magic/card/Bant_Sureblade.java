package magic.card;

import magic.model.MagicAbility;
import magic.model.MagicColor;
import magic.model.MagicPermanent;
import magic.model.MagicPowerToughness;
import magic.model.mstatic.MagicLayer;
import magic.model.mstatic.MagicStatic;

import java.util.Set;

public class Bant_Sureblade {
    public static boolean isValid(final MagicPermanent owner) {
        for (final MagicPermanent permanent : owner.getController().getPermanents()) {
            if (permanent != owner && MagicColor.isMulti(permanent)) {
                return true;
            }
        }
        return false;
    }
    
    public static final MagicStatic S1 = new MagicStatic(MagicLayer.ModPT) {
        @Override
        public void modPowerToughness(final MagicPermanent source,final MagicPermanent permanent,final MagicPowerToughness pt) {
            if (isValid(permanent)) {
                pt.add(1,1);
            }
        }        
    };
    
    public static final MagicStatic S2 = new MagicStatic(MagicLayer.Ability) {
        @Override
        public void modAbilityFlags(final MagicPermanent source,final MagicPermanent permanent,final Set<MagicAbility> flags) {
            if (isValid(permanent)) {
                flags.add(MagicAbility.FirstStrike);
            }
        }
    }; 
}
