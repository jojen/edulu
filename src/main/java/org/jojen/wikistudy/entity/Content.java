package org.jojen.wikistudy.entity;


import javax.persistence.*;
import java.io.Serializable;

/**
 * User: jochen
 * Date: 6/4/13
 * Time: 3:45 PM
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Content implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

	@ManyToOne
	private Lesson parent;

	private Integer index;

	@PrePersist
	@PreUpdate
	private void prepareIndex() {
		if (parent != null) {
			index = parent.getContent().indexOf(this);
		}
	}

    public String getType() {
        return this.getClass().getSimpleName();
    }

    public Integer getId() {
        return id;
    }

    public Boolean getIsEditable() {
        return true;
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Content content = (Content) o;

		if (id != null ? !id.equals(content.id) : content.id != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
