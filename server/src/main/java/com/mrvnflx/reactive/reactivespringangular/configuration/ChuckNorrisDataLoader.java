package com.mrvnflx.reactive.reactivespringangular.configuration;

import com.github.javafaker.Faker;
import com.mrvnflx.reactive.reactivespringangular.domain.Quote;
import com.mrvnflx.reactive.reactivespringangular.repository.QuoteMongoReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.stream.LongStream;

@Component
@Slf4j
public class ChuckNorrisDataLoader implements CommandLineRunner {

    private QuoteMongoReactiveRepository quoteMongoReactiveRepository;

    public ChuckNorrisDataLoader(QuoteMongoReactiveRepository quoteMongoReactiveRepository) {
        this.quoteMongoReactiveRepository = quoteMongoReactiveRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        quoteMongoReactiveRepository.deleteAll().block();

        Flux.fromStream(
                LongStream.range(1, 1000).boxed()
                .map(n -> quoteMongoReactiveRepository.save(new Quote(String.valueOf(n), "Chuck Norris", faker.chuckNorris().fact())))
        ).subscribe(m -> log.info("New quote loaded: {}", m.block()));

        log.info("Repository now contains {} entries.", quoteMongoReactiveRepository.count().block());
    }
}
