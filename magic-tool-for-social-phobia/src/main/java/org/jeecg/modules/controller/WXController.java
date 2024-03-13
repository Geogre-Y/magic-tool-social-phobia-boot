package org.jeecg.modules.controller;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.draft.WxMpAddDraft;
import me.chanjar.weixin.mp.bean.draft.WxMpDraftArticles;
import me.chanjar.weixin.mp.bean.draft.WxMpDraftList;
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.entity.Cast;
import org.jeecg.modules.entity.Forecast;
import org.jeecg.modules.entity.ITrafficUserConfig;
import org.jeecg.modules.enums.WxTemplateEnum;
import org.jeecg.modules.service.ITrafficUserConfigService;
import org.jeecg.modules.service.IWeatherService;
import org.jeecg.modules.utils.EventDispatcher;
import org.jeecg.modules.utils.MessageUtil;
import org.jeecg.modules.utils.MsgDispatcher;
import org.jeecg.modules.utils.ShaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 余吉钊
 * @date 2024/1/11 9:59
 */
@RestController
@RequestMapping("/test/magic-tool")
@Slf4j
@Api(tags = "I型人群出门神器")
public class WXController {

    @Autowired
    private WxMpService mpService;

    @Autowired
    private ITrafficUserConfigService userConfigService;

    @Autowired
    private IWeatherService iWeatherService;

    @Value("${wx.mp.message-token}")
    private String wxMessageToken;

    @GetMapping("/test")
    @ApiOperation("测试")
    public Object test() {

//        ImportParams params = new ImportParams();
//        List<CityCode> list = ExcelImportUtil.importExcel(new File(
//                "f:/Chrome downloads/AMap_adcode_citycode.xlsx"), Map.class, params);
//        cityCodeService.saveBatch(list);
//        log.info("访问测试");

        return "null";
    }

