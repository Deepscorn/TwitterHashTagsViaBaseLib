package ru.touchin.mylibrary.fragments.base;

import android.animation.LayoutTransition;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import org.zuzuk.tasks.aggregationtask.AggregationTaskStageState;
import org.zuzuk.tasks.realloading.RealLoadingAggregationTaskListener;
import org.zuzuk.ui.UIUtils;
import org.zuzuk.ui.fragments.BaseExecutorFragment;
import org.zuzuk.ui.views.typefaces.Typefaces;

import java.util.List;

import ru.touchin.mylibrary.activities.MainActivity;
import ru.touchin.mylibrary.api.RequestFailListener;

public abstract class BaseLoadedFragment extends BaseExecutorFragment implements RequestFailListener {
    private static final String CURRENT_FOCUS_ID_STATE = "CURRENT_FOCUS_ID_STATE";

    protected View loadingRefreshButton;
    protected View loadingProgressBar;
    protected View loadingContentContainer;

    protected View currentFocusView;

    protected final RealLoadingAggregationTaskListener listenerWithProgressBar = new RealLoadingAggregationTaskListener() {
        @Override
        public void onRealLoadingStarted(AggregationTaskStageState currentTaskStageState) {
            findViewById(R.id.loadingProgressBar).setVisibility(View.VISIBLE);
        }

        @Override
        public void onRealLoaded(AggregationTaskStageState currentTaskStageState) {
            findViewById(R.id.loadingProgressBar).setVisibility(View.GONE);
        }

        @Override
        public void onRealFailed(AggregationTaskStageState currentTaskStageState) {
            findViewById(R.id.loadingProgressBar).setVisibility(View.GONE);
            onRequestFailure(currentTaskStageState.getExceptions());
        }
    };

    protected final RealLoadingAggregationTaskListener listenerBackground = new RealLoadingAggregationTaskListener() {
        @Override
        public void onRealLoadingStarted(AggregationTaskStageState currentTaskStageState) {
        }

        @Override
        public void onRealLoaded(AggregationTaskStageState currentTaskStageState) {
        }

        @Override
        public void onRealFailed(AggregationTaskStageState currentTaskStageState) {
            onRequestFailure(currentTaskStageState.getExceptions());
        }
    };

    @Override
    public void configureActionBar() {
        super.configureActionBar();
        getActivity().findViewById(R.id.toolbar).setVisibility(isActionBarVisible() ? View.VISIBLE : View.GONE);
    }

    public boolean isHomeButtonVisible() {
        return true;
    }

    protected boolean isActionBarVisible() {
        return true;
    }

    @Override
    public CharSequence getTitle() {
        int titleResourceId = getTitleResourceId();
        return wrapActionBarText(getString(titleResourceId > 0 ? titleResourceId : R.string.android_base_fragment_title));
    }

    public int getTitleResourceId() {
        return 0;
    }

    protected CharSequence wrapActionBarText(String text) {
        Typeface actionBarTypeface = Typefaces.getByName(getActivity(), "BeaufortforLOL-Regular");
        return UIUtils.wrapTextWithTypeface(getActivity(), text, actionBarTypeface);
    }

    protected MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    protected abstract View createContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    @Override
    public View onCreateViewInternal(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading, container, false);

