package org.fanti.uploader.server.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.fanti.uploader.server.controller.base.BaseController;
import org.fanti.uploader.server.dto.ResultDTO;
import org.fanti.uploader.server.util.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/8/25
 */

@Controller
@RequestMapping("/")
public class UploaderController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploaderController.class);

    @Value("${upload.folder}")
    private String uploadFolder;


    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        model.addAttribute("message", "Hello world!");
        return "hello";
    }

    @ResponseBody
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public ResultDTO upload (HttpServletRequest request, HttpServletResponse response) {
        if ("a".equals("b")) {
            LOGGER.info("request:{}", "no");
        }

        try {
            //1.得到解析器工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();

            //2.得到解析器
            ServletFileUpload upload = new ServletFileUpload(factory);

            //3.判断上传表单的类型
            if (!ServletFileUpload.isMultipartContent (request)) {
                //上传表单为普通表单，则按照传统方式获取数据即可
                return ajaxDoneSuccess();
            }

            //为上传表单，则调用解析器解析上传数据
            List<FileItem> list = upload.parseRequest(request);  //FileItem

            //遍历list，得到用于封装第一个上传输入项数据fileItem对象
            for (FileItem item : list) {

                if (item.isFormField()) {
                    //得到的是普通输入项
                    String fieldName = item.getFieldName();  //得到输入项的名称
                    String value = item.getString();
                    LOGGER.info("field:{}, value:{}" , fieldName, value);
                } else {
                    //得到上传输入项
                    String filename = item.getName();  //得到上传文件名  C:\Documents and Settings\ThinkPad\桌面\1.txt
                    filename = filename.substring(filename.lastIndexOf("\\") + 1 );
                    InputStream in = item.getInputStream();   //得到上传数据
                    int len = 0;
                    byte[] buffer = new byte[1024];


                    String savePath = this.uploadFolder;
                    File file = new File(savePath + "\\" + filename);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileOutputStream out = new FileOutputStream(file);  //向upload目录中写入文件
                    while ((len = in.read(buffer)) > 0 ) {
                        out.write(buffer, 0, len);
                    }

                    in.close();
                    out.close();
                }
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return ajaxDoneSuccess();
    }


}
