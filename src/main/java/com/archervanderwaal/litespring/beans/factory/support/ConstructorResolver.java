package com.archervanderwaal.litespring.beans.factory.support;

import com.archervanderwaal.litespring.beans.BeanDefinition;
import com.archervanderwaal.litespring.beans.ConstructorArgument;
import com.archervanderwaal.litespring.beans.SimpleTypeConverter;
import com.archervanderwaal.litespring.beans.TypeMismatchException;
import com.archervanderwaal.litespring.beans.factory.BeanCreationException;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.Objects;

import java.util.List;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/11
 */
public class ConstructorResolver {

    private final AbstractBeanFactory beanFactory;

    private final Logger logger = Logger.getLogger(ConstructorResolver.class);

    public ConstructorResolver(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object autowireConstructor(final BeanDefinition beanDefinition) {
        Constructor<?> constructorToUse = null;
        Object[] argsToUse = null;
        Class<?> beanClass;
        try {
            beanClass = this.beanFactory.getClassLoader().loadClass(beanDefinition.getBeanClassName());
        } catch (ClassNotFoundException e) {
            throw new BeanCreationException(beanDefinition.getBeanId(), "Instantiation of bean failed, can't resolve class", e);
        }
        Constructor<?>[] candidates = beanClass.getConstructors();
        BeanDefinitionValueResolver valueResolver =
                new BeanDefinitionValueResolver(this.beanFactory);
        ConstructorArgument constructorArgument = beanDefinition.getConstructorArgument();
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();
        for (Constructor<?> candidate : candidates) {
            Class<?>[] parameterTypes = candidate.getParameterTypes();
            // if the count of this constructor's params not equals beanDefinition's constructorArgument.valueHolders
            if (parameterTypes.length != constructorArgument.getArgumentCount()) {
                continue;
            }
            argsToUse = new Object[parameterTypes.length];
            boolean result = this.valuesMatchTypes(parameterTypes, constructorArgument.getArgumentValues(),
                    argsToUse, valueResolver, typeConverter);
            if (result) {
                constructorToUse = candidate;
                break;
            }
        }
        if (Objects.isNull(constructorToUse)) {
            throw new BeanCreationException(beanDefinition.getBeanId(), "can't find a suitable constructor");
        }
        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException(beanDefinition.getBeanId(), "can't find a create instance using " + constructorToUse);
        }
    }

    private boolean valuesMatchTypes(Class<?>[] parameterTypes,
                                     List<ConstructorArgument.ValueHolder> valueHolders,
                                     Object[] argsToUse,
                                     BeanDefinitionValueResolver valueResolver,
                                     SimpleTypeConverter typeConverter) {
        for (int i = 0; i < parameterTypes.length; i++) {
            ConstructorArgument.ValueHolder valueHolder
                    = valueHolders.get(i);
            // originalValue instanceof RuntimeBeanReference or TypedStringValue
            Object originalValue = valueHolder.getValue();
            try {
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                Object convertedValue = typeConverter.convertIfNecessary(resolvedValue, parameterTypes[i]);
                argsToUse[i] = convertedValue;
            } catch (TypeMismatchException e) {
                return false;
            }
        }
        return true;
    }
}
