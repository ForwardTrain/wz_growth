package com.school.wz_growth.service.Impl;


import com.alibaba.fastjson.JSONObject;

import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.Code;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.common.validator.StringUnits;
import com.school.wz_growth.dao.SysBasicToolMapper;
import com.school.wz_growth.model.front.request.sys.SysBasicChinaCodeRequestVo;
import com.school.wz_growth.service.SysBasicToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysBasicToolServiceImpl implements SysBasicToolService {

    @Autowired
    private SysBasicToolMapper sysBasicToolMapper;

    @Override
    public SingleResult<DataResponse> queryChinaCode(SysBasicChinaCodeRequestVo requestVo) throws ServiceException {
        try {
            Map<String,Object> params = new HashMap<>();
            Object code = params.put("code", StringUnits.isNotNullOrEmpty(requestVo.getCode()) ? requestVo.getCode() : "33");
            params.put("name", requestVo.getName());

            Map<String,Object> res_map = new HashMap<>();
            List< Map<String,Object>> res_list = sysBasicToolMapper.china_node_code(params);
            res_map.put("total",res_list.size());
            res_map.put("list",res_list);

            return SingleResult.buildSuccess(res_map);
        } catch (Throwable t) {
            t.printStackTrace();
            return SingleResult.buildFailure(Code.ERROR, t.getMessage());
        }
    }


    public static void main(String[] args) {
        // 文件路径
        String inputFilePath = "/Users/xxm/Desktop/温州医科大学—生长因子数据库/similar.txt";

        String outputFilePath = "/Users/xxm/Desktop/lipolytic_enzymes.sql"; // 输出 SQL 文件路径
//        // 输入文件路径
//        String inputFilePath = "similar.txt";
//        // 输出 SQL 文件路径
//        String outputFilePath = "lipolytic_enzymes.sql";

        try {
            // 读取输入文件的所有行
            List<String> lines = Files.readAllLines(Paths.get(inputFilePath));
            StringBuilder sqlBuilder = new StringBuilder();

            // 创建表的 SQL 语句
//            sqlBuilder.append("-- 创建 lipolytic_enzymes 表\n");
//            sqlBuilder.append("CREATE TABLE IF NOT EXISTS lipolytic_enzymes (\n" +
//                    "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
//                    "    enzyme_family VARCHAR(255) NOT NULL,\n" +
//                    "    enzyme_name VARCHAR(255) NOT NULL,\n" +
//                    "    enzyme_code VARCHAR(255) NOT NULL\n" +
//                    ");\n\n");

            // 解析数据并生成插入语句
            String currentFamily = null; // 当前酶家族名称
            for (int i = 0; i < lines.size(); i++)
            {
                System.out.println(i);
                String line = lines.get(i);
                line = line.trim(); // 去除首尾空格
                if (line.isEmpty())
                {
                    currentFamily = null;
                    continue; // 跳过空行
                }

                if (currentFamily == null)
                {
                    // 如果是酶家族名称行，提取家族名称
                    currentFamily = line;//line.replaceAll("'", "").trim();
                    continue;
                }

                // 如果是酶数据行，解析酶名称和代码
                String[] entries = line.split(","); // 按逗号分隔
                for (String entry : entries)
                {
                    entry = entry.trim(); // 去除首尾空格
                    if (!entry.isEmpty()) {
                        // 提取酶名称和代码
                        String enzymeName = entry.split("\\s+")[0].trim();
                        String enzymeCode = entry.substring(entry.indexOf("(") + 1, entry.indexOf(")")).trim();

                        // 生成 INSERT INTO 语句
                        sqlBuilder.append("INSERT INTO lipolytic_enzymes (enzyme_family, enzyme_name, enzyme_code) VALUES\n" +
                                        "('").append(currentFamily).append("', '")
                                .append(enzymeName).append("', '")
                                .append(enzymeCode).append("');\n");
                    }
                }
            }

            // 将生成的 SQL 内容写入输出文件
            Files.write(Paths.get(outputFilePath), sqlBuilder.toString().getBytes());
            System.out.println("SQL 文件已成功生成: " + outputFilePath);

        } catch (IOException e) {
            System.err.println("处理文件时出错: " + e.getMessage());
        }
    }

    // 处理每个分段的内容
    private static void processSegment(String str) {
        System.out.println("=== Segment Start ===");
        System.out.println(); // 将字节转换为字符串
        System.out.println("=== Segment End ===\n");
    }

}
