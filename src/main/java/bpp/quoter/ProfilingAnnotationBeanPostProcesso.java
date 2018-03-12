package bpp.quoter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dasha on 11.03.18.
 */
@Configuration
public class ProfilingAnnotationBeanPostProcesso implements BeanPostProcessor {
    Map<String, Class> map = new HashMap<String, Class>();
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        if(aClass.isAnnotationPresent(Profiling.class))
            map.put(beanName, aClass);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        final Class beanClass = map.get(beanName);
        if(beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                    long before = System.nanoTime();
                    Object retVal = method.invoke(bean, objects);
                    long after = System.nanoTime();
                    System.out.println("Class " + beanClass.getSimpleName() + " worked: "+(after-before)+" ns");
                    return retVal;
                }
            });
        } else return bean;
    }
}
