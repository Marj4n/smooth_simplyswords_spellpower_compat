package org.marj4n.smooth_simplyswords_spellpower_compat.mixin;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import net.sweenus.simplyswords.compat.SpellScalingAssignments;
import net.sweenus.simplyswords.compat.SpellSchoolDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Compatibility bridge for Simply Swords 1.70.2 on Minecraft 1.20.1.
 *
 * <p>Simply Swords 1.70.2 still reads {@code SpellSchool.attribute}, while
 * Spell Power 1.6.x exposes the school's backing attribute through
 * {@code SpellSchool#getAttributeEntry()} instead. The old field access throws
 * {@link NoSuchFieldError} as soon as Simply Swords builds spell-power weapon
 * modifiers/tooltips (for example when the inventory is opened).</p>
 *
 * <p>We intercept only the two Simply Swords helper methods that still touch
 * the removed field and reproduce their original behavior using the new API.
 * No weapon balance, school assignment, config, or damage formula is changed.</p>
 */
@Mixin(targets = "net.sweenus.simplyswords.fabric.FabricHelperMethods", remap = false)
public abstract class FabricHelperMethodsMixin {

    @Inject(method = "spellPowerAttribute", at = @At("HEAD"), cancellable = true, remap = false)
    private static void smoothCompat$spellPowerAttribute(
            Identifier scalingId, CallbackInfoReturnable<EntityAttribute> cir) {
        SpellSchool school = smoothCompat$resolveSchool(scalingId);
        cir.setReturnValue(smoothCompat$attribute(school));
    }

    @Inject(
            method = "activeSpellSchoolDisplay",
            at = @At("HEAD"),
            cancellable = true,
            remap = false)
    private static void smoothCompat$activeSpellSchoolDisplay(
            Identifier scalingId, CallbackInfoReturnable<SpellSchoolDisplay> cir) {
        SpellSchool school = smoothCompat$resolveSchool(scalingId);

        if (school == null) {
            Identifier fallback =
                    SpellScalingAssignments.defaultSpellPowerSchool(scalingId)
                            .orElse(new Identifier("spell_power", "arcane"));
            cir.setReturnValue(new SpellSchoolDisplay(fallback, Text.literal(fallback.toString())));
            return;
        }

        EntityAttribute attribute = smoothCompat$attribute(school);
        Text name =
                attribute == null
                        ? Text.literal(school.id.toString())
                        : Text.translatable(attribute.getTranslationKey());

        cir.setReturnValue(new SpellSchoolDisplay(school.id, name));
    }

    @Unique
    private static EntityAttribute smoothCompat$attribute(SpellSchool school) {
        if (school == null) {
            return null;
        }

        RegistryEntry<EntityAttribute> entry = school.getAttributeEntry();
        return entry == null ? null : entry.value();
    }

    @Unique
    private static SpellSchool smoothCompat$resolveSchool(Identifier scalingId) {
        Identifier configured = SpellScalingAssignments.spellPowerSchool(scalingId).orElse(null);
        SpellSchool school =
                configured == null ? null : SpellSchools.getSchool(configured.toString());

        if (school != null) {
            return school;
        }

        Identifier fallback =
                SpellScalingAssignments.defaultSpellPowerSchool(scalingId).orElse(null);
        return fallback == null ? null : SpellSchools.getSchool(fallback.toString());
    }
}
