package bpp.quoter;

import org.apache.el.util.ReflectionUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Random;

/**
 * Created by dasha on 11.03.18.
 */
@Configuration
public class InjectRandomIntBeanPostProcesso implements BeanPostProcessor {
    private Random random = new Random();
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            Randomize annotation = field.getAnnotation(Randomize.class);
            if (annotation != null) {
                if(!field.getType().equals(int.class))
                    throw new RuntimeException("don't put @InjectRandomInt above " + field.getType());
                if (Modifier.isFinal(field.getModifiers())) {
                    throw new RuntimeException("can't inject to final fields");
                }
                int min = annotation.min();
                int max = annotation.max();
                int randomInt = min + random.nextInt(max - min);
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, randomInt);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
