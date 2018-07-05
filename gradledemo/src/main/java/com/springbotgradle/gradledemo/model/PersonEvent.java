package com.springbotgradle.gradledemo.model;

import java.util.Date;

public class PersonEvent {
	
	private Person person;
    private Date date;
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public PersonEvent(Person person, Date date) {
		this.person = person;
		this.date = date;
	}
    

}
