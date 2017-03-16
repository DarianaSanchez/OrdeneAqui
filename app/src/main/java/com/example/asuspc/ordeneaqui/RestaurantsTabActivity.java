package com.example.asuspc.ordeneaqui;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asuspc.ordeneaqui.adapters.RestaurantesAdapter;
import com.example.asuspc.ordeneaqui.models.Restaurante;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsTabActivity extends Fragment {
    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected RestaurantesAdapter adapter;
    protected List<Restaurante> restauranteList;

    private static final String TAG = "RestaurantsTabActivity";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected RestaurantsTabActivity.LayoutManagerType mCurrentLayoutManagerType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_restaurants_tab, container, false);
        rootView.setTag(TAG);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = RestaurantsTabActivity.LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (RestaurantsTabActivity.LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }

        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        restauranteList = new ArrayList<>();
        adapter = new RestaurantesAdapter(container.getContext(), restauranteList);

        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        //recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new RestaurantsTabActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareRestaurants();

        return rootView;
    }

    /**
     * Adding few albums for testing
     */
    private void prepareRestaurants() {
        int[] covers = new int[]{
                R.drawable.album2,
                R.drawable.album2,
                R.drawable.album2,
                R.drawable.album2,
                R.drawable.album2,
                R.drawable.album2,
                R.drawable.album2,
                R.drawable.album2};
        Restaurante a = new Restaurante(1, "Pizza Hut", "Make it great", "Av. Nuñez de Cáceres", R.drawable.album2);
        restauranteList.add(a);

        a = new Restaurante(1, "Pizza Hut", "Make it great", "Av. Nuñez de Cáceres", R.drawable.album2);
        restauranteList.add(a);

        a = new Restaurante(1, "Pizza Hut", "Make it great", "Av. Nuñez de Cáceres", R.drawable.album2);
        restauranteList.add(a);

        a = new Restaurante(1, "Pizza Hut", "Make it great", "Av. Nuñez de Cáceres", R.drawable.album2);
        restauranteList.add(a);

        a = new Restaurante(1, "Pizza Hut", "Make it great", "Av. Nuñez de Cáceres", R.drawable.album2);
        restauranteList.add(a);

        a = new Restaurante(1, "Pizza Hut", "Make it great", "Av. Nuñez de Cáceres", R.drawable.album2);
        restauranteList.add(a);

        a = new Restaurante(1, "Pizza Hut", "Make it great", "Av. Nuñez de Cáceres", R.drawable.album2);
        restauranteList.add(a);

        a = new Restaurante(1, "Pizza Hut", "Make it great", "Av. Nuñez de Cáceres", R.drawable.album2);
        restauranteList.add(a);

        adapter.notifyDataSetChanged();
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    public void setRecyclerViewLayoutManager(RestaurantsTabActivity.LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = RestaurantsTabActivity.LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = RestaurantsTabActivity.LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = RestaurantsTabActivity.LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.scrollToPosition(scrollPosition);
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }
}