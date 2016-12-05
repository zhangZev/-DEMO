package com.henghao.dingding.adapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.benefit.buy.library.utils.tools.ToolsDate;
import com.henghao.dingding.ActivityFragmentSupport;
import com.henghao.dingding.Constant;
import com.henghao.dingding.R;
import com.henghao.dingding.utils.DensityUtil;
import com.lidroid.xutils.BitmapUtils;

/**
 * 巡检列表适配器〈一句话功能简述〉 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class KQAdapter extends ArrayAdapter<String> {

	private final LayoutInflater inflater;

	private final BitmapUtils mBitmapUtils;

	private final ActivityFragmentSupport mActivityFragmentSupport;

	private final List<String> mList;

	private static final int msgKey1 = 1;

	private HodlerView mHodlerView;

	private String time;

	public KQAdapter(ActivityFragmentSupport activityFragment, List<String> list) {
		super(activityFragment, R.layout.listview_item, list);
		this.mActivityFragmentSupport = activityFragment;
		this.mList = list;
		this.inflater = LayoutInflater.from(activityFragment);
		this.mBitmapUtils = new BitmapUtils(activityFragment, Constant.CACHE_DIR_PATH);
		this.mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_fail_big);
		this.mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		this.mHodlerView = null;
		if (convertView == null) {
			this.mHodlerView = new HodlerView();
			convertView = this.inflater.inflate(R.layout.listview_item, null);
			this.mHodlerView.tv_up = (TextView) convertView.findViewById(R.id.tv_up);
			this.mHodlerView.tv_shangbantime = (TextView) convertView.findViewById(R.id.tv_shangbantime);
			this.mHodlerView.tv_shangban = (TextView) convertView.findViewById(R.id.tv_shangban);
			this.mHodlerView.tv_zaotime = (TextView) convertView.findViewById(R.id.tv_zaotime);
			this.mHodlerView.tv_zaowhere = (TextView) convertView.findViewById(R.id.tv_zaowhere);
			this.mHodlerView.ll_zaobuka = (LinearLayout) convertView.findViewById(R.id.ll_zaobuka);
			this.mHodlerView.ll_zaodaka = (LinearLayout) convertView.findViewById(R.id.ll_zaodaka);
			this.mHodlerView.ll_clickzaodaka = (LinearLayout) convertView.findViewById(R.id.ll_clickzaodaka);
			this.mHodlerView.view_ = convertView.findViewById(R.id.view_);
			convertView.setTag(this.mHodlerView);
		}
		else {
			this.mHodlerView = (HodlerView) convertView.getTag();
		}
		LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) this.mHodlerView.view_.getLayoutParams(); // 取控件textView当前的布局参数
		java.util.Date dataTime = ToolsDate.parseDateTime(ToolsDate.getCurrentDateTime());
		java.util.Date datezao = ToolsDate.parseDateTime(ToolsDate.getCurrentDate() + " 09:00:00");
		java.util.Date datewan = ToolsDate.parseDateTime(ToolsDate.getCurrentDate() + " 18:00:00");

		// 判断是早上还是晚上 apm=0 表示上午，apm=1表示下午。
		int whatApm = compare_date();
		whatViewShow(position, linearParams, dataTime, datezao, whatApm);

		new TimeThread().start();
		return convertView;
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
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
					Date date = new Date();
					KQAdapter.this.time = sdf.format(date);
					Message msg = new Message();
					msg.what = msgKey1;
					KQAdapter.this.mHandler.sendMessage(msg);
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
					KQAdapter.this.mHodlerView.tv_zaotime.setText(KQAdapter.this.time);
					break;
				default:
					break;
			}
		}
	};

	/**
	 * 〈一句话功能简述〉
	 * 〈功能详细描述〉
	 * @param position
	 * @param linearParams
	 * @param dataTime
	 * @param datezao
	 * @param whatApm
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	private void whatViewShow(int position, LinearLayout.LayoutParams linearParams, java.util.Date dataTime,
	        java.util.Date datezao, int whatApm) {
		switch (position) {
			case 0:
				// 上部View
				this.mHodlerView.view_.setVisibility(View.VISIBLE);
				this.mHodlerView.tv_shangbantime.setText("上班时间：09:00");
				this.mHodlerView.tv_up.setText("上");
				if (whatApm == 0) {
					// 早上
					this.mHodlerView.ll_zaodaka.setVisibility(View.VISIBLE);
					this.mHodlerView.ll_zaobuka.setVisibility(View.GONE);
					this.mHodlerView.ll_clickzaodaka.setBackgroundResource(R.drawable.icon_orangecircle);
					this.mHodlerView.tv_up.setBackgroundResource(R.drawable.icon_bluecirclesmall);
					this.mHodlerView.tv_shangbantime.setTextColor(this.mActivityFragmentSupport.getResources()
					        .getColor(R.color.black));
					linearParams.height = DensityUtil.dip2px(this.mActivityFragmentSupport, 270);// 控件的高强制设成20
					linearParams.width = 2;// 控件的宽强制设成30
					this.mHodlerView.view_.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件</pre>
					int zaoTime = compareDate(dataTime, datezao);
					switch (zaoTime) {
						case 1:
							// 迟到
							this.mHodlerView.tv_shangban.setText("迟到打卡");
							break;
						case 0:
						case -1:
							this.mHodlerView.tv_shangban.setText("签到打卡");
							break;
						default:
							break;
					}
				}
				else {
					// 下午
					this.mHodlerView.ll_zaodaka.setVisibility(View.GONE);
					this.mHodlerView.ll_zaobuka.setVisibility(View.VISIBLE);
					this.mHodlerView.tv_up.setBackgroundResource(R.drawable.icon_grayciecle);
					this.mHodlerView.ll_clickzaodaka.setBackgroundResource(R.drawable.icon_grayciecle);
					this.mHodlerView.tv_shangbantime.setTextColor(this.mActivityFragmentSupport.getResources()
					        .getColor(R.color.text_color_c));
					linearParams.height = DensityUtil.dip2px(this.mActivityFragmentSupport, 80);// 控件的高强制设成20
					linearParams.width = 2;// 控件的宽强制设成30
					this.mHodlerView.view_.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件</pre>
					int zaoTime = compareDate(dataTime, datezao);
					switch (zaoTime) {
						case 1:
							// 迟到
							this.mHodlerView.tv_shangban.setText("迟到打卡");
							break;
						case 0:
						case -1:
							this.mHodlerView.tv_shangban.setText("签到打卡");
							break;
						default:
							break;
					}
				}
				break;
			case 1:
				// 下部View
				this.mHodlerView.view_.setVisibility(View.GONE);
				this.mHodlerView.tv_up.setText("下");
				this.mHodlerView.tv_shangbantime.setText("下班时间：18:00");
				if (whatApm == 0) {
					// 早上
					this.mHodlerView.ll_zaodaka.setVisibility(View.GONE);
					this.mHodlerView.ll_zaobuka.setVisibility(View.GONE);
					this.mHodlerView.tv_up.setBackgroundResource(R.drawable.icon_grayciecle);
					this.mHodlerView.ll_clickzaodaka.setBackgroundResource(R.drawable.icon_grayciecle);
					this.mHodlerView.tv_shangbantime.setTextColor(this.mActivityFragmentSupport.getResources()
					        .getColor(R.color.text_color_c));
				}
				else {
					// 下午
					this.mHodlerView.ll_zaodaka.setVisibility(View.VISIBLE);
					this.mHodlerView.ll_zaobuka.setVisibility(View.GONE);
					this.mHodlerView.tv_up.setBackgroundResource(R.drawable.icon_bluecirclesmall);
					this.mHodlerView.ll_clickzaodaka.setBackgroundResource(R.drawable.icon_bluecirclebig);
					this.mHodlerView.tv_shangbantime.setTextColor(this.mActivityFragmentSupport.getResources()
					        .getColor(R.color.black));
					linearParams.height = DensityUtil.dip2px(this.mActivityFragmentSupport, 100);// 控件的高强制设成20
					linearParams.width = 2;// 控件的宽强制设成30
					this.mHodlerView.view_.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件</pre>
					int zaoTime = compareDate(dataTime, datezao);
					switch (zaoTime) {
						case 1:
						case 0:
						case -1:
							// 下班
							this.mHodlerView.tv_shangban.setText("下班打卡");
						default:
							break;
					}
				}
				break;

			default:
				break;
		}
	}

	/**
	 * 判断是否迟到 是否早退
	 * 〈一句话功能简述〉
	 * 〈功能详细描述〉
	 * @param dt1 当前时间
	 * @param dt2 规定时间
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

	/*
	 * (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (this.mList.size() > 2) {
			return 2;
		}
		return super.getCount();
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

	private class HodlerView {
		View view_;

		/**
		 * 上、下
		 */
		TextView tv_up;

		/**
		 * 上班下班时间
		 */
		TextView tv_shangbantime;

		/**
		 * 签到按钮字
		 */
		TextView tv_shangban;
		/**
		 * 当前时间
		 */
		TextView tv_zaotime;
		/**
		 * 当前位置
		 */
		TextView tv_zaowhere;

		/**
		 * 申请补卡，更新补卡View
		 */
		LinearLayout ll_zaobuka;

		/**
		 * 打卡View
		 */
		LinearLayout ll_zaodaka;

		/**
		 * 打卡View
		 */
		LinearLayout ll_clickzaodaka;

	}
}
