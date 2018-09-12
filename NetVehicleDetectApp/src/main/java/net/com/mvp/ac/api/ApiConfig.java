package net.com.mvp.ac.api;

/**
 * @Title: ApiConfig.java
 * @Description: 接口api
 */

public interface ApiConfig {

    String BASE_URL_front = "http://";

    //获取所有用户
    String GETALLUSERS = "/api/User/AllUser";

    //用户登录
    String USERLOGIN = "/api/User/Login";

    //获取车辆列表1
    String CARS_LIST_FRONT = "/api/DetectionQueue/";

    //获取车辆列表2
    String CARS_LIST_BEHIND = "/QueueStatus";

    //flag:917  项目检测中途退出的情况
    int ITEM_MIDDLE_EXIT_CODE = 99;

    //获取车辆列表status
    String CARS_LIST_STATUS = "0";

    //获取待检车辆列表
//    /api/DetectionQueue/0/QueueType/0/QueueStatus
//    最后加一个参数，是否只读取当天数据  0读取所有， 1读取当天
    String CARS_LIST_DAI_JIAN = "/api/DetectionQueue/0/QueueType/0/QueueStatus/0/Today";
//    String CARS_LIST_DAI_JIAN = "/api/DetectionQueue/0/QueueType/0/QueueStatus";

    //上传照片
    String UPLOAD_PHOTO = "/api/Image/PDA";

//    http://192.168.1.188:8089/api/FormItem/OutItem/1/1/2

    //外检不合格项目分类列表
    String BUHEGE_XIANGMU_LIST = "/api/FormItem/OutItem/";


    //    http://localhost:53964/api/FormItem/PhotoType
    //查询照片类型
    String PHOTO_TYPE_WAI_JIAN = "/api/FormItem/PhotoType";

    //项目开始
    String ITEM_START = "/api/Platform/ItemStart";

    //项目结束
    String ITEM_END = "/api/Platform/ItemEnd";

    //平台的
    String PLAT_FORM_DATA = "/api/Platform/Data";

    //外检上报不合格数据
    String REPORTED_DATA = "/api/DetectionData";

    // 获取所有人工检验项目接口
    String GET_ALL_ITEM = "/api/FormItem/AllManualItem";

    //获取需要检测的人工检验项目接口
    String GET_MY_ITEM = "/api/DetectionItem/ManualItem";

    //获取对应车辆要检测的项目
    String CAR_ITEM_CHECK = "/api/DetectionItem/0/QueueType/";

    // 视频接口
    String VIDEO_ITEM = "/api/Platform/Video";

    // 获取服务器时间
    String GET_SERVICE_TIME = "/api/Platform/Video";

    /**
     * 更新车辆状态接口
     * <p>
     * 参数：Type 0
     * DetectionID 队列id
     * //三个参数传一个对应的  0：待检测 1：上线了 2： 合格 3 不合格
     * DynamicStatus //底盘动态
     * AppearanceStatus//外检
     * RoadStatus//路试
     **/
    String UPDATE_CAR_STATUS = "/api/DetectionQueue/UpdateQueueStatus";

    //获取某辆车的外检拍照的项目
    String WAI_JIAN_PHOTO_ITEM = "/api/DetectionItem/OutPhotoItem";

    String DC_PICTURE = "/api/Platform/Picture";

    //项目总结束
    // "/api/DetectionQueue/{0}/DetectionType/{1}/DetectionID";
    String ITEM_ALL_END = "/api/DetectionQueue/0/DetectionType/";


    // {type}/QueueType/{status}/DynaStatus底盘动态
    String CAR_LIST_DIPAN = CARS_LIST_FRONT + "0/QueueType/0/DynaStatus";

    // {type}/QueueType/{status}/AppearStatus外检
    String CAR_LIST_WAIJIAN = CARS_LIST_FRONT + "0/QueueType/0/AppearStatus";

    // {type}/QueueType/{status}/RoadStatus路试
    String CAR_LIST_LUSHI = CARS_LIST_FRONT + "0/QueueType/0/RoadStatus";


    // {type}/QueueType/{status}/DynaStatus底盘动态
    String CAR_LIST_DIPAN_2 = CARS_LIST_FRONT + "0/QueueType/99/DynaStatus";

    // {type}/QueueType/{status}/AppearStatus外检
    String CAR_LIST_WAIJIAN_2 = CARS_LIST_FRONT + "0/QueueType/99/AppearStatus";

    // {type}/QueueType/{status}/RoadStatus路试
    String CAR_LIST_LUSHI_2 = CARS_LIST_FRONT + "0/QueueType/99/RoadStatus";

//重新上线的
    // {type}/QueueType/{status}/DynaStatus底盘动态
    String CAR_LIST_DIPAN_3 = CARS_LIST_FRONT + "0/QueueType/1/DynaStatus";

    // {type}/QueueType/{status}/AppearStatus外检
    String CAR_LIST_WAIJIAN_3 = CARS_LIST_FRONT + "0/QueueType/1/AppearStatus";

    // {type}/QueueType/{status}/RoadStatus路试
    String CAR_LIST_LUSHI_3 = CARS_LIST_FRONT + "0/QueueType/1/RoadStatus";

    //复检查询-车牌号
    String RECHECK_CARS_LIST = "/api/DetectionQueue/SearchQueueWeb";

    //重新下线查询-车牌号
    String CARS_DOWN_LINE = "/api/DetectionQueue/SearchCarQueue";

    //新车调度--车辆列表
    String NEW_CARS_LIST= "/api/DetectionQueue/0/QueueType/0/QueueStatus/0/Today";

    //远程上线
    String CARS_UP_LINE = "/api/Remote/Para";

}
