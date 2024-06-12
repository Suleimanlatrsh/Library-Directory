// -----------------------------------------------------
// Assignment (2)
// Question: (1)
// Written by: (Sohaib Daami 40260605)
// -----------------------------------------------------

import java.io.Serializable;


/**
* The Movie class creates movie objects
*
* @author Sohaib Daami
* @version 1.0
* @see String
*/
public class Movie implements Serializable {
	
	//attributes
	int year;
	String title;
	int duration;
	double score;
	String rating;
	String genre;
	String director;
	String actor_1;
	String actor_2;
	String actor_3;
	
	//default constructor
	public Movie() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	* constructor that takes the necessary parameters to create a Movie object
	*
	* @param int year
	* @param String title
	* @param int duration
	* @param String genre
	* @param String rating
	* @param double score
	* @param String director
	* @param String actor_1
	* @param String actor_2
	* @param String actor_3
	*/
	public Movie(int year, String title, int duration, String genre, String rating, double score,
			     String director, String actor_1, String actor_2, String actor_3 ) {
		this.year = year;
		this.title = title;
		this.duration = duration;
		this.genre = genre;
		this.rating = rating;
		this.score = score;
		this.director = director;
		this.actor_1 = actor_1;
		this.actor_2 = actor_2;
		this.actor_3 = actor_3;
	}
	
	
	//getters and setters
	public String getActor_1() {
		return actor_1;
	}
	public String getActor_2() {
		return actor_2;
	}
	public String getActor_3() {
		return actor_3;
	}
	public String getDirector() {
		return director;
	}
	public int getDuration() {
		return duration;
	}
	public String getGenre() {
		return genre;
	}
	public String getRating() {
		return rating;
	}
	public double getScore() {
		return score;
	}
	public String getTitle() {
		return title;
	}
	public int getYear() {
		return year;
	}
	public void setActor_1(String actor_1) {
		this.actor_1 = actor_1;
	}
	public void setActor_2(String actor_2) {
		this.actor_2 = actor_2;
	}
	public void setActor_3(String actor_3) {
		this.actor_3 = actor_3;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	/**
	* Takes an object and compares to the invoking object
	*
	* @param an Object
	* @return boolean 
	*/
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Movie))
			return false;
		Movie OBJ = (Movie) obj;
		return (year == OBJ.year && title.equals(OBJ.title) && duration == OBJ.duration
				&& genre.equals(OBJ.genre) && rating.equals(OBJ.rating)
				&& score == OBJ.score && director.equals(OBJ.director)
				&& actor_1.equals(OBJ.actor_1)&& actor_2.equals(OBJ.actor_2)
				&& actor_3.equals(OBJ.actor_3));
	}
	
	//toString
	@Override
	public String toString() {
		return (year+" "+title+" "+duration+" "+genre+" "+rating+" "
				+score+" "+director+" "+actor_1+" "+actor_2+" "+actor_3);
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