        ViewGroup contentContainerView = (ViewGroup) view.findViewById(R.id.loadingContentContainer);
        View contentView = createContentView(inflater, contentContainerView, savedInstanceState);
        contentContainerView.addView(contentView);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.topContainer);
        LayoutTransition layoutTransition = frameLayout.getLayoutTransition();
        frameLayout.setLayoutTransition(null);
        loadingRefreshButton = view.findViewById(R.id.loadingRefreshButton);
        loadingRefreshButton.setVisibility(View.GONE);
        loadingProgressBar = view.findViewById(R.id.loadingProgressBar);
        loadingProgressBar.setVisibility(View.GONE);
        loadingContentContainer = view.findViewById(R.id.loadingContentContainer);
        loadingContentContainer.setVisibility(getContentVisibilityOnViewCreated());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) { // strange animations blinking
            frameLayout.setLayoutTransition(layoutTransition);
        }

        if(shouldRestoreFocus() && savedInstanceState != null){
            int currentFocusId = savedInstanceState.getInt(CURRENT_FOCUS_ID_STATE, View.NO_ID);
            if(currentFocusId != View.NO_ID){
                currentFocusView = findViewById(currentFocusId);
                showSoftInputIfNeeded();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (shouldRestoreFocus()) {
            if (currentFocusView != null
                    && currentFocusView.getId() != R.id.topContainer
                    && currentFocusView instanceof EditText) {
                outState.putInt(CURRENT_FOCUS_ID_STATE, currentFocusView.getId());
            }
        }
    }

    protected boolean shouldRestoreFocus() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        showSoftInputIfNeeded();
    }

    @Override
    public void onPause() {
        super.onPause();
        currentFocusView = getMainActivity().getCurrentFocus();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loadingRefreshButton = null;
        loadingProgressBar = null;
        loadingContentContainer = null;
        currentFocusView = null;
    }

    protected int getContentVisibilityOnViewCreated() {
        return View.VISIBLE;
    }

    public boolean navigationDrawerEnabled() {
        return true;
    }

    @Override
    public void onRequestFailure(List<Exception> exceptionList) {
        getMainActivity().onRequestFailure(exceptionList);
    }

    private void showSoftInputIfNeeded(){
        if(currentFocusView != null
            && currentFocusView.getId() != R.id.topContainer
            && currentFocusView instanceof EditText){
            postRequestFocus();
        }
    }

    protected void postRequestFocus() {
        getPostHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isResumed()
                        && currentFocusView != null) {
                    currentFocusView.requestFocus();
                    getMainActivity().showSoftInput(currentFocusView);
                }
            }
        }, 500);
    }

    public Fragment setFirstFragment(Class<?> fragmentClass) {
        return setFirstFragment(fragmentClass, null);
    }

    public Fragment setFirstFragment(Class<?> fragmentClass, Bundle args) {
        return getMainActivity().setFirstFragment(fragmentClass, args);
    }

    public Fragment setFragment(Class fragmentClass) {
        return setFragment(fragmentClass, null);
    }

    public Fragment setFragment(Class fragmentClass, Bundle args) {
        return getMainActivity().setFragment(fragmentClass, args);
    }

    public Fragment pushFragment(Class fragmentClass) {
        return pushFragment(fragmentClass, null);
    }

    public Fragment pushFragment(Class fragmentClass, Bundle args) {
        return getMainActivity().pushFragment(fragmentClass, args);
    }

    public Fragment pushFragmentForResult(Class fragmentClass) {
        return pushFragmentForResult(fragmentClass, null);
    }

    public Fragment pushFragmentForResult(Class fragmentClass, Bundle args) {
        Fragment pushedFragment = getMainActivity().pushFragment(fragmentClass, args);
        pushedFragment.setTargetFragment(this, 1); // TODO request code ???
        return pushedFragment;
    }

    public Fragment replaceCurrFragment(Class fragmentClass) {
        return replaceCurrFragment(fragmentClass, null);
    }

    public Fragment replaceCurrFragment(Class fragmentClass, Bundle args) {
        FragmentManager fragmentManager = getMainActivity().getSupportFragmentManager();

        if(0 < fragmentManager.getBackStackEntryCount()) {
            popBackStack();
            return setFragment(fragmentClass, args);
        } else {
            return setFirstFragment(fragmentClass, args);
        }
    }

    public void popBackStack(){
        getMainActivity().getSupportFragmentManager().popBackStack();
    }

    public void popBackStackWithInvalidate(){
        if(getTargetFragment() != null
                && getTargetFragment() instanceof BaseLoadingFragment){
            ((BaseLoadingFragment)getTargetFragment()).setNeedInvalidating();
        }
        getMainActivity().getSupportFragmentManager().popBackStack();
    }

}
