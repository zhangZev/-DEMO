package com.henghao.dingding.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.dingding.ActivityFragmentSupport;
import com.henghao.dingding.R;

public class DialogAlertUP extends Dialog implements OnClickListener {
	private TextView dialog_ok;
	private TextView dialog_cancel;
	private final DialogAlertListener listener;
	private final String title;
	private String description;
	private Object param;
	private String s_cancel;
	private String s_ok;
	private String time;
	private String where;
	private String hintMS;
	private ActivityFragmentSupport mactivity;

	public interface DialogAlertListener {
		public void onDialogCreate(Dialog dlg, Object param);

		public void onDialogOk(Dialog dlg, Object param);

		public void onDialogCancel(Dialog dlg, Object param);
	}

	public DialogAlertUP(ActivityFragmentSupport context, DialogAlertListener listener, String title,
	        String description, String s_cancel, String s_ok, String time, String where, String hintMS) {
		super(context, R.style.dialog_alert);
		this.mactivity = context;
		this.listener = listener;
		this.title = title;
		this.description = description;
		this.s_cancel = s_cancel;
		this.s_ok = s_ok;
		this.hintMS = hintMS;
		this.time = time;
		this.where = where;
	}

	public DialogAlertUP(Context context, DialogAlertListener listener, String title, Object param) {
		super(context, R.style.dialog_alert);
		this.listener = listener;
		this.title = title;
		this.param = param;
	}

	private EditText m_edit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_alert_editup);
		setCancelable(false);
		setCanceledOnTouchOutside(true);
		this.dialog_ok = (TextView) findViewById(R.id.dialog_ok);
		TextView tv_where = (TextView) findViewById(R.id.tv_where);
		tv_where.setText(this.where);
		TextView tv_time = (TextView) findViewById(R.id.tv_time);
		tv_time.setText(this.time);
		LinearLayout ll_alert = (LinearLayout) findViewById(R.id.ll_alert);
		LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) ll_alert.getLayoutParams();
		linearParams.width = (int) (this.mactivity.mScreenWidth * 0.8);// 控件的宽强制设成30
		ll_alert.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件</pre>
		this.dialog_ok.setOnClickListener(this);
		this.dialog_cancel = (TextView) findViewById(R.id.dialog_cancel);
		this.dialog_cancel.setOnClickListener(this);
		TextView txtTitle = (TextView) findViewById(R.id.dialog_title);
		txtTitle.setText(this.title);
		this.m_edit = (EditText) findViewById(R.id.m_edit);
		this.m_edit.setHint(this.hintMS);
		if (null != this.s_ok) {
			this.dialog_ok.setText(this.s_ok);
		}
		if (null != this.s_cancel) {
			this.dialog_cancel.setText(this.s_cancel);
		}

		if (this.listener != null) {
			this.listener.onDialogCreate(this, this.param);
		}

	}

	@Override
	public void onClick(View v) {
		if (v == this.dialog_ok) {
			onBtnOk();
		}
		else if (v == this.dialog_cancel) {
			onBtnCancel();
		}
	}

	private void onBtnOk() {
		// cancel();
		if (this.listener != null) {
			this.listener.onDialogOk(this, ToolsKit.getEditText(this.m_edit));
		}
	}

	private void onBtnCancel() {
		cancel();
		if (this.listener != null) {
			this.listener.onDialogCancel(this, this.param);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			onBtnCancel();
		}
		return super.onKeyDown(keyCode, event);
	}
}
