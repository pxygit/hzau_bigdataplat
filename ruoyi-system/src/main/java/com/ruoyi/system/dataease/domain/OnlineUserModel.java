package com.ruoyi.system.dataease.domain;

import lombok.Data;
import java.io.Serializable;

@Data
public class OnlineUserModel implements Serializable {

    private static final long serialVersionUID = 190044376129186283L;

    private String token;

    private String ip;

    private Long loginTime;
}
