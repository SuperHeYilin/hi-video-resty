package cn.diffpi.resource.module.user.model;

import cn.diffpi.kit.DateUtil;
import cn.diffpi.kit.ExcelUtil;
import cn.diffpi.resource.BaseModel;
import cn.dreampie.common.http.exception.HttpException;
import cn.dreampie.common.http.result.HttpStatus;
import cn.dreampie.orm.annotation.Table;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Table(name="client_user_address")
public class UserAddress extends BaseModel<UserAddress> {
    public static final UserAddress dao = new UserAddress();

    /**
     * 批量上传
     * @param fileName
     * @param path
     * @return
     */
    public boolean upload(String fileName, String path) {
        // 服务器文件地址
        File file = new File(path + "\\" + fileName);
        ExcelUtil excelUtil = new ExcelUtil();
        // excel导出的数据
        List<Object[]> listExcel = new ArrayList<>();
        // 用于存放model对象
        List<UserAddress> addresses = new ArrayList<>();

        try {
            InputStream inputStream = new FileInputStream(file);
            listExcel = excelUtil.importDataFromExcel(inputStream, file.getName());
            System.out.println("文件个数: " + listExcel.size());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,"paramErr","文件导入失败");
        }

        for (Object[] o : listExcel) {
            UserAddress address = new UserAddress();
            address
                    .set("province",o[0])
                    .set("city",o[1])
                    .set("area",o[2])
                    .set("street",o[3])
                    .set("address",o[4])
                    .set("is_default", 0)
                    .set("create_type",1)
                    .set("create_time",DateUtil.getCurrentDate())
                    .set("is_del", 0);

            addresses.add(address);
        }

        // 批量保存
        UserAddress.dao.save(addresses);

        return true;
    }
}
