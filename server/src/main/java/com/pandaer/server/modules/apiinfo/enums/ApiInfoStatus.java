package com.pandaer.server.modules.apiinfo.enums;

import lombok.Getter;

@Getter
public enum ApiInfoStatus {
    ENABLE("enable"),
    DISABLE("disable"),
    ;
    private final String value;

    ApiInfoStatus(String value) {
        this.value = value;
    }

}
