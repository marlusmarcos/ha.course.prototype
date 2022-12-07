package com.labcomu.ha.prototype.rest.experiment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.labcomu.ha.prototype.experiment.ExperimentSetup;

@RestController
@RequestMapping("experiment")
@Validated
public class ExperimentSetupController {


	@GetMapping("setup")
	public ResponseEntity<?> setup(@RequestParam int errorType, @RequestParam int experimentDuration,
			@RequestParam int occurrences, @RequestParam double errorDuration) {
		
		
		ExperimentSetup.setup(errorType, experimentDuration, occurrences, errorDuration);

		return new ResponseEntity<String>("Reset OK", HttpStatus.OK);

	}
}
