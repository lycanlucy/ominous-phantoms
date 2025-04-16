package io.github.lycanlucy.ominous_phantoms;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = OminousPhantoms.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class OminousPhantomsConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    private static final ModConfigSpec.BooleanValue PHANTOMS_DROP_BONES = BUILDER
            .comment("Whether Phantoms should drop have a chance of dropping bones on top of their standard loot table.")
            .translation("config.ominous_phantoms.phantoms_drop_bones")
            .define("phantomsDropBones", true);
    static final ModConfigSpec SPEC = BUILDER.build();
    public static boolean phantomsDropBones;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        phantomsDropBones = PHANTOMS_DROP_BONES.get();
    }
}
