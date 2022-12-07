package com.labcomu.ha.prototype.experiment;

import java.util.SplittableRandom;

public class RandomError implements Error {

	private static final SplittableRandom random = new SplittableRandom();

	private static boolean inError = false;
	private static double eventStartTime = 0;

	private static double ERROR_DURATION_LIMIT_MS = 5000;
	private static double AVAILABILITY_LIMIT_MS = 1000;

	private static double ERROR_PROBABILITY = 2;

	@Override
	public void execute() {
		tryError();

	}

	public synchronized static void tryError() {

		updateState();

		if (inError) {

			throw new RuntimeException("Injected Error");

		}
	}

	private static void updateState() {

		double now = System.currentTimeMillis();
		double eventDuration = now - eventStartTime;

		if (inError && eventDuration > ERROR_DURATION_LIMIT_MS) {
			inError = false;
			eventStartTime = now;
		} else if (!inError && eventDuration > AVAILABILITY_LIMIT_MS) {

			if (testErrorProbability()) {
				inError = true;
				eventDuration = now;
			}

		}

	}

	private static boolean testErrorProbability() {
		return random.nextDouble(0, 100) <= ERROR_PROBABILITY;
	}

}
