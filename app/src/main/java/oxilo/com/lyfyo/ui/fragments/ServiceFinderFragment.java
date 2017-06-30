package oxilo.com.lyfyo.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

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
import oxilo.com.lyfyo.ui.EndlessRecyclerOnScrollListener;
import oxilo.com.lyfyo.ui.adapter.ServiceFinderAdapter;
import oxilo.com.lyfyo.ui.modal.Service;
import oxilo.com.lyfyo.ui.view.VerticalSpaceItemDecoration;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ServiceFinderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceFinderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String SEPARATOR = ",";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.serach_services)
    EditText serachServices;
    @BindView(R.id.recyleview)
    RecyclerView recyleview;

    ServiceFinderAdapter serviceFinderAdapter;
    ArrayList<Service> serviceModals;
    Unbinder unbinder;
    @BindView(R.id.clear)
    ImageView clear;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private SelectedCheckBox selectedCheckBoxListener;
    private ArrayList<String>filterServices ;

    public ServiceFinderFragment() {
        // Required empty public constructor
    }

    public void setCheckedListener(SelectedCheckBox selectedCheckBoxListener){
        this.selectedCheckBoxListener = selectedCheckBoxListener;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServiceFinderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ServiceFinderFragment newInstance(String param1, String param2) {
        ServiceFinderFragment fragment = new ServiceFinderFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_finder, container, false);
        unbinder = ButterKnife.bind(this, view);
        initClassRefs();
        return view;
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.go_back, R.id.done,R.id.clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.go_back:
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().popBackStack();
                break;
            case R.id.done:
                getSelectedValue();
                break;
            case R.id.clear:
                serachServices.setText("");
                break;
        }
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

    private void initClassRefs() {
        serviceModals = new ArrayList<>();
        serviceFinderAdapter = new ServiceFinderAdapter(R.layout.service_search_row, serviceModals, getActivity());

        serachServices.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                serviceFinderAdapter.clearItem();
                serviceFinderAdapter.notifyDataSetChanged();
                startSearch(s.toString(), 1);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        LinearLayoutManager li1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        li1.setSmoothScrollbarEnabled(true);
        recyleview.setLayoutManager(li1);
        recyleview.addItemDecoration(
                new VerticalSpaceItemDecoration(getActivity(), R.drawable.divider));
        recyleview.setAdapter(serviceFinderAdapter);

        recyleview.addOnScrollListener(new EndlessRecyclerOnScrollListener(li1) {
            @Override
            public void onLoadMore(int current_page) {
                startSearch(serachServices.getText().toString(), current_page);
            }
        });

        serviceFinderAdapter.setOnItemClickListener(new ServiceFinderAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                try {
                    AppCompatCheckBox checkBox = (AppCompatCheckBox) v.findViewById(R.id.checkbox);
                    Service service = (Service) checkBox.getTag();
                    service.setSelected(checkBox.isChecked());
                    serviceFinderAdapter.notifyItemChanged(position);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        startSearch("ch", 1);
    }

    private void startSearch(String q, int page) {
        try {
            WebService webService = ServiceFactory.createRetrofitService(WebService.class);
            webService.findService(q, page)
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
                                serviceModals = mapper.readValue(mapping.getString("Service"), new TypeReference<List<Service>>() {
                                });
                                Log.e("SIZE==", "" + serviceModals.size());
                                for (Service service : serviceModals) {
                                    serviceFinderAdapter.addItem(service);
                                }

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

    private void getSelectedValue(){
//        StringBuilder stringBuilder = new StringBuilder();
        filterServices = new ArrayList<>();
       for (int i= 0;i<serviceFinderAdapter.dataSet.size();i++){
               Service service = (Service) serviceFinderAdapter.dataSet.get(i);
               if (service.isSelected()){
//                   stringBuilder.append(service.getSEName().trim());
//                   stringBuilder.append(SEPARATOR);
                   filterServices.add(service.getSEName());
               }
       }

//        String csv = stringBuilder.toString().trim();
//        System.out.println(csv);

        //OUTPUT: Milan,London,New York,San Francisco,

        //Remove last comma
//        csv = csv.substring(0, csv.length() - SEPARATOR.length());

//        System.out.println(csv);
        selectedCheckBoxListener.selectedValue(filterServices);
        ((AppCompatActivity) getActivity()).getSupportFragmentManager().popBackStack();
    }

    interface SelectedCheckBox{
        public void selectedValue( ArrayList<String>filterServices);
    }
}
