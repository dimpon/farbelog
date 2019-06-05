package org.farbelog.core;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ColorLoggerFactory<R extends HasCollor<L>, L> {

    private final L original;

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
    }

    private class DynamicInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (Arrays.asList(HasCollor.class.getMethods()).contains(method)) {
                ///color
                $locals.set((ASCIColor) args[0]);
                return proxy;
            }


            Object[] argss;
            if ($locals.get() != null) {
                argss = replaceArgs(method, $locals.get(), args);
                $locals.remove();
            } else if (baseColor != null) {
                argss = replaceArgs(method, baseColor, args);
            } else {
                argss = args;
            }


            return method.invoke(original, args);
        }

        private Object[] replaceArgs(Method method, ASCIColor color, Object[] args) {
            int[] argss = $methods.getOrDefault(method.toString(), new int[0]);
            String pattern = color.pattern();
            for (int i : argss) {
                args[i] = String.format(pattern, args[i].toString());
            }
            return args;
        }

    }

    private Map<String, int[]> $methods = new HashMap<String, int[]>() {{
        put("public abstract void org.slf4j.Logger.info(java.lang.String)", new int[]{0});
    }};

    @Value
    public final static class InitPair<R extends Class<? extends HasCollor<L>>, L> {
        private final R clazz;
        private final L logger;
    }
}
