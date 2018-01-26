package wang.viewsimple;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.ColorInt;
import android.util.TypedValue;

/**
 * 水印图
 * Created by Wang on 2018/1/24.
 */
public class Watermark {

    private WatermarkParams mWatermarkParams;

    private Watermark(WatermarkParams mWatermarkParams) {
        this.mWatermarkParams = mWatermarkParams;
    }

    public BitmapDrawable getWatermarkBitmapDrawable() {

        Bitmap bitmap = Bitmap.createBitmap(mWatermarkParams.mWidth, mWatermarkParams.mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(mWatermarkParams.mBackgroundColor);
        Paint paint = new Paint();
        paint.setColor(mWatermarkParams.mTextColor);
        paint.setAlpha((int) (255 * mWatermarkParams.mAlpha));
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(mWatermarkParams.mTextSize);

        canvas.rotate(mWatermarkParams.mDegrees, 0, mWatermarkParams.mHeight);
        canvas.drawText(mWatermarkParams.mDrawText, -mWatermarkParams.mDegrees, mWatermarkParams.mHeight, paint);

        BitmapDrawable bitmapDrawable = new BitmapDrawable(mWatermarkParams.context.getResources(), bitmap);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        bitmapDrawable.setDither(true);

        return bitmapDrawable;
    }

    public static class Builder {

        private WatermarkParams mWatermarkParams;

        //水印背景字符
        public Builder(Context context, String drawText) {
            mWatermarkParams = new WatermarkParams(context, drawText);
        }
        
        //文字大小sp
        public Builder setTextSizeSp(int textSize) {
            mWatermarkParams.mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP
                    , textSize, mWatermarkParams.context.getResources().getDisplayMetrics());
            return this;
        }

        //单项宽度
        public Builder setWidthDp(int width) {
            mWatermarkParams.mWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                    , width, mWatermarkParams.context.getResources().getDisplayMetrics());
            return this;
        }

        //单项高度
        public Builder setHeightDp(int height) {
            mWatermarkParams.mHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                    , height, mWatermarkParams.context.getResources().getDisplayMetrics());
            return this;
        }


        //文字颜色
        public Builder setTextColor(@ColorInt int textColor) {
            mWatermarkParams.mTextColor = textColor;
            return this;
        }

        //背景颜色
        public Builder setBackgroundColor(@ColorInt int backgroundColor) {
            mWatermarkParams.mBackgroundColor = backgroundColor;
            return this;
        }

        //倾斜角度
        public Builder setDegrees(int degrees) {
            mWatermarkParams.mDegrees = degrees;
            return this;
        }

        //透明度 0-1
        public Builder setAlpha(float alpha) {
            mWatermarkParams.mAlpha = alpha;
            return this;
        }

        public Watermark build() {
            return new Watermark(mWatermarkParams);
        }

    }

    private static class WatermarkParams {
        
        Context context;
        String mDrawText;
        int mBackgroundColor;
        int mTextColor;
        int mTextSize;
        int mWidth;
        int mHeight;
        int mDegrees;
        float mAlpha;

        WatermarkParams(Context context, String mDrawText) {
            this.context = context;
            this.mDrawText = mDrawText;
            mTextColor = Color.parseColor("#ebebeb");
            mBackgroundColor = Color.WHITE;
            mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
            mWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, context.getResources().getDisplayMetrics());
            mHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, context.getResources().getDisplayMetrics());
            mDegrees = -30;
            mAlpha = 0.8f;
        }

    }

}
