import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;

public class Generate_1to5 {
    public static void main(String[] args) throws InterruptedException {
        Mono.empty();
        Flux.empty();
        Mono<Integer> mono = Mono.just(1); //Создание из отдельных элементов/коллекций
        Flux<Integer> flux = Flux.just(1, 2, 3);

        Flux<Integer> fluxFromMono = mono.flux(); //Преобразование Mono во flux
        Mono<Boolean> monoFromFlux = flux.any(s -> s.equals(1)); //Преобразование flux в моно
        Mono<Integer> integerMono = flux.elementAt(1);

        Flux.range(1, 5);//.subscribe(System.out::println);

         Flux.fromIterable(Arrays.asList(1, 2, 3)).subscribe(System.out::println);

      Flux.<String>generate(sink -> {
          sink.next("Hello");
      })
              .delayElements(Duration.ofMillis(500))
             .take(4)
              .subscribe(System.out::println);

                Thread.sleep(5000l);
    }
}