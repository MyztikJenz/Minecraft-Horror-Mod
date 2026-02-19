package com.nukethemfromorbit.spooky.sound.client;

public final class CaveLoopState {
	private static volatile boolean active;
	private static volatile long resumeAtMillis;

	private CaveLoopState() {
	}

	public static boolean shouldSuppressMusic() {
		long now = System.currentTimeMillis();
		return active || now > resumeAtMillis;
	}

	public static void setActive(boolean isActive) {
		if (active && !isActive) {
			resumeAtMillis = System.currentTimeMillis() + 5000L;
		}
		if (isActive) {
			resumeAtMillis = 0L;
		}
		active = isActive;
	}
}
