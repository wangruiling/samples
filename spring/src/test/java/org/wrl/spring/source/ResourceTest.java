package org.wrl.spring.source;

import org.junit.Test;
import org.springframework.core.io.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-11-09 15:47
 */
public class ResourceTest {
    /**
     * ByteArrayResource 代表 byte[]数组资源，对于“getInputStream”操作将返回一个ByteArrayInputStream。ByteArrayResource 可多次读取数组资源，即 isOpen ()永远返回 false
     */
    @Test
    public void testByteArrayResource() {
        Resource resource = new ByteArrayResource("Hello World!".getBytes());
        if (resource.exists()) {
            dumpStream(resource);
        }
    }

    /**
     * InputStreamResource 代表 java.io.InputStream 字节流，对于“getInputStream ”操作将直接返回该字节流，因此只能读取一次该字节流，即“isOpen”永远返回 true。
     */
    @Test
    public void testInputStreamResource() {
        ByteArrayInputStream bis = new ByteArrayInputStream("Hello World!".getBytes());
        Resource resource = new InputStreamResource(bis);
        if(resource.exists()) {
            dumpStream(resource);
        }
        assertThat(resource.isOpen()).isTrue();
    }

    /**
     * FileSystemResource 代表 java.io.File 资源，对于“getInputStream ”操作将返回底层文件的字节流，“isOpen”将永远返回 false，从而表示可多次读取底层文件的字节流
     */
    @Test
    public void testFileResource() {
        File file = new File("d:/test.txt");
        Resource resource = new FileSystemResource(file);
        if(resource.exists()) {
            dumpStream(resource);
        }
        assertThat(resource.isOpen()).isFalse();
    }

    /**
     * 使用默认的加载器加载资源，将加载当前 ClassLoader 类路径上相对于根路径的资源
     * @throws IOException
     */
    @Test
    public void testClasspathResourceByDefaultClassLoader() throws IOException {
        Resource resource = new ClassPathResource("org/wrl/spring/source/test1.properties");
        if(resource.exists()) {
            dumpStream(resource);
        }
        System.out.println("path:" + resource.getFile().getAbsolutePath());
        assertThat(resource.isOpen()).isFalse();
    }

    /**
     * 使用指定的 ClassLoader 进行加载资源，将加载指定的 ClassLoader 类路径上相对于根路径的资源
     * @throws IOException
     */
    @Test
    public void testClasspathResourceByClassLoader() throws IOException {
        ClassLoader cl = this.getClass().getClassLoader();
        Resource resource = new ClassPathResource("org/wrl/spring/source/test1.properties" , cl);
        if(resource.exists()) {
            dumpStream(resource);
        }
        System.out.println("path:" + resource.getFile().getAbsolutePath());
        assertThat(resource.isOpen()).isFalse();
    }

    /**
     * 使用指定的类进行加载资源，将尝试加载相对于当前类的路径的资源
     * 类路径一般都是相对路径，即相对于类路径或相对于当前类的路径，因此如果使用“/test1.properties”带前缀“/”的路径，将自动删除“/”得到“test1.properties”
     * @throws IOException
     */
    @Test
    public void testClasspathResourceByClass() throws IOException {
        Resource resource2 = new ClassPathResource("test1.properties" , this.getClass());
        if(resource2.exists()) {
            dumpStream(resource2);
        }
        System.out.println("path:" + resource2.getFile().getAbsolutePath());
        assertThat(resource2.isOpen()).isFalse();
    }

    /**
     * 加载 jar 包里的资源，首先在当前类路径下找不到，最后才到 Jar 包里找，而且在第一个 Jar 包里找到的将被返回
     * 如果当前类路径包含“overview.html”，在项目的“resources”目录下，将加载该资源，否则将加载 Jar 包里的“overview.html”，而且不能使用“resource.getFile()”，应该使用
     * “resource.getURL()”，因为资源不存在于文件系统而是存在于 jar 包里，URL 类似于 “file:/C:/.../***.jar!/overview.html”。
     * @throws IOException
     */
    @Test
    public void classpathResourceTestFromJar() throws IOException {
        Resource resource = new ClassPathResource("overview.html");
        if(resource.exists()) {
            dumpStream(resource);
        }
        System.out.println("path:" + resource.getURL().getPath());
        assertThat(resource.isOpen()).isFalse();
    }

    private void dumpStream(Resource resource) {
        InputStream is = null;
        try {
            //1.获取文件资源
            is = resource.getInputStream();
            //2.读取资源
            byte[] descBytes = new byte[is.available()];
            is.read(descBytes);
            System.out.println(new String(descBytes));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //3.关闭资源
                is.close();
            } catch (IOException e) {
            }
        }
    }
}
