package me.nullpoint.api.utils.render;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public enum EnumChatFormatting {
    BLACK("BLACK", '0', 0),
    DARK_BLUE("DARK_BLUE", '1', 1),
    DARK_GREEN("DARK_GREEN", '2', 2),
    DARK_AQUA("DARK_AQUA", '3', 3),
    DARK_RED("DARK_RED", '4', 4),
    DARK_PURPLE("DARK_PURPLE", '5', 5),
    GOLD("GOLD", '6', 6),
    GRAY("GRAY", '7', 7),
    DARK_GRAY("DARK_GRAY", '8', 8),
    BLUE("BLUE", '9', 9),
    GREEN("GREEN", 'a', 10),
    AQUA("AQUA", 'b', 11),
    RED("RED", 'c', 12),
    LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13),
    YELLOW("YELLOW", 'e', 14),
    WHITE("WHITE", 'f', 15),
    OBFUSCATED("OBFUSCATED", 'k', true),
    BOLD("BOLD", 'l', true),
    STRIKETHROUGH("STRIKETHROUGH", 'm', true),
    UNDERLINE("UNDERLINE", 'n', true),
    ITALIC("ITALIC", 'o', true),
    RESET("RESET", 'r', -1);

    private static final Map<String, EnumChatFormatting> nameMapping = Maps.newHashMap();

    /**
     * Matches formatting codes that indicate that the client should treat the following text as bold, recolored,
     * obfuscated, etc.
     */
    private static final Pattern formattingCodePattern = Pattern.compile("(?i)" + '\u00a7' + "[0-9A-FK-OR]");

    /**
     * The name of this color/formatting
     */
    private final String name;

    /**
     * The formatting code that produces this format.
     */
    private final char formattingCode;
    private final boolean fancyStyling;

    /**
     * The control string (section sign + formatting code) that can be inserted into client-side text to display
     * subsequent text in this format.
     */
    private final String controlString;

    /**
     * The numerical index that represents this color
     */
    private final int colorIndex;

    private static String func_175745_c(final String p_175745_0_) {
        return p_175745_0_.toLowerCase().replaceAll("[^a-z]", "");
    }

    EnumChatFormatting(final String formattingName, final char formattingCodeIn, final int colorIndex) {
        this(formattingName, formattingCodeIn, false, colorIndex);
    }

    EnumChatFormatting(final String formattingName, final char formattingCodeIn, final boolean fancyStylingIn) {
        this(formattingName, formattingCodeIn, fancyStylingIn, -1);
    }

    EnumChatFormatting(final String formattingName, final char formattingCodeIn, final boolean fancyStylingIn, final int colorIndex) {
        this.name = formattingName;
        this.formattingCode = formattingCodeIn;
        this.fancyStyling = fancyStylingIn;
        this.colorIndex = colorIndex;
        this.controlString = "\u00a7" + formattingCodeIn;
    }

    /**
     * Returns the numerical color index that represents this formatting
     */
    public int getColorIndex() {
        return this.colorIndex;
    }

    /**
     * False if this is just changing the color or resetting; true otherwise.
     */
    public boolean isFancyStyling() {
        return this.fancyStyling;
    }

    /**
     * Checks if this is a color code.
     */
    public boolean isColor() {
        return !this.fancyStyling && this != RESET;
    }

    /**
     * Gets the friendly name of this value.
     */
    public String getFriendlyName() {
        return this.name().toLowerCase();
    }

    public String toString() {
        return this.controlString;
    }

    /**
     * Returns a copy of the given string, with formatting codes stripped away.
     *
     * @param text The text to strip formatting codes from
     */
    public static String getTextWithoutFormattingCodes(final String text) {
        return text == null ? null : formattingCodePattern.matcher(text).replaceAll("");
    }

    /**
     * Gets a value by its friendly name; null if the given name does not map to a defined value.
     *
     * @param friendlyName The friendly name
     */
    public static EnumChatFormatting getValueByName(final String friendlyName) {
        return friendlyName == null ? null : nameMapping.get(func_175745_c(friendlyName));
    }

    public static EnumChatFormatting func_175744_a(final int p_175744_0_) {
        if (p_175744_0_ < 0) {
            return RESET;
        } else {
            for (final EnumChatFormatting enumchatformatting : values()) {
                if (enumchatformatting.getColorIndex() == p_175744_0_) {
                    return enumchatformatting;
                }
            }

            return null;
        }
    }

    public static Collection<String> getValidValues(final boolean p_96296_0_, final boolean p_96296_1_) {
        final List<String> list = Lists.newArrayList();

        for (final EnumChatFormatting enumchatformatting : values()) {
            if ((!enumchatformatting.isColor() || p_96296_0_) && (!enumchatformatting.isFancyStyling() || p_96296_1_)) {
                list.add(enumchatformatting.getFriendlyName());
            }
        }

        return list;
    }

    static {
        for (final EnumChatFormatting enumchatformatting : values()) {
            nameMapping.put(func_175745_c(enumchatformatting.name), enumchatformatting);
        }
    }
}
