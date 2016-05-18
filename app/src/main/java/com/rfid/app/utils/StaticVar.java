package com.rfid.app.utils;

/**
 * Created by sly on 2016/5/18.
 */
public class StaticVar {
    private static StaticVar staticVar;

    private StaticVar() {
    }

    public static StaticVar getInstence() {
        if (staticVar == null) {
            staticVar = new StaticVar();
        }
        return staticVar;
    }

    public boolean serialIsOpe;

    public boolean isSerialIsOpe() {
        return serialIsOpe;
    }

    public void setSerialIsOpe(boolean serialIsOpe) {
        this.serialIsOpe = serialIsOpe;
    }
}
