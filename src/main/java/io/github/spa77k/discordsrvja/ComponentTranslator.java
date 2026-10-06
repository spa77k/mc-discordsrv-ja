package io.github.spa77k.discordsrvja;

import java.util.List;
import java.util.Map;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

/**
 * Turns a component into plain text, resolving translatable parts with a Minecraft language file.
 */
public final class ComponentTranslator {
    private final Map<String, String> translations;

    public ComponentTranslator(Map<String, String> translations) {
        this.translations = Map.copyOf(translations);
    }

    public String translate(Component component) {
        StringBuilder out = new StringBuilder();
        append(out, component);
        return out.toString();
    }

    private void append(StringBuilder out, Component component) {
        if (component instanceof TextComponent text) {
            out.append(text.content());
        } else if (component instanceof TranslatableComponent translatable) {
            appendTranslatable(out, translatable);
        } else {
            // Keybinds, selectors, scores and so on: let the server's own serializer handle them.
            out.append(PlainTextComponentSerializer.plainText().serialize(component.children(List.of())));
        }
        for (Component child : component.children()) {
            append(out, child);
        }
    }

    private void appendTranslatable(StringBuilder out, TranslatableComponent component) {
        String format = translations.get(component.key());
        if (format == null) {
            format = component.fallback();
        }
        if (format == null) {
            out.append(PlainTextComponentSerializer.plainText().serialize(component.children(List.of())));
            return;
        }
        List<Component> args = component.arguments().stream().map(arg -> arg.asComponent()).toList();
        format(out, format, args);
    }

    /** Applies Minecraft's format syntax: %s, %d, %1$s and %%. */
    private void format(StringBuilder out, String format, List<Component> args) {
        int next = 0;
        int i = 0;
        while (i < format.length()) {
            char c = format.charAt(i);
            if (c != '%' || i + 1 >= format.length()) {
                out.append(c);
                i++;
                continue;
            }
            char d = format.charAt(i + 1);
            if (d == '%') {
                out.append('%');
                i += 2;
            } else if (d == 's' || d == 'd') {
                appendArg(out, args, next++);
                i += 2;
            } else {
                int j = i + 1;
                while (j < format.length() && Character.isDigit(format.charAt(j))) {
                    j++;
                }
                if (j > i + 1 && j + 1 < format.length() && format.charAt(j) == '$'
                        && (format.charAt(j + 1) == 's' || format.charAt(j + 1) == 'd')) {
                    appendArg(out, args, Integer.parseInt(format.substring(i + 1, j)) - 1);
                    i = j + 2;
                } else {
                    out.append(c);
                    i++;
                }
            }
        }
    }

    private void appendArg(StringBuilder out, List<Component> args, int index) {
        if (index >= 0 && index < args.size()) {
            append(out, args.get(index));
        }
    }
}
