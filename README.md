
# 🌌 RandomTP V1.0 🌌

This plugin allows players to be teleported randomly in list of worlds.

Tested on paper 1.17.1 with Lands v5.12.1.


## Soft Dependency
- Lands


## Configuration

You can change different parameters from RandomTP.yml.
```
# You can per default change this between fr and en.
langauge: fr

# The cooldown in seconds between each random teleportation.
# You can set "randomtp.cooldown.bypass" permission to bypass this.
cooldown: 120

# The list of active worlds.
worlds:
  # The name of your world where player can use /rtp.
  our_world_name:
    # This represent the border of your random teleport region.
    border:
      pos1:
        x: 1000
        z: 1000
      pos2:
        x: -1000
        z: -1000
    # This section can be cut if you don't want to add
    # an exclude region in a world.
    exclude:
      pos1:
        x: 200
        z: 200
      pos2:
        x: -200
        z: -200
```


## Permissions
- `randomtp.command.<command>` Add a specific command to a player.
- `randomtp.cooldown.bypass` Allow player to bypass the rtp cooldown.
- `randomtp.countdown.bypass` Allow player to bypass the rtp countdown.


## Authors

- [@LudovicAns](https://github.com/LudovicAns)


[![Logo](https://i.imgur.com/sB82UfM.png)](https://github.com/EdenStacks)

