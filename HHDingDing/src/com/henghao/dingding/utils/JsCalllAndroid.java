package com.henghao.dingding.utils;

import android.webkit.JavascriptInterface;

public interface JsCalllAndroid {
	@JavascriptInterface
    public void send(String data, Object callBack) ;
}
