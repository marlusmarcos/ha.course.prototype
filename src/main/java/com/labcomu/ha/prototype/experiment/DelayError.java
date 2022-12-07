package com.labcomu.ha.prototype.experiment;

public class DelayError implements Error {

	
	private static final int DELAY_TIME = 2000;

	@Override
	public void execute() {
		
		try {
			Thread.sleep(DELAY_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
