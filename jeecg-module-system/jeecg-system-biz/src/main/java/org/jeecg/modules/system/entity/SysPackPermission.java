package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 产品包菜单关系表
 * @Author 余维华
 * @Date:   2022-12-31
 * @Version: V1.0
 */
@Data
@TableName("sys_tenant_pack_perms")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sys_tenant_pack_perms对象", description="产品包菜单关系表")
public class SysPackPermission implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键编号*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键编号")
    private String id;
	/**租户产品包名称*/
	@Excel(name = "租户产品包名称", width = 15)
    @ApiModelProperty(value = "租户产品包名称")
    private String packId;
	/**菜单id*/
	@Excel(name = "菜单id", width = 15)
    @ApiModelProperty(value = "菜单id")
    private String permissionId;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
