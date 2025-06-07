package com.ruoyi.system.dataease.domain;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

@Data
@Builder
public class TokenInfo implements Serializable {

    private String username;

    private Long userId;

    private Long exp;

    public String format() {
        return username + "," + userId;
    }
}

