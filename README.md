# Smooth SimplySwords SpellPower Compat

Small Fabric 1.20.1 compatibility bridge for:

- Simply Swords `1.70.2-1.20.1`
- Spell Power Attributes `1.6.x+1.20.1`

## Why this exists

Simply Swords 1.70.2 still reads the old `SpellSchool.attribute` field. Spell Power 1.6.x removed that field and exposes the backing attribute through `SpellSchool#getAttributeEntry()`.

Without this patch, opening inventory/tooltips for affected Simply Swords items can crash with:

```text
java.lang.NoSuchFieldError: net.spell_power.api.SpellSchool.attribute
```

This mod intercepts only the two old Simply Swords helper methods that use that removed field and returns the same data through the Spell Power 1.6.x API.

It does **not** alter weapon balance, spell-school assignments, configs, or damage formulas.

## Build

```powershell
.\gradlew clean build
```

Expected jar:

```text
build/libs/smooth_simplyswords_spellpower_compat-1.0.0.jar
```

## Runtime requirements

- Minecraft 1.20.1
- Fabric Loader
- Simply Swords 1.70.2-1.20.1
- Spell Power 1.6.x

This is intentionally version-scoped. Remove the addon when Simply Swords gains native support for the Spell Power 1.6.x API.
