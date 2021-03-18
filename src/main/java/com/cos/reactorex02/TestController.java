package com.cos.reactorex02;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
// Netty 서버는 비동기 서버, Tomcat 서버는 스레드 서버
// flux는 n개 이상의 데이터를 응답할 때, mono는 0~1개의 데이터를 응답할 때

@RestController
public class TestController {

	@GetMapping("/flux1")
	public Flux<?> flux1() {
		return Flux.just(1,2,3,4).log(); 
	}
	
	// produces : 내가 어떤 mime 타입으로 응답할지 정함. MessageConverter를 건드리는것
	@GetMapping(value = "/flux2", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<?> flux2() {
		return Flux.just(1,2,3,4).delayElements(Duration.ofSeconds(3)).log(); 
	}
	
	// 무한으로 실행 됨
	@GetMapping(value = "/flux3", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<?> flux3() {
		return Flux.interval(Duration.ofSeconds(1)).log(); 
	}
	
	@GetMapping(value = "/mono1", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<?> mono1() {
		return Mono.just(1).log();
	}
}
