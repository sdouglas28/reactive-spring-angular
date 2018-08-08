package com.mrvnflx.reactive.reactivespringangular.repository;

import com.mrvnflx.reactive.reactivespringangular.domain.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuoteMongoBlockingRepository extends CrudRepository<Quote, String> {

    @Query("{ id: {$exists: true }}")
    List<Quote> retrieveAllQuotesPaged(final Pageable page);
}
