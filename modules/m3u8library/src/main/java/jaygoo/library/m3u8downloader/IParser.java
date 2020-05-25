package jaygoo.library.m3u8downloader;

import jaygoo.library.m3u8downloader.bean.M3U8;

/**
 * @author huangyong
 * createTime 2019-09-26
 */
public interface IParser {

    M3U8 getParseResult(String url);
}
