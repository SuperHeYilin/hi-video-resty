package cn.diffpi.resource;

import cn.diffpi.kit.video.ELOUtil;
import cn.diffpi.kit.video.FileUtil;
import cn.diffpi.resource.file.FileResource;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author super
 * @date 2018/6/26 17:13
 */
public class Text {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map = ELOUtil.countRange(1300, 1551, 0);
        System.out.println(map.get("a"));
        System.out.println(map.get("b"));
    }

}
