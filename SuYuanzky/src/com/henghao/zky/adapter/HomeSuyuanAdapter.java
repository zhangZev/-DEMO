package com.henghao.zky.adapter;

import java.util.List;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.henghao.zky.ActivityFragmentSupport;
import com.henghao.zky.Constant;
import com.henghao.zky.R;
import com.henghao.zky.activity.CommonWebActivity;
import com.henghao.zky.model.entity.SuyuanEntity;
import com.lidroid.xutils.BitmapUtils;

/**
 * suyuan〈一句话功能简述〉 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HomeSuyuanAdapter extends ArrayAdapter<SuyuanEntity> {

	private final LayoutInflater inflater;

	private final BitmapUtils mBitmapUtils;

	private final ActivityFragmentSupport mActivityFragmentSupport;

	public HomeSuyuanAdapter(ActivityFragmentSupport activityFragment, List<SuyuanEntity> mList) {
		super(activityFragment, R.layout.item_suyuanlist, mList);
		this.mActivityFragmentSupport = activityFragment;
		this.inflater = LayoutInflater.from(activityFragment);
		this.mBitmapUtils = new BitmapUtils(activityFragment, Constant.CACHE_DIR_PATH);
		this.mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_fail_big);
		this.mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HodlerView mHodlerView = null;
		if (convertView == null) {
			mHodlerView = new HodlerView();
			convertView = this.inflater.inflate(R.layout.item_suyuanlist, null);
			mHodlerView.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			mHodlerView.img_tip = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(mHodlerView);
		}
		else {
			mHodlerView = (HodlerView) convertView.getTag();
		}
		final SuyuanEntity mEntity = getItem(position);
		mHodlerView.tv_title.setText(mEntity.getTitle());
		mHodlerView.img_tip.setImageResource(mEntity.getImage());
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra(Constant.INTNET_URL, mEntity.getUrl());
				intent.putExtra(Constant.INTNET_TITLE, "新闻动态");
				intent.setClass(HomeSuyuanAdapter.this.mActivityFragmentSupport, CommonWebActivity.class);
				HomeSuyuanAdapter.this.mActivityFragmentSupport.startActivity(intent);
			}
		});
		return convertView;
	}

	private class HodlerView {

		ImageView img_tip;

		TextView tv_title;

	}
}
