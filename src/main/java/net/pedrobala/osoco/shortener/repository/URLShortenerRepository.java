package net.pedrobala.osoco.shortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.pedrobala.osoco.shortener.model.URLShortener;

@Repository
public interface URLShortenerRepository extends JpaRepository<URLShortener, Long> {
	@Query(value = "SELECT next_val FROM hibernate_sequence", nativeQuery = true)
	Long getNextUrlId();

	@Query(value = "from URLShortener u where u.originalURL = :originalURL")
	Optional<URLShortener> findByOriginalURL(@Param(value = "originalURL") String originalURL);

	@Query(value = "from URLShortener u where u.shortenedURL = :shortenedURL")
	Optional<URLShortener> findByShortenedURL(@Param(value = "shortenedURL") String shortenedURL);
}
