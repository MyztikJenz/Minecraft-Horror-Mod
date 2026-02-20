package com.nukethemfromorbit.spooky;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.render.DimensionEffects;

public class SpookyClient implements ClientModInitializer {
    private static final Identifier MOON_BILLBOARD = Identifier.of("spooky", "textures/special/job_application.png");
    private static final float BILLBOARD_WIDTH = 50.0f;
    private static final float BILLBOARD_HEIGHT = BILLBOARD_WIDTH * 480.0f / 338.0f;
    private static final float BILLBOARD_DISTANCE = 60.0f;
    private static final long MIDNIGHT_TIME = 18000L;
    private static final long MIDNIGHT_WINDOW = 500L;
    private static int nightCycleCount = 0;
    private static boolean inNightWindow = false;

    @Override
    public void onInitializeClient() {
        WorldRenderEvents.AFTER_ENTITIES.register(SpookyClient::renderMoonBillboard);
    }

    private static void renderMoonBillboard(WorldRenderContext context) {
        if (context.world() == null || context.matrixStack() == null || context.consumers() == null) {
            return;
        }

        ClientWorld world = context.world();
        if (world.getDimensionEffects().getSkyType() != DimensionEffects.SkyType.NORMAL) {
            return;
        }

        long timeOfDay = world.getTimeOfDay() % 24000L;
        long distanceFromMidnight = Math.abs(timeOfDay - MIDNIGHT_TIME);
        boolean nightWindow = distanceFromMidnight <= MIDNIGHT_WINDOW;
        if (nightWindow && !inNightWindow) {
            nightCycleCount++;
        }
        inNightWindow = nightWindow;
        if (!nightWindow || nightCycleCount % 3 != 0) {
            return;
        }

        float tickDelta = context.tickCounter().getTickDelta(false);
        double angle = world.getSkyAngle(tickDelta) * Math.PI * 2.0;
        Vec3d direction = new Vec3d(Math.sin(angle), -Math.cos(angle), 0.0).normalize();
        Vec3d cameraPos = context.camera().getPos();
        Vec3d targetPos = cameraPos.add(direction.multiply(BILLBOARD_DISTANCE));

        MatrixStack matrices = context.matrixStack();
        matrices.push();
        matrices.translate(targetPos.x - cameraPos.x, targetPos.y - cameraPos.y, targetPos.z - cameraPos.z);
        matrices.multiply(context.camera().getRotation());
        matrices.scale(-1.0f, -1.0f, 1.0f);

        float halfW = BILLBOARD_WIDTH / 2.0f;
        float halfH = BILLBOARD_HEIGHT / 2.0f;
        VertexConsumer consumer = context.consumers().getBuffer(RenderLayer.getEntityTranslucent(MOON_BILLBOARD));
        int light = LightmapTextureManager.MAX_LIGHT_COORDINATE;

        consumer.vertex(matrices.peek().getPositionMatrix(), -halfW, -halfH, 0.0f)
            .color(255, 255, 255, 255)
            .texture(0.0f, 1.0f)
            .overlay(OverlayTexture.DEFAULT_UV)
            .light(light)
            .normal(0.0f, 0.0f, 1.0f);
        consumer.vertex(matrices.peek().getPositionMatrix(), halfW, -halfH, 0.0f)
            .color(255, 255, 255, 255)
            .texture(1.0f, 1.0f)
            .overlay(OverlayTexture.DEFAULT_UV)
            .light(light)
            .normal(0.0f, 0.0f, 1.0f);
        consumer.vertex(matrices.peek().getPositionMatrix(), halfW, halfH, 0.0f)
            .color(255, 255, 255, 255)
            .texture(1.0f, 0.0f)
            .overlay(OverlayTexture.DEFAULT_UV)
            .light(light)
            .normal(0.0f, 0.0f, 1.0f);
        consumer.vertex(matrices.peek().getPositionMatrix(), -halfW, halfH, 0.0f)
            .color(255, 255, 255, 255)
            .texture(0.0f, 0.0f)
            .overlay(OverlayTexture.DEFAULT_UV)
            .light(light)
            .normal(0.0f, 0.0f, 1.0f);

        matrices.pop();
    }
}
