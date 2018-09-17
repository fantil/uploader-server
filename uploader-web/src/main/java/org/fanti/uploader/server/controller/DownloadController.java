package org.fanti.uploader.server.controller;

import com.alibaba.fastjson.JSON;
import org.fanti.uploader.server.bean.FileInfo;
import org.fanti.uploader.server.constant.BasicConstants;
import org.fanti.uploader.server.constant.ControllerConstants;
import org.fanti.uploader.server.controller.base.BaseController;
import org.fanti.uploader.server.db.DBFile;
import org.fanti.uploader.server.dto.FileDTO;
import org.fanti.uploader.server.dto.ResultDTO;
import org.fanti.uploader.server.service.download.DownloadService;
import org.fanti.uploader.server.service.upload.UploadService;
import org.fanti.uploader.server.util.DirUtil;
import org.fanti.uploader.server.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/8/25
 */

@Controller
@RequestMapping("/download")
public class DownloadController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadController.class);

    @Autowired
    DownloadService downloadService;

    @Value("${upload.folder}")
    private String uploadFolder;

    @ResponseBody
    @RequestMapping(value = "/file")
    public ResultDTO download (HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(value = "fileName", required = true) String fileName) throws IOException {
        if (StringUtil.isNullString(fileName)) {
            return ajaxDoneFail("param error");
        }

        LOGGER.info("filename:{}", fileName);
        File file = new File(uploadFolder + File.separator + fileName);

//        response.setContentType("text/html; charset=UTF-8");
//        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename="
                + URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("Content-Length", file.length() + "");
        FileInputStream fis = new FileInputStream(file);
        OutputStream os = response.getOutputStream();
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = fis.read(b)) != -1) {
            os.write(b, 0, len);
        }
        os.flush();
        os.close();
        fis.close();

        return ajaxDoneSuccess();
    }

    @ResponseBody
    @RequestMapping(value = "/listFiles")
    public ResultDTO listFiles (String currentDir) {
        currentDir = DirUtil.replaceFileSeparator(currentDir);
        List<DBFile> dbFileList = downloadService.listFiles(currentDir);
        List<FileDTO> fileList = new ArrayList<>();

        if (dbFileList == null || dbFileList.size() == 0) {
            return ajaxDoneSuccess(fileList);
        }

        for (DBFile dbFile : dbFileList) {
            FileDTO fileDTO = new FileDTO();
            fileDTO.setId(dbFile.getId() + "");
            fileDTO.setType(BasicConstants.FILE_TYPE_IS_FILE);
            fileDTO.setName(dbFile.getName());
            fileDTO.setPath(dbFile.getRealPath());

            fileList.add(fileDTO);
        }

        return ajaxDoneSuccess(fileList);
    }
}
