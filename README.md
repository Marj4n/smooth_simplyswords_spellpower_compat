# Smooth SimplySwords SpellPower Compat

Fabric 1.20.1 bridge for Simply Swords 1.70.2 and Spell Power 1.6.x. Replaces the two legacy helper calls that read the removed `SpellSchool.attribute` field, fixing affected tooltips without changing weapon balance or damage formulas.

## Build

Use JDK 21 with the included wrapper; output targets Java 17.

```sh
./gradlew build
```

Install the regular JAR from `build/libs/` with Fabric API, Simply Swords and Spell Power. This adapter targets specific versions; review whether it is still needed before upgrading those dependencies.

## Source layout

`src/main/java` contains the adapter; `src/main/resources` contains mod and mixin metadata.

## License

By Marj4n. See [LICENSE.txt](LICENSE.txt).

## Reuse permissions

Reusing or modifying Marj4n-owned source code or assets requires prior written permission. Contact Marj4n through [nivekts.vercel.app](https://nivekts.vercel.app/) with the intended use. See [LICENSE.txt](LICENSE.txt) for gameplay permissions and exceptions. Third-party licenses, existing grants and GitHub platform rights remain applicable.
