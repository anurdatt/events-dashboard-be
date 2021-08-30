package com.tcsonline.eventsdashboard.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="EVENT")
public class Event {

	@Id
	@Column(name="LINK")
	private String link;
	
	@Column(name="NAME")
	private String name;
	
	@DateTimeFormat(/*iso=ISO.DATE_TIME,*/ pattern="yyyy-MM-dd hh:mm:SS.s")
	@Column(name="START")
	private Date start;
	
	@DateTimeFormat(/*iso=ISO.DATE_TIME,*/ pattern="yyyy-MM-dd hh:mm:SS.s")
	@Column(name="END")
	private Date end;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	
}
