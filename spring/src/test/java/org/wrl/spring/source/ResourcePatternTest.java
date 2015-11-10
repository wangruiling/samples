package org.wrl.spring.source;

import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-11-10 13:38
 */
public class ResourcePatternTest {
    /**
     * “classpath”： 用于加载类路径（包括 jar 包）中的一个且仅一个资源；对于多个匹配的也只返回一个，所以如果需要多个匹配的请考虑“classpath*:”前缀
     * @throws IOException
     */
    @Test
    public void testClasspathPrefix() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        //只加载一个绝对匹配Resource，且通过ResourceLoader.getResource进行加载
        Resource[] resources = resolver.getResources("classpath:META-INF/INDEX.LIST");
        assertThat(resources.length).isEqualTo(1);

        //只加载一个匹配的Resource，且通过ResourceLoader.getResource进行加载
        resources = resolver.getResources("classpath:META-INF/*.LIST");
        System.out.println(resources.length);
        assertThat(resources.length == 0).isTrue();


        //只加载一个绝对匹配Resource，且通过ResourceLoader.getResource进行加载
        resources = resolver.getResources("classpath:META-INF/MANIFEST.MF");
        assertThat(resources.length).isEqualTo(1);
        System.out.println(resources[0].getURL().getPath());
    }

    /**
     * “classpath*”： 用于加载类路径（包括 jar 包）中的所有匹配的资源。带通配符的 classpath 使用“ClassLoader”的“Enumeration<URL> getResources(String name)”方法来查找通配符之前的资源，
     * 然后通过模式匹配来获取匹配的资源。如 “classpath:META-INF/*.LIST”将首先加载通配符之前的目录“META-INF”，然后再 遍历路径进行子路径匹配从而获取匹配的资源。
     * @throws IOException
     */
    @Test
    public void testClasspathAsteriskPrefix() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        //将加载多个绝对匹配的所有Resource
        //将首先通过ClassLoader.getResources("META-INF")加载非模式路径部分
        //然后进行遍历模式匹配
        Resource[] resources = resolver.getResources("classpath*:META-INF/INDEX.LIST");
        assertThat(resources.length > 1).isTrue();

        //将加载多个模式匹配的Resource
        resources = resolver.getResources("classpath*:META-INF/*.LIST");
        assertThat(resources.length > 1).isTrue();


    }

    @Test
    public void testClasspathAsteriskPrefixLimit() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        //将首先通过ClassLoader.getResources("")加载目录，
        //将只返回文件系统的类路径不返回jar的跟路径
        //然后进行遍历模式匹配
        Resource[] resources = resolver.getResources("classpath*:asm-*.txt");
        assertThat(resources.length == 0).isTrue();

        //将通过ClassLoader.getResources("asm-license.txt")加载
        //asm-license.txt存在于com.springsource.net.sf.cglib-2.2.0.jar
        resources = resolver.getResources("classpath*:asm-license.txt");
        assertThat(resources.length > 0).isTrue();

        //将只加载文件系统类路径匹配的Resource
        resources = resolver.getResources("classpath*:LICENS*");
        assertThat(resources.length == 1).isTrue();
    }

    @Test
    public void testFilekPrefix() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("file:E:/*.txt");
        assertThat(resources.length > 0).isTrue();
    }

    @Test
    public void testVfsPrefix() throws IOException {
//        //1.创建一个虚拟的文件目录
//        VirtualFile home = VFS.getChild("/home");
//        //2.将虚拟目录映射到物理的目录
//        VFS.mount(home, new RealFileSystem(new File("d:")));
//        //3.通过虚拟目录获取文件资源
//        VirtualFile testFile = home.getChild("test.txt");
//
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource[] resources = resolver.getResources("/home/test.txt");
//        assertThat(resources.length > 0);
//        System.out.println(resources[0].getClass());

    }
}
