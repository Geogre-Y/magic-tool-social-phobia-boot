package org.jeecg.common.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * @Author 余维华
 */
@Data
public class PageVO {

    @ApiModelProperty(value = "当前分页数", required = true)
    @NotNull
    private Integer size;

    @ApiModelProperty(value = "分页大小", required = true)
    @NotNull
    private Integer current;


}
