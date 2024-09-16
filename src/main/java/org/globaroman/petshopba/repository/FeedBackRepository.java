package org.globaroman.petshopba.repository;

import org.globaroman.petshopba.model.user.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedBackRepository extends JpaRepository<Feedback, Long> {
}
