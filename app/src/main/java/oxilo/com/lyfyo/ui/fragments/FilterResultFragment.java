package oxilo.com.lyfyo.ui.fragments;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

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
import oxilo.com.lyfyo.ApplicationController;
import oxilo.com.lyfyo.GeoSearchModel;
import oxilo.com.lyfyo.LyfoPrefs;
import oxilo.com.lyfyo.PermissionUtils;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.network.api.ServiceFactory;
import oxilo.com.lyfyo.network.api.WebService;
import oxilo.com.lyfyo.ui.EndlessRecyclerOnScrollListener;
import oxilo.com.lyfyo.ui.StggeredEndlessRecyclerOnScrollListener;
import oxilo.com.lyfyo.ui.activity.DetailActivity;
import oxilo.com.lyfyo.ui.adapter.FilterListAdapter;
import oxilo.com.lyfyo.ui.adapter.OfferListAdapter;
import oxilo.com.lyfyo.ui.adapter.SallonListAdapter;
import oxilo.com.lyfyo.ui.common.BaseActivity;
import oxilo.com.lyfyo.ui.modal.FilterDatum;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilterResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterResultFragment extends Fragment implements FilterFragment.Filter,LocationSearchFragment.Select{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;
    @BindView(R.id.recyclerView3)
    RecyclerView recyclerView3;
    @BindView(R.id.cardView)
    RecyclerView recyclerView4;
    Unbinder unbinder;

    SallonListAdapter sallonListAdapter;
    OfferListAdapter offerListAdapter;

    @BindView(R.id.sublocality)
    TextView sublocality;
    @BindView(R.id.location_rl)
    RelativeLayout locationRl;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.filter)
    TextView filter;
    @BindView(R.id.admin_area)
    TextView adminArea;
    @BindView(R.id.constraint_rl)
    NestedScrollView constraintRl;
    @BindView(R.id.search_recyle_list)
    RecyclerView searchRecyleList;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private String offer;
    private String rating;
    private String popular;
    private String recently_add;
    private String order_by_cost;
    private int gender;
    private String csv;
    private FilterListAdapter filterListAdapter;
    private ArrayList<FilterDatum> filterdatamList;
    private boolean isLocationNeedToUpdate=true;
    private boolean isViewNeedUpdate=false;


    public FilterResultFragment() {
        // Required empty public constructor
    }

    public void setValue(  String offer,
             String rating,
             String popular,
            String recently_add,
            String order_by_cost,
            int gender,
            String csv){

          this.offer = offer;
        this. rating= rating;
        this.popular = popular;
        this.recently_add = recently_add;
        this. order_by_cost = order_by_cost;
        this. gender = gender;
        this.csv = csv;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initClassRefrence();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void passRefrence(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        LyfoPrefs lyfoPrefs = new LyfoPrefs();
        lyfoPrefs.getLyfoPrefs(getActivity());
        sublocality.setText(lyfoPrefs.getCity(getActivity()));
        adminArea.setText(lyfoPrefs.getAdminArea(getActivity()));
        updateView();
        showProgress(true);
        applyFilter(1);
        return rootView;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.location_rl)
    public void onViewClicked() {
        LocationSearchFragment locationSearchFragment = LocationSearchFragment.newInstance("", "");
        locationSearchFragment.setSelectedListener(this);
        ((BaseActivity) getActivity()).startFragment(locationSearchFragment, getActivity());
    }

    @OnClick(R.id.filter)
    public void onFilterClicked() {
//        Intent i = new Intent(getActivity(), FilterActivity.class);
//        startActivityForResult(i, 1);

        FilterFragment filterFragment =  FilterFragment.newInstance("","");
        filterFragment.setFilterListener(this);
        ((BaseActivity)getActivity()).startFragment(filterFragment,getActivity());
    }

    @Override
    public void selectedLocation(final String location,final String city) {
         isLocationNeedToUpdate = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isAdded()) {
                    if (location != null) {
                        sublocality.setText(location);
                        adminArea.setText(city);
                    }

                }
            }
        }, 700);


    }

    @Override
    public void ApplyFilter(final String offer1, final String rating1, final String popular1, final String recently1, final String order_by_cost1, final int gender1, final String csv1) {
        isLocationNeedToUpdate = false;
        isViewNeedUpdate = true;
        offer = offer1;
        rating = rating1;
        popular = popular1;
        recently_add = recently1;
        order_by_cost = order_by_cost1;
        gender = gender1;
        csv = csv1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isAdded()) {
                    updateView();
                    showProgress(true);
                    applyFilter(1);
                }
            }
        }, 700);
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }

    private ArrayList<String> loadDummy() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }
        return list;
    }

    private ArrayList<String> loadOffer() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Trending offer");
        list.add("Offer near by you");
        list.add("Offer of the day");
        list.add("Recently booked Offer");
        return list;
    }

    private void initClassRefrence() {
        sallonListAdapter = new SallonListAdapter(R.layout.row, loadDummy(), getContext());
        LinearLayoutManager li1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager li2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager li3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(li1);
        recyclerView2.setLayoutManager(li2);
        recyclerView3.setLayoutManager(li3);

        recyclerView.setAdapter(sallonListAdapter);
        recyclerView2.setAdapter(sallonListAdapter);
        recyclerView3.setAdapter(sallonListAdapter);

        offerListAdapter = new OfferListAdapter(R.layout.offer_row, loadOffer(), getContext());
        LinearLayoutManager li4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView4.setLayoutManager(li4);
        recyclerView4.setAdapter(offerListAdapter);
    }




