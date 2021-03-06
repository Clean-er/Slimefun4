package io.github.thebusybiscuit.slimefun4.testing.tests.items.implementations.food;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.implementation.items.food.MonsterJerky;
import io.github.thebusybiscuit.slimefun4.testing.TestUtilities;
import io.github.thebusybiscuit.slimefun4.testing.interfaces.SlimefunItemTest;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public class TestMonsterJerky implements SlimefunItemTest<MonsterJerky> {

    private static ServerMock server;
    private static SlimefunPlugin plugin;

    @BeforeAll
    public static void load() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(SlimefunPlugin.class);
    }

    @AfterAll
    public static void unload() {
        MockBukkit.unmock();
    }

    @Override
    public MonsterJerky registerSlimefunItem(SlimefunPlugin plugin, String id) {
        SlimefunItemStack item = new SlimefunItemStack(id, Material.ROTTEN_FLESH, "&5Test Monster Jerky");
        MonsterJerky jerky = new MonsterJerky(TestUtilities.getCategory(plugin, "monster_jerky"), item, RecipeType.NULL, new ItemStack[9]);
        jerky.register(plugin);
        return jerky;
    }

    @Test
    @Disabled("Potion Effects are currently not fully implemented in MockBukkit")
    public void testConsumptionBehaviour() {
        PlayerMock player = server.addPlayer();
        player.addPotionEffect(PotionEffectType.HUNGER.createEffect(20, 2));
        MonsterJerky jerky = registerSlimefunItem(plugin, "TEST_MONSTER_JERKY");

        simulateConsumption(player, jerky);

        Assertions.assertFalse(player.hasPotionEffect(PotionEffectType.HUNGER));
        Assertions.assertTrue(player.hasPotionEffect(PotionEffectType.SATURATION));
    }

}
