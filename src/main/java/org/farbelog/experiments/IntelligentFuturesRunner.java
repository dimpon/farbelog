package org.farbelog.experiments;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiPredicate;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class IntelligentFuturesRunner<K, S, T extends Runnable & IntelligentFuturesRunner.HasSolutionValue<S>> {

	final Map<K, Deque<Pair<S, CompletableFuture<Void>>>> futures = new ConcurrentHashMap<>();

	BiPredicate<S, S> runOnlyAfter;

	public void addRunnable(final K key, final T task) {

		futures.compute(key, (t, cf) -> {
			if (cf == null) {
				Deque<Pair<S, CompletableFuture<Void>>> l = new LinkedList<>();
				l.add(Pair.of(task.getSolutionValue(), CompletableFuture.runAsync(task)));
				return l;
			} else {

				if (runOnlyAfter.test(cf.getLast().getKey(), task.getSolutionValue())) {

					CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(cf.stream()
							.filter(p -> runOnlyAfter.test(p.getKey(), task.getSolutionValue()))
							.map(Pair::getValue).toArray(CompletableFuture[]::new));

					CompletableFuture<Void> completableFuture = voidCompletableFuture.thenRunAsync(task);
					cf.addLast(Pair.of(task.getSolutionValue(), completableFuture));
					return cf;

				} else {

					Optional<CompletableFuture<Void>> k2CompletableFutureOpt = cf.stream()
							.filter(p -> runOnlyAfter.test(p.getKey(), task.getSolutionValue()))
							.map(Pair::getValue)
							.findFirst();

					if (k2CompletableFutureOpt.isPresent()) {
						CompletableFuture<Void> completableFuture1 = k2CompletableFutureOpt.get().thenRunAsync(task);
						cf.addLast(Pair.of(task.getSolutionValue(), completableFuture1));
						return cf;
					} else {
						cf.add(Pair.of(task.getSolutionValue(), CompletableFuture.runAsync(task)));
						return cf;
					}
				}
			}
		});


		CopyOnWriteArrayList li;


	}

	public interface HasSolutionValue<L> {
		L getSolutionValue();
	}

	@AllArgsConstructor(staticName = "of")
	@Getter
	public static class Pair<K,V>{
		private K key;
		private V value;

	}

}
