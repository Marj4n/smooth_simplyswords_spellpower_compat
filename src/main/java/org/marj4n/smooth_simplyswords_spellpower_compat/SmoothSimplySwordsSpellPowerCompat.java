package org.marj4n.smooth_simplyswords_spellpower_compat;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SmoothSimplySwordsSpellPowerCompat implements ModInitializer {
    public static final String MOD_ID = "smooth_simplyswords_spellpower_compat";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info(
                "Smooth SimplySwords SpellPower Compat loaded: bridging Simply Swords 1.70.2 to Spell Power 1.6.x.");
    }
}
