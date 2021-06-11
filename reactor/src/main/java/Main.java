import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;

public class Main {
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

        Flux
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
                ).subscribe(System.out::println);

        // Flux    //Аналог - push. Push - однопоточный, create - многопоточный
        //         .create(sink -> {
        //             telegramProducer.subscribe(new BaseSubscriber<Object>() {
        //                 @Override
        //                 protected void hookOnNext(Object value) {
        //                     sink.next(value);
//
        //                 @Override
        //                 protected void hookOnComplete() {
        //                     sink.complete();
        //                 }
        //             })
        //                         ).subs
        //             sink.onRequest(r -> {
        //                 sink.next("DB returns: " + telegramProducer.blockFirst());
        //             });
        //         });

        // Flux<String> second = Flux
        //         .just("World", "coder")
        //         .repeat();

        // Flux<String> sumFlux = Flux
        //         .just("hello", "dru", "java", "Linux", "Asia", "java")
        //         .zipWith(second, (f,s) -> String.format("%s %s", f, s) );

        // Flux<String> string = sumFlux
        //         .delayElements(Duration.ofMillis(1300))
        //         .timeout(Duration.ofSeconds(1))
//   //           .retry(3)
//   //           .onErrorReturn("To slow")
        //         .onErrorResume(throwable ->
        //                 Flux
        //                     .interval(Duration.ofMillis(300))
        //                     .map(String::valueOf)
        //         )
        //         .skip(2)
        //         .take(3);

        //         string.subscribe(
        //                 v -> System.out.println(v),
        //                 e -> System.err.println(e),
        //                 () -> System.out.println("finished")
        //         );

        Thread.sleep(5000l);
    }
}
