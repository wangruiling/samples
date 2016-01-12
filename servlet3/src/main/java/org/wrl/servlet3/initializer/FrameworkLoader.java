package org.wrl.servlet3.initializer;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * 框架实现 ServletContainerInitializer
 * – 应用程序启动时调用 onStartup()
 * – 负责初始化框架 — 如果有
 • 例如，注册控制器 servlet、连接到数据库等

 通过 @HandlesTypes 实现批注
 – 将 initializer 与它的批注、类或接口集相关联
 – 例如 @HandlesTypes({Fred.class})
 * @author: wangrl
 * @Date: 2016-01-12 23:10
 */
@HandlesTypes(MyWebFramework.class)
public class FrameworkLoader implements ServletContainerInitializer{
    @Override
    public void onStartup(Set<Class<?>> webAppInitializerClasses, ServletContext ctx) throws ServletException {
        System.out.println("MyFrameworkLoader ServletContainerInitializer init");
        System.out.println(webAppInitializerClasses);

        if (webAppInitializerClasses != null) {
            for(Class<?> clz : webAppInitializerClasses) {
                if(!(Modifier.isInterface(clz.getModifiers()) || Modifier.isAbstract(clz.getModifiers()))) {
                    //dynamicRegistrate(ctx, clz);

                    //Other processing
                }
            }
        }

    }

    /**
     * 动态配置
     * @param ctx
     * @param clz
     */
    private void dynamicRegistrate(ServletContext ctx, Class<?> clz) {
        //注册Servlet
        ServletRegistration.Dynamic servReg = ctx.addServlet(clz.getName(), (Class<? extends MyWebFramework>) clz);

        //Add mappings
        String pattern = "/" + clz.getSimpleName().substring(0, 1).toLowerCase() + clz.getSimpleName().substring(1);
        servReg.addMapping(pattern);
        System.out.println(pattern);
    }
}
