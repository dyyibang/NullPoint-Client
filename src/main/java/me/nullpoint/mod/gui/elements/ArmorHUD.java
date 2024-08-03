package me.nullpoint.mod.gui.elements;

import me.nullpoint.Nullpoint;
import me.nullpoint.api.managers.GuiManager;
import me.nullpoint.api.utils.entity.EntityUtil;
import me.nullpoint.api.utils.render.Render2DUtil;
import me.nullpoint.mod.gui.clickgui.tabs.Tab;
import me.nullpoint.mod.modules.impl.client.HUD;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class ArmorHUD extends Tab {

	public ArmorHUD() {
		this.width = 80;
		this.height = 34;
		this.x = (int) Nullpoint.CONFIG.getFloat("armor_x", 0);
		this.y = (int) Nullpoint.CONFIG.getFloat("armor_y", 200);
	}

	@Override
	public void update(double mouseX, double mouseY, boolean mouseClicked) {
		if (GuiManager.currentGrabbed == null && HUD.INSTANCE.armor.getValue()) {
			if (mouseX >= (x) && mouseX <= (x + width)) {
				if (mouseY >= (y) && mouseY <= (y + height)) {
					if (mouseClicked) {
						GuiManager.currentGrabbed = this;
					}
				}
			}
		}
	}

	@Override
	public void draw(DrawContext drawContext, float partialTicks, Color color) {
		if (HUD.INSTANCE.armor.getValue()) {
			if (Nullpoint.GUI.isClickGuiOpen()) {
				Render2DUtil.drawRect(drawContext.getMatrices(), x, y, width, height, new Color(0, 0, 0, 70));
			}
			int xOff = 0;
			for (ItemStack armor : mc.player.getInventory().armor) {
				xOff += 20;

				if (armor.isEmpty()) continue;
				MatrixStack matrixStack = drawContext.getMatrices();
				matrixStack.push();
				int damage = EntityUtil.getDamagePercent(armor);
				int yOffset = height / 2;
				drawContext.drawItem(armor, this.x + width - xOff, this.y + yOffset);
				drawContext.drawItemInSlot(mc.textRenderer, armor, this.x + width - xOff, this.y + yOffset);
				drawContext.drawText(mc.textRenderer,
						String.valueOf(damage),
						x + width + 8 - xOff - mc.textRenderer.getWidth(String.valueOf(damage)) / 2,
						y + yOffset - mc.textRenderer.fontHeight - 2,
						new Color((int) (255f * (1f - ((float) damage / 100f))), (int) (255f * ((float) damage / 100f)), 0).getRGB(),
						true);
				matrixStack.pop();
			}
		}
	}
}
