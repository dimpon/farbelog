package org.farbelog.core;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.farbelog.loggers.SupporedLogger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ColorLoggerFactory/*<R extends HasCollor<L>, L>*/ {

	/*public static <R extends HasCollor<L>, L> LoggerWrapper<R, L> type(SupporedLogger<R, L> logger) {
		return new LoggerWrapper<R, L>(logger);
	}*/

	public static <R extends HasCollor<L>, L> R wrapLogger(L original) {
		return new LoggerWrapper<R, L>(original).getLogger();
	}

	public static <R extends HasCollor<L>, L> R wrapLogger(L original, ASCIColor color) {
		return new LoggerWrapper<R, L>(original, color).getLogger();
	}

	/*public static <R extends HasCollor<L>, L> getLogger(L original, ASCIColor color) {

	}*/

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static class LoggerWrapper<R extends HasCollor<L>, L> {

		private SupporedLogger supporedLogger = new SupporedLogger();

		private L original;
		private ASCIColor baseColor = null;
		private static ThreadLocal<ASCIColor> $locals = new ThreadLocal<>();

		private LoggerWrapper(L original) {
			this.original = original;
		}

		private LoggerWrapper(L original, ASCIColor color) {
			this.original = original;
			this.baseColor = color;
		}

		@SuppressWarnings("unchecked")
		private R getLogger() {

			Class<?>[] interfaces = findInterfaces(this.original.getClass(), new Class<?>[]{HasCollor.class});
			Class<?>[] dInterfaces = Arrays.stream(interfaces).distinct().toArray(Class<?>[]::new);

			Class<?> anInterface = supporedLogger.findInterface(interfaces);

			return (R) Proxy.newProxyInstance(
					ColorLoggerFactory.class.getClassLoader(),
					new Class<?>[]{anInterface},
					new LoggerWrapper.DynamicInvocationHandler());
		}

		private Class<?>[] findInterfaces(Class<?> clazz, Class<?>[] interfaces) {

			Class<?>[] newInterfaces = clazz.getInterfaces();
			Class<?> superclass = clazz.getSuperclass();
			if (superclass == null)
				return concatenate(interfaces, newInterfaces);

			return findInterfaces(superclass, concatenate(interfaces, newInterfaces));
		}

		private <T> T[] concatenate(T[] a, T[] b) {
			int aLen = a.length;
			int bLen = b.length;

			@SuppressWarnings("unchecked")
			T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
			System.arraycopy(a, 0, c, 0, aLen);
			System.arraycopy(b, 0, c, aLen, bLen);

			return c;
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
}
