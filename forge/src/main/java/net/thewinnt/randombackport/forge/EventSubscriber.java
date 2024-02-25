package net.thewinnt.randombackport.forge;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.thewinnt.randombackport.RandomCommand;

@Mod.EventBusSubscriber(bus = Bus.FORGE)
public class EventSubscriber {
    @SubscribeEvent()
    public static void registerCommands(RegisterCommandsEvent event) {
        RandomCommand.register(event.getDispatcher());
    }
}
