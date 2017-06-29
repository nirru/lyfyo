package oxilo.com.lyfyo.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.ui.adapter.OfferListAdapter;
import oxilo.com.lyfyo.ui.adapter.SallonListAdapter;
import oxilo.com.lyfyo.ui.adapter.ServiceListAdapter;
import oxilo.com.lyfyo.ui.view.MyScrollView;
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
    private String mParam1;
    private String mParam2;

    private ServiceListAdapter serviceListAdapter;
    private OnFragmentInteractionListener mListener;

    MyScrollView myScrollView;
    int y;
    LinearLayoutManager li1;

    public ServiceFragments() {
        // Required empty public constructor
    }

    public void setMyScrollView(MyScrollView myScrollView){
        this.myScrollView = myScrollView;
    }

    public MyScrollView getMyScrollView(){
        return  this.myScrollView;
    }

    public void setYCordinateOfViewPager(int y){
        this.y = y;
    }

    public int getYCordinate(){
        return y;
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
    public static ServiceFragments newInstance(String param1, String param2) {
        ServiceFragments fragment = new ServiceFragments();
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

    private ArrayList<String> loadDummy() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }
        return list;
    }

    private void initClassRefrence() {
        serviceListAdapter = new ServiceListAdapter(R.layout.service_row, loadDummy(), getContext());
        li1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
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
    }
}
