package org.farbelog.core;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.farbelog.loggers.SupporedLogger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ColorLoggerFactory/*<R extends HasCollor<L>, L>*/ {

	public static <R extends HasCollor<L>, L> LoggerWrapper<R, L> type(SupporedLogger<R, L, Class<?>> logger) {
		return new LoggerWrapper<R, L>(logger);
	}



	/*private final L original;

	private ASCIColor baseColor = null;
	private static ThreadLocal<ASCIColor> $locals = new ThreadLocal<>();

	public static <R extends HasCollor<L>, L> R getLogger(Class<R> clazz, L original, ASCIColor color) {
		ColorLoggerFactory<R, L> factory = new ColorLoggerFactory<>(original, color);
		return factory.getLogger(clazz);
	}

	public static <R extends HasCollor<L>, L> R getLogger(Class<R> clazz, L original) {
		ColorLoggerFactory<R, L> factory = new ColorLoggerFactory<>(original);
		return factory.getLogger(clazz);
	}

	@SuppressWarnings("unchecked")
	private R getLogger(Class<R> clazz) {
		return (R) Proxy.newProxyInstance(
				ColorLoggerFactory.class.getClassLoader(),
				new Class[]{clazz},
				new ColorLoggerFactory.DynamicInvocationHandler());
	}*/

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static class LoggerWrapper<R extends HasCollor<L>, L> {
		private final SupporedLogger<R, L, Class<?>> loggerType;

		private L original;
		private ASCIColor baseColor = null;
		private static ThreadLocal<ASCIColor> $locals = new ThreadLocal<>();

		public LoggerWrapper<R, L> color(ASCIColor color) {
			this.baseColor = color;
			return this;
		}

		@SuppressWarnings("unchecked")
		public R getLogger(Class<?> clazz) {

			original = loggerType.createOriginalLogger().apply(clazz);


			return (R) Proxy.newProxyInstance(
					ColorLoggerFactory.class.getClassLoader(),
					new Class[]{loggerType.coloredLogger()},
					new LoggerWrapper.DynamicInvocationHandler());
		}

		private class DynamicInvocationHandler implements InvocationHandler {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

				if (Arrays.asList(HasCollor.class.getMethods()).contains(method)) {
					///color
					$locals.set((ASCIColor) args[0]);
					return proxy;
				}

				if ($locals.get() != null) {
					replaceArgs(method, $locals.get(), args);
					$locals.remove();
				} else if (baseColor != null) {
					replaceArgs(method, baseColor, args);
				}

				return method.invoke(original, args);
			}

			private void replaceArgs(Method method, ASCIColor color, Object[] args) {
				int[] argss = SupporedLogger.methods.getOrDefault(method.toString(), new int[0]);
				String pattern = color.pattern();
				for (int i : argss) {
					args[i] = String.format(pattern, args[i].toString());
				}
			}

		}

	}



	/*@Value
	public final static class InitPair<R extends Class<? extends HasCollor<L>>, L> {
		private final R clazz;
		private final L logger;
	}*/
}
