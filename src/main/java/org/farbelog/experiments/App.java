package org.farbelog.experiments;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;



import lombok.SneakyThrows;

public class App {

	enum Mode {
		R, W;
	}

	private static <V, U, T> Optional<T> withBoth(Optional<V> optLeft, Optional<U> optRight, BiFunction<V, U, T> biFunction) {

		return optLeft.flatMap(left ->
				optRight.map(right ->
						biFunction.apply(left, right)));
	}

	public static void main(String[] args) throws Exception {

		Optional<String> a = Optional.empty();
		Optional<String> b = Optional.of("B");

		String s1 = withBoth(a, b, (s, s2) -> s + s2).orElse("XXX");


		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("\u001b[?25l");
		System.out.println("\u001b[2J");

	/*	ATask d1 = new ATask(2, 1, 15, 300);
		ATask d4 = new ATask(2, 10, 15, 300);

		ATask d2 = new ATask(2, 20, 15, 300);
		ATask d3 = new ATask(2, 30, 15, 300);

		CompletableFuture<Void> voidCompletableFuture0 = CompletableFuture.runAsync(d1);
		CompletableFuture<Void> voidCompletableFuture1 = voidCompletableFuture0.thenRunAsync(d4);
		CompletableFuture<Void> voidCompletableFuture2 = voidCompletableFuture0.thenRunAsync(d2);
		CompletableFuture<Void> voidCompletableFuture3 = voidCompletableFuture0.thenRunAsync(d3);

		CompletableFuture<?> voidCompletableFuture = CompletableFuture
				.allOf(voidCompletableFuture0, voidCompletableFuture1, voidCompletableFuture2, voidCompletableFuture3);

		//wait untill all futures are done
		voidCompletableFuture.get();*/

		CompletableFuture<String> abc = CompletableFuture.supplyAsync(() -> "abc");
		System.out.println(abc.get());


		BiPredicate<Mode, Mode> runOnlyAfter = (m1, m2) -> !(m1 == Mode.R && m2 == Mode.R);


		IntelligentFuturesRunner<Integer, Mode, ATask> holder = new IntelligentFuturesRunner<>();
		holder.runOnlyAfter = runOnlyAfter;



		holder.addRunnable(5, new ATask(5, 1, 15, 20, Mode.R));
		holder.addRunnable(5, new ATask(5, 10, 15, 50, Mode.W));
		holder.addRunnable(5, new ATask(5, 20, 15, 40, Mode.R));
		holder.addRunnable(5, new ATask(5, 30, 15, 20, Mode.W));
		holder.addRunnable(5, new ATask(5, 40, 15, 50, Mode.R));

		holder.addRunnable(6, new ATask(6, 1, 177, 20, Mode.R));
		holder.addRunnable(6, new ATask(6, 10, 177, 50, Mode.R));
		holder.addRunnable(6, new ATask(6, 20, 177, 40, Mode.R));
		holder.addRunnable(6, new ATask(6, 30, 177, 20, Mode.W));
		holder.addRunnable(6, new ATask(6, 40, 177, 50, Mode.R));

		holder.addRunnable(1, new ATask(1, 1, 15, 100, Mode.W));
		holder.addRunnable(1, new ATask(1, 10, 15, 150, Mode.R));
		holder.addRunnable(1, new ATask(1, 20, 15, 100, Mode.R));
		holder.addRunnable(1, new ATask(1, 30, 15, 20, Mode.R));
		holder.addRunnable(1, new ATask(1, 40, 15, 50, Mode.W));

		holder.addRunnable(2, new ATask(2, 1, 10, 200, Mode.R));
		holder.addRunnable(2, new ATask(2, 10, 10, 200, Mode.R));
		holder.addRunnable(2, new ATask(2, 20, 10, 200, Mode.R));

		holder.addRunnable(3, new ATask(3, 1, 11, 50, Mode.W));
		holder.addRunnable(3, new ATask(3, 10, 11, 200, Mode.W));
		holder.addRunnable(3, new ATask(3, 20, 11, 100, Mode.W));

		holder.addRunnable(4, new ATask(4, 1, 187, 70, Mode.R));
		holder.addRunnable(4, new ATask(4, 10, 187, 20, Mode.R));
		holder.addRunnable(4, new ATask(4, 20, 187, 30, Mode.R));
		holder.addRunnable(4, new ATask(4, 30, 187, 10, Mode.W));

		CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(
				holder.futures.values().stream().flatMap(Collection::stream).map(IntelligentFuturesRunner.Pair::getValue).toArray(CompletableFuture[]::new));

		//Thread.sleep(2000);

		//	voidCompletableFuture.cancel(true);


		/*CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(
				holder.futures.values().stream().flatMap(o -> o.)
*/
		//wait untill all futures are done
		voidCompletableFuture.get();

		//ExecutorService e = Executors.newFixedThreadPool(5);
		//e.submit(aTask);
		/*e.submit(d1);
		e.submit(d2);
		e.submit(d3);
		e.submit(d4);*/

	}

	private static class ATask implements Runnable, IntelligentFuturesRunner.HasSolutionValue<Mode> {

		private int y, x, color;
		private long sleep;
		private Mode mode;

		public ATask(int y, int x, int color, long sleep, Mode mode) {
			this.y = y;
			this.x = x;
			this.color = color;
			this.sleep = sleep;
			this.mode = mode;
			System.out.print("\u001B[" + (y + 2) + ";" + x + "H" + "\u001b[38;5;" + color + "m " + mode + ":[0]\u001b[0m");
		}

		@SneakyThrows
		@Override
		public void run() {
			for (int index = 0; index < 100; index++) {
				//System.out.print(index);
				System.out.print("\u001B[" + (y + 2) + ";" + x + "H" + "\u001b[38;5;" + color + "m " + mode + ":[" + index + "]\u001b[0m");
				Thread.sleep(sleep);
			}
		}

		@Override
		public Mode getSolutionValue() {
			return mode;
		}
	}

}
