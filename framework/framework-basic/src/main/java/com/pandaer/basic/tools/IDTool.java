package com.pandaer.basic.tools;

import cn.hutool.core.util.IdUtil;

public class IDTool {

    public static String genID() {
        return IdUtil.getSnowflakeNextIdStr();
    }

    public static void main(String[] args) {
        System.out.println(genID());
        System.out.println(genID());
        System.out.println(genID());
    }
}
