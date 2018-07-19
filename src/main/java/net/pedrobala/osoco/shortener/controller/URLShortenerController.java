package net.pedrobala.osoco.shortener.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.pedrobala.osoco.shortener.model.URLShortener;
import net.pedrobala.osoco.shortener.service.URLShortenerService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class URLShortenerController {

	@Autowired
	private URLShortenerService urlShortenerService;

	@PostMapping(value = "/shortenURL", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<URLShortener> createURL(@Valid @RequestBody URLShortener url) {
		System.out.println("Acortando url: " + url.getOriginalURL());

		Optional<URLShortener> urlShortener = urlShortenerService.saveUrl(url.getOriginalURL());

		if (urlShortener.isPresent()) {
			return new ResponseEntity<>(urlShortener.get(), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/redirect/{id}")
	public ResponseEntity redirect(@PathVariable String id) throws URISyntaxException {
		System.out.println("Redirigiendo a url con id: " + id);
		Optional<URLShortener> originalURL = urlShortenerService.findByShortenedURL(id);
		if (originalURL.isPresent()) {
			String urlToRedirect = originalURL.get().getOriginalURL();
			return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(new URI(urlToRedirect)).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

}
