package oxilo.com.lyfyo.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.network.api.ServiceFactory;
import oxilo.com.lyfyo.network.api.WebService;
import oxilo.com.lyfyo.ui.activity.DetailActivity;
import oxilo.com.lyfyo.ui.activity.FilterActivity;
import oxilo.com.lyfyo.ui.adapter.OfferListAdapter;
import oxilo.com.lyfyo.ui.adapter.SallonListAdapter;
import oxilo.com.lyfyo.ui.common.BaseActivity;
import oxilo.com.lyfyo.ui.modal.Salon;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements LocationSearchFragment.Select{
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
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
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initClassRefrence();
        getSallon();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.location_rl)
    public void onViewClicked() {
        LocationSearchFragment locationSearchFragment = LocationSearchFragment.newInstance("","");
        locationSearchFragment.setSelectedListener(this);
        ((BaseActivity)getActivity()).startFragment(locationSearchFragment,getActivity());
    }

    @OnClick(R.id.filter)
    public void onFilterClicked() {
        Intent i = new Intent(getActivity(), FilterActivity.class);
        startActivity(i);
    }

    @Override
    public void selectedLocation(String location) {
        Log.e("TEST","" + location);
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

    @Override
    public void onResume() {
        super.onResume();
        sallonListAdapter.setOnItemClickListener(new SallonListAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
//                ((MainActivity)getActivity()).startFragment(DetailFragment.newInstance("",""));

                Intent i = new Intent(getActivity(), DetailActivity.class);
                startActivity(i);
            }
        });
    }

    private void getSallon() {
        try {
            WebService webService = ServiceFactory.createRetrofitService(WebService.class);
            webService.sallonList()
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
                                List<Salon> salonList = mapper.readValue(mapping.getString("Salon"), new TypeReference<List<Salon>>(){ });
                                Log.e("SIZE==", "" + salonList.size());

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
}
