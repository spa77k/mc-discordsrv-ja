package io.github.spa77k.discordsrvja;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.Test;

class ComponentTranslatorTest {
    private final ComponentTranslator translator = new ComponentTranslator(Map.of(
            "death.attack.mob", "%1$sは%2$sに殺害された",
            "death.attack.player.item", "%1$sは%2$sの%3$sで殺害された",
            "entity.minecraft.zombie", "ゾンビ",
            "chat.square_brackets", "[%s]",
            "advancements.adventure.sleep_in_bed.title", "良い夢見てね",
            "test.sequential", "%s と %s で 100%%"));

    @Test
    void translatesNestedArguments() {
        Component message = Component.translatable("death.attack.mob",
                Component.text(".Player90959655"), Component.translatable("entity.minecraft.zombie"));
        assertEquals(".Player90959655はゾンビに殺害された", translator.translate(message));
    }

    @Test
    void translatesPositionalArgumentsAndItemNames() {
        Component message = Component.translatable("death.attack.player.item",
                Component.text("Alex"), Component.text("Steve"),
                Component.translatable("chat.square_brackets", Component.text("Sword")));
        assertEquals("AlexはSteveの[Sword]で殺害された", translator.translate(message));
    }

    @Test
    void translatesAdvancementTitle() {
        assertEquals("良い夢見てね",
                translator.translate(Component.translatable("advancements.adventure.sleep_in_bed.title")));
    }

    @Test
    void handlesSequentialArgumentsAndPercent() {
        Component message = Component.translatable("test.sequential", Component.text("A"), Component.text("B"));
        assertEquals("A と B で 100%", translator.translate(message));
    }

    @Test
    void keepsChildrenAndUsesFallbackForUnknownKeys() {
        Component message = Component.text("[")
                .append(Component.translatable("unknown.key", "Fallback"))
                .append(Component.text("]"));
        assertEquals("[Fallback]", translator.translate(message));
    }
}
