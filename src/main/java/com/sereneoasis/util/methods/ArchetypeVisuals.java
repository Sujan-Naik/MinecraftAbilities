package com.sereneoasis.util.methods;

import com.sereneoasis.archetypes.Archetype;
import com.sereneoasis.archetypes.DisplayBlock;
import com.sereneoasis.archetypes.data.ArchetypeDataManager;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class ArchetypeVisuals {

    public interface ArchetypeVisual {
        void playVisual(Location loc, double size, double radius, int tb, int amount, int colour);

        void playShotVisual(Location loc, Vector dir, double angle, double size, double radius, int tb, int amount, int colour);
    }


    public static class FireVisual implements ArchetypeVisual {

        @Override
        public void playVisual(Location loc, double size, double radius, int tb, int amount, int red) {
            TDBs.playTDBs(loc, DisplayBlock.FIRE, tb, size, radius);

            Particles.spawnParticle(Particle.WAX_ON, loc, amount, radius, 0);
            Particles.spawnParticle(Particle.FLAME, loc, amount, radius, 0);
            Particles.spawnColoredParticle(loc, red, radius, size, ArchetypeDataManager.getArchetypeData(Archetype.FIRE).getRealColor());
//            Particles.spawnColoredParticle(loc, red, radius, size*3, Color.fromRGB(220, 20, 60));
        }

        @Override
        public void playShotVisual(Location loc, Vector dir, double angle, double size, double radius, int tb, int amount, int colour) {
            for (Location helixLoc : Locations.getSeveralHelixes(loc, dir, Math.round(Math.round(radius)), 20, Math.round(Math.round(radius)), Math.round(Math.round(angle)), true, 3)) {
//                TDBs.playTDBs(helixLoc, DisplayBlock.FIRE, tb, size, radius);

                Particles.spawnParticle(Particle.WAX_ON, helixLoc, amount, 0.3, 0);
                Particles.spawnParticle(Particle.FLAME, helixLoc, amount, 0.1, 0);
                Particles.spawnColoredParticle(helixLoc, amount, radius, size, ArchetypeDataManager.getArchetypeData(Archetype.FIRE).getRealColor());

            }
        }
    }


}
