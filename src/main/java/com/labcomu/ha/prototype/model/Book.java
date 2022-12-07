package com.labcomu.ha.prototype.model;

public class Book {
	
	private static int ID_COUNTER = 0;
	
	public final static Book NULLABLE_BOOK = new Book();
	

	private int id;
	private String name;
	private String editor;
	private int year;
	private BookScore score;
	

	public Book() {
		this(0,"BOOK_NOT_FOUND","",0);
	}
	public Book(int id, String name, String editor, int year) {
		super();
		this.id = id;
		this.name = name;
		this.editor = editor;
		this.year = year;
	}
	public Book(String name, String editor, int year) {
		this(++ID_COUNTER,name,editor,year);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	public BookScore getScore() {
		return score;
	}
	public void setScore(BookScore score) {
		this.score = score;
	}

	
	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", editor=" + editor + ", year=" + year + ", score=" + score + "]";
	}
}