    //    she'h's
    @GetMapping("/testFB")
    @ApiOperation("测试发布草稿")
    public Object testFB() throws WxErrorException {
        // this.mpService.getWxMpConfigStorage().getAppId();
//        this.mpService.getUserService().userList();
//        WxMpFreePublishList freePublishList = this.mpService.getFreePublishService().getPublicationRecords(0,10);
        String filePath = "E:/img.jpg";
        File img = new File(filePath);
        WxMpMaterial wxMpMaterial = new WxMpMaterial();
        wxMpMaterial.setFile(img);
        wxMpMaterial.setName("img.jpg");
//        type只支持四种类型素材(video/image/voice/thumb)
        WxMpMaterialUploadResult mediaResult = this.mpService.getMaterialService().materialFileUpload("image",
                wxMpMaterial);
        log.info("media_id:" + mediaResult.getMediaId());
        List<WxMpDraftArticles> list = new ArrayList<>();
        WxMpDraftArticles wxMpDraftArticles = new WxMpDraftArticles();
        wxMpDraftArticles.setTitle("新的一天大家早上好");
        wxMpDraftArticles.setAuthor("余维华");
        wxMpDraftArticles.setContent(" <p>\n" + "    <p style=\"text-align: center;\">\n" +
                "        <img class=\"rich_pages wxw-img\" data-galleryid=\"\" data-imgfileid=\"100000008\" " +
                "data-ratio=\"0.6666666666666666\"\n" + "            data-s=\"300,640\"\n" +
                "            data-src=\"https://mmbiz.qpic" + ".cn/mmbiz_jpg" +
                "/SibvcNTfMwY30L6nkzfaBbUr0wPyRJiawnyJ2LcUpEnUxaFuI1XSfEoS8r8elabxiccic1FlibZsWn7COSOHosAdicWg/640" +
                "?wx_fmt=jpeg\"\n" + "            data-type=\"jpeg\" data-w=\"1080\" style=\"\">\n" + "    </p>\n" +
                "    <p>i型人群，简称i人，网络流行词，指性格比较内敛、内向的人，简单来说就是社恐。</p>\n" +
                "    <p>当内向型人格的人面临社交场合时，他们可能会感到紧张或害怕，这可能会进一步导致他们避免社交活动或表现出不自然的举止。</p>\n" +
                "    <section>\n" +
                "        <mp-common-poi class=\"js_editor_mppoi appmsg_poi_iframe custom_select_card js_uneditable\" " +
                "data-pluginname=\"poi\"\n" +
                "            data-id=\"0.8133082618904475\" data-name=\"%E9%97%BD%E6%B1%9F%E5%AD%A6%E9%99%A2\"\n" +
                "            data-address=\"%E7%A6%8F%E5%BB%BA%E7%9C%81%E7%A6%8F%E5%B7%9E%E5%B8%82%E9%97%BD%E4%BE%AF" +
                "%E5%8E%BF%E6%BA%AA%E6%BA%90%E5%AE%AB%E8%B7%AF200%E5%8F%B7\"\n" +
                "            data-img=\"https%3A%2F%2Fmmbiz.qlogo" + ".cn%2Fmmbiz_png" +
                "%2FSibvcNTfMwY30L6nkzfaBbUr0wPyRJiawnUxDoTZbpMarDEdTXP7ZWTmIO3tLb09pO8ILL4GpvnicSIDeTF9w3UOg%2F0" +
                "%3Fwx_fmt%3Dpng%26from%3Dappmsg\"\n" +
                "            data-longitude=\"119.168647766\" data-latitude=\"26.0652484894\" " +
                "data-poiid=\"4195449873407334127\"\n" +
                "            data-districtid=\"\" data-province=\"\" data-city=\"\" " +
                "data-type=\"2\"></mp-common-poi>\n" + "    </section>\n" +
                "    <section class=\"channels_iframe_wrp wxw_wechannel_card_not_horizontal\">\n" +
                "        <mp-common-videosnap\n" +
                "            class=\"js_uneditable custom_select_card channels_iframe videosnap_video_iframe " +
                "videosnap_image_iframe\"\n" + "            data-pluginname=\"mpvideosnap\"\n" +
                "            data-url=\"https://findermp.video.qq" + ".com/251/20350/stodownload?encfilekey" +
                "=oibeqyX228riaCwo9STVsGLPj9UYCicgttveop1NqliajJicw3fuy1fKEOiabj4lHUCZZ6rqHv1sjWAsrCGpFOu8SU8AY2SBrrLJcaUIYtRj5TTCia7BoTssb5hUpOI1HyRfWULXauU343ByoY&amp;adaptivelytrans=0&amp;bizid=1023&amp;dotrans=0&amp;hy=SH&amp;idx=1&amp;m=f980a0d52e05aaa5d376814cab1206f8&amp;token=AxricY7RBHdVS6jdlolseic3fjibS96dcwkXPxCX2ZksIdOH2MoTDBEefBWg2k5WDflhtgMbQVBFpg\"\n" +
                "            data-headimgurl=\"http://wx.qlogo" +
                ".cn/finderhead/ajNVdqHZLLBH5JkbFm975IQ87eReQJNRF4QNstLvCjBulgPGG8GSpA/0\"\n" + "            " +
                "data-username=\"v2_060000231003b20faec8c7e28c1fc0ddc70ceb35b07777e9c6d02d1fbf4368bf09f84d1cf78d" +
                "@finder\"\n" + "            data-nickname=\"丝丝aa\" data-desc=\"\" data-imgcount=\"1\" " +
                "data-nonceid=\"981741721291402401\" data-type=\"image\"\n" +
                "            data-mediatype=\"undefined\"\n" + "            data-authiconurl=\"https://dldir1v6.qq" +
                ".com/weixin/checkresupdate/auth_icon_level2_588a474b89e94776b07b87dc361fd0ab.png\"\n" +
                "            data-from=\"new\" data-width=\"1248\" data-height=\"1717\"\n" +
                "            data-id=\"export/UzFfAgtgekIEAQAAAAAALQ0sDpwcngAAAAstQy6ubaLX4KHWvLEZgBPEvIIUaiJ3Ea" +
                "-CzNPgMIsOMotNKJV05EUkJtm2v9p-\"\n" +
                "            data-isdisabled=\"0\" data-errortips=\"\"></mp-common-videosnap>\n" + "    </section>\n" +
                "    <section>\n" +
                "        <mp-common-claudio class=\"js_editor_claudio res_iframe js_uneditable custom_select_card\"\n" +
                "            cover=\"https://finder.video.qq" + ".com/251/20304/stodownload?encfilekey" +
                "=rjD5jyTuFrIpZ2ibE8T7YmwgiahniaXswqzicyMInGHJx7NIvTJVQxviaziaqkEJFiaa5zLOk2hTDEKmBqWuf5Npjic9tf554XJpvNHbR7ql18K6doPY7LoOJ2NDlw&amp;token=x5Y29zUxcibCvkDbNQE5SdJfZVzLial8M5cFRDic4bU3b6dYx3VT75K2ZoDyiad8r33Y&amp;idx=1&amp;bizid=1023&amp;dotrans=0&amp;hy=SH&amp;m=&amp;scene=0&amp;picformat=200\"\n" +
                "            name=\"旺自己的10个极简好习惯\" author=\"我是傅小米啊\" duration=\"542\"\n" +
                "            data-url=\"https://findermp.video.qq" + ".com/251/20304/stodownload?encfilekey" +
                "=rjD5jyTuFrIpZ2ibE8T7YmwgiahniaXswqzicyMInGHJx7NIvTJVQxviaziaqkEJFiaa5zLOk2hTDEKmBqWuf5Npjic9tf554XJpvNHbR7ql18K6doPY7LoOJ2NDlw&amp;bizid=1023&amp;dotrans=0&amp;hy=SH&amp;idx=1&amp;m=&amp;scene=0&amp;token=AxricY7RBHdVS6jdlolseicyNY6UK5gIzBeo4pA8iaFExOXl64roFOhSRzyP7z0gaXcODXFAKicDsxM\"\n" +
                "            " +
                "data-username=\"v2_060000231003b20faec8c5e48a18c1d4cf04ef33b077736abd25b64f0fdc1bce13990c90e695" +
                "@finder\"\n" + "            data-nonceid=\"1413498587612891495\" data-tid=\"14292126257881155875\" " +
                "data-playable=\"1\" data-mediatype=\"17\"\n" + "            data-from=\"new\"\n" + "            " +
                "data-id=\"export" +
                "/UzFfAgtgekIEAQAAAAAA5C8mJuwDdgAAAAstQy6ubaLX4KHWvLEZgBPE96FYTixPL86FzNPgMIsPvmavQxQxMT3w-xZWfBIK" +
                "\"\n" + "            data-type=\"audio\" data-pluginname=\"insertaudio\" " +
                "listen_id=\"294478620098897271\"></mp-common-claudio>\n" + "    </section>\n" + "    <p>\n" +
                "        <br />\n" + "    </p>\n" + "    <p style=\"display: none;\">\n" +
                "        <mp-style-type data-value=\"3\"></mp-style-type>\n" + "    </p>\n" + "    </p>");
        wxMpDraftArticles.setNeedOpenComment(0);
        wxMpDraftArticles.setThumbMediaId(mediaResult.getMediaId());
        list.add(wxMpDraftArticles);
        WxMpAddDraft wxMpAddDraft = new WxMpAddDraft();
        wxMpAddDraft.setArticles(list);
        this.mpService.getDraftService().addDraft(wxMpAddDraft);
        return wxMpAddDraft;

    }

