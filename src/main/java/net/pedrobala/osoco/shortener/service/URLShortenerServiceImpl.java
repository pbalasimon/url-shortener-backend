package net.pedrobala.osoco.shortener.service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.pedrobala.osoco.shortener.model.URLShortener;
import net.pedrobala.osoco.shortener.repository.URLShortenerRepository;
import net.pedrobala.osoco.shortener.utils.Base62;

@Service
public class URLShortenerServiceImpl implements URLShortenerService {
	@Autowired
	private URLShortenerRepository urlShortenerRepository;

	@Value("${domain.url}")
	private String domain;

	public Optional<URLShortener> saveUrl(String originalURL) {
		System.out.println("Guardando url: " + originalURL);
		URLShortener urlShortener = new URLShortener();
		if (validateURL(originalURL)) {

			Optional<URLShortener> existsURL = urlShortenerRepository.findByOriginalURL(originalURL);

			if (existsURL.isPresent()) {
				urlShortener.setId(existsURL.get().getId());
				urlShortener.setOriginalURL(originalURL);
				urlShortener.setShortenedURL(existsURL.get().getShortenedURL());
				return Optional.of(urlShortener);
			}

			Long id = urlShortenerRepository.getNextUrlId();
			urlShortener.setId(id);
			urlShortener.setShortenedURL(domain + "/" + Base62.toBase62(id));
			urlShortener.setOriginalURL(originalURL);
			urlShortener = urlShortenerRepository.save(urlShortener);
			return Optional.ofNullable(urlShortener);
		}
		return Optional.empty();
	}

	private boolean validateURL(String url) {
		try {
			URL urlValidator = new URL(url);
			urlValidator.toURI();
			System.out.println("URL " + url + " válida");
			return true;
		} catch (MalformedURLException ex) {
			System.err.println("URL no válida " + ex.getMessage());
			return false;
		} catch (URISyntaxException ex) {
			System.err.println("URL no válida  " + ex.getMessage());
			return false;
		}
	}

	@Override
	public Optional<URLShortener> findByShortenedURL(String id) {
		String shortenedURL = domain + "/" + id;
		return urlShortenerRepository.findByShortenedURL(shortenedURL);
	}
}
