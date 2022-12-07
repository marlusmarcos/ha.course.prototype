package com.labcomu.ha.prototype.model;

import java.util.Objects;

public class BookScore {
	
	
	public final static BookScore EMPTY = new BookScore("BOOKSCORE_EMPTY",Double.NaN);
	public final static BookScore INFO_NOT_AVAILABLE = new BookScore("BOOKSCORE_INFO_NOT_AVAILABLE",Double.NaN);
	
	private String description;
	private double score;
	
	public BookScore() {
		super();
	}

	public BookScore(int id, double score) {
		super();
		this.score = score;
		this.description = "Evaluation of book "+id;
	}

	public BookScore(String description, double score) {
		this.description = description;
		this.score = score;
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



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookScore other = (BookScore) obj;
		return Objects.equals(description, other.description);
	}


}
