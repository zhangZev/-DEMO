package com.henghao.dingding.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.benefit.buy.library.utils.tools.ToolsDate;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.benefit.buy.library.views.NoScrollListView;
import com.henghao.dingding.ActivityFragmentSupport;
import com.henghao.dingding.R;
import com.henghao.dingding.model.entity.UserLoginEntity;
import com.henghao.dingding.utils.DensityUtil;
import com.henghao.dingding.utils.UserLoginList;
import com.henghao.dingding.views.dialog.DialogAlertUP;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 考勤签到
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2016-11-29
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class KQActivity extends ActivityFragmentSupport {

	@ViewInject(R.id.tv_data)
	private TextView tv_data;

	private boolean isFlag;

	/**
	 * 晚背景
	 */
	@ViewInject(R.id.ll_wan)
	private LinearLayout ll_wan;
	/**
	 * 早背景
	 */
	@ViewInject(R.id.ll_zao)
	private LinearLayout ll_zao;
	/**
	 * 早打卡
	 */
	@ViewInject(R.id.ll_zaodaka)
	private LinearLayout ll_zaodaka;
	/**
	 * 早补卡
	 */
	@ViewInject(R.id.ll_zaobuka)
	private LinearLayout ll_zaobuka;
	/**
	 * 晚打卡
	 */
	@ViewInject(R.id.ll_wandaka)
	private LinearLayout ll_wandaka;
	/**
	 * 晚补卡
	 */
	@ViewInject(R.id.ll_wanbuka)
	private LinearLayout ll_wanbuka;
	/**
	 * /**
	 * 早打卡按钮
	 */
	@ViewInject(R.id.ll_clickzaodaka)
	private LinearLayout ll_clickzaodaka;
	/**
	 * /**
	 * 晚打卡按钮
	 */
	@ViewInject(R.id.ll_clickwandaka)
	private LinearLayout ll_clickwandaka;
	/**
	 * 上图标
	 */
	@ViewInject(R.id.tv_up)
	private TextView tv_up;
	/**
	 * 描述
	 */
	@ViewInject(R.id.tv_toptext)
	private TextView tv_toptext;
	/**
	 * 下图标
	 */
	@ViewInject(R.id.tv_down)
	private TextView tv_down;
	/**
	 * 灰色线
	 */
	@ViewInject(R.id.view_)
	private View view_;

	/**
	 * 0 表示上午，1表示下午。
	 */
	private int whatApm;

	/**
	 * 早打卡时间
	 */
	@ViewInject(R.id.tv_zaotime)
	private TextView tv_zaotime;

	/**
	 * 晚打卡时间
	 */
	@ViewInject(R.id.tv_wantime)
	private TextView tv_wantime;
	/**
	 * 上班打卡
	 */
	@ViewInject(R.id.tv_shangban)
	private TextView tv_shangban;
	/**
	 * 下班打卡
	 */
	@ViewInject(R.id.tv_xiaban)
	private TextView tv_xiaban;

	/**
	 * 是否进入打卡范围图标
	 */
	@ViewInject(R.id.img_zaowhere)
	private ImageView img_zaowhere;

	@ViewInject(R.id.img_wanwhere)
	private ImageView img_wanwhere;
	/**
	 * 是否进入打卡范围描述
	 */
	@ViewInject(R.id.tv_zaowhere)
	private TextView tv_zaowhere;

	@ViewInject(R.id.tv_wanwhere)
	private TextView tv_wanwhere;

	@ViewInject(R.id.tv_xiabantime)
	private TextView tv_xiabantime;

	@ViewInject(R.id.tv_shangbantime)
	private TextView tv_shangbantime;

	/**
	 * 缺卡记录
	 */
	@ViewInject(R.id.tv_zaoque)
	private TextView tv_zaoque;

	@ViewInject(R.id.tv_wanque)
	private TextView tv_wanque;

	/**
	 * 打卡地址显示
	 */
	@ViewInject(R.id.tv_addreszao)
	private TextView tv_addreszao;

	@ViewInject(R.id.tv_addreswan)
	private TextView tv_addreswan;

	// 用户名
	@ViewInject(R.id.tv_username)
	private TextView tv_username;

	@ViewInject(R.id.tv_userIcon)
	private TextView tv_userIcon;

	private static final int msgKey1 = 1;

	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListenner();

	@ViewInject(R.id.listview_kq)
	private NoScrollListView listview_kq;
	private String address;

	// latitude: 26.626491121790554, longitude: 106.66190367889827

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mActivityFragmentView.viewMain(R.layout.activity_kq);
		this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
		this.mActivityFragmentView.viewEmptyGone();
		this.mActivityFragmentView.viewLoading(View.GONE);
		this.mActivityFragmentView.clipToPadding(true);
		setContentView(this.mActivityFragmentView);
		com.lidroid.xutils.ViewUtils.inject(this);
		UserLoginEntity mUserEntity = UserLoginList.mUserList.get(UserLoginList.number);
		this.tv_username.setText(mUserEntity.getUserName());
		if (mUserEntity.getUserName().toCharArray().length > 2) {
			this.tv_userIcon.setText(mUserEntity.getUserName().substring(1, 3));
		}
		else {
			this.tv_userIcon.setText(mUserEntity.getUserName());
		}
		initWidget();
		initData();

	}

	/**
	 * 〈一句话功能简述〉
	 * 〈功能详细描述〉
	 * @param title 标题
	 * @param where 地址
	 * @param hintMS 描述
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	private void showLoginAlert(String title, String where, String hintMS) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
		Date date = new Date();
		DialogAlertUP dlg = new DialogAlertUP(this, new DialogAlertUP.DialogAlertListener() {

			@Override
			public void onDialogOk(Dialog dlg, Object param) {
				final String userName = param.toString();
				dlg.cancel();
			}

			@Override
			public void onDialogCreate(Dialog dlg, Object param) {

			}

			@Override
			public void onDialogCancel(Dialog dlg, Object param) {
				dlg.cancel();
			}
		}, title, "", "", "提交", sdf.format(date), where, hintMS);
		dlg.show();
	}

	/*
	 * (non-Javadoc)
	 * @see com.henghao.wenbo.ActivityFragmentSupport#initWidget()
	 */
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		initWithCenterBar();
		this.mCenterTextView.setVisibility(View.VISIBLE);
		this.mCenterTextView.setText("考勤打卡");
		initWithBar();
		this.mLeftTextView.setText("返回");
		this.mLeftTextView.setVisibility(View.VISIBLE);
		initView();
	}

	/**
	 * 〈一句话功能简述〉
	 * 〈功能详细描述〉
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	private void initView() {
		this.whatApm = compare_date();
		RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) this.view_.getLayoutParams(); // 取控件textView当前的布局参数
		java.util.Date dataTime = ToolsDate.parseDateTime(ToolsDate.getCurrentDateTime());
		java.util.Date datezao = ToolsDate.parseDateTime(ToolsDate.getCurrentDate() + " 09:00:00");
		java.util.Date datewan = ToolsDate.parseDateTime(ToolsDate.getCurrentDate() + " 18:00:00");
		switch (this.whatApm) {
			case 0:
				// 早上未打卡、还没到下午
				this.tv_toptext.setText("愉悦的工作，从到公司的第一刻开始");
				this.ll_zao.setBackgroundColor(getResources().getColor(R.color.zao_color));
				this.ll_wan.setBackgroundColor(getResources().getColor(R.color.white));
				this.ll_zaodaka.setVisibility(View.VISIBLE);
				this.ll_zaobuka.setVisibility(View.GONE);
				this.ll_wandaka.setVisibility(View.GONE);
				this.tv_zaoque.setVisibility(View.GONE);
				this.ll_wanbuka.setVisibility(View.GONE);
				// 地址显示
				this.tv_addreszao.setVisibility(View.GONE);
				this.tv_wanque.setVisibility(View.GONE);
				this.tv_xiabantime.setTextColor(getResources().getColor(R.color.black));
				this.tv_shangbantime.setTextColor(getResources().getColor(R.color.text_color_c));
				this.tv_up.setBackgroundResource(R.drawable.icon_bluecirclesmall);
				this.tv_down.setBackgroundResource(R.drawable.icon_graycirclesmall);
				linearParams.height = DensityUtil.dip2px(this, 250);// 控件的高强制设成20
				linearParams.width = 1;// 控件的宽强制设成30
				this.view_.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件</pre>
				int zaoTime = compareDate(dataTime, datezao);
				switch (zaoTime) {
					case 1:
						// 迟到
						this.tv_shangban.setText("迟到打卡");
						break;
					case 0:
					case -1:
						this.tv_shangban.setText("签到打卡");
						break;
					default:
						break;
				}
				break;
			case 1:
				// 早上未打卡、时间到下午
				this.tv_toptext.setText("生命不是一场赛跑，而是一场旅行");
				this.ll_wan.setBackgroundColor(getResources().getColor(R.color.wan_color));
				this.ll_zao.setBackgroundColor(getResources().getColor(R.color.white));
				// 早上
				this.tv_zaoque.setVisibility(View.VISIBLE);
				this.tv_zaoque.setText("缺卡");
				this.ll_zaodaka.setVisibility(View.GONE);
				this.ll_zaobuka.setVisibility(View.VISIBLE);
				this.tv_addreszao.setVisibility(View.GONE);
				// 下午
				this.ll_wanbuka.setVisibility(View.GONE);
				this.ll_wandaka.setVisibility(View.VISIBLE);
				this.tv_addreswan.setVisibility(View.GONE);
				this.tv_wanque.setVisibility(View.GONE);
				this.tv_xiabantime.setTextColor(getResources().getColor(R.color.text_color_c));
				this.tv_shangbantime.setTextColor(getResources().getColor(R.color.black));
				this.tv_up.setBackgroundResource(R.drawable.icon_graycirclesmall);
				this.tv_down.setBackgroundResource(R.drawable.icon_bluecirclesmall);
				linearParams.height = DensityUtil.dip2px(this, 100);// 控件的高强制设成20
				linearParams.width = 1;// 控件的宽强制设成30
				this.view_.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件</pre>
				int wanTime = compareDate(dataTime, datewan);
				switch (wanTime) {
					case 1:
					case 0:
					case -1:
						// 下班
						this.tv_xiaban.setText("下班打卡");
						break;
					default:
						break;
				}
				break;
			case 2:
				// 早上已打卡、显示下午打卡
				this.tv_toptext.setText("生命不是一场赛跑，而是一场旅行");
				this.ll_wan.setBackgroundColor(getResources().getColor(R.color.wan_color));
				this.ll_zao.setBackgroundColor(getResources().getColor(R.color.white));
				// 早上显示地点，打卡时间等
				this.ll_zaodaka.setVisibility(View.GONE);
				this.ll_zaobuka.setVisibility(View.GONE);
				this.tv_zaoque.setVisibility(View.VISIBLE);
				this.tv_zaoque.setText("迟到");
				this.tv_addreszao.setVisibility(View.VISIBLE);
				// 显示下午打卡
				this.ll_wandaka.setVisibility(View.VISIBLE);
				this.tv_addreswan.setVisibility(View.GONE);
				this.tv_wanque.setVisibility(View.GONE);
				this.ll_wanbuka.setVisibility(View.GONE);
				this.tv_xiabantime.setTextColor(getResources().getColor(R.color.text_color_c));
				this.tv_shangbantime.setTextColor(getResources().getColor(R.color.black));
				this.tv_up.setBackgroundResource(R.drawable.icon_graycirclesmall);
				this.tv_down.setBackgroundResource(R.drawable.icon_bluecirclesmall);
				linearParams.height = DensityUtil.dip2px(this, 100);// 控件的高强制设成20
				linearParams.width = 1;// 控件的宽强制设成30
				this.view_.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件
				this.tv_xiaban.setText("下班打卡");
				break;
			case 3:
				// 早上下午都已打卡
				this.tv_toptext.setText("一天的工作您辛苦啦");
				this.ll_wan.setBackgroundColor(getResources().getColor(R.color.white));
				this.ll_zao.setBackgroundColor(getResources().getColor(R.color.white));
				// 早上显示地点，打卡时间等
				this.ll_zaodaka.setVisibility(View.GONE);
				this.ll_zaobuka.setVisibility(View.GONE);
				this.tv_zaoque.setVisibility(View.VISIBLE);
				this.tv_zaoque.setText("正常");
				this.tv_zaoque.setBackground(getResources().getDrawable(R.drawable.icon_bluejuxing));
				this.tv_addreszao.setVisibility(View.VISIBLE);
				// 下午显示地点，打卡时间等
				this.ll_wandaka.setVisibility(View.GONE);
				this.tv_addreswan.setVisibility(View.VISIBLE);
				this.tv_wanque.setVisibility(View.VISIBLE);
				this.tv_wanque.setText("迟到");
				this.ll_wanbuka.setVisibility(View.GONE);
				this.tv_xiabantime.setTextColor(getResources().getColor(R.color.black));
				this.tv_shangbantime.setTextColor(getResources().getColor(R.color.black));
				this.tv_up.setBackgroundResource(R.drawable.icon_graycirclesmall);
				this.tv_down.setBackgroundResource(R.drawable.icon_graycirclesmall);
				linearParams.height = DensityUtil.dip2px(this, 100);// 控件的高强制设成20
				linearParams.width = 1;// 控件的宽强制设成30
				this.view_.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件
				this.tv_xiaban.setText("下班打卡");
				break;
			default:
				break;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.henghao.wenbo.ActivityFragmentSupport#initData()
	 */
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		// 时间计时
		new TimeThread().start();
		this.mLocationClient = new LocationClient(this);
		this.mLocationClient.registerLocationListener(this.myListener); // 注册监听
		// /LocationClientOption类用来设置定位SDK的定位方式，
		LocationClientOption option = new LocationClientOption(); // 以下是给定位设置参数
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		this.mLocationClient.setLocOption(option);
		this.mLocationClient.start();

	}

	@OnClick({
	        R.id.ll_clickzaodaka, R.id.ll_clickwandaka, R.id.tv_zaoshengqing, R.id.tv_wanshengqing
	})
	private void viewClick(View v) {
		if (this.address == null || ToolsKit.isEmpty(this.address)) {
			return;
		}
		switch (v.getId()) {
			case R.id.ll_clickzaodaka:

				showLoginAlert("打卡备注 ", this.address, "请填写打卡备注（选填）");
				break;
			case R.id.ll_clickwandaka:

				showLoginAlert("打卡备注 ", this.address, "请填写打卡备注（选填）");
				break;
			case R.id.tv_zaoshengqing:
				// 早申请
				showLoginAlert("缺卡备注 ", this.address, "请填写缺卡原因（选填）");
				break;
			case R.id.tv_wanshengqing:
				// 晚申请
				showLoginAlert("缺卡备注 ", this.address, "请填写缺卡原因（选填）");
				break;
			default:
				break;
		}
	}

	/**
	 * 用于显示时间
	 * 〈一句话功能简述〉
	 * 〈功能详细描述〉
	 * @author zhangxianwen
	 * @version HDMNV100R001, 2016-11-30
	 * @see [相关类/方法]
	 * @since [产品/模块版本]
	 */
	public class TimeThread extends Thread {
		@Override
		public void run() {
			do {
				try {
					Thread.sleep(1000);
					Message msg = new Message();
					msg.what = msgKey1;
					KQActivity.this.mHandler.sendMessage(msg);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			while (true);
		}
	}

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case msgKey1:
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
					Date date = new Date();
					KQActivity.this.tv_zaotime.setText(sdf.format(date));
					KQActivity.this.tv_wantime.setText(sdf.format(date));
					break;
				default:
					break;
			}
		}
	};

	private class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null) {
				return;
			}
			double longitude = location.getLongitude();
			double latitude = location.getLatitude();
			if (location.getStreet() == null) {
				KQActivity.this.address = location.getAddrStr();
			}
			else {
				KQActivity.this.address = location.getStreet();
			}
			LatLng p1LL = new LatLng(26.626491121790554, 106.66190367889827);
			LatLng p2LL = new LatLng(latitude, longitude);
			final double distance = DistanceUtil.getDistance(p1LL, p2LL);
			if (!validateInternetmain()) {
				KQActivity.this.address = null;
			}
			whatWhere(distance, KQActivity.this.address);
			// latitude: 26.626491121790554, longitude: 106.66190367889827
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	/**
	 * 是否进入打卡范围
	 * 〈一句话功能简述〉
	 * 〈功能详细描述〉
	 * @param distance
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	private void whatWhere(double distance, String where) {
		// TODO Auto-generated method stub
		if (distance > 500 || where == null || where.equals("")) {
			whatViewClick(false);
		}
		else {
			whatViewClick(true);
		}
		if (where == null || ToolsKit.isEmpty(where)) {
			// 点击事件取消
			// 灰色
			this.ll_clickwandaka.setBackgroundResource(R.drawable.icon_grayciecle);
			this.ll_clickzaodaka.setBackgroundResource(R.drawable.icon_grayciecle);
			this.img_zaowhere.setImageResource(R.drawable.icon_orangetip);
			this.tv_zaowhere.setText("不在考勤范围：定位失败");
			this.img_wanwhere.setImageResource(R.drawable.icon_orangetip);
			this.tv_wanwhere.setText("不在考勤范围：定位失败");
		}
		else {
			// 点击事件
			// 蓝色
			this.ll_clickwandaka.setBackgroundResource(R.drawable.icon_bluecirclebig);
			// 黄色
			this.ll_clickzaodaka.setBackgroundResource(R.drawable.icon_orangecircle);
			if (distance <= 500) {
				// 进入打卡范围
				this.img_zaowhere.setImageResource(R.drawable.icon_greengougou);
				this.tv_zaowhere.setText("已进入考勤范围：" + where);
				this.img_wanwhere.setImageResource(R.drawable.icon_greengougou);
				this.tv_wanwhere.setText("已进入考勤范围：" + where);
			}
			else {
				// 不在打卡范围
				this.img_zaowhere.setImageResource(R.drawable.icon_orangetip);
				this.tv_zaowhere.setText("不在考勤范围：" + where);
				this.img_wanwhere.setImageResource(R.drawable.icon_orangetip);
				this.tv_wanwhere.setText("不在考勤范围：" + where);
			}

		}

	}

	/**
	 * 比较时间前后
	 * 〈一句话功能简述〉
	 * 〈功能详细描述〉
	 * @param dt1
	 * @param dt2
	 * @return
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public int compareDate(java.util.Date dt1, java.util.Date dt2) {
		if (dt1.getTime() > dt2.getTime()) {
			System.out.println("dt1 在dt2前");
			return 1;
		}
		else if (dt1.getTime() < dt2.getTime()) {
			System.out.println("dt1在dt2后");
			return -1;
		}
		else {// 相等
			return 0;
		}
	}

	/**
	 * 〈一句话功能简述〉
	 * 〈功能详细描述〉
	 * @return apm=0 表示上午，apm=1表示下午。
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	private int compare_date() {
		long time = System.currentTimeMillis();
		final Calendar mCalendar = Calendar.getInstance();
		mCalendar.setTimeInMillis(time);

		int hour = mCalendar.get(Calendar.HOUR);
		int apm = mCalendar.get(Calendar.AM_PM);
		return apm;
	}

	/**
	 * 〈一句话功能简述〉
	 * 〈功能详细描述〉
	 * @param isClick
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	private void whatViewClick(boolean isClick) {
		this.ll_clickzaodaka.setClickable(isClick);
		this.ll_clickwandaka.setClickable(isClick);
	}

}
