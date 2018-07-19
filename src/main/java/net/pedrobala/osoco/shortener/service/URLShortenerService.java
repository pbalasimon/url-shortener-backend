package net.pedrobala.osoco.shortener.service;

import java.util.Optional;

import net.pedrobala.osoco.shortener.model.URLShortener;

public interface URLShortenerService {
	Optional<URLShortener> saveUrl(String originalURL);

	Optional<URLShortener> findByShortenedURL(String shortenedURL);
}
