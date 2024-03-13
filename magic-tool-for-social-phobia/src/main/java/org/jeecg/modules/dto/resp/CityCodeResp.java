package org.jeecg.modules.dto.resp;

import lombok.Data;
import org.jeecg.modules.entity.CityCode;

import java.util.List;

/**
 * @author 余吉钊
 * &#064;date  2024/2/29 14:07
 */
@Data
public class CityCodeResp extends CityCode {
    private List<CityCodeResp> children;

    public CityCodeResp(String cityName, String cityCode, String adcCode) {
        super(cityName, cityCode, adcCode);
    }

    public CityCodeResp() {
        super();
    }
}
