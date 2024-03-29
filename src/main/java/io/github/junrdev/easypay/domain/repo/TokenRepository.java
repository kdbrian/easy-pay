package io.github.junrdev.easypay.domain.repo;

import io.github.junrdev.easypay.domain.model.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<Token, String> {
}
