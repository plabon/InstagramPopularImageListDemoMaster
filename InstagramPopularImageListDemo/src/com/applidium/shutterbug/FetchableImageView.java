package com.applidium.shutterbug;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.applidium.shutterbug.utils.ShutterbugManager;
import com.applidium.shutterbug.utils.ShutterbugManager.ShutterbugManagerListener;

public class FetchableImageView extends ImageView implements ShutterbugManagerListener {
	
	private float mCornerRadius;
    public interface FetchableImageViewListener {
        void onImageFetched(Bitmap bitmap, String url);
        

        void onImageFailure(String url);
    }

    private FetchableImageViewListener mListener;

    public FetchableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCornerRadius(float cornerRadius) {
		mCornerRadius = cornerRadius;
	}
 
	@Override
	protected void onDraw(Canvas canvas) {
		// Round some corners betch!
		Drawable maiDrawable = getDrawable();
		if (maiDrawable instanceof BitmapDrawable && mCornerRadius > 0) {
			Paint paint = ((BitmapDrawable) maiDrawable).getPaint();
	        final int color = 0xff424242;
	        Rect bitmapBounds = maiDrawable.getBounds();
	        final RectF rectF = new RectF(bitmapBounds);
	        // Create an off-screen bitmap to the PorterDuff alpha blending to work right
			int saveCount = canvas.saveLayer(rectF, null,
                    Canvas.MATRIX_SAVE_FLAG |
                    Canvas.CLIP_SAVE_FLAG |
                    Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                    Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                    Canvas.CLIP_TO_LAYER_SAVE_FLAG);
			// Resize the rounded rect we'll clip by this view's current bounds
			// (super.onDraw() will do something similar with the drawable to draw)
			getImageMatrix().mapRect(rectF);
	        paint.setAntiAlias(true);
	        canvas.drawARGB(0, 0, 0, 0);
	        paint.setColor(color);
	        canvas.drawRoundRect(rectF, mCornerRadius, mCornerRadius, paint);
 
			Xfermode oldMode = paint.getXfermode();
			// This is the paint already associated with the BitmapDrawable that super draws
	        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
	        super.onDraw(canvas);
	        paint.setXfermode(oldMode);
	        canvas.restoreToCount(saveCount);
		} else {
			super.onDraw(canvas);
		}
	}
    
    
    public FetchableImageViewListener getListener() {
        return mListener;
    }

    public void setListener(FetchableImageViewListener listener) {
        mListener = listener;
    }

    public void setImage(String url) {
    	setImage(url, new ColorDrawable(getContext().getResources().getColor(R.color.transparent)));
    }

    public void setImage(String url, int placeholderDrawableId) {
        setImage(url, getContext().getResources().getDrawable(placeholderDrawableId));
    }

   /* public void setImageRoundedValues(float roundedPixel,boolean isRounded)
    {
    	doRounded=isRounded;
    	roundPx=roundedPixel;
    }*/
    
    public void setImage(String url, Drawable placeholderDrawable) {
        final ShutterbugManager manager = ShutterbugManager.getSharedImageManager(getContext());
        manager.cancel(this);
        setImageDrawable(placeholderDrawable);
        if (url != null) {
            manager.download(url, this);
        }
    }

    public void cancelCurrentImageLoad() {
        ShutterbugManager.getSharedImageManager(getContext()).cancel(this);
    }

    @Override
    public void onImageSuccess(ShutterbugManager imageManager, Bitmap bitmap, String url) {
        /*if(doRounded)
        	bitmap=getRoundedCornerBitmap(bitmap);*/
    	setImageBitmap(bitmap);
        requestLayout();
        if (mListener != null) {
            mListener.onImageFetched(bitmap, url);
        }
    }

    @Override
    public void onImageFailure(ShutterbugManager imageManager, String url) {
        if (mListener != null) {
            mListener.onImageFailure(url);
        }
    }

	/*public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),

		bitmap.getHeight(), Config.ARGB_8888);
		Bitmap output = bitmap.copy(Bitmap.Config.ARGB_8888, true);

		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;

		final Paint paint = new Paint();

		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);

		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(
				android.graphics.PorterDuff.Mode.SRC_IN));

		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}*/
}
