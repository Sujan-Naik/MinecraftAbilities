# Minecraft Ability Plugin (For paper 1.20.4)
This is an ability framework based on a stripped down version of an ability plugin I have been developing for my Minecraft server.

It contains ground-breaking features for Minecraft that really push the limits of what is possible server-side.

In short, players can join a server and select an archetype (my chosen word for their class), and view a list of abilities using commands.
Players can then bind abilities to their hotbar slots which can have extremely complicated functionality - the sky really is the limit.

## Examples of how I have used it (These are still in development)
[Sun Archetype](https://www.youtube.com/watch?v=aD2KAmf4mvc)

[Chaos Archetype](https://www.youtube.com/watch?v=9eR5o-34O9U)

Main feature outline:
- Use of block displays
> These can function similarly to entities (i.e. move outside the block grid)
> They can be different sizes
> Temporary block display system to automatically handle a large amount of spawning
- Temporary block system to allow for revertable changes to blocks
> i.e. raising a wall that goes away after a certain time period
> Can handle a large amount of blocks at any given time
> Can be reverted when desired or whenever
- Helper ability system
> Developers can make extremely complex abilities by stitching together helpers

## How to use it ? 
1) Fork the project
> By default it comes with a "fire" archetype and 2 abilities "FireBall" (CruelSun from the Sun Archetype video renamed) and "FlamingRays"
2) To register an archetype head to com.sereneoasis.archetypes.Archetype and add a new enum
3) To create an ability for the archetype (ideally in com.serenoasis.<archetype folder>), simply extend either MasterAbility or CoreAbility
> MasterAbility is very useful for properly utilising Helper Abilities (although not necessary)
4) Register configuration values in com.sereneoasis.config.ConfigManager 
> Call method saveConfigValuesAbility for all abilities
> Call method saveConfigValuesCombo for all combination abilities
> You must call saveAttributeValuesArchetype (these add Minecraft attributes to archetype users, input 0 to have no effect)
> Call saveArchetypeCosmetics to assign a colour
5) Head to 
- For regular abilities: com.sereneoasis.listeners.AbilitiesListener to register [listeners](https://www.spigotmc.org/wiki/using-the-event-api/)
- For combo abilities: com.sereneoasis.ability.ComboManager and look at method checkForCombo 
> These define what must be done to create an ability or handle functions
6) Package the plugin using maven and place within your plugins folder
> Either restart the server or use plugman to reload (you cannot be online for the latter)
7) Type /abilities followed by either display, choose or bind
> E.g. View Fire archetype abilities with /abilities display Fire
> Choose Fire with /abilities choose Fire
> Bind Fire abilities, for example FireBall, with /abilities bind FireBall (whilst hovering on the intended slot)






