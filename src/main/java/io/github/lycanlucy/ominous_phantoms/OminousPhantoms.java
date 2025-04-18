package io.github.lycanlucy.ominous_phantoms;

import com.mojang.logging.LogUtils;
import io.github.lycanlucy.ominous_phantoms.effect.OminousPhantomsEffects;
import io.github.lycanlucy.ominous_phantoms.loot.OminousPhantomsLootModifiers;
import io.github.lycanlucy.ominous_phantoms.particle.OminousPhantomsParticleTypes;
import io.github.lycanlucy.ominous_phantoms.recipe.OminousPhantomsRecipeSerializers;
import io.github.lycanlucy.ominous_phantoms.sound.OminousPhantomsSoundEvents;
import net.minecraft.client.particle.SpellParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

@Mod(OminousPhantoms.MOD_ID)
public class OminousPhantoms {
    public static final String MOD_ID = "ominous_phantoms";
    private static final Logger LOGGER = LogUtils.getLogger();

    public OminousPhantoms(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        OminousPhantomsParticleTypes.register(modEventBus);
        OminousPhantomsSoundEvents.register(modEventBus);
        OminousPhantomsEffects.register(modEventBus);
        OminousPhantomsLootModifiers.register(modEventBus);
        OminousPhantomsRecipeSerializers.register(modEventBus);
        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, OminousPhantomsConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @Mod(value = OminousPhantoms.MOD_ID, dist = Dist.CLIENT)
    public static class OminousPhantomsClient {
        public OminousPhantomsClient(ModContainer container) {
            container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }

        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(OminousPhantomsParticleTypes.NIGHT_OMEN.get(), SpellParticle.Provider::new);
        }
    }
}
