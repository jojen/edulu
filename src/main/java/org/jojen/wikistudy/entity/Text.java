package org.jojen.wikistudy.entity;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public class Text extends Content implements Serializable {
    private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(Text.class);


	@Size(max = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String text;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return getPreparedText();
    }

    public void setText(String text) {
        this.text = text;
    }

	private String getPreparedText(){
		if(text == null){
			return null;
		}
		// TODO das machen wir noch wo anderst hin
		String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";

		Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = compiledPattern.matcher(text);
		while(matcher.find()){
			//matcher.replaceFirst()
			log.info(matcher.group());
		}
		return matcher.replaceAll("youtube-link");
	}
}
