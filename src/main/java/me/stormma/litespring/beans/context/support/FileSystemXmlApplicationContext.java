package me.stormma.litespring.beans.context.support;

import me.stormma.litespring.beans.context.ApplicationContext;
import me.stormma.litespring.beans.factory.support.DefaultBeanFactory;
import me.stormma.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import me.stormma.litespring.core.io.FileSystemResource;
import me.stormma.litespring.core.io.Resource;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class FileSystemXmlApplicationContext extends AbstractApplicationContext {

    public FileSystemXmlApplicationContext(String configurationFile) {
        super(configurationFile);
    }

    @Override
    protected Resource getResourceByPath(String configurationFile) {
        return new FileSystemResource(configurationFile);
    }
}
