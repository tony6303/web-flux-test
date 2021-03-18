package com.cos.reactorex02;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;

@CrossOrigin
@RestController
public class TestController2 {
	// Processor
	Sinks.Many<String> sink; // reactor 기본 제공
	AtomicLong counter;
	
	// multicast : 새로 들어온 데이터만 응답. hot sequence
	// replay : 기존 데이터를 재생한 다음, 새 데이터를 응답. cold sequence 채팅으로 치면? 이전 채팅내용이 보임 ?
	
	public TestController2() {
		this.sink = Sinks.many().multicast().onBackpressureBuffer();
		this.counter = new AtomicLong();
	}
	
	@PostMapping("/send")
	public void send(@RequestBody SendReqDto sendReqDto) { // 입력창의 내용을 가져와서 send 해야한다
//		EmitResult result = sink.tryEmitNext("Hello World #" + counter.getAndIncrement()); // publishing (발행인)
//        if (result.isFailure()) {
//          // do something here, since emission failed
//        }
		
		sink.tryEmitNext(sendReqDto.getUsername()+ " : " + sendReqDto.getChat());
	}
	
	// 
	// data : 실제값 \n\n
	
	@GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ServerSentEvent<String>> sse() { // Flux<ServerSentEvent<String>>
		return sink.asFlux().map(e -> ServerSentEvent.builder(e).build()).doOnCancel(()->{
			System.out.println("sse 종료됨");
			sink.asFlux().blockLast();
		});
	}
	
	
	@GetMapping("/")
	public Flux<Integer> findAll(){
		return Flux.just(1,2,3,4,5,6).log();
	}
}
