package org.fanti.uploader.test;

import com.alibaba.fastjson.JSON;
import org.fanti.uploader.server.util.StringUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
//        String fullPath = "/fweffw/fwesf/fwesdc/wefawces/wes";
//        String fullPath = "\\fweffw\\fwesf\\fwesdc\\wefawces\\wes";
//        String fullPath = "\\fweffw";
//        String fullPath = "\\";
        String fullPath = "fwef";
        String dirName = fullPath.substring(fullPath.lastIndexOf(File.separator) + 1);

        LOGGER.info("dirName:{}", dirName);
    }

    @Test
    public void splitTest5() {
        String fullPath = "\\fweffw\\asd\\ghj\\bvnc\\wes";
//        String fullPath = "\\fweffw";
//        String currentPath = "\\";
        String currentPath = "\\fweffw";

        String[] dirNameArray = StringUtil.splitByFileSeparator(fullPath.substring(currentPath.length()));

        String dirName = "";
        if (dirNameArray.length == 1) {
            dirName = dirNameArray[0];
        } else {
            dirName = dirNameArray[1];
        }

        LOGGER.info("dirNameArray:{}", JSON.toJSONString(dirNameArray));
        LOGGER.info("dirName:{}", dirName);
    }

    @Test
    public void replaceTest() {
        String id = "/abc/123/456/txt";

        id = id.replace("/", "\\");
        LOGGER.info("id:{}", id);

    }

    @Test
    public void dateToStringTest() {
        long monthMillisecond = 30 * 86400 * 1000L;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - monthMillisecond);

        LOGGER.info("id:{}", System.currentTimeMillis());
        LOGGER.info("id:{}", System.currentTimeMillis() - monthMillisecond);
        LOGGER.info("id:{}", monthMillisecond);
        LOGGER.info("id:{}", timestamp.toString());
    }
}
