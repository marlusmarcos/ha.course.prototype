package com.labcomu.ha.prototype.rest;

import java.time.Duration;
import java.util.Arrays;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.labcomu.ha.prototype.model.BookScore;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.decorators.Decorators;

@Component
public class BookScoreGateway {

	private static final int TIMEOUT = 1;

	private final WebClient webClient;
	
	private static BookScoreGateway instance;
	
	Logger logger = LoggerFactory.getLogger(BookScoreGateway.class);

	private CircuitBreaker circuitBreaker=null;
	
	
	

	public BookScoreGateway() {

		this.webClient = WebClient.create("http://localhost:8080/");
	}
	
	public BookScore executeRequest(int id) {

		BookScore bookScore = null;

		
		  bookScore = webClient
				  		.get()
				  		.uri("api/evaluation/{id}", id)
				  		.accept(MediaType.APPLICATION_JSON)
				  		.retrieve()
				  		.bodyToMono(BookScore.class)
				  		.timeout(Duration.ofSeconds(TIMEOUT))
				  		.block();
		  
		 
		return bookScore;
	}

	
	public BookScore getBookScore(int id) {

		BookScore bookScore = null;
		
	    
	      
	    bookScore = executeRequest(id);
	      
		
		return bookScore;
	}

	
	public BookScore getBookScoreCB(int id) {

		BookScore bookScore = null;
		
        CircuitBreaker circuitBreaker = getCB();
	    
	      
	    Supplier<BookScore> bookScoreSupplier =  () -> this.executeRequest(id);
	      
	    Supplier<BookScore> decoratedBookScoreSupplier = Decorators
	    													.ofSupplier(bookScoreSupplier)
	    													.withCircuitBreaker(circuitBreaker)
	    										            .withFallback(Arrays.asList(CallNotPermittedException.class,Throwable.class),
	    										            		e->this.getBookScoreFallback(id,e))

	    													.decorate();

	    bookScore = decoratedBookScoreSupplier.get();
	    
		
		
		return bookScore;
	}

	private CircuitBreaker getCB() {
		
		
		if(circuitBreaker == null) {
		
		
			CircuitBreakerConfig config = CircuitBreakerConfig
	                .custom()
	                .slidingWindowType(SlidingWindowType.COUNT_BASED)
	                .minimumNumberOfCalls(5)
	                .slidingWindowSize(10)
	                .failureRateThreshold(50.0f)
	                .writableStackTraceEnabled(false)
	                .automaticTransitionFromOpenToHalfOpenEnabled(true)
	                .waitDurationInOpenState(Duration.ofSeconds(2))
	                .writableStackTraceEnabled(false)
	                .slowCallRateThreshold(50.0f)
	                .slowCallDurationThreshold(Duration.ofSeconds(TIMEOUT))
	                .build();
		    
		    
		    CircuitBreakerRegistry cbRegistry = CircuitBreakerRegistry.of(config);
		    circuitBreaker = cbRegistry.circuitBreaker("cbBookStore");
		    
		    
		    circuitBreaker.getEventPublisher().onCallNotPermitted(e -> logger.info("[onCallNotPermitted]"+e.toString()));
		    circuitBreaker.getEventPublisher().onError(e -> logger.info("[onError]"+e.toString()));
		    circuitBreaker.getEventPublisher().onFailureRateExceeded(e -> logger.info("[onFailureRateExceeded]"+e.toString()));
		    circuitBreaker.getEventPublisher().onStateTransition(e -> logger.info("[onStateTransition]"+e.toString()));
		}

		
		return circuitBreaker;
	}

	public BookScore getBookScoreFallback(int id, Throwable e) {

		logger.info("Fallback GetBookStore ("+id+") ["+e.getMessage()+"] ");

		return BookScore.EMPTY;
	}

	public static synchronized BookScoreGateway getInstance() {
		
		if(instance == null) {
			
			instance = new BookScoreGateway();
		}

		return instance;
	}

}
