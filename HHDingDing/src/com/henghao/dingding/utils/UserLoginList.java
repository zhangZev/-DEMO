/*
 * 文件名：UserLoginList.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved.
 * 描述：
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.dingding.utils;

import java.util.ArrayList;
import java.util.List;

import com.henghao.dingding.model.entity.UserLoginEntity;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2016-12-2
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UserLoginList {

	public static List<UserLoginEntity> mUserList;

	public static int number;

	static {
		mUserList = new ArrayList<UserLoginEntity>();
		UserLoginEntity mEntity = new UserLoginEntity("20130226", "罗铸", "恒昊软件");
		UserLoginEntity mEntity2 = new UserLoginEntity("20130227", "施显召", "恒昊软件");
		UserLoginEntity mEntity3 = new UserLoginEntity("20130228", "陈武旭", "恒昊软件");
		UserLoginEntity mEntity4 = new UserLoginEntity("20130301", "晏朋橙", "恒昊软件");
		UserLoginEntity mEntity5 = new UserLoginEntity("20130302", "董木易", "恒昊软件");
		UserLoginEntity mEntity6 = new UserLoginEntity("20130304", "李佳", "恒昊软件");
		UserLoginEntity mEntity8 = new UserLoginEntity("20130303", "白蕙", "恒昊软件");
		UserLoginEntity mEntity7 = new UserLoginEntity("20130305", "晏齐云", "恒昊软件");
		UserLoginEntity mEntity9 = new UserLoginEntity("20130306", "张显武", "恒昊软件");
		mUserList.add(mEntity);
		mUserList.add(mEntity2);
		mUserList.add(mEntity3);
		mUserList.add(mEntity4);
		mUserList.add(mEntity5);
		mUserList.add(mEntity6);
		mUserList.add(mEntity7);
		mUserList.add(mEntity8);
		mUserList.add(mEntity9);
	}

}
