package com.modules.demo2.test.nio.netty.primary.list;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListTest {


    public static void main(String[] args){
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        list.add("a");
        list.add("b");
        list2.add("A");
        list2.add("B");

        list.addAll(list2);
        System.out.println(list.toString());

        MonitorVideoFile monitorVideoFile = new MonitorVideoFile();
        monitorVideoFile.setFileName("file1");
        monitorVideoFile.setFileUrl("www.baidu.com");
        List<MonitorVideoFile> filesJsonDataList = new ArrayList<>();
        filesJsonDataList.add(monitorVideoFile);
        String filesJsonDataListStr = "[{\"fileName\":\"sqlResult_2026150的副本 2.mp4\",\"fileUrl\":\"http://file.bqj.cn/file/2037f4a69a6a49ccd6bdfde94116af18.mp4\"},{\"fileName\":\"sqlResult_2026150的副本 2.mp4\",\"fileUrl\":\"http://anne-bqj-file.oss-cn-beijing.aliyuncs.com/file/2037f4a69a6a49ccd6bdfde94116af18.mp4\"}]";
        List<MonitorVideoFile> filesJsonDataList2 = JSON.parseArray(filesJsonDataListStr, MonitorVideoFile.class);

        filesJsonDataList.addAll(filesJsonDataList2);

        String s = JSON.toJSONString(filesJsonDataList);
        System.out.println(s);
        List<MonitorVideoFile> videoFiles = JSON.parseArray(s, MonitorVideoFile.class);


        String str = "{\"orderSeqNo\":\"TBM201903191841457344\",\"taskName\":\"sdf\",\"monitorType\":\"3\",\"monitorStatus\":\"2\",\"monitorCycle\":1,\"fileNum\":\"\",\"infringementNum\":0,\"taskBegin\":1552992106000,\"payAmount\":\"\",\"payWay\":\"\",\"payStatus\":\"unpay\",\"payDate\":\"\",\"tranSerialsNo\":\"\",\"beanAmount\":\"\",\"isUseHongbao\":\"\",\"hongbaoAmount\":\"\",\"source\":\"\",\"registerId\":\"1302420\",\"taskStartTime\":\"\",\"taskEndTime\":\"\",\"remarks\":\"saf\",\"contentSource\":\"2\",\"refOrderSeqNo\":\"\",\"monitorVideo\":{\"orderSeqNo\":\"TBM201903191841457344\",\"taskName\":\"sdf\",\"title\":\"sf\",\"fileNum\":\"\",\"monitorCycle\":1,\"monitorStatus\":\"2\",\"infringementMainNum\":\"\",\"infringementNum\":\"\",\"beInfringementNum\":\"\",\"taskBegin\":1552992106000,\"contentSource\":\"2\",\"refOrderSeqNo\":\"YZ201901161043097072\",\"registerId\":\"1302420\"},\"monitorContentVideos\":[{\"id\":\"27\",\"isNewRecord\":false,\"remarks\":\"saf\",\"createByUserId\":\"\",\"updateByUserId\":\"\",\"createDate\":\"2019-03-19 18:41:46\",\"updateDate\":\"2019-03-19 18:41:46\",\"delFlag\":\"0\",\"orderSeqNo\":\"TBM201903191841457344\",\"fileName\":\"E92DB0B89DCFAFC7EF6F5660D5EDA292.avi\",\"fileUrl\":\"http://file.bqj.cn/file/e92db0b89dcfafc7ef6f5660d5eda292.avi\",\"registerId\":\"1302420\",\"beInfringementNum\":\"\",\"contentSource\":\"2\"},{\"id\":\"28\",\"isNewRecord\":false,\"remarks\":\"saf\",\"createByUserId\":\"\",\"updateByUserId\":\"\",\"createDate\":\"2019-03-19 18:41:46\",\"updateDate\":\"2019-03-19 18:41:46\",\"delFlag\":\"0\",\"orderSeqNo\":\"TBM201903191841457344\",\"fileName\":\"1.mp4\",\"fileUrl\":\"https://anne-bqj-file.oss-cn-beijing.aliyuncs.com/file/bc3f6c89cfe04e0c0641fe4037f16490.mp4\",\"registerId\":\"1302420\",\"beInfringementNum\":\"\",\"contentSource\":\"2\"}]}";
        MonitorVideoInfoVO vo = JSON.parseObject(str, MonitorVideoInfoVO.class);

        System.out.println(JSON.toJSONString(vo.getMonitorContentVideos().get(0)));

        List<String> sl = Arrays.asList("123,".split(","));
        System.out.println(sl.size());
        for(String one:sl){
            System.out.println(one);
        }
    }

    static class MonitorVideoFile {
        private String fileName; //标题
        private String fileUrl; //文件地址

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }
    }


}