    /**
     * @param offset 分页页数，从0开始 从全部素材的该偏移位置开始返回，0表示从第一个素材返回
     * @param count  每页数量 返回素材的数量，取值在1到20之间
     * @return
     */
    @GetMapping("/listDraft")
    @ApiOperation("获取草稿箱")
    public Object listDraft(@RequestParam("offset") Integer offset, @RequestParam(
            "count") Integer count) throws WxErrorException {
        WxMpDraftList list = mpService.getDraftService().listDraft(offset, count);

        return list;
    }

    @GetMapping("/userList")
    @ApiOperation("获取关注公众号的用户列表")
    public Object userList(@RequestParam("nextId") String nextId) throws WxErrorException {
        WxMpUserList userList = mpService.getUserService().userList();
        return userList;
    }


    @GetMapping("/sendTemplate")
    @ApiOperation("给指定的用户发送模板消息")
    public Object sendTemplate(@RequestParam(
            "userConfigId") @NotNull @Valid Integer userConfigId) throws WxErrorException {
        return sendTemplateMessage(userConfigId);
    }

    public Result<String> sendTemplateMessage(Integer userConfigId) throws WxErrorException {
        ITrafficUserConfig userConfig = userConfigService.getById(userConfigId);
        if (ObjectUtil.isNull(userConfig)) {
            return Result.error("该配置不存在");
        }
        WxMpTemplateMessage message = new WxMpTemplateMessage();
        message.setToUser(userConfig.getWxOpenId());
        message.setTemplateId(WxTemplateEnum.OUT.getValue());
        //点击模板跳转URL
        message.setUrl("http://47.98.228.176:3000/wxDefault?url=" + userConfig.getWxOpenId());

        //设置模板数据
        List<WxMpTemplateData> dataList = new ArrayList<>();
        WxMpTemplateData data = new WxMpTemplateData();
        data.setName("outTime");
        data.setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        dataList.add(data);
        Forecast forecast = iWeatherService.getWeatherByCityCode(userConfig.getCityCode());
        Cast cast = forecast.getCasts().get(0);

        WxMpTemplateData data1 = new WxMpTemplateData();
        data1.setName("city");
        data1.setValue(forecast.getCity());
        dataList.add(data1);

        WxMpTemplateData data2 = new WxMpTemplateData();
        data2.setName("dayweather");
        data2.setValue(cast.getDayweather());
        dataList.add(data2);

        WxMpTemplateData data3 = new WxMpTemplateData();
        data3.setName("daytemp");
        data3.setValue(cast.getDaytemp() + "°C");
        dataList.add(data3);

        WxMpTemplateData data4 = new WxMpTemplateData();
        data4.setName("nightweather");
        data4.setValue(cast.getNightweather());
        dataList.add(data4);

        WxMpTemplateData data5 = new WxMpTemplateData();
        data5.setName("nighttemp");
        data5.setValue(cast.getNighttemp() + "°C");
        dataList.add(data5);

        message.setData(dataList);
        String sendMessage = mpService.getTemplateMsgService().sendTemplateMsg(message);
        return Result.ok(sendMessage);
    }

    /**
     * @param request
     * @return void
     * @author 余维华
     * @description: 微信官方发送得token验证
     **/
    @GetMapping("/wxRequest")
    public String doGet(HttpServletRequest request, HttpServletResponse response) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        log.info("signature[{}], timestamp[{}], nonce[{}], echostr[{}]", signature, timestamp, nonce, echostr);
        if (ShaUtil.getSHA1(wxMessageToken, signature, timestamp, nonce)) {
            log.info("数据源为微信后台，将echostr[{}]返回！", echostr);
            return echostr;
        }
        return "";
    }

    ;

    /**
     * @param request
     * @param response
     * @return void
     * @author 余维华
     * @description: 微信官方得消息的接收和处理
     **/
    @PostMapping("/wxRequest")
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> messageMap = MessageUtil.parseXml(request);
            System.out.println("消息：" + messageMap.get("Content"));
            String type = messageMap.get("MsgType");
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String data;
            if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(type)) {
                //事件处理
                data = EventDispatcher.processEvent(messageMap);
            } else {
                data = MsgDispatcher.processMessage(messageMap);
            }
            PrintWriter out = response.getWriter();
            out.print(data);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ;
}
