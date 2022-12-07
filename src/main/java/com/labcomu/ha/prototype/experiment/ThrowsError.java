package com.labcomu.ha.prototype.experiment;

public class ThrowsError implements Error {

	@Override
	public void execute() {
		throw new RuntimeException("Injected Error");
	}

}
