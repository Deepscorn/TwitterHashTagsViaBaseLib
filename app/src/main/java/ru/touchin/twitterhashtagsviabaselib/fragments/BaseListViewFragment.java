package ru.touchin.twitterhashtagsviabaselib.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.zuzuk.tasks.aggregationtask.AggregationTaskStageState;
import org.zuzuk.ui.adapters.ProviderAdapter;

public abstract class BaseListViewFragment extends BasePullToRefreshFragment {

    private final static String LIST_POSITION_EXTRA = "LIST_POSITION_EXTRA";
    private final static String LIST_TOP_MARGIN_EXTRA = "LIST_TOP_MARGIN_EXTRA";

    private int listPosition;
    private int listTopMargin;
    private boolean isListViewStateValid = false;
    private AbsListView absListView;
    private final Runnable updatePositionAction = new Runnable() {
        public void run() {
            restoreListViewState();
        }
    };

    /**
     * Returns adapter of base ListView so developer should create it with view initialization.
     * It is normal because adapter should only responses for UI but not for logic
     */
    protected Adapter getAdapter() {
        if (absListView == null) {
            return null;
        }

        ListAdapter baseAdapter = absListView.getAdapter();
        if (baseAdapter instanceof HeaderViewListAdapter) {
            return ((HeaderViewListAdapter) baseAdapter).getWrappedAdapter();
        }
        return baseAdapter;
    }

    /* Returns last list position of ListView */
    protected int getListPosition() {
        return listPosition;
    }

    protected void setListPosition(int listPosition) {
        this.listPosition = listPosition;
    }

    /* Resets fragment current state. (e.g. clears cached list position of ListView) */
    protected void resetFragmentState() {
        listPosition = 0;
        listTopMargin = 0;
    }

    /* Restores list view state. Usually after fragment loading */
    protected void restoreListViewState() {
        if (absListView instanceof ListView) {
            ((ListView) absListView).setSelectionFromTop(listPosition, listTopMargin);
        } else {
            absListView.setSelection(listPosition);
        }
        isListViewStateValid = true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        absListView = findViewByType(AbsListView.class, getView());
    }

    @Override
    protected void onFragmentDataLoaded(AggregationTaskStageState currentTaskStageState) {
        if (!isListViewStateValid) {
            getPostHandler().postDelayed(updatePositionAction, 10);  // currently no idea why doesn't work without delay
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        absListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int headersCount = absListView instanceof ListView ? ((ListView) absListView).getHeaderViewsCount() : 0;
                int listItemPosition = position - headersCount;
                if (listItemPosition < 0 || listItemPosition >= getAdapter().getCount()) {
                    return;
                }

                BaseListViewFragment.this.onItemClick(listItemPosition);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        listPosition = absListView.getFirstVisiblePosition();
        View topItem = absListView.getChildAt(0);
        listTopMargin = topItem != null ? topItem.getTop() : 0;
        isListViewStateValid = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(LIST_POSITION_EXTRA, listPosition);
        outState.putInt(LIST_TOP_MARGIN_EXTRA, listTopMargin);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            listPosition = savedInstanceState.getInt(LIST_POSITION_EXTRA, 0);
            listTopMargin = savedInstanceState.getInt(LIST_TOP_MARGIN_EXTRA, 0);
        }
    }

    /* Raises when user tap on ListView item */
    protected void onItemClick(int position) {
    }

    @Override
    public void onDestroyView() {
        Adapter adapter = getAdapter();
        if (adapter instanceof ProviderAdapter) {
            ((ProviderAdapter) adapter).dispose();
        }
        super.onDestroyView();
        absListView = null;
    }

}
