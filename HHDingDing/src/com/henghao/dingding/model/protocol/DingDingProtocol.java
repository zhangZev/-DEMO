/*
 * 文件名：LoginFilfterProtocol.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved.
 * 描述：
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.dingding.model.protocol;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.henghao.dingding.ProtocolUrl;
import com.henghao.dingding.model.ascyn.BaseModel;
import com.henghao.dingding.model.ascyn.BeeCallback;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015年6月5日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DingDingProtocol extends BaseModel {

	public DingDingProtocol(Context context) {
		super(context);
	}

	/**
	 * 〈一句话功能简述〉
	 * 〈功能详细描述〉
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public void getNfcById() {
		try {
			String url = ProtocolUrl.APP_GET_NFCBYID;
			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("nfcId", nfcId);
			this.mBeeCallback.url(url).type(String.class).params(params);
			this.aq.ajax(this.mBeeCallback);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final BeeCallback<String> mBeeCallback = new BeeCallback<String>() {

		@Override
		public void callback(String url, String object, AjaxStatus status) {
			try {
				/**** start ****/
//				BaseEntity mBaseEntity = ToolsJson.parseObjecta(object, BaseEntity.class);
//				if (mBaseEntity == null) {
//					NfcDetailsProtocol.this.OnMessageResponse(url, mBaseEntity, status);
//					return;
//				}
//				String data =object;
//				if (ToolsKit.isEmpty(data)) {
//					NfcDetailsProtocol.this.OnMessageResponse(url, mBaseEntity, status);
//					return;
//				}
				/**** end ****/
				if (url.endsWith(ProtocolUrl.APP_GET_NFCBYID)) {
//					String dataConfig = ToolsSecret.decode(data);
					DingDingProtocol.this.OnMessageResponse(url, object, status);
				}

			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
}