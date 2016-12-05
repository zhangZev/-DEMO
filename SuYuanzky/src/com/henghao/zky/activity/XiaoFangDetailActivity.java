package com.henghao.zky.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.henghao.zky.ActivityFragmentSupport;
import com.henghao.zky.Constant;
import com.henghao.zky.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 扫描之后详情
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2016-11-17
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class XiaoFangDetailActivity extends ActivityFragmentSupport {

	@ViewInject(R.id.image_view)
	private ImageView imageView;

	@ViewInject(R.id.tv_des)
	private TextView tv_des;
	@ViewInject(R.id.tv_title)
	private TextView tv_title;
	@ViewInject(R.id.tv_data)
	private TextView tv_data;
	@ViewInject(R.id.tv_canshu)
	private TextView tv_canshu;
	@ViewInject(R.id.tv_changjia)
	private TextView tv_changjia;
	@ViewInject(R.id.tv_jianjie)
	private TextView tv_jianjie;
	@ViewInject(R.id.data_zihua)
	private TextView data_zihua;

	@ViewInject(R.id.ll_view)
	private LinearLayout ll_view;

	@ViewInject(R.id.ll_changjia)
	private LinearLayout ll_changjia;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mActivityFragmentView.viewMain(R.layout.activity_xiaofang);
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
		initWithBar();
		this.mLeftTextView.setText("NFC详情");
		this.mLeftTextView.setVisibility(View.VISIBLE);
		setView();
	}

	/**
	 * 〈一句话功能简述〉
	 * 〈功能详细描述〉
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	private void setView() {
		int type = getIntent().getIntExtra(Constant.INTNET_TYPE, 0);
		String title = null;
		String data = null;
		String jianjie = null;
		String changjia = null;
		String canshu = null;
		int image = 0;
		switch (type) {
			case 1:
				image = R.drawable.icon_naifeng;
				title = "大庆奶粉";
				data = "2016-11-20";
				jianjie = "庆老奶粉（全脂甜奶粉），源于黑龙江大草原优质牛奶，45年优良品质保证。大庆老奶粉以优质生牛乳为主要原料，添加白砂糖，奶香浓郁，奶质爽滑润口，是您可放心选择的健康营养食品。 纯香味浓 品质首选";
				canshu = "400g/袋";
				changjia = "大庆乳品厂有限责任公司";
				break;
			case 2:
				this.ll_changjia.setVisibility(View.GONE);
				image = R.drawable.icon_zihua;
				title = "海纳百川字画";
				data = "张晓苏";
				this.data_zihua.setText("作者:");
				jianjie = "海纳百川、有容乃大";
				canshu = "1.5m*2.5m";
				changjia = "";
				break;
			case 3:
				image = R.drawable.icon_maotai;
				title = "茅台集团盛世玉液五星佳品52°浓香型白酒";
				data = "2015-10";
				jianjie = "贵州茅台酒厂集团由中国贵州茅台酒厂有限责任公司及其全资子公司、控股公司、参股公司等近 20 家企业构成，集团所涉足的产业领域包括白酒、啤酒、葡萄酒、红酒、证券、银行、保险、物业、科研等。";
				canshu = "500ml";
				changjia = "贵州茅台酒厂集团技术开发公司";
				break;
			case 4:
				image = R.drawable.icon_yan;
				title = "贵烟（软高遵）";
				data = "2016-10";
				jianjie = "贵烟（软高遵）香烟为贵烟品牌旗下的一款香烟。口味特点： 云贵高原清甜的烟草本香修饰的焦甜，面包香，香草香，可可香，让人抽吸时体会到浓郁，丰满，谐调的复合香韵，生津，回甜。";
				canshu = "84mm 软";
				changjia = "贵州中烟工业有限责任公司";
				break;
			case 5:
				image = R.drawable.icon_yimiao;
				title = "鼻炎一针灵 兔药鼻福 感冒鼻炎肺炎喷嚏鼻涕宠物养兔疫苗";
				data = "2015-04-22 10:00";
				jianjie = "【适 应 证】兔巴氏杆菌病、鼻炎、肺炎、产后感染等。症见精神沉郁，被毛粗乱，食欲减退或废绝，呼吸急促，体温升高至40℃以上，鼻流浆液性或脓性鼻液，口和鼻孔周围皮肤发炎，眼睑肿胀，结膜潮红，眼屎增加下痢，粪便中混有粘液或血液，母兔产后注射本品可有效预防和产后感染。";
				canshu = "20ml：土霉素1g（100万单位）";
				changjia = "广州灌文兔业";
				break;
			default:
				this.ll_view.setVisibility(View.GONE);
				this.imageView.setVisibility(View.GONE);
				this.tv_des.setText("此NFC标签无记录");
				return;
		}
		this.imageView.setImageResource(image);
		this.tv_title.setText(title);
		this.tv_data.setText(data);
		this.tv_canshu.setText(canshu);
		this.tv_changjia.setText(changjia);
		this.tv_jianjie.setText(jianjie);

	}

	/*
	 * (non-Javadoc)
	 * @see com.henghao.wenbo.ActivityFragmentSupport#initData()
	 */
	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}
