package org.jeecg.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 余吉钊
 * @date 2024/2/26 14:32
 */
@Data
@TableName("i_map_city_code")
public class CityCode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String cityName;

    private String cityCode;

    private String adcCode;


    public CityCode(String cityName, String cityCode, String adcCode) {
        this.cityName = cityName;
        this.cityCode = cityCode;
        this.adcCode = adcCode;
    }

    public CityCode() {
    }
}
