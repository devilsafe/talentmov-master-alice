package jaygoo.library.m3u8downloader;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-26
 * 解析器接口，外部可动态配置，添加足够多的解析器，有助于提高解析成功率
 */
public interface IParserGroup {
    ArrayList<IParser> getParseModel();
}
