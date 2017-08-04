package oxilo.com.lyfyo.ui.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.ui.activity.ProfileSetUpActivity;
import oxilo.com.lyfyo.utils.DeviceUtils;

/**
 * Created by nikk on 26/6/17.
 */

public class PhoneNumber extends BottomSheetDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.validate)
    TextView validate;
    Unbinder unbinder;
    @BindView(R.id.mobile_number_xy)
    EditText mobileNumberXy;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhoneNumber newInstance(String param1, String param2) {
        PhoneNumber fragment = new PhoneNumber();
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
        View view = inflater.inflate(R.layout.phone_number, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        addGlobaLayoutListener(getView());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.validate)
    public void onViewClicked() {
        if (mobileNumberXy.getText().length()==10){
            dismiss();
            Intent i = new Intent(getActivity(), ProfileSetUpActivity.class);
            i.putExtra("mobile",mobileNumberXy.getText().toString());
            startActivity(i);
        }
        else{
           mobileNumberXy.setError("Please enter valid mobile number");
        }

    }

    @OnClick(R.id.clear)
    public void onCrossClicked() {
        dismiss();
    }

    private void addGlobaLayoutListener(final View view) {
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                DeviceUtils deviceUtils=new DeviceUtils(getActivity());
                setPeekHeight(deviceUtils.getHeight());
                v.removeOnLayoutChangeListener(this);
            }
        });
    }


    private BottomSheetBehavior getBottomSheetBehaviour() {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) ((View) getView().getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
            return (BottomSheetBehavior) behavior;
        }
        return null;
    }

    public void setPeekHeight(int peekHeight) {
        BottomSheetBehavior behavior = getBottomSheetBehaviour();
        if (behavior == null) {
            return;
        }
        behavior.setPeekHeight(peekHeight);
    }


    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

}
