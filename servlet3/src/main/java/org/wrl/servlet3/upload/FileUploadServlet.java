package org.wrl.servlet3.upload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

/**
 * 文件上传
 * 要使用request的getPart()或getParts()方法对上传的文件进行操作的话，有两个要注意的地方。首先，用于上传文件的form表单的enctype必须为multipart/form-data；其次，对于使用注解声明的Servlet，我们必须在其对应类上使用@MultipartConfig进行标注，而对于在web.xml文件进行配置的Servlet我们也需要指定其multipart-config属性
 * @author: wangrl
 * @Date: 2016-01-11 23:50
 */
@WebServlet("/servlet/upload")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //只需通过request的getPart(String partName)获取到上传的对应文件对应的Part或者通过getParts()方法获取到所有上传文件对应的Part
        Part part = req.getPart("upload");

        //格式如：form-data; name="upload"; filename="YNote.exe"
        String disposition = part.getHeader("content-disposition");
        System.out.println(disposition);
        String fileName = disposition.substring(disposition.lastIndexOf("=")+2, disposition.length()-1);
        String fileType = part.getContentType();
        long fileSize = part.getSize();
        System.out.println("fileName: " + fileName);
        System.out.println("fileType: " + fileType);
        System.out.println("fileSize: " + fileSize);
        String uploadPath = req.getServletContext().getRealPath("/upload");
        System.out.println("uploadPath" + uploadPath);

        //之后我们就可以通过part的write(String fileName)方法把对应文件写入到磁盘。或者通过part的getInputStream()方法获取文件对应的输入流，然后再对该输入流进行操作
        part.write(uploadPath + File.separator +fileName);
    }
}
