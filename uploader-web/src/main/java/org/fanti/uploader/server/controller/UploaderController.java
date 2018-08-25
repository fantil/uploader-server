package org.fanti.uploader.server.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.fanti.uploader.server.controller.base.BaseController;
import org.fanti.uploader.server.dto.ResultDTO;
import org.fanti.uploader.server.service.upload.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    UploadService uploadService;

    @Value("${upload.folder}")
    private String uploadFolder;

    @ResponseBody
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public ResultDTO upload (HttpServletRequest request) {
        long start = System.currentTimeMillis();
        LOGGER.info("----------------- start upload -------------------");
        try {
            //1.得到解析器工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();

            //2.得到解析器
            ServletFileUpload upload = new ServletFileUpload(factory);

            //3.判断上传表单的类型
            if (!ServletFileUpload.isMultipartContent(request)) {
                //上传表单为普通表单，则按照传统方式获取数据即可
                return ajaxDoneSuccess();
            }

            List<FileItem> list = upload.parseRequest(request);
            uploadService.upload(list);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        LOGGER.info("time expired:{}ms", (System.currentTimeMillis() - start));
        LOGGER.info("----------------- end upload -------------------");

        return ajaxDoneSuccess();
    }


}
