package org.fanti.uploader.test;

import com.alibaba.fastjson.JSON;
import org.fanti.uploader.server.util.StringUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/9/15
 */

public class StringTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringTest.class);

    @Test
    public void substringTest() {
        String id = "dsfasfdadskfxhsdljvlesadlls";

        String result = id.substring(0,2);

        LOGGER.info("result:{}", result);
    }

    @Test
    public void splitTest() {
//        String id = "abc.123.456.txt";
        String id = "\\";

        String[] info = id.split("\\\\");
        LOGGER.info("info:{}", JSON.toJSONString(info));
        LOGGER.info("File.separator:{}", File.separator);

//        String result = info[info.length - 1];

//        LOGGER.info("result:{}", result);
    }

    @Test
    public void splitTest2() {
        String id = "/abc/123/456/txt";
//        String id = File.separator;

        String[] info = id.split("/");
        LOGGER.info("info:{}", JSON.toJSONString(info));
    }


    @Test
    public void splitTest3() {
        String id = "/abc/123/456/txt";

        String[] info = id.split("/");

        LOGGER.info("info:{}", JSON.toJSONString(info));

        List<String> pathList = new ArrayList<>();

        int index = id.lastIndexOf("/");
        LOGGER.info("index:{}", index);

        String value = id.substring(index);
        LOGGER.info("value:{}", value);
    }


    @Test
    public void splitTest4() {
//        String id = "/abc/123/456/txt";
//        String fullId = "/abc/123/456/txt/fsdf/gsrdg/gsrdgf";
//
//        String info = fullId.split(id)[1].split("/")[1];
//
//        LOGGER.info("info:{}", JSON.toJSONString(info));
//        LOGGER.info("info:{}", JSON.toJSONString(fullId.split(id)[1]));
//        LOGGER.info("info:{}", JSON.toJSONString(fullId.split(id)[1].split("/")));

        String fullPath = "\\ttt";
        String currentPath = "\\";
//        String dirName = StringUtil.splitByFileSeparator(fullPath.split(currentPath)[1])[1];

        LOGGER.info("info:{}", JSON.toJSONString(fullPath.substring(currentPath.length())));
//        LOGGER.info("info:{}", JSON.toJSONString(fullPath.substring(currentPath.length())[0]));
        LOGGER.info("info:{}", JSON.toJSONString(StringUtil.splitByFileSeparator(fullPath.substring(currentPath.length()))));
//        LOGGER.info("info:{}", JSON.toJSONString(StringUtil.splitByFileSeparator(fullPath.split(currentPath)[1])[1]));
    }

    @Test
    public void replaceTest() {
        String id = "/abc/123/456/txt";

        id = id.replace("/", "\\");
        LOGGER.info("id:{}", id);

    }
}
