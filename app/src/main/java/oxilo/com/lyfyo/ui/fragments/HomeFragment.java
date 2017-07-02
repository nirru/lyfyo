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
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements FilterFragment.Filter,LocationSearchFragment.Select, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, ActivityCompat.OnRequestPermissionsResultCallback {
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

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mCurrentLocation;
    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;

    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;
    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    public static final String TAG = "TAG==";
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


    public HomeFragment() {
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
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        if (checkPlayServices()) {
            if (!PermissionUtils.isLocationEnabled(getActivity())) {
                ApplicationController.getInstance().showSettingsAlert(getActivity());
                return;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (filterdatamList==null)
            filterdatamList = new ArrayList<>();
        outState.putParcelableArrayList("list", filterdatamList);
        outState.putBoolean("update", isViewNeedUpdate);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //probably orientation change
            filterdatamList = (ArrayList<FilterDatum>) savedInstanceState.getSerializable("list");
//            isViewNeedUpdate = (Boolean)savedInstanceState.getBoolean("update");
        } else {
            if (filterdatamList != null) {
                //returning from backstack, data is fine, do nothing
                int size = filterdatamList.size();
                if (isViewNeedUpdate)
                  updateView();
            } else {
                //newly created, compute data
//                myData = computeData();
            }
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

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isLocationNeedToUpdate)
            resumeService();
//        if (isViewNeedUpdate)
//            updateView();
        LyfoPrefs lyfoPrefs = new LyfoPrefs();
        lyfoPrefs.getLyfoPrefs(getActivity());
        if (lyfoPrefs.getCity(getActivity())!=null)
            sublocality.setText(lyfoPrefs.getCity(getActivity()));
        if (lyfoPrefs.getAdminArea(getActivity())!=null)
            adminArea.setText(lyfoPrefs.getAdminArea(getActivity()));

        sallonListAdapter.setOnItemClickListener(new SallonListAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent i = new Intent(getActivity(), DetailActivity.class);
                startActivity(i);
            }
        });

        if (filterListAdapter!=null){
            filterListAdapter.setOnItemClickListener(new FilterListAdapter.MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {

                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(getActivity());
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(getActivity());
            mGoogleApiClient.disconnect();
        }
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
        StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        searchRecyleList.setLayoutManager(llm);
        if (filterdatamList==null)
        filterdatamList = new ArrayList<>();
        filterListAdapter = new FilterListAdapter(R.layout.filter_row,filterdatamList,getActivity());
        searchRecyleList.setAdapter(filterListAdapter);
        searchRecyleList.addOnScrollListener(new StggeredEndlessRecyclerOnScrollListener(llm) {
            @Override
            public void onLoadMore(int current_page) {
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


    //    /**
//     * Enables the My Location layer if the fine location permission has been granted.
//     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            requestPermissions(new String[]{ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mGoogleApiClient != null) {
            // Access to the location has been granted to the app.
            startLocationUpdates();
        }
    }

    /**
     * Requests location updates from the FusedLocationApi.
     */
    protected void startLocationUpdates() {
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                requestPermissions(new String[]{ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                return;
            } else {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.

        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /* Creates a dialog for an error message */
    private void showErrorDialog(int errorCode) {
        // Create a fragment for the error dialog
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        // Pass the error that should be displayed
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), "errordialog");
    }

    /* Called from ErrorDialogFragment when the dialog is dismissed. */
    public void onDialogDismissed() {
        mResolvingError = false;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            stopLocationUpdates();
            String city = GeoSearchModel.getCityInfo(location.getLatitude(), location.getLongitude(), getActivity());
            String adminArea = GeoSearchModel.getAdminArea(location.getLatitude(), location.getLongitude(), getActivity());
            LyfoPrefs lyfo = new LyfoPrefs();
            lyfo.getEditor(getActivity());
            lyfo.saveCity(city, getActivity());
            lyfo.saveAdminArea(adminArea, getActivity());
            lyfo.saveLat((float) location.getLatitude(), getActivity());
            lyfo.saveLng((float) location.getLongitude(), getActivity());

            this.adminArea.setText(adminArea);
            this.sublocality.setText(city);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE)
            return;
        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                return;
            } else {
                mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                startLocationUpdates();
            }
        } else {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            Toast.makeText(getActivity().getApplicationContext(), "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
        } else if (i == CAUSE_NETWORK_LOST) {
            Toast.makeText(getActivity().getApplicationContext(), "Network lost. Please re-connect.", Toast.LENGTH_SHORT).show();
        }
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@android.support.annotation.NonNull ConnectionResult result) {
        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (result.hasResolution()) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(getActivity(), REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                mGoogleApiClient.connect();
            }
        } else {
            // Show dialog using GoogleApiAvailability.getErrorDialog()
            showErrorDialog(result.getErrorCode());
            mResolvingError = true;
        }
    }


    public static class ErrorDialogFragment extends DialogFragment {
        public ErrorDialogFragment() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the error code and retrieve the appropriate dialog
            int errorCode = this.getArguments().getInt(DIALOG_ERROR);
            return GoogleApiAvailability.getInstance().getErrorDialog(
                    this.getActivity(), errorCode, REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            dialog.dismiss();
        }
    }

    /**
     * Method to verify google play services on the device
     */
    protected boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getActivity(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                ((AppCompatActivity) getActivity()).finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Creating google api client object
     */
    public synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), 0 /* clientId */, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        createLocationRequest();
    }

    protected void resumeService() {
        if (checkPlayServices()) {
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                mGoogleApiClient.stopAutoManage(getActivity());
                mGoogleApiClient.disconnect();
            }
            buildGoogleApiClient();
        }
    }

}
