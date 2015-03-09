package jp.egaonohon.and.multitouch;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MultiTouch extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MyView view = new MyView(this);
		setContentView(view);
	}

	/**
	 *
	 * @author 1107AND
	 *
	 */
	class MyView extends View {
		final static int MULTI_TOUCH_MAX = 10;
		private PointF[] points = new PointF[MULTI_TOUCH_MAX];
		private int[] color = { Color.GREEN, Color.CYAN, Color.BLUE, Color.RED,
				Color.YELLOW, Color.BLACK, Color.rgb(255, 102, 102),
				Color.rgb(204, 255, 102), Color.rgb(153, 255, 255),
				Color.rgb(50, 50, 50) };

		public MyView(Context context) {
			super(context);
			setBackgroundColor(Color.WHITE);
			setFocusable(true);

			/**
			 * 最初は画面の外側に描画しておく。 一つpointsが押されると、そこがカウント1になってポイントの0番目に格納。
			 */
			for (int i = 0; i < MULTI_TOUCH_MAX; i++) {
				points[i] = new PointF(-1.0F, -1.0F);
			}
		}

		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			// タッチ位置の描画。カウント分for分で回す。
			for (int i = 0; i < MULTI_TOUCH_MAX; i++) {
				if (points[i].x < 0.0 && points[i].y < 0.0)
					continue;
				paint.setColor(color[i]);
				canvas.drawCircle(points[i].x, points[i].y, 60, paint);
			}
		}

		/**
		 * マルチタッチのポイントはここ。 getPointerCount()が、ポイントの数を取得するメソッド。
		 *
		 */
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
			int count = event.getPointerCount();
			for (int i = 0; i < count; i++) {
				/**
				 * getPointerIdでイベントのゼロ番目のiDを引っ張ってきて、その○番目を配列に格納。
				 * 配列は、MyViewクラスで宣言しているもの。
				 * countを、idとして取得。ポイント番目のところにイベントを閉まっていく。
				 *
				 * 一つpointsが押されると、そこがカウント1になってポイントの0番目に格納。
				 */
				int id = event.getPointerId(i);
				points[id].x = event.getX(i);
				points[id].y = event.getY(i);
			}
			invalidate();
			return true;
		}
	}
}