package com.thesis.constant;



    /**
     * @author hty
     * @date 2023-11-23 16:39
     * @email 1156388927@qq.com
     * @description 目前项目中可调用的文心的模型以及对应的url
     */

    public enum XuanYuanModel  {

        //TODO:当前增加了模型但是并没有对这些模型适配对应的处理方法
        XuanYuan_4bit("XuanYuan4bit","https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/xuanyuan_70b_chat");



        private final String name;
        private final String url;

        private XuanYuanModel(String name,String url){
            this.name = name;
            this.url = url;
        }

        public static String getUrl(XuanYuanModel xuanYuanModel){
            return xuanYuanModel.url;
        }
    }


