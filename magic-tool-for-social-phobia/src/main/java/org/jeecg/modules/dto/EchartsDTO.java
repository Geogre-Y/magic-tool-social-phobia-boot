package org.jeecg.modules.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @param <T>
 * @Description 折线图数据
 * @ClassName EchartsIntegerVO
 * @Author bo
 * @date 2024.01.04 14:32
 */
@Data
@ApiModel("数据趋势图")
public class EchartsDTO<T> {


    @ApiModelProperty(value = "x轴")
    private List<String> xAxis;


    @ApiModelProperty(value = "数据")
    private List<Series<T>> series;


    /**
     * @param <T>
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Series<T> {

        @ApiModelProperty(value = "名")
        private String name;


        @ApiModelProperty(value = "数据")
        private List<T> data;

    }

}
