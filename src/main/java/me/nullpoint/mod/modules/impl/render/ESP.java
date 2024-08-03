package me.nullpoint.mod.modules.impl.render;

import me.nullpoint.api.utils.math.MathUtil;
import me.nullpoint.api.utils.render.Render3DUtil;
import me.nullpoint.api.utils.world.BlockUtil;
import me.nullpoint.asm.accessors.IEntity;
import me.nullpoint.mod.modules.Module;
import me.nullpoint.mod.modules.settings.impl.ColorSetting;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.ArrayList;

public class ESP extends Module {
	private final ColorSetting item = add(new ColorSetting("Item", new Color(255, 255, 255, 100)).injectBoolean(true));
	private final ColorSetting player = add(new ColorSetting("Player", new Color(255, 255, 255, 100)).injectBoolean(true));
	private final ColorSetting chest = add(new ColorSetting("Chest", new Color(255, 255, 255, 100)).injectBoolean(false));
	public ESP() {
		super("ESP", Category.Render);
	}

    @Override
	public void onRender3D(MatrixStack matrixStack, float partialTicks) {
		if (item.booleanValue || player.booleanValue) {
			for (Entity entity : mc.world.getEntities()) {
				if (entity instanceof ItemEntity && item.booleanValue) {
					Color color = this.item.getValue();
					Render3DUtil.draw3DBox(matrixStack, ((IEntity) entity).getDimensions().getBoxAt(new Vec3d(MathUtil.interpolate(entity.lastRenderX, entity.getX(), partialTicks), MathUtil.interpolate(entity.lastRenderY, entity.getY(), partialTicks), MathUtil.interpolate(entity.lastRenderZ, entity.getZ(), partialTicks))), color, false, true);
				} else if (entity instanceof PlayerEntity && player.booleanValue) {
					Color color = this.player.getValue();
					Render3DUtil.draw3DBox(matrixStack, ((IEntity) entity).getDimensions().getBoxAt(new Vec3d(MathUtil.interpolate(entity.lastRenderX, entity.getX(), partialTicks), MathUtil.interpolate(entity.lastRenderY, entity.getY(), partialTicks), MathUtil.interpolate(entity.lastRenderZ, entity.getZ(), partialTicks))).expand(0, 0.1, 0), color, false, true);
				}
			}
		}
		if (chest.booleanValue) {
			ArrayList<BlockEntity> blockEntities = BlockUtil.getTileEntities();
			for(BlockEntity blockEntity : blockEntities) {
				if(blockEntity instanceof ChestBlockEntity) {
					Box box = new Box(blockEntity.getPos());
					Render3DUtil.draw3DBox(matrixStack, box, chest.getValue());
				}
			}
		}
	}
}
