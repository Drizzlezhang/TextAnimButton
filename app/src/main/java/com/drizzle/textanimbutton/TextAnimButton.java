package com.drizzle.textanimbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.ViewAnimator;

public class TextAnimButton extends ViewAnimator {
	public static final int STATE_DEFAULT = 0;
	public static final int STATE_DONE = 1;

	private TextView tvDefault;
	private TextView tvDone;

	private String defaultText;
	private String doneText;

	private int defaultColor;
	private int doneColor;

	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;

	private int animStyle;

	private int textSize;

	private static final long RESET_STATE_DELAY_MILLIS = 2000;

	private int currentState;

	private Runnable revertStateRunnable = new Runnable() {
		@Override public void run() {
			setCurrentState(STATE_DEFAULT);
		}
	};

	public TextAnimButton(Context context) {
		this(context, null);
	}

	public TextAnimButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(getContext()).inflate(R.layout.view_send_comment_button, this, true);
		TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextAnimButton, 0, 0);
		defaultText = array.getString(R.styleable.TextAnimButton_default_text);
		doneText = array.getString(R.styleable.TextAnimButton_done_text);
		defaultColor = array.getColor(R.styleable.TextAnimButton_default_text_color, Color.WHITE);
		doneColor = array.getColor(R.styleable.TextAnimButton_done_text_color, Color.WHITE);
		textSize = array.getDimensionPixelSize(R.styleable.TextAnimButton_text_size,
			(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
		animStyle = array.getInt(R.styleable.TextAnimButton_anim_style, UP);
		array.recycle();
		initTextView();
	}

	private void initTextView() {
		tvDefault = (TextView) findViewById(R.id.tvDefault);
		tvDone = (TextView) findViewById(R.id.tvDone);
		if (defaultText == null) {
			defaultText = "";
		}
		if (doneText == null) {
			doneText = "";
		}
		tvDefault.setText(defaultText);
		tvDefault.setTextColor(defaultColor);
		tvDefault.setTextSize(px2sp(getContext(), textSize));
		tvDone.setText(doneText);
		tvDone.setTextColor(doneColor);
		tvDone.setTextSize(px2sp(getContext(), textSize));
	}

	@Override protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		currentState = STATE_DEFAULT;
	}

	@Override protected void onDetachedFromWindow() {
		removeCallbacks(revertStateRunnable);
		super.onDetachedFromWindow();
	}

	public void setCurrentState(int state) {
		if (state == currentState) {
			return;
		}
		currentState = state;
		int defaultinanim, defaultoutanim, doneinanim, doneoutanim;
		if (animStyle == UP) {
			defaultinanim = R.anim.slide_in_down;
			defaultoutanim = R.anim.slide_out_up;
			doneinanim = R.anim.slide_in_up;
			doneoutanim = R.anim.slide_out_down;
		} else if (animStyle == DOWN) {
			defaultinanim = R.anim.slide_in_up;
			defaultoutanim = R.anim.slide_out_down;
			doneinanim = R.anim.slide_in_down;
			doneoutanim = R.anim.slide_out_up;
		} else if (animStyle == LEFT) {
			defaultinanim = R.anim.slide_in_right;
			defaultoutanim = R.anim.slide_out_right;
			doneinanim = R.anim.slide_in_left;
			doneoutanim = R.anim.slide_out_left;
		} else {
			defaultinanim = R.anim.slide_in_left;
			defaultoutanim = R.anim.slide_out_left;
			doneinanim = R.anim.slide_in_right;
			doneoutanim = R.anim.slide_out_right;
		}
		if (state == STATE_DONE) {
			setEnabled(false);
			postDelayed(revertStateRunnable, RESET_STATE_DELAY_MILLIS);
			setInAnimation(getContext(), doneinanim);
			setOutAnimation(getContext(), defaultoutanim);
		} else if (state == STATE_DEFAULT) {
			setEnabled(true);
			setInAnimation(getContext(), defaultinanim);
			setOutAnimation(getContext(), doneoutanim);
		}
		showNext();
	}

	public void setDefaultText(String defaultText) {
		this.defaultText = defaultText;
		tvDefault.setText(defaultText);
	}

	public void setDoneText(String doneText) {
		this.doneText = doneText;
		tvDone.setText(doneText);
	}

	public void setDefaultColor(int defaultColor) {
		this.defaultColor = defaultColor;
		tvDefault.setTextColor(defaultColor);
	}

	public void setDoneColor(int doneColor) {
		this.doneColor = doneColor;
		tvDone.setTextColor(doneColor);
	}

	public void setAnimStyle(int animStyle) {
		this.animStyle = animStyle;
	}

	/**
	 * 通过TyperArray拿到的文字大小为px,需要重新转换为sp
	 */
	private static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}
}