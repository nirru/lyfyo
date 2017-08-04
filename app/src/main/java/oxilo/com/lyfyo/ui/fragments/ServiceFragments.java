package oxilo.com.lyfyo.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.ui.EndlessRecyclerOnScrollListener;
import oxilo.com.lyfyo.ui.activity.DetailActivity;
import oxilo.com.lyfyo.ui.adapter.ServiceListAdapter;
import oxilo.com.lyfyo.ui.modal.Package;
import oxilo.com.lyfyo.ui.modal.Service;
import oxilo.com.lyfyo.ui.view.VerticalSpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ServiceFragments#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceFragments extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recylerview)
    RecyclerView recylerview;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private ArrayList<Service> services;
    private ArrayList<Package> mParam2;

    private ServiceListAdapter serviceListAdapter;
    private OnFragmentInteractionListener mListener;

    TextView price_view;

//    MyScrollView myScrollView;
//    int y;
//    LinearLayoutManager li1;

    public ServiceFragments() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServiceFragments.
     */
    // TODO: Rename and change types and number of parameters
    public static ServiceFragments newInstance(List<Service> param1, List<Package>  param2) {
        ServiceFragments fragment = new ServiceFragments();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, (ArrayList<? extends Parcelable>) param1);
        args.putParcelableArrayList(ARG_PARAM2, (ArrayList<? extends Parcelable>) param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            services = getArguments().getParcelableArrayList(ARG_PARAM1);
            mParam2 = getArguments().getParcelableArrayList(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.service_fragments, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initClassRefrence();
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

    private void initClassRefrence() {
        TextView price = ((DetailActivity)getActivity()).getPriceView();
        serviceListAdapter = new ServiceListAdapter(R.layout.service_row, services, mParam2,price,getContext());
        LinearLayoutManager li1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false)
        {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        }
        ;
        li1.setSmoothScrollbarEnabled(true);
        recylerview.setLayoutManager(li1);
        recylerview.addItemDecoration(
                new VerticalSpaceItemDecoration(getActivity(), R.drawable.divider));
        recylerview.setAdapter(serviceListAdapter);

        serviceListAdapter.setOnItemClickListener(new ServiceListAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
        });
    }

}
