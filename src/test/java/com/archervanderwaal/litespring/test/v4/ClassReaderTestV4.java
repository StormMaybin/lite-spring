package com.archervanderwaal.litespring.test.v4;

import com.archervanderwaal.litespring.core.annotation.AnnotationAttributes;
import com.archervanderwaal.litespring.core.io.ClassPathResource;
import com.archervanderwaal.litespring.core.type.classreading.AnnotationMetadataReadingVisitor;
import com.archervanderwaal.litespring.core.type.classreading.ClassMetadataReadingVisitor;
import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.ClassReader;

import java.io.IOException;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/13
 */
public class ClassReaderTestV4 {

    @Test
    public void testClassReader() throws IOException {
        ClassPathResource resource = new ClassPathResource("com/archervanderwaal/litespring/test/v4/entity/PetStoreService.class");
        ClassReader classReader = new ClassReader(resource.getInputStream());

        ClassMetadataReadingVisitor classMetaDataReadingVisitor = new ClassMetadataReadingVisitor();
        classReader.accept(classMetaDataReadingVisitor, ClassReader.SKIP_DEBUG);
        Assert.assertFalse(classMetaDataReadingVisitor.isAbstract());
    }

    @Test
    public void testGetAnnotation() throws IOException {
        ClassPathResource resource = new ClassPathResource("com/archervanderwaal/litespring/test/v4/entity/PetStoreService.class");
        ClassReader classReader = new ClassReader(resource.getInputStream());
        AnnotationMetadataReadingVisitor annotationMetadataVisitor = new AnnotationMetadataReadingVisitor();
        classReader.accept(annotationMetadataVisitor, ClassReader.SKIP_DEBUG);
        String annotation = "com.archervanderwaal.litespring.stereotype.Component";
        Assert.assertTrue(annotationMetadataVisitor.hasAnnotation(annotation));
        AnnotationAttributes attributes = annotationMetadataVisitor.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore", attributes.getString("value"));
    }
}
