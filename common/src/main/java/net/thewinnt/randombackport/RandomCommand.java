package net.thewinnt.randombackport;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.RangeArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;

public class RandomCommand {
    public static final SimpleCommandExceptionType NO_MIN_MAX = new SimpleCommandExceptionType(Component.translatableWithFallback("commands.random.error.no_min_or_max", "Invalid range for /random - both min and max must be present"));
    public static final SimpleCommandExceptionType MAX_LESS_THAN_MIN = new SimpleCommandExceptionType(Component.translatableWithFallback("commands.random.error.max_less_than_min", "Invalid range for /random - max must be larger than min"));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("random")
            .then(Commands.literal("roll")
                .then(Commands.argument("range", RangeArgument.intRange())
                    .executes(context -> {
                        // Grab value
                        Ints range = RangeArgument.Ints.getRange(context, "range");
                        if (range.getMin() == null || range.getMax() == null) {
                            throw NO_MIN_MAX.create();
                        }
                        if (range.getMin() >= range.getMax()) {
                            throw MAX_LESS_THAN_MIN.create();
                        }
                        ServerPlayer executor = context.getSource().getPlayerOrException();
                        int value = RandomBackport.RANDOM.nextInt(range.getMin(), range.getMax() + 1);

                        // Construct message
                        MutableComponent message = Component.translatableWithFallback("commands.random.roll.a_single", "%1$s rolled ", executor.getDisplayName());
                        message.append(Component.literal(String.valueOf(value)).withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD));
                        message.append(Component.translatableWithFallback("commands.random.roll.b_single", " from range "));
                        message.append(createRange(range));

                        // Send message
                        PlayerList players = context.getSource().getServer().getPlayerList();
                        for (ServerPlayer i : players.getPlayers()) {
                            i.displayClientMessage(message, false);
                        }
                        return value;
                    })
                    .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                        .executes(context -> {
                            // Get values
                            Ints range = RangeArgument.Ints.getRange(context, "range");
                            if (range.getMin() == null || range.getMax() == null) {
                                throw NO_MIN_MAX.create();
                            }
                            if (range.getMin() >= range.getMax()) {
                                throw MAX_LESS_THAN_MIN.create();
                            }
                            ServerPlayer executor = context.getSource().getPlayerOrException();
                            int amount = IntegerArgumentType.getInteger(context, "amount");
                            int[] values = new int[amount];
                            for (int i = 0; i < amount; i++) {
                                values[i] = RandomBackport.RANDOM.nextInt(range.getMin(), range.getMax() + 1);
                            }

                            // Construct message
                            MutableComponent message = Component.translatableWithFallback("commands.random.roll.a_multiple", "%1$s rolled ", executor.getDisplayName());
                            message.append(Component.literal(String.valueOf(amount)).withStyle(ChatFormatting.YELLOW));
                            message.append(Component.translatableWithFallback("commands.random.roll.b_multiple", " numbers from range "));
                            message.append(createRange(range));
                            message.append(Component.literal(":"));
                            boolean isGold = false;
                            for (int i : values) {
                                message.append(Component.literal(" " + i).withStyle(ChatFormatting.BOLD, isGold ? ChatFormatting.GOLD : ChatFormatting.YELLOW));
                                isGold = !isGold;
                            }
                            PlayerList players = context.getSource().getServer().getPlayerList();
                            for (ServerPlayer i : players.getPlayers()) {
                                i.displayClientMessage(message, false);
                            }
                            return values[0];
                        })
                    )
                )
            )
        );
    }

    private static Component createRange(Ints range) {
        String value = String.format("[%1$s - %2$s]", range.getMin(), range.getMax());
        return Component.literal(value).withStyle(ChatFormatting.GREEN);
    }
}
