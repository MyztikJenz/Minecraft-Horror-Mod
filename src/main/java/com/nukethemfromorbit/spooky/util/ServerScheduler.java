package com.nukethemfromorbit.spooky.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public final class ServerScheduler {
	private static final List<ScheduledTask> TASKS = new ArrayList<>();
	private static boolean registered;

	private ServerScheduler() {
	}

	public static void register() {
		if (registered) {
			return;
		}
		registered = true;
		ServerTickEvents.END_SERVER_TICK.register(server -> tick());
	}

	public static void schedule(int delayTicks, Runnable action) {
		if (delayTicks <= 0) {
			action.run();
			return;
		}
		TASKS.add(new ScheduledTask(delayTicks, action));
	}

	private static void tick() {
		if (TASKS.isEmpty()) {
			return;
		}
		Iterator<ScheduledTask> iterator = TASKS.iterator();
		while (iterator.hasNext()) {
			ScheduledTask task = iterator.next();
			task.remaining--;
			if (task.remaining <= 0) {
				try {
					task.action.run();
				} finally {
					iterator.remove();
				}
			}
		}
	}

	private static final class ScheduledTask {
		private int remaining;
		private final Runnable action;

		private ScheduledTask(int remaining, Runnable action) {
			this.remaining = remaining;
			this.action = action;
		}
	}
}
