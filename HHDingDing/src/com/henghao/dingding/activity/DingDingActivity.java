package com.henghao.dingding.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.henghao.dingding.ActivityFragmentSupport;
import com.henghao.dingding.R;
import com.henghao.dingding.model.entity.UserLoginEntity;
import com.henghao.dingding.utils.UserLoginList;
import com.henghao.dingding.views.dialog.DialogAlertEdit;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 考勤主页面
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2016-11-29
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DingDingActivity extends ActivityFragmentSupport {

	private List<UserLoginEntity> mUserList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mActivityFragmentView.viewMain(R.layout.activity_dingding);
		this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
		this.mActivityFragmentView.viewEmptyGone();
		this.mActivityFragmentView.viewLoading(View.GONE);
		this.mActivityFragmentView.clipToPadding(true);
		setContentView(this.mActivityFragmentView);
		com.lidroid.xutils.ViewUtils.inject(this);
		initWidget();

	}

	/*
	 * (non-Javadoc)
	 * @see com.henghao.wenbo.ActivityFragmentSupport#initWidget()
	 */
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		this.mUserList = new ArrayList<UserLoginEntity>();
		initWithCenterBar();
		this.mCenterTextView.setVisibility(View.VISIBLE);
		this.mCenterTextView.setText("恒昊考勤");
		showLoginAlert();
	}

	/**
	 * 〈一句话功能简述〉
	 * 〈功能详细描述〉
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	private void showLoginAlert() {
		// TODO Auto-generated method stub
		DialogAlertEdit dlg = new DialogAlertEdit(this, new DialogAlertEdit.DialogAlertListener() {

			@Override
			public void onDialogOk(Dialog dlg, Object param) {
				final String userName = param.toString();
				boolean hasUser = false;
				for (int i = 0; i < UserLoginList.mUserList.size(); i++) {
					if (UserLoginList.mUserList.get(i).getUserId().equals(userName)) {
						hasUser = true;
						UserLoginList.number = i;
						dlg.cancel();
						break;
					}
				}
				if (!hasUser) {
					msg("没有此工号");
				}
			}

			@Override
			public void onDialogCreate(Dialog dlg, Object param) {

			}

			@Override
			public void onDialogCancel(Dialog dlg, Object param) {
				dlg.cancel();
//				finish();
				getFMApplication().exit();
			}
		}, "登录", "", "取消", "确定");
		dlg.show();
	}

	/*
	 * (non-Javadoc)
	 * @see com.henghao.wenbo.ActivityFragmentSupport#initData()
	 */
	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@OnClick({
	        R.id.ll_kaoqing, R.id.ll_qiandao
	})
	private void viewClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
			case R.id.ll_qiandao:
				// 签到
				intent.setClass(this, QiandaoActivity.class);
				startActivity(intent);
				break;
			case R.id.ll_kaoqing:
				// 考勤
				intent.setClass(this, KQActivity.class);
				startActivity(intent);
				break;

			default:
				break;
		}
	}

}
