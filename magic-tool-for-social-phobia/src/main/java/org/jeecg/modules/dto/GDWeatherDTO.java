package org.jeecg.modules.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.entity.Forecast;

import java.util.List;

/**
 * @author 余吉钊
 * @date 2024/2/19 14:18
 */
@Data
public class GDWeatherDTO {
    private String count;
    private List<Forecast> forecasts;
    private String info;
    @ApiModelProperty("返回状态说明,10000代表正确")
    private String infocode;
    private String status;
}
