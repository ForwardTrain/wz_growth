package com.school.wz_growth.web.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML数据提取工具类
 */
public class HtmlDataExtractor {

    /**
     * 从HTML中提取指定div中的barChart数据
     *
     * @param html  HTML内容
     * @param divId 目标div的id
     * @return 提取的JSON数组列表
     */
    public static List<JSONArray> extractBarChartData(String html, String divId) {
        List<JSONArray> resultList = new ArrayList<>();

        try {
            // 方法1: 直接使用正则表达式从HTML字符串中提取
            Pattern divPattern = Pattern.compile("<div[^>]*id=[\"']" + divId + "[\"'][^>]*>([\\s\\S]*?)</div>", Pattern.DOTALL);
            Matcher divMatcher = divPattern.matcher(html);

            if (divMatcher.find()) {
                String divContent = divMatcher.group(0);

                // 匹配barChart函数调用和JSON数组
                Pattern barChartPattern = Pattern.compile("barChart\\((\\[.*?\\])\\s*,\\s*\\{", Pattern.DOTALL);
                Matcher barChartMatcher = barChartPattern.matcher(divContent);

                while (barChartMatcher.find()) {
                    String jsonArrayStr = barChartMatcher.group(1);
                    try {
                        // 解析为JSONArray
                        JSONArray jsonArray = JSON.parseArray(jsonArrayStr);
                        resultList.add(jsonArray);
                    } catch (Exception e) {
                        System.err.println("解析JSON出错: " + e.getMessage());
                    }
                }
            }

            // 如果上面的方法没有找到结果，尝试使用DOM解析
            if (resultList.isEmpty()) {
                Document doc = Jsoup.parse(html);
                Element divElement = doc.getElementById(divId);

                if (divElement != null) {
                    // 尝试方法2: 查找整个文档中的script标签
                    Elements allScripts = doc.getElementsByTag("script");
                    for (Element script : allScripts) {
                        String scriptContent = script.html();
                        if (scriptContent.contains("('#" + divId + "')") ||
                                scriptContent.contains("(\"#" + divId + "\")")) {

                            Pattern pattern = Pattern.compile("barChart\\((\\[.*?\\])\\s*,\\s*\\{", Pattern.DOTALL);
                            Matcher matcher = pattern.matcher(scriptContent);

                            while (matcher.find()) {
                                String jsonArrayStr = matcher.group(1);
                                try {
                                    JSONArray jsonArray = JSON.parseArray(jsonArrayStr);
                                    resultList.add(jsonArray);
                                } catch (Exception e) {
                                    System.err.println("解析JSON出错: " + e.getMessage());
                                }
                            }
                        }
                    }

                    // 如果还是没有找到结果，尝试方法3: 从父节点查找
                    if (resultList.isEmpty()) {
                        Element parent = divElement.parent();
                        if (parent != null) {
                            Elements parentScripts = parent.getElementsByTag("script");
                            for (Element script : parentScripts) {
                                String scriptContent = script.html();
                                Pattern pattern = Pattern.compile("barChart\\((\\[.*?\\])\\s*,\\s*\\{", Pattern.DOTALL);
                                Matcher matcher = pattern.matcher(scriptContent);

                                while (matcher.find()) {
                                    String jsonArrayStr = matcher.group(1);
                                    try {
                                        JSONArray jsonArray = JSON.parseArray(jsonArrayStr);
                                        resultList.add(jsonArray);
                                    } catch (Exception e) {
                                        System.err.println("解析JSON出错: " + e.getMessage());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

    /**
     * 从URL获取HTML并提取barChart数据
     *
     * @param url   请求的URL
     * @param divId 目标div的id
     * @return 提取的JSON数组列表
     */
    public static List<JSONArray> fetchAndExtractBarChartData(String url, String divId) throws IOException {
        // 获取HTML内容
        Document doc = Jsoup.connect(url).get();
        String html = doc.outerHtml();

        // 提取数据
        return extractBarChartData(html, divId);
    }

    /**
     * 提取单个barChart数据（如果只需要第一个匹配项）
     *
     * @param url   请求的URL
     * @param divId 目标div的id
     * @return 第一个匹配的JSON数组，如果没有则返回null
     */
    public static JSONArray extractFirstBarChartData(String url, String divId) throws IOException {
        // 获取HTML内容
        Document doc = Jsoup.connect(url).get();
        String html = doc.outerHtml();
        List<JSONArray> dataList = extractBarChartData(html, divId);
        return dataList.isEmpty() ? null : dataList.get(0);
    }
} 