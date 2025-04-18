package io.github.lycanlucy.ominous_phantoms;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = OminousPhantoms.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class OminousPhantomsConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue PICKY_PHANTOMS = BUILDER
            .comment("Phantoms only attack players with the Night Omen effect.")
            .translation("config.ominous_phantoms.picky_phantoms")
            .define("pickyPhantoms", true);

    private static final ModConfigSpec.BooleanValue BONY_PHANTOMS = BUILDER
            .comment("Phantoms have a chance of dropping bones on top of their standard loot table.")
            .translation("config.ominous_phantoms.bony_phantoms")
            .define("bonyPhantoms", true);

    private static final ModConfigSpec.BooleanValue MENDING_MEMBRANES = BUILDER
            .comment("Phantom Membranes can be used to repair any damaged item in the crafting grid.")
            .translation("config.ominous_phantoms.mending_membranes")
            .define("mendingMembranes", true);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean pickyPhantoms;
    public static boolean bonyPhantoms;
    public static boolean mendingMembranes;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        pickyPhantoms = PICKY_PHANTOMS.get();
        bonyPhantoms = BONY_PHANTOMS.get();
        mendingMembranes = MENDING_MEMBRANES.get();
    }
}
