package com.main.library.model;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "book", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	
	private String author;
	
	private Long pages;
	
	private Long copies;
	
	private Date dateBorrowed;
	
	//ManyToMany mapping setup between user and role 
		@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
		//creating a 3rd table
		@JoinTable(
				name = "borrowed_books",
				joinColumns = @JoinColumn(
						name = "user_id", referencedColumnName = "id"),
				inverseJoinColumns = @JoinColumn(
						name = "book_id", referencedColumnName = "id")
				)
		private Collection<Book> books;
		
		public Book() {
			
		}

		public Book(Long id, String title, String author, Long pages, Long copies, Date dateBorrowed,
				Collection<Book> books) {
			super();
			this.id = id;
			this.title = title;
			this.author = author;
			this.pages = pages;
			this.copies = copies;
			this.dateBorrowed = dateBorrowed;
			this.books = books;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public Long getPages() {
			return pages;
		}

		public void setPages(Long pages) {
			this.pages = pages;
		}

		public Long getCopies() {
			return copies;
		}

		public void setCopies(Long copies) {
			this.copies = copies;
		}

		public Date getDateBorrowed() {
			return dateBorrowed;
		}

		public void setDateBorrowed(Date dateBorrowed) {
			this.dateBorrowed = dateBorrowed;
		}

		public Collection<Book> getBooks() {
			return books;
		}

		public void setBooks(Collection<Book> books) {
			this.books = books;
		}
		
	

}
