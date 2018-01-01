package com.Marissa.FAQ.utils;

import com.Marissa.FAQ.controller.vo.Params;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Pattern;

public class CommonUtils {

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    public static String HOST_DOMAIN = "http://127.0.0.1:8080/";
    public static String IMAGE_DIR = "test"+ File.separator;
    public static String[] IMAGE_FILE_EXT = new String[]{"pdf"};

    public static boolean isFileAllowed(String fileExt){
        for(String ext : IMAGE_FILE_EXT){
            if(ext.equals(fileExt)){
                return true;
            }
        }
        return false;
    }

   /* public static void download(String fileName,String filePath,
                                HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //声明本次下载状态的记录对象
        DownloadRecord downloadRecord = new DownloadRecord(fileName, filePath, request);
        //设置响应头和客户端保存文件名
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        //用于记录以完成的下载的数据量，单位是byte
        long downloadedLength = 0l;
        try {
            //打开本地文件流
            InputStream inputStream = new FileInputStream(filePath);
            //激活下载操作
            OutputStream os = response.getOutputStream();

            //循环写入输出流
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
                downloadedLength += b.length;
            }

            // 这里主要关闭。
            os.close();
            inputStream.close();
        } catch (Exception e){
            downloadRecord.setStatus(DownloadRecord.STATUS_ERROR);
            throw e;
        }
        downloadRecord.setStatus(DownloadRecord.STATUS_SUCCESS);
        downloadRecord.setEndTime(new Timestamp(System.currentTimeMillis()));
        downloadRecord.setLength(downloadedLength);
        //存储记录
    }
*/

    public static Params transformToParams(String params){
        Params params1 = null;
        try{
            params1 = objectMapper.readValue(params, Params.class);
        } catch (IOException e){
            logger.error("参数字符串转换出错！");
        }
        return params1;
    }

    public static String transformToString(Object obj){
        String str = "";
        try{
            str = objectMapper.writeValueAsString(obj);
        } catch (IOException e){
            logger.error("对象转换字符串出错！");
        }
        return str;
    }
    public static String getJSONString(int code){
        String str = "";
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",code);

        try{
            str = objectMapper.writeValueAsString(map);
        } catch (Exception e){
            logger.error("Object转换字符串失败！");
        }
        return str;
    }

    public static String getJSONString(int code, String msg){
        String str = "";
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",code);
        map.put("msg",msg);

        try{
            str = objectMapper.writeValueAsString(map);
        } catch (Exception e){
            logger.error("Object转换字符串失败！");
        }
        return str;
    }

    public static String getJSONString(int code, Map<String, Object> params){
        String str = "";
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",code);
        for(Map.Entry<String, Object> entry : params.entrySet()){
            map.put(entry.getKey(), entry.getValue());
        }
        try{
            str = objectMapper.writeValueAsString(map);
        } catch (Exception e){
            logger.error("Object转换字符串失败！");
        }
        return str;
    }

    public static String shaEncode(String str){
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
//            encdeStr = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encdeStr;
    }

    /**
     * 测试主函数
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        String str = new String("123456");
        System.out.println("原始：" + str);
        System.out.println("SHA后：" + shaEncode(str));
    }

    private static final Pattern IPV4_PATTERN =
            Pattern.compile(
                    "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    private static final Pattern PORT_PATTERN =
            Pattern.compile(
                    "/^[1-9]$|(^[1-9][0-9]$)|(^[1-9][0-9][0-9]$)|(^[1-9][0-9][0-9][0-9]$)|(^[1-6][0-5][0-5][0-3][0-5]$)/");
    private static final Pattern IPV6_STD_PATTERN =
            Pattern.compile(
                    "^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");

    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN =
            Pattern.compile(
                    "^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    private static boolean isIPv6StdAddress(final String input) {
        return IPV6_STD_PATTERN.matcher(input).matches();
    }

    private static boolean isIPv6HexCompressedAddress(final String input) {
        return IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv4Address(final String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6Address(final String input) {
        return isIPv6StdAddress(input) || isIPv6HexCompressedAddress(input);
    }

    public static boolean isPort(final String input) {
        return PORT_PATTERN.matcher(input).matches();
    }

    public static boolean isNotNullOrEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean listNotEmpty(Collection<?> list) {
        return null != list && list.size() > 0;
    }

    public static boolean listNotEmpty(Object[] list) {
        return null != list && list.length > 0;
    }

    public static boolean numValidate(Integer num) {
        return num != null && num.intValue() > 0;
    }

    public static boolean numValidate(Long num) {
        return num != null && num.longValue() > 0;
    }

    public static List<Integer> convertToList(int[] ids) {
        List<Integer> list = new ArrayList<Integer>();
        for(int id : ids){
            list.add(id);
        }
        return list;
    }

    public static List<Integer> convertToList(int id) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(id);
        return list;
    }
    public static List<Long> convertToList(Long id) {
        List<Long> list = new ArrayList<Long>();
        list.add(id);
        return list;
    }
    public static List<String> convertToList(String name) {
        List<String> list = new ArrayList<String>();
        list.add(name);
        return list;
    }

    public static List<String> convertToList(String[] names) {
        List<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList(names));
        return list;
    }

}