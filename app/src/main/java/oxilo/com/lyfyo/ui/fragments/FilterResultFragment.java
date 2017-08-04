package oxilo.com.lyfyo.ui.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import oxilo.com.lyfyo.LyfoPrefs;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.network.api.ServiceFactory;
import oxilo.com.lyfyo.network.api.WebService;
import oxilo.com.lyfyo.ui.EndlessRecyclerOnScrollListener;
import oxilo.com.lyfyo.ui.activity.DetailActivity;
import oxilo.com.lyfyo.ui.adapter.NearBySaloonListAdapter;
import oxilo.com.lyfyo.ui.modal.FilterDatum;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link } interface
 * to handle interaction events.
 * Use the {@link FilterResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterResultFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1, mParam2;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    Unbinder unbinder;


    private NearBySaloonListAdapter nearBySaloonListAdapter;
    private ArrayList<FilterDatum> neaDatumArrayList;
    LyfoPrefs lyfoPrefs;


    public FilterResultFragment() {
        // Required empty public constructor
    }

    public void setValue(String offer, String rating, String popular, String recently_add,
                         String order_by_cost,
                         int gender,
                         String csv) {

//        this.offer = offer;
//        this. rating= rating;
//        this.popular = popular;
//        this.recently_add = recently_add;
//        this. order_by_cost = order_by_cost;
//        this. gender = gender;
//        this.csv = csv;

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilterResultFragment newInstance(String param1, String param2) {
        FilterResultFragment fragment = new FilterResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = inflater.inflate(R.layout.see_all, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showProgress(true);
        init();
        getNearbySallon(lyfoPrefs.getLat(getActivity()), lyfoPrefs.getlng(getActivity()), lyfoPrefs.getAdminArea(getActivity()), 1);


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void init() {
        lyfoPrefs = new LyfoPrefs();
        lyfoPrefs.getLyfoPrefs(getActivity());
        neaDatumArrayList  = new ArrayList<>();
        nearBySaloonListAdapter = new NearBySaloonListAdapter(R.layout.filter_row, neaDatumArrayList, getActivity());
        LinearLayoutManager lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(nearBySaloonListAdapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(lm) {
            @Override
            public void onLoadMore(int current_page) {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        nearBySaloonListAdapter.addItem(null);
                    }
                });
                getNearbySallon(lyfoPrefs.getLat(getActivity()), lyfoPrefs.getlng(getActivity()), lyfoPrefs.getAdminArea(getActivity()), current_page);
            }
        });

        nearBySaloonListAdapter.setOnItemClickListener(new NearBySaloonListAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                FilterDatum filterDatum = (FilterDatum) nearBySaloonListAdapter.dataSet.get(position);
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra("list", filterDatum);
                startActivity(i);
            }
        });
    }

    private void getNearbySallon(double lat, double lng, String location, int page) {
        try {
            WebService webService = ServiceFactory.createRetrofitService(WebService.class);
            webService.sallonByLocation(location, lat, lng, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<ResponseBody>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull Response<ResponseBody> responseBodyResponse) {
                            try {
                                String sd = new String(responseBodyResponse.body().bytes());
                                JSONObject mapping = new JSONObject(sd);
                                ObjectMapper mapper = new ObjectMapper();
                                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                                neaDatumArrayList = mapper.readValue(mapping.getString("Salon"), new TypeReference<List<FilterDatum>>() {
                                });
                                if (nearBySaloonListAdapter.dataSet.size()>0){
                                    nearBySaloonListAdapter.removeItem(nearBySaloonListAdapter.dataSet.size()-1);
                                }
                                for (FilterDatum filter : neaDatumArrayList) {
                                    nearBySaloonListAdapter.addItem(filter);
                                }
                                showProgress(false);


                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                int shortAnimTime = 200;

                recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                recyclerView.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (isAdded() && recyclerView != null)
                            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });

                progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                progressBar.animate().setDuration(shortAnimTime).alpha(
                        show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (isAdded() && progressBar != null)
                            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
            } else {
                // The ViewPropertyAnimator APIs are not available, so simply show
                // and hide the relevant UI components.
                recyclerView.setVisibility(show ? View.VISIBLE : View.GONE);
                recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        getActivity().finish();
    }
}
