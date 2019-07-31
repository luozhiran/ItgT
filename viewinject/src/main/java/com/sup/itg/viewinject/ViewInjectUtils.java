package com.sup.itg.viewinject;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ViewInjectUtils {

    private static final String METHOD_SET_CONTENTVIEW = "setContentView";
    private static final String METHOD_FINDVIEWBYID = "findViewById";

    private static void injectContentView(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        if (clazz.isAnnotationPresent(ContentView.class)) {
            ContentView contentView = clazz.getAnnotation(ContentView.class);
            int layoutId = contentView.value();
            try {
                Method method = clazz.getMethod(METHOD_SET_CONTENTVIEW, int.class);
                method.invoke(activity, layoutId);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static void injectViews(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field fl : fields) {
            if (fl.isAnnotationPresent(ViewInject.class)) {
                ViewInject viewInject = fl.getAnnotation(ViewInject.class);
                int value = viewInject.value();
                if (value != -1) {
                    try {
                        Method method = clazz.getMethod(METHOD_FINDVIEWBYID, int.class);
                        Object resView = method.invoke(activity, value);
                        fl.setAccessible(true);
                        fl.set(activity, resView);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void injectEvents(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(OnClick.class)) {
                OnClick onClick = method.getAnnotation(OnClick.class);
                Class<? extends Annotation> annotationType = onClick.annotationType();
                if (annotationType.isAnnotationPresent(EventBase.class)) {
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    String listenerSetter = eventBase.listenerSetter();
                    Class<?> listenerType = eventBase.listenerType();
                    String methodName = eventBase.methodName();
                    int[] viewIds = onClick.value();
                    //通过InvocationHandler设置代理
                    DynamicHandler handler = new DynamicHandler(activity);
                    handler.addMethod(methodName, method);
                    View.OnClickListener onClickListener = (View.OnClickListener) Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, handler);
                    try {
                        for (int viewId : viewIds) {
                            View view = activity.findViewById(viewId);
                            Method setEventListenerMethod = null;
                            setEventListenerMethod = view.getClass().getMethod(listenerSetter, listenerType);
                            setEventListenerMethod.invoke(view, onClickListener);
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
    }

    public static void inject(Activity activity) {
        injectContentView(activity);
        injectViews(activity);
        injectEvents(activity);
    }

}
