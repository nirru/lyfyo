package oxilo.com.lyfyo.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.ui.activity.FilterActivity;
import oxilo.com.lyfyo.ui.activity.FilterResultActivity;
import oxilo.com.lyfyo.ui.common.BaseActivity;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, ServiceFinderFragment.SelectedCheckBox {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.scrollView2)
    ScrollView scrollView2;
    @BindView(R.id.imageView5)
    ImageView imageView5;
    @BindView(R.id.rs_high_to_low)
    TextView rsHighToLow;
    @BindView(R.id.imageView6)
    ImageView imageView6;
    @BindView(R.id.rs_low_to_high)
    TextView rsLowToHigh;

    @BindView(R.id.dismiss)
    TextView dismiss;
    @BindView(R.id.reset)
    TextView reset;
    @BindView(R.id.filter)
    TextView filter;
    @BindView(R.id.appCompatButton)
    AppCompatButton appCompatButton;
    @BindView(R.id.offer_swicth)
    SwitchCompat offerSwicth;
    @BindView(R.id.start_img)
    ImageView startImg;
    @BindView(R.id.start_heart)
    ImageView startHeart;
    @BindView(R.id.start_added)
    ImageView startAdded;
    @BindView(R.id.radioButton)
    AppCompatRadioButton radioButton;
    @BindView(R.id.radio_button_2)
    AppCompatRadioButton radioButton2;
    @BindView(R.id.appCompatCheckBox)
    AppCompatCheckBox maleCheckBox;
    @BindView(R.id.female_checkbox)
    AppCompatCheckBox femaleCheckbox;
    @BindView(R.id.kids_checkbox)
    AppCompatCheckBox kidsCheckbox;
    @BindView(R.id.hair_cut_swicth)
    SwitchCompat hairCutSwicth;
    @BindView(R.id.facial_switch)
    SwitchCompat facialSwitch;
    @BindView(R.id.massage_switch)
    SwitchCompat massageSwitch;

    String csv = "";
    int gender = 1;
    String order_by_cost = "high";
    String recently_add = "on";
    String offer = "on";
    String popular = "high";
    String rating = "high";

    boolean rating_rl = false;
    boolean fav_rl = false;
    boolean recently_rl = false;

    boolean hair_cut = false;
    boolean facial = false;
    boolean massage = false;

    private  ArrayList<String>filterServices ;

    Unbinder unbinder;

    Filter filteListener;

    public FilterFragment() {
        // Required empty public constructor
    }

    public void setFilterListener(Filter filter){
        this.filteListener = filter;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilterFragment newInstance(String param1, String param2) {
        FilterFragment fragment = new FilterFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_filter, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        offerSwicth.setOnCheckedChangeListener(this);
        hairCutSwicth.setOnCheckedChangeListener(this);
        facialSwitch.setOnCheckedChangeListener(this);
        massageSwitch.setOnCheckedChangeListener(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
    public void selectedValue(ArrayList<String> list) {
        int size = list.size();
        this.filterServices = list;
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

    @OnClick({R.id.ratig_rl, R.id.fav_rl, R.id.recently_rl, R.id.appCompatButton, R.id.search, R.id.dismiss, R.id.reset, R.id.start_img, R.id.start_heart, R.id.start_added, R.id.radioButton, R.id.radio_button_2, R.id.appCompatCheckBox, R.id.female_checkbox, R.id.kids_checkbox})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dismiss:
                getActivity().finish();
                break;
            case R.id.reset:
                offerSwicth.setChecked(false);
                startImg.setImageResource(R.drawable.ic_star_border_grey_600_24dp);
                startHeart.setImageResource(R.drawable.ic_favorite_border_grey);
                startAdded.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);

                imageView5.setImageResource(R.drawable.rs_blank);
                rsHighToLow.setTextColor(getResources().getColor(android.R.color.primary_text_dark));
                radioButton.setChecked(false);
                imageView6.setImageResource(R.drawable.rs_fill);
                rsLowToHigh.setTextColor(getResources().getColor(R.color.yellow));
                radioButton2.setChecked(true);

                maleCheckBox.setChecked(false);
                femaleCheckbox.setChecked(false);
                kidsCheckbox.setChecked(false);

                hairCutSwicth.setChecked(false);
                facialSwitch.setChecked(false);
                massageSwitch.setChecked(false);
                break;
            case R.id.start_img:
                if (!rating_rl) {
                    rating = "high";
                    rating_rl = true;
                    startImg.setImageResource(R.drawable.ic_star_yellow_24dp);
                } else {
                    rating = "low";
                    rating_rl = false;
                    startImg.setImageResource(R.drawable.ic_star_border_grey_600_24dp);
                }
                break;
            case R.id.start_heart:
                if (!fav_rl) {
                    popular = "high";
                    fav_rl = true;
                    startHeart.setImageResource(R.drawable.ic_favorite_yellow_24dp);
                } else {
                    popular = "low";
                    fav_rl = false;
                    startHeart.setImageResource(R.drawable.ic_favorite_border_grey);
                }
                break;
            case R.id.start_added:
                if (!recently_rl) {
                    recently_add = "on";
                    recently_rl = true;
                    startAdded.setImageResource(R.drawable.ic_add_circle_yellow_24dp);
                } else {
                    recently_add = "off";
                    recently_rl = false;
                    startAdded.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);
                }
                break;
            case R.id.radioButton:
                order_by_cost = "high";
                radioButton2.setChecked(false);
                radioButton.setChecked(true);
                imageView5.setImageResource(R.drawable.rs_fill);
                imageView6.setImageResource(R.drawable.rs_blank);
                rsHighToLow.setTextColor(getResources().getColor(R.color.yellow));
                rsLowToHigh.setTextColor(getResources().getColor(android.R.color.secondary_text_dark));
                break;
            case R.id.radio_button_2:
                order_by_cost = "low";
                radioButton2.setChecked(true);
                radioButton.setChecked(false);
                imageView6.setImageResource(R.drawable.rs_fill);
                imageView5.setImageResource(R.drawable.rs_blank);
                rsLowToHigh.setTextColor(getResources().getColor(R.color.yellow));
                rsHighToLow.setTextColor(getResources().getColor(android.R.color.secondary_text_dark));
                break;
            case R.id.appCompatCheckBox:
                gender = 1;
                maleCheckBox.setChecked(true);
                femaleCheckbox.setChecked(false);
                kidsCheckbox.setChecked(false);
                break;
            case R.id.female_checkbox:
                gender = 2;
                femaleCheckbox.setChecked(true);
                maleCheckBox.setChecked(false);
                kidsCheckbox.setChecked(false);
                break;
            case R.id.kids_checkbox:
                gender = 3;
                kidsCheckbox.setChecked(true);
                maleCheckBox.setChecked(false);
                femaleCheckbox.setChecked(false);
                break;
            case R.id.search:
                //launch service search screen
                ServiceFinderFragment serviceFinderFragment = ServiceFinderFragment.newInstance("", "");
                serviceFinderFragment.setCheckedListener(this);
                ((FilterActivity) getActivity()).startFragment(serviceFinderFragment, getActivity());
                break;
            case R.id.appCompatButton:
                serviceFilterValue();
                break;
            case R.id.ratig_rl:
                if (!rating_rl) {
                    rating = "high";
                    rating_rl = true;
                    startImg.setImageResource(R.drawable.ic_star_yellow_24dp);
                } else {
                    rating = "low";
                    rating_rl = false;
                    startImg.setImageResource(R.drawable.ic_star_border_grey_600_24dp);
                }
                break;
            case R.id.fav_rl:
                if (!fav_rl) {
                    popular = "high";
                    fav_rl = true;
                    startHeart.setImageResource(R.drawable.ic_favorite_yellow_24dp);
                } else {
                    popular = "low";
                    fav_rl = false;
                    startHeart.setImageResource(R.drawable.ic_favorite_border_grey);
                }
                break;
            case R.id.recently_rl:
                if (!recently_rl) {
                    recently_add = "on";
                    recently_rl = true;
                    startAdded.setImageResource(R.drawable.ic_add_circle_yellow_24dp);
                } else {
                    recently_add = "off";
                    recently_rl = false;
                    startAdded.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.offer_swicth:
                if (isChecked)
                    offer = "on";
                else
                    offer = "off";
                break;
            case R.id.hair_cut_swicth:
                hair_cut = isChecked;
                break;
            case R.id.facial_switch:
               facial = isChecked;
                break;
            case R.id.massage_switch:
                massage = isChecked;
                break;
        }
    }

    private void serviceFilterValue(){
        StringBuilder stringBuilder = new StringBuilder();
        if (filterServices==null)
            filterServices = new ArrayList<>();
        if (hair_cut)
            filterServices.add("Hair Cut");
        if (facial)
            filterServices.add("Facial");
        if (massage)
            filterServices.add("Head Massage");


        for (int i = 0;i <filterServices.size();i++){
            stringBuilder.append(filterServices.get(i).trim());
            stringBuilder.append(ServiceFinderFragment.SEPARATOR);
        }
        String csv = stringBuilder.toString().trim();
        if (csv.length()>0)
        csv = csv.substring(0, csv.length() - ServiceFinderFragment.SEPARATOR.length());
        else
            csv="";

        System.out.println(csv);
        Intent intent = new Intent(getActivity(), FilterResultActivity.class);
        intent.putExtra("offer", offer);
        intent.putExtra("rating",rating);
        intent.putExtra("pouplar",popular);
        intent.putExtra("recently",recently_add);
        intent.putExtra("sortbycost",order_by_cost);
        intent.putExtra("gender",gender);
        intent.putExtra("csv",csv);
        getActivity().finishAffinity();
        getActivity().startActivity(intent);
//        getActivity().setResult(RESULT_OK, intent);
//        getActivity().finish();




//        this.filteListener.ApplyFilter(offer,rating,popular,recently_add,order_by_cost,gender,csv);
//        ((AppCompatActivity)getActivity()).getSupportFragmentManager().popBackStack();

    }

    interface Filter{
        public void ApplyFilter(String offer,String rating,String popular,String recently
        ,String order_by_cost,int gender,String csv);
    }

}
