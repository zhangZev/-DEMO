package com.henghao.zky.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.benefit.buy.library.utils.tools.ToolsKit;
import com.benefit.buy.library.utils.tools.ToolsToast;
import com.benefit.buy.library.viewpagerindicator.CirclePageIndicator;
import com.henghao.zky.Constant;
import com.henghao.zky.R;
import com.henghao.zky.activity.MainActivity;
import com.henghao.zky.activity.XiaoFangDetailActivity;
import com.henghao.zky.adapter.HomeSuyuanAdapter;
import com.henghao.zky.adapter.ImagePagerAdapter;
import com.henghao.zky.model.entity.SuyuanEntity;
import com.henghao.zky.utils.CommonAutoViewpager;
import com.henghao.zky.views.viewpage.AutoScrollViewPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zbar.lib.zxing.CaptureActivity;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2016年8月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HomeFragment extends FragmentSupport {

	@ViewInject(R.id.tv_setnfc)
	public TextView tv_setnfc;

	@ViewInject(R.id.lay_1)
	public LinearLayout mNfcLayout;

	public AutoScrollViewPager viewPager;

	@ViewInject(R.id.m_listview)
	private ListView mListView;

	public CirclePageIndicator indicator;

	private NfcAdapter mNfcadapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		this.mActivityFragmentView.viewMain(R.layout.fragment_home);
		this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
		this.mActivityFragmentView.viewEmptyGone();
		this.mActivityFragmentView.viewLoading(View.GONE);
		ViewUtils.inject(this, this.mActivityFragmentView);
		initWidget();
		initData();
		return this.mActivityFragmentView;
	}

	private void initData() {
		// TODO Auto-generated method stub
		View common_viewpages = LayoutInflater.from(this.mActivity).inflate(R.layout.common_viewpages, this.mListView,
		        false);
		this.viewPager = (AutoScrollViewPager) common_viewpages.findViewById(R.id.view_pager);
		this.indicator = (CirclePageIndicator) common_viewpages.findViewById(R.id.indicator);
		CommonAutoViewpager.viewPageAdapter(this.mActivity, this.viewPager, this.indicator);
		List<SuyuanEntity> mList = new ArrayList<SuyuanEntity>();
		SuyuanEntity entity1 = new SuyuanEntity();
		entity1.setTitle("中科院与深圳市签署合作共建国科大深圳校区备忘录");
		entity1.setImage(R.drawable.image_1);
		entity1.setUrl("http://news.ucas.ac.cn/index.php?option=com_content&view=article&id=227550&catid=340&Itemid=176");
		mList.add(entity1);

		SuyuanEntity entity2 = new SuyuanEntity();
		entity2.setTitle("中国科学院浙江数字内容研究院副院长曲波接受新华网专访");
		entity2.setImage(R.drawable.image_2);
		entity2.setUrl("http://www.ln.xinhuanet.com/topic/2016/xinhuahuiketing39/index.html");
		mList.add(entity2);

		SuyuanEntity entity3 = new SuyuanEntity();
		entity3.setTitle("【物联网之声】防伪溯源助力食品安全");
		entity3.setImage(R.drawable.image_3);
		entity3.setUrl("http://www.kejixun.com/article/160831/217356.shtml");
		mList.add(entity3);
		SuyuanEntity entity4 = new SuyuanEntity();
		entity4.setTitle("浅析物联网领域常见的几大核心关键技术");
		entity4.setImage(R.drawable.image_4);
		entity4.setUrl("http://iot.ofweek.com/2016-11/ART-132209-11000-30069783.html");
		mList.add(entity4);

		this.mListView.addHeaderView(common_viewpages);
		HomeSuyuanAdapter mAdapter = new HomeSuyuanAdapter(this.mActivity, mList);
		this.mListView.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * 标题操作
	 * 〈一句话功能简述〉
	 * 〈功能详细描述〉
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	private void initwithContent() {
		// TODO Auto-generated method stub
		this.mActivityFragmentView.getNavitionBarView().setVisibility(View.GONE);
	}

	public void setNfcText() {
		this.mNfcLayout.setVisibility(View.VISIBLE);
		if (this.mNfcadapter.isEnabled()) {
			this.tv_setnfc.setText("NFC监听中...");
		}
		else {
			this.tv_setnfc.setText("请在设置中打开NFC");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.henghao.suyuan.fragment.FragmentSupport#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (this.mNfcadapter == null) {
			this.mNfcLayout.setVisibility(View.VISIBLE);
			this.tv_setnfc.setText("该手机不具备NFC扫描功能");
		}
		else {
			setNfcText();
		}
	}

	public void initWidget() {
		NfcManager manager = (NfcManager) this.mActivity.getSystemService(Context.NFC_SERVICE);
		this.mNfcadapter = manager.getDefaultAdapter();
		initwithContent();
	}

	@OnClick({
	        R.id.m_submit1, R.id.m_submit2, R.id.m_submit3, R.id.lay_1, R.id.m_layout
	})
	private void viewClick(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
			case R.id.m_submit1:
				// NFC扫描
				if (this.mNfcadapter == null) {
					ToolsToast.show(this.mActivity, "该手机不具备NFC扫描功能");
					return;
				}
				if (this.mNfcadapter.isEnabled()) {
					ToolsToast.show(this.mActivity, "请将NFC标签放置手机背后扫描");
				}
				else {
					startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
				}
				break;
			case R.id.m_submit2:
				// 二维码扫描
				intent.setClass(this.mActivity, CaptureActivity.class);
				startActivityForResult(intent, 1000);
				break;
			case R.id.m_submit3:
				// 巡检
				intent.setClass(this.mActivity, MainActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}
	}

	/**
	 * 〈一句话功能简述〉
	 * 〈功能详细描述〉
	 * @param context
	 * @return NFC是否可以开启
	 *         [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	private boolean hasNfc() {
		boolean bRet = false;
//	        if (adapter != null && adapter.isEnabled()) {
		if (this.mNfcadapter != null) {
			// adapter存在，能启用
			bRet = true;
		}
		return bRet;
	}

	/**
	 * 广告
	 * @param List<AdEntity>
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	private void homeAds(List<Integer> ads) {
		if (!ToolsKit.isEmpty(ads)) {
			this.viewPager.startAutoScroll();
			this.indicator.setVisibility(View.VISIBLE);
			if (ads.size() == 1) {
				this.indicator.setVisibility(View.GONE);
				this.viewPager.stopAutoScroll();
			}

			this.viewPager.setAdapter(new ImagePagerAdapter(this.mActivity, ads));
			this.indicator.setViewPager(this.viewPager);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/* 回调内容 */
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		switch (requestCode) {

			case 1000:
				/**
				 * 二维码返回
				 */
				String result = data.getStringExtra("result");
				Intent intent2 = new Intent();
				int type = 0;
				if (result.contains("EPL35951|3")) {
					type = 3;
				}
				else if (result.contains("EPL35951|2")) {
					type = 2;
				}
				else if (result.contains("EPL35951|1")) {
					type = 1;
				}
				else {
					type = 5;
				}
				if (type == 5) {
					ToolsToast.show(this.mActivity, result);
					return;
				}
				intent2.putExtra(Constant.INTNET_TYPE, type);
				intent2.setClass(this.mActivity, XiaoFangDetailActivity.class);
				startActivity(intent2);
				break;
		}
	}

}
