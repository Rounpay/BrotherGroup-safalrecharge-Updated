package com.solution.brothergroup.Util.RecyclerViewStickyHeader;

import android.view.View;

public interface HeaderStickyListener {
    int getHeaderPositionForItem(int itemPosition);

    int getHeaderLayout(int headerPosition);
    String getHeaderString(int headerPosition);
    void bindHeaderData(View header, int headerPosition);

    boolean isHeader(int itemPosition);
}
