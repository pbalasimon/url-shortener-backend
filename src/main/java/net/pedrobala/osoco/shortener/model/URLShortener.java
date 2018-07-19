package net.pedrobala.osoco.shortener.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "urls")
public class URLShortener {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Column(name = "original_url")
	private String originalURL;

	@Column(name = "shortened_url")
	private String shortenedURL;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOriginalURL() {
		return originalURL;
	}

	public void setOriginalURL(String originalURL) {
		this.originalURL = originalURL;
	}

	public String getShortenedURL() {
		return shortenedURL;
	}

	public void setShortenedURL(String shortenedURL) {
		this.shortenedURL = shortenedURL;
	}

}
