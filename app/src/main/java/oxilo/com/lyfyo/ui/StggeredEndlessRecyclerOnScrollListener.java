package oxilo.com.lyfyo.ui;

/**
 * Created by ericbasendra on 02/12/15.
 */
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public abstract class StggeredEndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = StggeredEndlessRecyclerOnScrollListener.class.getSimpleName();
    private int scrolledDistance = 0;
    private boolean controlsVisible = false;
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    int pastVisibleItems, visibleItemCount, totalItemCount;

    private int current_page = 1;

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    public StggeredEndlessRecyclerOnScrollListener(StaggeredGridLayoutManager mStaggeredGridLayoutManager) {
        this.mStaggeredGridLayoutManager = mStaggeredGridLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mStaggeredGridLayoutManager.getItemCount();
        //firstVisibleItem = mStaggeredGridLayoutManager.findFirstVisibleItemPosition();
        int[] firstVisibleItems = null;
        firstVisibleItems = mStaggeredGridLayoutManager.findFirstVisibleItemPositions(firstVisibleItems);
        if (firstVisibleItems != null && firstVisibleItems.length > 0) {
            pastVisibleItems = firstVisibleItems[0];
        }


        if (loading) {
            if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (pastVisibleItems + visibleThreshold)) {
            // End has been reached

            // Do something
            current_page++;

            onLoadMore(current_page);

            loading = true;
        }

        if (scrolledDistance > 1 && controlsVisible) {
            controlsVisible = false;
            scrolledDistance = 0;
        } else if (scrolledDistance < -1 && !controlsVisible) {
            controlsVisible = true;
            scrolledDistance = 0;
        }

        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            scrolledDistance += dy;
        }
    }

    public abstract void onLoadMore(int current_page);
}
