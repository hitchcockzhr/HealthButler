package com.housekeeper.ar.healthhousekeeper.followup;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.ScrollView;


public class ListViewForScrollView extends ListView {
	private ScrollView parentScrollView;
	public ListViewForScrollView(Context context) {
		super(context);
	}
	public ListViewForScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public ListViewForScrollView(Context context, AttributeSet attrs,
								 int defStyle) {
		super(context, attrs, defStyle);
	}
//	@Override
//	/**
//	 * 重写该方法，达到使ListView适应ScrollView的效果
//	 */
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//				MeasureSpec.AT_MOST);
//		super.onMeasure(widthMeasureSpec, expandSpec);
//	}

	@Override
	public
	boolean onInterceptTouchEvent(MotionEvent ev) {
		switch
				(ev.getAction()) {
			case
					MotionEvent.ACTION_DOWN:

				setParentScrollAble(false);//当手指触到listview的时候，让父ScrollView交出ontouch权限，也就是让父scrollview停住不能滚动
				Log.d("GXF","onInterceptTouchEvent down");
			case MotionEvent.ACTION_MOVE:

				Log.d("GXF","onInterceptTouchEvent move");
				break;

			case MotionEvent.ACTION_UP:

				Log.d("GXF","onInterceptTouchEvent up");
			case
					MotionEvent.ACTION_CANCEL:

				Log.d("GXF","onInterceptTouchEvent cancel");

				setParentScrollAble(true);//当手指松开时，让父ScrollView重新拿到onTouch权限

				break;
			default:
				break;

		}
		return super.onInterceptTouchEvent(ev);

	}


	/**
	 * 是否把滚动事件交给父scrollview
	 *
	 * @param
	flag
	 */
	private void setParentScrollAble(boolean flag) {

		parentScrollView.requestDisallowInterceptTouchEvent(!flag);//这里的parentScrollView就是listview外面的那个scrollview


	}
	public void setParentScrollView(ScrollView sv){
		parentScrollView = sv;
	}

	private int maxHeight;
	public int getMaxHeight() {
		return
				maxHeight;
	}
	public void setMaxHeight(int maxHeight) {

		this.maxHeight = maxHeight;
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		if (maxHeight > -1) {

			heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight,
					MeasureSpec.AT_MOST);
		}
		super.onMeasure(widthMeasureSpec,
				heightMeasureSpec);
		System.out.println(getChildAt(0));
	}

}

