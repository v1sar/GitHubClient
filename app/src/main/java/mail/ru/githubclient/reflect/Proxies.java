package mail.ru.githubclient.reflect;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.common.base.Defaults;
import com.google.common.reflect.AbstractInvocationHandler;
import com.google.common.reflect.Reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

public class Proxies {

    private static final Handler MAIN_THREAD = new Handler(Looper.getMainLooper());

    public static <T> Wrapper<T> wrap(Class<T> clazz, T interfaceImpl) {
        final AtomicReference<T> reference = new AtomicReference<>(interfaceImpl);
        final T internal = wrapInternal(clazz, reference);
        return new Wrapper<T>() {
            @Override
            public T delegate() {
                return internal;
            }

            @Override
            public void unregister() {
                reference.set(null);
            }
        };
    }

    private static <T> T wrapInternal(Class<T> clazz, final AtomicReference<T> interfaceImpl) {
        return Reflection.newProxy(clazz,
                new AbstractInvocationHandler() {
                    @Override
                    protected Object handleInvocation(@NonNull Object proxy,
                                                      @NonNull final Method method,
                                                      @NonNull final Object[] args) throws Throwable {
                        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                            call(interfaceImpl, method, args);
                        } else {
                            MAIN_THREAD.post(new Runnable() {
                                @Override
                                public void run() {
                                    call(interfaceImpl, method, args);
                                }
                            });
                        }
                        return Defaults.defaultValue(method.getReturnType());
                    }
                });
    }

    private static void call(AtomicReference<?> reference, Method method, Object[] args) {
        Object o = reference.get();
        if (o == null) {
            return;
        }
        //noinspection TryWithIdenticalCatches
        try {
            method.invoke(o, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
