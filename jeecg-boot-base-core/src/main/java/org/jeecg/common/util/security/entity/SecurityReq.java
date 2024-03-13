package org.jeecg.common.util.security.entity;

import lombok.Data;

/**
 * @Description: SecurityReq
 * @Author 余维华
 */
@Data
public class SecurityReq {
    private String data;
    private String pubKey;
    private String signData;
    private String aesKey;
}
