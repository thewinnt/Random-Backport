package net.thewinnt.randombackport.forge;

import net.thewinnt.randombackport.RandomBackport;
import net.minecraftforge.fml.common.Mod;

@Mod(RandomBackport.MOD_ID)
public class RandomBackportForge {
    public RandomBackportForge() {
        RandomBackport.init();
    }
}