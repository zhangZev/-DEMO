package com.henghao.suyuan.utils;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.henghao.suyuan.ActivityFragmentSupport;
import com.henghao.suyuan.Constant;
import com.henghao.suyuan.R;
import com.henghao.suyuan.activity.CommonWebActivity;

/**
 * Created by Administrator on 2015/8/3.
 */
public class PopupWindowHelper {

	private final View popupView;

	private PopupWindow mPopupWindow;

	private static final int TYPE_WRAP_CONTENT = 0, TYPE_MATCH_PARENT = 1;

	public PopupWindowHelper(final ActivityFragmentSupport mContext) {
		this.popupView = LayoutInflater.from(mContext).inflate(R.layout.activity_showview, null);
		TextView tv_delete = (TextView) this.popupView.findViewById(R.id.tv_text);
		tv_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra(
				        Constant.INTNET_DATA,
				        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中国科学院浙江数字内容研究院是由浙江省科技厅、绍兴市政府与中科院自动化研究所三方共建的重要创新载体，为从事公益服务的事业单位，实行理事会领导下的院长负责制，理事会由三方共同委派人员组成。研究院将结合绍兴市、浙江省乃至长三角地区产业培育的需要，重点开展海量信息处理、增强现实技术以及3D视觉工业标准化等技术研发，打造国家级水平的文化科技创新基地、高新产业育成基地和高层次创新创业人才培育基地，带动区域文化科技领域的产业集聚发展，为推动绍兴产业转型升级和经济创新发展增强助力。<br><br>地址：浙江省绍兴滨海新城马欢路398号滨海新城科创园A座2楼 <br>手机：18257596111、13911822228<br>传真：0575-88300879<br>邮箱：qubo@zjcas.ac.cn<br>电话：0575-88300876<br>版本号：1.0");
				intent.putExtra(Constant.INTNET_TITLE, "关于我们");
				intent.setClass(mContext, CommonWebActivity.class);
				mContext.startActivity(intent);
			}
		});
	}

	public void showAsDropDown(View anchor) {
		initPopupWindow(TYPE_WRAP_CONTENT);
		this.mPopupWindow.showAsDropDown(anchor);
	}

	public void showAsDropDown(View anchor, int xoff, int yoff) {
		initPopupWindow(TYPE_WRAP_CONTENT);
		this.mPopupWindow.showAsDropDown(anchor, xoff, yoff);
	}

	public void showAtLocation(View parent, int gravity, int x, int y) {
		initPopupWindow(TYPE_WRAP_CONTENT);
		this.mPopupWindow.showAtLocation(parent, gravity, x, y);
	}

	public void dismiss() {
		if (this.mPopupWindow.isShowing()) {
			this.mPopupWindow.dismiss();
		}
	}

	public void showAsPopUp(View anchor) {
		showAsPopUp(anchor, 0, 0);
	}

	public void showAsPopUp(View anchor, int xoff, int yoff) {
		initPopupWindow(TYPE_WRAP_CONTENT);
		this.mPopupWindow.setAnimationStyle(R.style.AnimationUpPopup);
		this.popupView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		int height = this.popupView.getMeasuredHeight();
		int[] location = new int[2];
		anchor.getLocationInWindow(location);
		this.mPopupWindow.showAtLocation(anchor, Gravity.LEFT | Gravity.TOP, location[0] + xoff, location[1] - height
		        + yoff);
	}

	public void showFromTop(View anchor) {
		initPopupWindow(TYPE_MATCH_PARENT);
		this.mPopupWindow.setAnimationStyle(R.style.AnimationFromTop);
		this.mPopupWindow.showAtLocation(anchor, Gravity.LEFT | Gravity.TOP, 0, getStatusBarHeight());
	}

	/**
	 * touch outside dismiss the popupwindow, default is ture
	 * @param isCancelable
	 */
	public void setCancelable(boolean isCancelable) {
		if (isCancelable) {
			this.mPopupWindow.setOutsideTouchable(true);
			this.mPopupWindow.setFocusable(true);
		}
		else {
			this.mPopupWindow.setOutsideTouchable(false);
			this.mPopupWindow.setFocusable(false);
		}
	}

	public void initPopupWindow(int type) {
		if (type == TYPE_WRAP_CONTENT) {
			this.mPopupWindow = new PopupWindow(this.popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
			        ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		else if (type == TYPE_MATCH_PARENT) {
			this.mPopupWindow = new PopupWindow(this.popupView, ViewGroup.LayoutParams.MATCH_PARENT,
			        ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		this.mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		setCancelable(true);
	}

	private int getStatusBarHeight() {
		return Math.round(25 * Resources.getSystem().getDisplayMetrics().density);
	}
}
