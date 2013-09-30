package org.jojen.wikistudy.entity;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * User: jochen
 * Date: 6/5/13
 * Time: 6:05 PM
 */
@Entity
public class Image extends Embedded implements Serializable {
    private static final long serialVersionUID = 1L;

	private Boolean showPdf;

	public Boolean getShowPdf() {
		if(showPdf == null){
			showPdf = true;
		}
		return showPdf;
	}

	public void setShowPdf(Boolean showPdf) {
		this.showPdf = showPdf;
	}

	@Override
	public Boolean getIsEditable() {
		return true;
	}


}
