package com.media.playerlib.model;

import com.kk.taurus.playerbase.assist.InterEvent;
import com.kk.taurus.playerbase.assist.InterKey;

/**
 * creator huangyong
 * createTime 2019/3/12 上午10:29
 * path com.huangyong.playerlib.data
 * description:
 */
public interface DataInter {

    interface Event extends InterEvent {

        int EVENT_CODE_REQUEST_BACK = -100;
        int EVENT_CODE_REQUEST_CLOSE = -101;

        int EVENT_CODE_REQUEST_TOGGLE_SCREEN = -104;

        int EVENT_CODE_ERROR_SHOW = -111;

        int EVENT_CODE_TO_FLOAT_MODE = 221;

        int KEY_UPDATE_NET_SPEED = 1211;

        int EVENT_CODE_SERI_NEXT = 212;

        int KEY_CHOSE_SPEED = 1011;

        int EVENT_CODE_TO_DLAN_CAST = 1001;
        int EVENT_CODE_DOUBLE_CLICK = 1002;
        int FULLSCREEN = 1003;
        int EVENT_CODE_LOAD_DANMU = 1004;
        int EVENT_CODE_SEND_DANMU = 1005;
        int EVENT_CODE_SHOW_DANMU_INPUT = 1006;
        int EVENT_CODE_DANMU_CONTENT_ADD = 1007;
        int EVENT_CODE_CLOSE_SMALL_WINDOW = 1008;

        int EVENT_CODE_SAVE_PROGRESS = 1013;
        int KEY_UPDATE_AD_VISIBLE = 1014;
        int EVENT_CODE_REQUEST_START = 1015;

        int KEY_SHOW_AD = 1016;
        int EVENT_CODE_TO_GET_VIP = 1097;
        int EVENT_CODE_TO_EXIT = 1098;
        int RESTART_PLAY = 1099;
    }

    interface Key extends InterKey {

        String KEY_IS_LANDSCAPE = "isLandscape";

        String KEY_DATA_SOURCE = "data_source";

        String KEY_ERROR_SHOW = "error_show";

        String KEY_COMPLETE_SHOW = "complete_show";
        String KEY_CONTROLLER_TOP_ENABLE = "controller_top_enable";
        String KEY_CONTROLLER_SCREEN_SWITCH_ENABLE = "screen_switch_enable";

        String KEY_TIMER_UPDATE_ENABLE = "timer_update_enable";

        String KEY_NETWORK_RESOURCE = "network_resource";


        String KEY_UPDATE_NET_SPEED_CONTENT = "event_net_speed";

        //全局保存视频基础数据
        String MOVIE_INFO = "online_movie_info";

        //播放下一集操作
        String KEY_CURRENTPLAY_URL = "online_play_key";

        //是否显示选集
        String KEY_CHOSE_SERI_ENABLE = "chose_seri_enable";

        //是否是窗口模式
        String KEY_WINDOW_MODE = "window_mode";

        //显示倍速提示
        String KEY_SPEED_UP = "speed_up_chose";

        String KEY_CURRENTPLAY_TITLE = "current_play_title";
        //当前播放进度
        String KEY_CURRENTPLAY_PROGRESS = "current_progress";
        //当前播放的索引
        String KEY_CURRENTPLAY_INDEX = "current_play_index";
        String KEY_GESTURE_ENABLE = "video_getsture";
        String KEY_DATA_TITLE = "video_title";
        String KEY_DANMU_DATA = "danmu_data";
        String KEY_SEND_A_DANMU = "send_a_danmu";
        String KEY_DANMU_ADD = "add_a_danmu";
        String PARSE_URL = "parse_url";
        String TYPE_ID_ARRAY = "type_id_array";
        String TYPE_ID = "type_id";
        String LEVEL_ID = "level_id";
        String TOPIC_ID = "topic_id";
        String VOD_ID = "vod_id";
        String KEY_SHOW_AD = "show_ad";
        String AUTH_CODE = "auth_code";
        String TAB_NAME = "tab_name";
        String AD_IMG = "ad_img_url";
        String AD_LINK = "ad_link_url";
    }

    interface ReceiverKey {
        String KEY_LOADING_COVER = "loading_cover";
        String KEY_CONTROLLER_COVER = "controller_cover";
        String KEY_GESTURE_COVER = "gesture_cover";
        String KEY_COMPLETE_COVER = "complete_cover";
        String KEY_ERROR_COVER = "error_cover";
        String KEY_AD_COVER = "ad_cover";
        String KEY_CLOSE_COVER = "close_cover";
        String KEY_THUMB_COVER = "thumb_cover";
        String KEY_SMALL_CONTROLLER_COVER = "small_controller";
        String KEY_DANMU_COVER = "danmu_cover";
        String KEY_AUTH_COVER = "auth_cover";
    }

    interface PrivateEvent {
        int EVENT_CODE_UPDATE_SEEK = -201;
    }

}
