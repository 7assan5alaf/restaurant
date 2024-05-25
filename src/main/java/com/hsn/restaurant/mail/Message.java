package com.hsn.restaurant.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

	private  String messagwToUser = "Welcome to our site\n"
			+ "A restaurant is a place where you can eat a meal and pay for it. "
			+ "In restaurants, your food is usually served to you at your table by"
			+ " a waiter or waitress. The restaurant serves breakfast, lunch, and dinner. "
			+ "The food at the restaurant was good and the waiters were polite."
			+ "\nWe wish you all the best\n" + "Thank you for registering on our site";
	private   String subject = "Welcome to restaurant online";
	private   String from = "artgallery623@gmail.com";
}
