package com.henghao.dingding.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.henghao.dingding.ActivityFragmentSupport;
import com.henghao.dingding.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 签到提交界面 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author yanqiyun
 * @version HDMNV100R001, 2016-12-01
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class QiandaoSubmitActivity extends ActivityFragmentSupport {

	/**
	 * 签到时间
	 */
	@ViewInject(R.id.tv_time_qiandaosubmit)
	private TextView tv_time_qiandaosubmit;
	/**
	 * 签到地点
	 */
	@ViewInject(R.id.tv_address_qiandaosubmit)
	private TextView tv_address_qiandaosubmit;
	/**
	 * 签到备注
	 */
	@ViewInject(R.id.et_note_qiandao)
	private EditText et_note_qiandao;
	/**
	 * 选择照片
	 */
	@ViewInject(R.id.img_camera_qiandaosubmit)
	private ImageView img_camera_qiandaosubmit;
	/**
	 * 当前企业
	 */
	@ViewInject(R.id.tv_company_qiandaosubmit)
	private TextView tv_company_qiandaosubmit;
	/**
	 * 提交
	 */
	@ViewInject(R.id.btn_submit_qiandaosubmit)
	private Button btn_submit_qiandaosubmit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mActivityFragmentView.viewMain(R.layout.activity_qiandao_submit);
		this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
		this.mActivityFragmentView.viewEmptyGone();
		this.mActivityFragmentView.viewLoading(View.GONE);
		this.mActivityFragmentView.clipToPadding(true);
		setContentView(this.mActivityFragmentView);
		com.lidroid.xutils.ViewUtils.inject(this);
		initWidget();
		initData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.henghao.wenbo.ActivityFragmentSupport#initWidget()
	 */
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		initWithBar();
		mLeftTextView.setVisibility(View.VISIBLE);
		mLeftTextView.setText("返回");
		initWithCenterBar();
		this.mCenterTextView.setVisibility(View.VISIBLE);
		this.mCenterTextView.setText("签到");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.henghao.wenbo.ActivityFragmentSupport#initData()
	 */
	@Override
	public void initData() {
		Intent intent = getIntent();
		String time = intent.getStringExtra("time");
		String address = intent.getStringExtra("address");
		String company = intent.getStringExtra("company");
		tv_time_qiandaosubmit.setText(time);
		tv_address_qiandaosubmit.setText(address);
		tv_company_qiandaosubmit.setText(company);
	}

	@OnClick({ R.id.btn_submit_qiandaosubmit })
	private void viewClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit_qiandaosubmit:
			// 提交
			Toast.makeText(QiandaoSubmitActivity.this, "签到成功",
					Toast.LENGTH_SHORT).show();
			setResult(RESULT_OK);
			finish();
			break;
		}
	}

}