//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//                isLocationNeedToUpdate = false;
//                 offer = data.getStringExtra("offer");
//                 rating = data.getStringExtra("rating");
//                 popular = data.getStringExtra("pouplar");
//                 recently_add = data.getStringExtra("recently");
//                 order_by_cost = data.getStringExtra("sortbycost");
//                 gender = data.getIntExtra("gender", 1);
//                 csv = data.getStringExtra("csv");
//                System.out.println(csv);
//                constraintRl.setVisibility(View.GONE);
//                searchRecyleList.setVisibility(View.VISIBLE);
//                showProgress(true);
//
////                if (filterListAdapter!=null){
////                    filterListAdapter.clearItem();
////                    filterListAdapter.notifyDataSetChanged();
////                }
//                StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(2,
//                        StaggeredGridLayoutManager.VERTICAL);
//                searchRecyleList.setLayoutManager(llm);
//                 filterdatamList = new ArrayList<>();
//                 filterListAdapter = new FilterListAdapter(R.layout.filter_row,filterdatamList,getActivity());
//                 searchRecyleList.setAdapter(filterListAdapter);
//                searchRecyleList.addOnScrollListener(new StggeredEndlessRecyclerOnScrollListener(llm) {
//                    @Override
//                    public void onLoadMore(int current_page) {
//                        applyFilter(current_page);
//                    }
//                });
//
//                filterListAdapter.setOnItemClickListener(new FilterListAdapter.MyClickListener() {
//                    @Override
//                    public void onItemClick(int position, View v) {
//
//                    }
//                });
//
//
//
//                applyFilter(1);
//            }
//        }
//    }

    private void updateView(){
        constraintRl.setVisibility(View.GONE);
        searchRecyleList.setVisibility(View.VISIBLE);
        GridLayoutManager llm = new GridLayoutManager(getActivity(),2);
        llm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(filterListAdapter.getItemViewType(position)){
                    case FilterListAdapter.VIEW_ITEM:
                        return 1;
                    case FilterListAdapter.VIEW_PROG:
                        return 2; //number of columns of the grid
                    default:
                        return -1;
                }
            }
        });

        searchRecyleList.setLayoutManager(llm);

        if (filterdatamList==null)
        filterdatamList = new ArrayList<>();
        filterListAdapter = new FilterListAdapter(R.layout.filter_row,filterdatamList,getActivity());
        searchRecyleList.setAdapter(filterListAdapter);
        searchRecyleList.addOnScrollListener(new EndlessRecyclerOnScrollListener(llm) {
            @Override
            public void onLoadMore(int current_page) {
                searchRecyleList.post(new Runnable() {
                    @Override
                    public void run() {
                        filterListAdapter.addItem(null);
                    }
                });
                applyFilter(current_page);
            }
        });

    }
    private void applyFilter(int page) {
        try {
            LyfoPrefs lyfoPrefs = new LyfoPrefs();
            lyfoPrefs.getLyfoPrefs(getActivity());
            String location = "Malad";
            double lat = lyfoPrefs.getLat(getActivity());
            double lng = lyfoPrefs.getlng(getActivity());
            WebService webService = ServiceFactory.createRetrofitService(WebService.class);
            webService.filter(order_by_cost,rating,recently_add,csv,offer,gender,popular,location,
                    lat,lng,page)
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
                                filterdatamList = mapper.readValue(mapping.getString("FilterData"), new TypeReference<List<FilterDatum>>(){});
                                if (filterListAdapter.dataSet.size()>0){
                                   filterListAdapter.removeItem(filterListAdapter.dataSet.size()-1);
                                }
                                for (FilterDatum filter : filterdatamList) {
                                    filterListAdapter.addItem(filter);
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
                            e.printStackTrace();
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

                searchRecyleList.setVisibility(show ? View.GONE : View.VISIBLE);
                searchRecyleList.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (isAdded() && searchRecyleList!=null)
                        searchRecyleList.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });

                progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                progressBar.animate().setDuration(shortAnimTime).alpha(
                        show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (isAdded() && progressBar!=null)
                        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
            } else {
                // The ViewPropertyAnimator APIs are not available, so simply show
                // and hide the relevant UI components.
                searchRecyleList.setVisibility(show ? View.VISIBLE : View.GONE);
                searchRecyleList.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
