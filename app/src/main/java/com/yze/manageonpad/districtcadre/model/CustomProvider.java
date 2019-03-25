package com.yze.manageonpad.districtcadre.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.PieData;
import com.daivd.chart.data.format.IFormat;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.matrix.RotateHelper;
import com.daivd.chart.provider.BaseProvider;
import com.daivd.chart.utils.ColorUtils;
import java.util.Iterator;
import java.util.List;

class CustomPieProvider extends BaseProvider<PieData> {
    private RectF oval;
    private int totalAngle = 360;
    private PointF centerPoint;
    private int centerRadius;
    private double clickAngle = -1.0D;
    private final float startAngle = -90.0F;
    protected RotateHelper rotateHelper;
    private FontStyle textStyle = new FontStyle();
    private float centerCirclePercent = 0.3F;
    private boolean isClick;
    private IFormat<Double> valueFormat;
    private PorterDuffXfermode porterDuffXfermode;

    public CustomPieProvider() {
    }

    public boolean calculationChild(ChartData<PieData> chartData) {
        return true;
    }

    protected void matrixRectStart(Canvas canvas, Rect rect) {
        super.matrixRectStart(canvas, rect);
        if (this.rotateHelper != null && this.rotateHelper.isRotate()) {
            canvas.rotate((float)this.rotateHelper.getStartAngle(), (float)rect.centerX(), (float)rect.centerY());
        }

    }

    protected void drawProvider(Canvas canvas, Rect zoomRect, Rect rect, Paint paint) {
        int layerId = canvas.saveLayer((float)rect.left, (float)rect.top, (float)rect.right, (float)rect.bottom, (Paint)null, 31);
        this.getClass();
        float startAngle = -90.0F;
        float totalAngle = this.getAnimValue((float)this.totalAngle);
        paint.setStyle(Style.FILL);
        int h = zoomRect.height();
        int w = zoomRect.width();
        int maxRadius = Math.min(w / 2, h / 2);
        int x = maxRadius / 10;
        this.centerRadius = maxRadius - x;
        if (this.oval == null) {
            this.oval = new RectF((float)(zoomRect.centerX() - this.centerRadius), (float)(zoomRect.centerY() - this.centerRadius), (float)(zoomRect.centerX() + this.centerRadius), (float)(zoomRect.centerY() + this.centerRadius));
            this.centerPoint = new PointF((float)zoomRect.centerX(), (float)zoomRect.centerY());
        }

        if (this.rotateHelper != null) {
            this.rotateHelper.setRadius(this.centerRadius);
            this.rotateHelper.setOriginRect(rect);
        }

        List<PieData> pieDataList = this.chartData.getColumnDataList();
        float totalScores = 0.0F;

        PieData pieData;
        for(Iterator var14 = pieDataList.iterator(); var14.hasNext(); totalScores = (float)((double)totalScores + (Double)pieData.getChartYDataList())) {
            pieData = (PieData)var14.next();
        }

        for(int i = 0; i < pieDataList.size(); ++i) {
            pieData = (PieData)pieDataList.get(i);
            double value = (Double)pieData.getChartYDataList();
            float sweepAngle = (float)((double)totalAngle * value / (double)totalScores);
            if (pieData.isDraw()) {
                paint.setColor(pieData.getColor());
                if (this.clickAngle != -1.0D && this.clickAngle > (double)startAngle && this.clickAngle < (double)(startAngle + sweepAngle)) {
                    paint.setColor(ColorUtils.getDarkerColor(pieData.getColor()));
                    if (this.isClick && this.onClickColumnListener != null) {
                        this.onClickColumnListener.onClickColumn(pieData, 0);
                        this.isClick = false;
                    }
                }

                canvas.drawArc(this.oval, startAngle, sweepAngle, true, paint);
                if (this.isShowText()) {
                    canvas.save();
                    float var10001 = startAngle + sweepAngle / 2.0F;
                    this.getClass();
                    canvas.rotate(var10001 - -90.0F, (float)zoomRect.centerX(), (float)zoomRect.centerY());
                    this.textStyle.fillPaint(paint);
                    int textHeight = (int)paint.measureText("1", 0, 1);
                    String val = this.valueFormat != null ? this.valueFormat.format(value) : String.valueOf(value);
                    canvas.drawText(val, (float)(zoomRect.centerX() - val.length() * textHeight / 2), (float)(zoomRect.centerY() - maxRadius / 2), paint);
                    canvas.restore();
                }
            }

            startAngle += sweepAngle;
        }

        if (this.porterDuffXfermode == null) {
            this.porterDuffXfermode = new PorterDuffXfermode(Mode.SRC_OUT);
        }

        paint.setXfermode(this.porterDuffXfermode);
        if (this.centerCirclePercent > 0.0F) {
            paint.setColor(0);
            canvas.drawCircle((float)rect.centerX(), (float)rect.centerY(), (float)this.centerRadius * this.centerCirclePercent, paint);
        }

        canvas.restoreToCount(layerId);
        paint.setXfermode((Xfermode)null);
    }

    public double[] setMaxMinValue(double maxMinValue, double minValue) {
        return new double[0];
    }

    public void clickPoint(PointF point) {
        super.clickPoint(point);
        if (this.centerPoint != null) {
            int x = (int)(point.x - this.centerPoint.x);
            int y = (int)(point.y - this.centerPoint.y);
            int z = (int)Math.sqrt(Math.pow((double)Math.abs(x), 2.0D) + Math.pow((double)Math.abs(y), 2.0D));
            if (z <= this.centerRadius + 20) {
                double angle = Math.abs(Math.toDegrees(Math.atan((double)((point.y - this.centerPoint.y) / (point.x - this.centerPoint.x)))));
                if (x >= 0 && y < 0) {
                    angle = 90.0D - angle;
                } else if (x >= 0 && y >= 0) {
                    angle += 90.0D;
                } else if (x < 0 && y >= 0) {
                    angle = 270.0D - angle;
                } else {
                    angle += 270.0D;
                }

                angle = (angle - this.rotateHelper.getStartAngle()) % 360.0D;
                angle = angle < 0.0D ? 360.0D + angle : angle;
                this.clickAngle = angle + -90.0D;
                this.isClick = true;
                return;
            }

            this.clickAngle = -1.0D;
        }

    }

    public PointF getCenterPoint() {
        return this.centerPoint;
    }

    public void setCenterPoint(PointF centerPoint) {
        this.centerPoint = centerPoint;
    }

    public int getCenterRadius() {
        return this.centerRadius;
    }

    public void setCenterRadius(int centerRadius) {
        this.centerRadius = centerRadius;
    }

    public void setValueFormat(IFormat<Double> valueFormat) {
        this.valueFormat = valueFormat;
    }

    public void setCenterCirclepercent(float centerCirclePercent) {
        this.centerCirclePercent = centerCirclePercent;
    }

    public float getCenterCirclePercent() {
        return this.centerCirclePercent;
    }

    public void setCenterCirclePercent(float centerCirclePercent) {
        this.centerCirclePercent = centerCirclePercent;
    }


    public void setRotateHelper(RotateHelper rotateHelper) {
        this.rotateHelper = rotateHelper;
    }
}
