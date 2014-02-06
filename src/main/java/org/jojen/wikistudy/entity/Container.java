package org.jojen.wikistudy.entity;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.io.Serializable;



@Entity
public class Container extends Content implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(Container.class);
	private static final Integer DEFAULT_COLUMNS = 2;

	@OneToOne
	private Content firstContent;

	@OneToOne
	private Content secondContent;

	public Content getFirstContent() {
		return firstContent;
	}

	public void setFirstContent(Content firstContent) {
		this.firstContent = firstContent;
	}

	public Content getSecondContent() {
		return secondContent;
	}

	public void setSecondContent(Content secondContent) {
		this.secondContent = secondContent;
	}
}
