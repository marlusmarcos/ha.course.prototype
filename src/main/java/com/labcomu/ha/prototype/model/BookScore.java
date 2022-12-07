package com.labcomu.ha.prototype.model;

public class BookScore {
	
	
	public final static BookScore NULLABLE_BOOKSCORE = new BookScore();
	
	private String description;
	private double score;
	

	public BookScore(int id, double score) {
		super();
		this.score = score;
		this.description = "Evaluation of book "+id;
	}


	public BookScore() {
		this.description = "SCORE_NOT_AVAILABLE";
		this.score = Double.NaN;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}


	@Override
	public String toString() {
		return "BookScore [description=" + description + ", score=" + score + "]";
	}


}
