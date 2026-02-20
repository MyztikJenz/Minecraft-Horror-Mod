package com.nukethemfromorbit.spooky.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {
	private static final Identifier EVIL_BACKGROUND = Identifier.of("spooky", "images/evil_background.png");

	@Inject(method = "renderPanoramaBackground", at = @At("HEAD"), cancellable = true)
	private void spooky$renderStaticBackground(DrawContext context, float delta, CallbackInfo ci) {
		int width = context.getScaledWindowWidth();
		int height = context.getScaledWindowHeight();
		context.drawTexture(EVIL_BACKGROUND, 0, 0, 0, 0, width, height, width, height);
		ci.cancel();
	}
}
