# talentmov-master
## 追剧达人APK更新教程

前后端都需要配置 后端就是网站部分 路径 app/cms/src/app/Api
后台的api接口在我个人的网站中，有需要的可以到网站下载。
[也可以在github获取地址](https://github.com/devilsafe/jdziapp_appcms)


Mov.php 这个文件内 修改
```
public function checkUpdate(){
    $update = array(
            "versionCode" => 101,
            "updateMsg" =>"1.优化投屏播放及交互，欢迎体验。\n",
            "downloadUrl" =>"http://123.207.150.253/apk/BTMovie_1.6.3.apk",
            "isForce" => true,
            "version" =>"1.6.3");
    return $update;
}



"versionCode" => 101,   这个101就是新的APK内的版本号 比如没升级的apk是100 
如果你要升级就要先在这个地方写101

"version" =>"1.6.3"  这个就是前台显示的版本号 可以随意

"downloadUrl" =>"http://123.207.150.253/apk/BTMovie_1.6.3.apk",这个是你新编译的APK 的直连下载地址 什么网盘啥的没用
```

新的APK内一定要填写要升级到的版本号我apk内填写的是101跟1.0.1  修改这两个位置
``` 
也可以搜索 
public static final int VERSION_CODE =

```

都配置好老版本的就会提示升级了顺序也就是先制作新版APK 然后配置后台 后台一旦配置了 老版本就会立马提示更新了


