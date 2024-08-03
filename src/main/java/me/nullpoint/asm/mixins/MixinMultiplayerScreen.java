package me.nullpoint.asm.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.nullpoint.mod.gui.alts.AltScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;

import net.minecraft.text.Text;

@Mixin(MultiplayerScreen.class)
public class MixinMultiplayerScreen extends Screen {

	protected MixinMultiplayerScreen(Text title) {
		super(title);
	}

	@Inject(at = {@At("TAIL")}, method = {"init()V"})
	private void onInit(CallbackInfo ci) {
		this.addDrawableChild(ButtonWidget.builder(Text.of("Alt Manager"), b -> client.setScreen(new AltScreen((MultiplayerScreen)(Object)this)))
		 		.dimensions(this.width / 2 + 4 + 50, 7, 100, 20).build());
	}

}
