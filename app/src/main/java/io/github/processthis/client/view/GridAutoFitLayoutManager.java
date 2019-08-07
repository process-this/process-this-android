package io.github.processthis.client.view;

import android.content.Context;
import android.util.TypedValue;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This class extends GridLayout Manager and is used by the recyclerview in the userProfile,
 * featured, and home fragments
 */
public class GridAutoFitLayoutManager extends GridLayoutManager {

  private int mColumnWidth;
  private boolean mColumnWidthChanged = true;

  /**
   * This constructor sets the column with and context object of the class
   */
  public GridAutoFitLayoutManager(Context context, int columnWidth) {
    super(context, 1);
    setColumnWidth(checkedColumnWidth(context, columnWidth));
  }

  /**
   * This second overloaded constructor ets the column width of he grid layout when a reverse layout
   * is desired
   */
  public GridAutoFitLayoutManager(Context context, int columnWidth, int orientation,
      boolean reverseLayout) { /* Initially set spanCount to 1, will be changed automatically later. */
    super(context, 1, orientation, reverseLayout);
    setColumnWidth(checkedColumnWidth(context, columnWidth));
  }

  private int checkedColumnWidth(Context context, int columnWidth) {
    if (columnWidth
        <= 0) { /* Set default columnWidth value (48dp here). It is better to move this constant to static constant on top, but we need context to convert it to dp, so can't really do so. */
      columnWidth = (int) TypedValue
          .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48,
              context.getResources().getDisplayMetrics());
    }
    return columnWidth;
  }

  /**
   * This method sets the column width if changed
   */
  public void setColumnWidth(int newColumnWidth) {
    if (newColumnWidth > 0 && newColumnWidth != mColumnWidth) {
      mColumnWidth = newColumnWidth;
      mColumnWidthChanged = true;
    }
  }

  /**
   * This overridden required method sets the dimensions of the child elements in a grid view
   */
  @Override
  public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
    if (mColumnWidthChanged && mColumnWidth > 0) {
      int totalSpace;
      if (getOrientation() == RecyclerView.VERTICAL) {
        totalSpace = getWidth() - getPaddingRight() - getPaddingLeft();
      } else {
        totalSpace = getHeight() - getPaddingTop() - getPaddingBottom();
      }
      int spanCount = Math.max(1, totalSpace / mColumnWidth);
      setSpanCount(spanCount);
      mColumnWidthChanged = false;
    }
    super.onLayoutChildren(recycler, state);
  }

}