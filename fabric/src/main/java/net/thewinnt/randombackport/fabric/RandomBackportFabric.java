package net.thewinnt.randombackport.fabric;

import net.thewinnt.randombackport.RandomBackport;
import net.fabricmc.api.ModInitializer;

public class RandomBackportFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        RandomBackport.init();
    }
}