package com.labcomu.ha.prototype.experiment;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.SplittableRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExperimentSetup {

	private static final SplittableRandom random = new SplittableRandom();

	private static boolean inError = false;
	private static double currentErrorStartTime = 0;
	private static double experimentStartTime = 0;
	

	private static Error errorInstance;
	private static double defaultErrorDuration;
	private static Queue<Double> errorsOccurrenceTime;

	private static boolean injectionActivated=false;
	
	
	private static Logger logger = LoggerFactory.getLogger(ExperimentSetup.class);

	public synchronized static void tryError() {

		if (injectionActivated) {
			
			logger.info("tryError Executed");
			
			updateState();

			if (inError) {
				logger.info("Error Processed");
				errorInstance.execute();

			}
		}
	}

	private static void updateState() {

		double now = System.currentTimeMillis();
		
		double experimentElapsedTime = now - experimentStartTime;
		
		double errorEndTime = currentErrorStartTime + defaultErrorDuration;
		
		
		logger.info("Checking ERROR state [elapsedTime="+experimentElapsedTime+"]");

		
		if (inError &&  experimentElapsedTime > errorEndTime ) {

			inError = false;
			logger.info("Setting NO ERROR state [nextErrors="+errorsOccurrenceTime+"]");


		} else if (!inError && errorsOccurrenceTime.size() > 0) {

			double nextErrorStartTime = errorsOccurrenceTime.peek();

			if (experimentElapsedTime >= nextErrorStartTime) {
				
				logger.info("Setting ERROR state [time="+nextErrorStartTime+"]");

				inError = true;
				currentErrorStartTime = experimentElapsedTime;
				errorsOccurrenceTime.poll();
			}
			
		}else if(errorsOccurrenceTime.size() == 0) {
			
			inError = false;
			logger.info("Setting NO ERROR state [No more errors]");
			
		}

	}

	public synchronized static void setup(int errorType, int experimentDuration, int occurrences,
			double errorDuration) {
		
		injectionActivated = true;
		experimentStartTime = System.currentTimeMillis();
		

		defaultErrorDuration = errorDuration*1000;

		switch (errorType) {

		case Error.DELAY: {

			errorInstance = new DelayError();
			break;
		}
		case Error.THROWS: {

			errorInstance = new ThrowsError();
			break;

		}
		default:
			errorInstance = new ThrowsError();
			break;


		}

		double[] errorsOccurrences = new double[occurrences];

		for (int i = 0; i < occurrences; i++) {
			errorsOccurrences[i] = random.nextDouble(0, experimentDuration*1000);
		}

		Arrays.sort(errorsOccurrences);

		errorsOccurrenceTime = new LinkedList<Double>();

		for (double errorTimestamp : errorsOccurrences) {
			errorsOccurrenceTime.add(errorTimestamp);
		}
	}

}
