# Alchemist

A server-side Fabric mod about alchemy, inspired by Equivalent Exchange. It uses
[Polymer](https://modrinth.com/mod/polymer), so vanilla clients can join and see its items
and blocks through the server resource pack.

**Minecraft 26.3 · Fabric · Java 25**

## Content

- **Philosopher's Stone**: right-click a block to cycle it and its neighbours through a group
  (dirt → grass → stone…, log types, leaves, stone types, obsidian, stone bricks). Sneak-use
  to change the area size. Also a crafting catalyst for item transmutation recipes, and
  right-clicking a villager turns them into a new stone.
- **Destruction Catalyst / Catalytic Lens**: break an area of blocks in front of you.
  Sneak-use to change width and depth. Runs on fuel from your inventory, nearest blocks
  first: redstone dust (8 blocks), redstone blocks (72), glowstone dust (48), glowstone (192),
  then your health at 16 blocks per hitpoint, down to a last half heart. Unused blocks carry
  over to the next use.
- **Evertide / Volcanite Gem**: place water / lava without a bucket. The volcanite gem can
  soak lava back up. Both work as buckets in recipes.
- **Swiftwolf's Rending Gale**: ring trinket that grants creative flight.
- **Chalk**: draws a transmutation circle. Using a circle that has a villager on it turns the
  villager into a Philosopher's Stone.
- Fuels (Alchemical Coal, Mobius Fuel, Aeternalis Fuel), Dark Matter and Red Matter.

See `PLAN` for planned content.

## Server requirements

- Fabric Loader and Fabric API
- [Trinkets Updated](https://modrinth.com/mod/trinkets-updated) and its
  [Polymer patch](https://modrinth.com/mod/trinkets-polymer)

Polymer (including resource pack auto-hosting) and PlayerAbilityLib are bundled in the jar.

## Building

```sh
./gradlew build        # jar in build/libs
./gradlew runDatagen   # regenerate recipes, tags and lang in src/main/generated
./gradlew runServer    # dev server in ./run
```

Source art (Aseprite spritesheet, Blockbench model) lives in `art/`.

## License

[OSL-3.0](LICENSE)
