import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;

public class Flux_Improve {
    public static void main(String[] args) throws InterruptedException {
        Mono.empty();
        Flux.empty();

        Mono<Integer> mono = Mono.just(1); //Создание из отдельных элементов/коллекций
        Flux<Integer> flux = Flux.just(1, 2, 3);

        Flux<Integer> fluxFromMono = mono.flux(); //Преобразование Mono во flux
        Mono<Boolean> monoFromFlux = flux.any(s -> s.equals(1)); //Преобразование flux в моно
        Mono<Integer> integerMono = flux.elementAt(1);

        Flux.range(1, 5); //Генерируемые последовательности от 1 до 5
        //   Flux.range(1,5).subscribe(System.out::println); // 1) Выведем нашу последовательность
        Flux.fromIterable(Arrays.asList(1, 2, 3)); // 2) Выведем первые три элемента
        //Flux.<String>generate(sink -> {
        // sink.next("Hello"); // 3) Бесконечно выводит...
        //   })// .subscribe(System.out::println);
        //  .delayElements(Duration.ofMillis(500))
        //.take(4) //Берем n количество элементов
        //    .subscribe(System.out::println);

        Flux<Object> telegramProducer = Flux
                .generate(
                        () -> 2354,
                        (state, sink) -> {
                            if (state > 2366) {
                                sink.complete();
                            } else {
                                sink.next("Step: " + state);
                            }
                            return state + 3;
                        }
                );

        Flux    //Аналог - push. Push - однопоточный, create - многопоточный
                .create(sink -> {
                    telegramProducer.subscribe(new BaseSubscriber<Object>() {  //Работает когда у нас есть система подписок
                        @Override
                        protected void hookOnNext(Object value) {
                            sink.next(value);
                        }


                        protected void hookOnComplete() {
                            sink.complete();
                        }
                    });
                    sink.onRequest(r -> {
                        sink.next("DB_returns: " + telegramProducer.blockFirst()); //Мы сами пулим с внешнего сервиса
                    });
                });

        Flux<String> second = Flux
                .just("World", "coder")
                .repeat();

        Flux<String> sumFlux = Flux
                .just("hello", "dru", "java", "Linux", "Asia", "java")
                .zipWith(second, (f, s) -> String.format("%s %s", f, s));

        sumFlux
            //      .delayElements(Duration.ofMillis(1000))
                   .timeout(Duration.ofSeconds(1))
                .retry(3)
                  .onErrorReturn("To slow")
            //    .onErrorResume(throwable ->
            //            Flux
            //                    .interval(Duration.ofMillis(300))
            //                    .map(String::valueOf)
            //    )
                //.skip(2)
                //.take(5)
               .subscribe(System.out::println);
         Thread.sleep(5000l);
    }
}