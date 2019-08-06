package io.github.processthis.client.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

public class LineNumberedEditText extends AppCompatEditText {
  private final Context context;
  private Rect rect;
  private Paint paint;

  public LineNumberedEditText(Context context){
    super(context);
    this.context = context;
    init();
  }

  public LineNumberedEditText(Context context, AttributeSet attrs){
    super(context, attrs);
    this.context = context;
    init();
  }

  public LineNumberedEditText(Context context, AttributeSet attrs, int defStyle){
    super(context, attrs, defStyle);
    this.context = context;
    init();
  }

  private void init(){
    rect = new Rect();
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setStyle(Style.FILL);
    paint.setColor(Color.WHITE);
    paint.setTextSize(40);
    paint.setTypeface(Typeface.MONOSPACE);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    int baseline;
    int lineCount = getLineCount();
    int lineNumber = 1;

    for (int i = 0; i < lineCount; i++){
      baseline = getLineBounds(i, null);
      if (i == 0){
        canvas.drawText("" + lineNumber, rect.left, baseline, paint);
      }
      else if(getText().charAt(getLayout().getLineStart(i) - 1) == '\n'){
        canvas.drawText("" + lineNumber, rect.left, baseline, paint);
      }

      lineNumber++;
    }

    canvas.drawLine(60, getScaleY(), 60, getScrollY() + getHeight(), paint);

    if (lineCount < 100){
      setPadding(70, getPaddingTop(), getPaddingRight(), getPaddingBottom());
    }
    else if (lineCount < 1000){
      setPadding(90, getPaddingTop(), getPaddingRight(), getPaddingBottom());
    }

    super.onDraw(canvas);
  }
}
