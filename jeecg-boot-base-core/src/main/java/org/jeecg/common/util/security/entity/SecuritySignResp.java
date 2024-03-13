package org.jeecg.common.util.security.entity;

import lombok.Data;

/**
 * @Description: SecuritySignResp
 * @Author 余维华
 */
@Data
public class SecuritySignResp {
    private String data;
    private String signData;
    private String aesKey;
}
