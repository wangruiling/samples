package org.wrl.servlet3.upload;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * 文件上传
 * 要使用request的getPart()或getParts()方法对上传的文件进行操作的话，有两个要注意的地方。首先，用于上传文件的form表单的enctype必须为multipart/form-data；其次，对于使用注解声明的Servlet，我们必须在其对应类上使用@MultipartConfig进行标注，而对于在web.xml文件进行配置的Servlet我们也需要指定其multipart-config属性
 * @author: wangrl
 * @Date: 2016-01-11 23:50
 */
@WebServlet("/upload")
@MultipartConfig(
        location = "D:/tmp", //即默认为 javax.servlet.context.tempdir 如mvn jetty:run 在chapter4\target\tmp中
        maxRequestSize = 1024 * 1024 * 2,   //指定一次请求最大的上传数据量（上传的总和） 单位：字节， -1表示不限制
        maxFileSize = 1024 * 1024 * 1, //设定单个文件的最大大小，-1表示不限制
        fileSizeThreshold = 1024 * 1024 * 10 //当上传的文件大小大于该值时才写入文件
)
public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        getReqParameter(req);

        System.out.println("\n\n==============file1");
        //只要调用 就会触发所有Part的上传
        getFilePart(req.getPart("file1"));
        System.out.println("\n\n==============file1");

        //如果多个 只获取第一个
        System.out.println("\n\n==========file2");
        getFilePart(req.getPart("file2"));
        System.out.println("\n\n==========file2");

        //只需通过request的getPart(String partName)获取到上传的对应文件对应的Part或者通过getParts()方法获取到所有上传文件对应的Part
        getPartInfo(req.getParts());
    }

    private void getPartInfo(Collection<Part> parts) throws IOException {
        System.out.println("\n\n=============all part");
        for (Part part : parts) {
            System.out.println("\n\n=========name:::" + part.getName());
            System.out.println("=========size:::" + part.getSize());
            System.out.println("=========content-type:::" + part.getContentType());
            //格式如：form-data; name="upload"; filename="YNote.exe"
            System.out.println("=========header content-disposition:::" + part.getHeader("content-disposition"));
            //servlet 3.1 可以直接调用getSubmittedFileName得到客户端提交时的文件名
            System.out.println("submitted file name:" + part.getSubmittedFileName());
            System.out.println("=========file name:::" + getFileName(part.getHeader("content-disposition")));
            InputStream partInputStream = part.getInputStream();
            System.out.println("=========value:::" + IOUtils.toString(partInputStream));

            //别忘了关闭
            partInputStream.close();
        }
    }

    private void getReqParameter(HttpServletRequest req) throws IOException, ServletException {
        //如果servlet容器还没有处理Part部分，那么返回null
        //9.0.4.v20130625 已经修复了这个问题
        System.out.println("name:" + req.getParameter("name"));

        System.out.println("\n\n==========parameter name");
        //此时可以通过如下的一种得到表单数据
        System.out.println(IOUtils.toString(req.getPart("name").getInputStream()));
        //或当Part已经解析后 jetty会自动添加到parameters中，可以直接获取
        System.out.println(req.getParameter("name"));

        //如果容器还是不能处理 只能 req.getInputStream(); 然后自己解析
    }

    private void getFilePart(Part filePart) {
        try {
            //调用完InputStream记得关闭，否则删除不掉
            InputStream file1PartInputStream = filePart.getInputStream();
            System.out.println(IOUtils.toString(file1PartInputStream));
            //之后我们就可以通过part的write(String fileName)方法把对应文件写入到磁盘。或者通过part的getInputStream()方法获取文件对应的输入流，然后再对该输入流进行操作
            //写到相对于 MultipartConfig中的location位置
            filePart.write("a.txt");
            file1PartInputStream.close();

            //删除相关的文件
            //filePart.delete();
        } catch (Exception ise) {
            //文件上传失败
            ise.printStackTrace();
            String errorMsg = ise.getMessage();
            if(errorMsg.contains("Request exceeds maxRequestSize")) {
                //所有上传的部分超出了整个上传大小
            } else if(errorMsg.contains("Multipart Mime part file1 exceeds max filesize")) {
                //某个文件 超出单个文件上传大小
            } else {
                //其他错误
            }
        }
    }

    //没有提供直接获取文件名的API，需要拿到content-disposition再解析
    //servlet 3.1 直接使用Part.getSubmittedFileName即可拿到客户端的文件名
    private String getFileName(String contentDisposition) {
        String fileName = contentDisposition.substring(contentDisposition.lastIndexOf("=")+2, contentDisposition.length()-1);
        System.out.println("fileName:" + fileName);

        if(StringUtils.isEmpty(contentDisposition)) {
            return null;
        }
        //如 form-data; name="file1"; filename="TODO.txt"
        return StringUtils.substringBetween(contentDisposition, "filename=\"", "\"");

    }
}
