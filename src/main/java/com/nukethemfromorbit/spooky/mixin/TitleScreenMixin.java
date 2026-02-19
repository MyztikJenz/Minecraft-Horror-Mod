package com.nukethemfromorbit.spooky.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
	private static final Identifier EVIL_BACKGROUND = Identifier.of("spooky", "images/evil_background.png");

	@Inject(method = "renderPanoramaBackground", at = @At("HEAD"), cancellable = true)
	private void spooky$renderStaticBackground(DrawContext context, float delta, CallbackInfo ci) {
		int width = context.getScaledWindowWidth();
		int height = context.getScaledWindowHeight();
		context.drawTexture(EVIL_BACKGROUND, 0, 0, 0, 0, width, height, width, height);
		ci.cancel();
	}

	@Redirect(
		method = "render",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/screen/SplashTextRenderer;render(Lnet/minecraft/client/gui/DrawContext;ILnet/minecraft/client/font/TextRenderer;I)V"
		)
	)
	private void spooky$replaceSplashText(SplashTextRenderer renderer, DrawContext context, int screenWidth, TextRenderer textRenderer, int alpha) {
		String text = "Kyle is doomed!";
		context.getMatrices().push();
		context.getMatrices().translate(screenWidth / 2.0F + 123.0F, 69.0F, 0.0F);
		context.getMatrices().multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-20.0F));
		float f = 1.8F - MathHelper.abs(MathHelper.sin((float)(Util.getMeasuringTimeMs() % 1000L) / 1000.0F * (float)(Math.PI * 2)) * 0.1F);
		f = f * 100.0F / (textRenderer.getWidth(text) + 32);
		context.getMatrices().scale(f, f, f);
		context.drawCenteredTextWithShadow(textRenderer, text, 0, -8, 0x8B0000 | alpha);
		context.getMatrices().pop();
	}
}
