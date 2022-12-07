package com.labcomu.ha.prototype.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.labcomu.ha.prototype.experiment.ExperimentSetup;
import com.labcomu.ha.prototype.model.BookScore;

@RestController
@RequestMapping("api/evaluation")
@Validated

public class BookEvaluationController {
	
	private List<BookScore> bookScores;
	
	public BookEvaluationController() {
		
		bookScores = new ArrayList<BookScore>();
		Random randomScoreGenerator = new Random();
		
		for(int i = 1; i<=10000;i++) {
			BookScore bookScore = new BookScore(i,randomScoreGenerator.nextDouble(0,5));
			bookScores.add(bookScore);
		}
	}
	
	@GetMapping("/{id}")
	public BookScore getBookScore(@PathVariable int id) {
		int index = 0;
		BookScore bookScore;
		
		ExperimentSetup.tryError();
		
				
		if(id>0 && id <= bookScores.size()) {
			index = id -1;
			bookScore = bookScores.get(index);
		}
		else {
			
			bookScore = new BookScore();
		}

		
		return bookScore;
		
	}

}
