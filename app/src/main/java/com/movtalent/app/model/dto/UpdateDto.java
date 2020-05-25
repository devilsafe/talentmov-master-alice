package com.movtalent.app.model.dto;

/**
 * @author huangyong
 * createTime 2019-10-09
 */
public class UpdateDto {

    /**
     * Code : 200
     * Data : {"versionCode":999999999,"updateMsg":"近日发现极光影院app被人私自篡改，同时将版本号改为9.9.9，改名绿化版、去广告版等版本后上传各个网站、QQ群，请安装该版本用户注意，极光影院app本就是免费无广告app，9.9.9版本为盗版版本，已无法正常更新，很多功能无法正常使用，务必尽快卸载.\n\n请添加qq群 682499902 获取官方正版版本，以获得稳定、安全的观影体验。\n同时请知悉，极光影院app为完全免费应用，纯净无广告，不会添加任何和电影无关的信息，为的就是提供极致的观影体验。\n极光影院app未曾上架任何应用商店和市场、网站，只在qq群和公众号发布更新，请大家擦亮眼睛，盗版app极不安全，会有很多无可预知的安全风险，务必远离。\n\n1.6.3版本已发布，优化投屏播放及交互，欢迎体验。\n","downloadUrl":"http://123.207.150.253/apk/BTMovie_1.6.3.apk","isForce":true,"version":"1.6.3"}
     * Msg :
     */

    private int Code;
    private DataBean Data;
    private String Msg;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public static class DataBean {
        /**
         * versionCode : 999999999
         * updateMsg : 近日发现极光影院app被人私自篡改，同时将版本号改为9.9.9，改名绿化版、去广告版等版本后上传各个网站、QQ群，请安装该版本用户注意，极光影院app本就是免费无广告app，9.9.9版本为盗版版本，已无法正常更新，很多功能无法正常使用，务必尽快卸载.

         请添加qq群 682499902 获取官方正版版本，以获得稳定、安全的观影体验。
         同时请知悉，极光影院app为完全免费应用，纯净无广告，不会添加任何和电影无关的信息，为的就是提供极致的观影体验。
         极光影院app未曾上架任何应用商店和市场、网站，只在qq群和公众号发布更新，请大家擦亮眼睛，盗版app极不安全，会有很多无可预知的安全风险，务必远离。

         1.6.3版本已发布，优化投屏播放及交互，欢迎体验。
         * downloadUrl : http://123.207.150.253/apk/BTMovie_1.6.3.apk
         * isForce : true
         * version : 1.6.3
         */

        private int versionCode;
        private String updateMsg;
        private String downloadUrl;
        private boolean isForce;
        private String version;

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getUpdateMsg() {
            return updateMsg;
        }

        public void setUpdateMsg(String updateMsg) {
            this.updateMsg = updateMsg;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public boolean isIsForce() {
            return isForce;
        }

        public void setIsForce(boolean isForce) {
            this.isForce = isForce;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
