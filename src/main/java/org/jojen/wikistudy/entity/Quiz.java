package org.jojen.wikistudy.entity;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: jochen
 * Date: 06.06.13
 * Time: 21:00
 */
@Entity
public class Quiz extends Content implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
