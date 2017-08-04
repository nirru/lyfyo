package oxilo.com.lyfyo.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lyfyo.calender.HorizontalCalendar;
import com.lyfyo.calender.HorizontalCalendarListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import oxilo.com.lyfyo.ApplicationController;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.ui.activity.ConfirmBookingActivity;
import oxilo.com.lyfyo.ui.adapter.SpinnerAdapter;
import oxilo.com.lyfyo.ui.adapter.TimeListAdapter;
import oxilo.com.lyfyo.ui.modal.UserDetail;

import static oxilo.com.lyfyo.AppConstant.USER_DETAIL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalenderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalenderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.available)
    TextView available;
    @BindView(R.id.not_available)
    TextView notAvailable;
    Unbinder unbinder;

    HorizontalCalendar horizontalCalendar, horizontalCalendar1;
    @BindView(R.id.time_spinner)
    AppCompatSpinner timeSpinner;
    @BindView(R.id.timeview)
    RecyclerView timeview;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private int last_position = -1;
    private Calendar endDate;
    private Calendar startDate;
    private Calendar defaultDate;

    private Date selected_date;


    HashMap hashMap = new HashMap();
    HashMap h1 = new HashMap();

    ArrayList<String> strings = new ArrayList<>();
    ArrayList<Date> calendarArrayList = new ArrayList<>();
    private SpinnerAdapter adapter;
    HorizontalCalendar.Builder builder;

    private boolean needToCall = true;
    private TimeListAdapter timeListAdapter;

    public CalenderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalenderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalenderFragment newInstance(String param1, String param2) {
        CalenderFragment fragment = new CalenderFragment();
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
        View view = inflater.inflate(R.layout.fragment_calender, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        createRange();
        renderCalender();
        horizontalCalendar.setCalendarListener(hl);
        getTimeList();
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



    @OnClick({R.id.back, R.id.done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                getActivity().finish();
                break;
            case R.id.done:
                Intent i = new Intent(getActivity(), ConfirmBookingActivity.class);
                startActivity(i);
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

    private void init(View v) {
        builder = new HorizontalCalendar.Builder(v, R.id.calendarView);
    }

    private void createRange() {
        endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        /** start month from now */
        startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        defaultDate = Calendar.getInstance();
        defaultDate.add(Calendar.MONTH, 0);
        defaultDate.add(Calendar.DAY_OF_WEEK, +0);

        Calendar spinnder_date = Calendar.getInstance();
        spinnder_date.add(Calendar.MONTH, 0);
        DateFormat formater = new SimpleDateFormat("MMM yyyy");
        while (spinnder_date.before(endDate)) {
            // add one month to date per loop
            String date = formater.format(spinnder_date.getTime()).toUpperCase();
            strings.add(date);
            calendarArrayList.add(spinnder_date.getTime());
            System.out.println(date);
            spinnder_date.add(Calendar.MONTH, 1);
        }

        for (int i = 0; i < strings.size(); i++) {
            hashMap.put(strings.get(i).toString(), i);
            h1.put(i, calendarArrayList.get(i));
        }

        selected_date = (Date) h1.get(0);
        String[] stockArr = new String[strings.size()];
        stockArr = strings.toArray(stockArr);
        adapter = new SpinnerAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, stockArr);
        timeSpinner.setAdapter(adapter);
        timeSpinner.setOnItemSelectedListener(l);
    }

    private void renderCalender() {
        /** end after 1 month from now */
        horizontalCalendar = builder.startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(7)
                .dayNameFormat("EEE")
                .dayNumberFormat("dd")
                .monthFormat("MMM")
                .showDayName(true)
                .showMonthName(false)
                .defaultSelectedDate(defaultDate.getTime())
                .textColor(Color.LTGRAY, Color.WHITE)
                .selectedTextBackground(getActivity().getResources().getDrawable(R.drawable.rounded_textview), getActivity().getResources().getDrawable(R.drawable.rounded_transparent_textview))
                .selectorColor(Color.TRANSPARENT)
                .build();

    }

    Spinner.OnItemSelectedListener l = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//            if (last_position!=-1){
//                Log.e("VAL==","" + selected_date.getDate());
//                Date d1 = (Date) h1.get(position);
//                selected_date.setMonth(d1.getMonth());
//                horizontalCalendar.selectDate(selected_date,false);
//            }
//            last_position = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    HorizontalCalendarListener hl = new HorizontalCalendarListener() {
        @Override
        public void onDateSelected(Date date, int position) {
//            Toast.makeText(getActivity(), DateFormat.getDateInstance().format(date) + " is selected!", Toast.LENGTH_SHORT).show();
            UserDetail userDetail = ApplicationController.getInstance().getAppPrefs().getObject(USER_DETAIL,UserDetail.class);
            userDetail.setDate(DateFormat.getDateInstance().format(date));
            ApplicationController.getInstance().getAppPrefs().putObject(USER_DETAIL,userDetail);
            ApplicationController.getInstance().getAppPrefs().commit();
//          DateFormat formater = new SimpleDateFormat("MMM yyyy");
//          String month = formater.format(date.getTime()).toUpperCase();
//          Log.e("DATE==","" + date.getDate());
//
//          if (!month.equals(timeSpinner.getSelectedItem().toString())){
////              last_position = -1;
//              selected_date.setDate(date.getDate());
//              timeSpinner.setSelection((int) hashMap.get(month));
//          }
        }
    };

    private void getTimeList() {

        UserDetail userDetail = ApplicationController.getInstance().getAppPrefs().getObject(USER_DETAIL,UserDetail.class);
        userDetail.setTime("8:00 am");
        ApplicationController.getInstance().getAppPrefs().putObject(USER_DETAIL,userDetail);
        ApplicationController.getInstance().getAppPrefs().commit();

        List<String> timeList = new ArrayList<>();
        timeList.add("8.00 am");
        timeList.add("8.15 am");
        timeList.add("8.30 am");
        timeList.add("8.45 am");

        timeList.add("9.00 am");
        timeList.add("9.15 am");
        timeList.add("9.30 am");
        timeList.add("9.45 am");
        timeList.add("10.00 am");

        timeList.add("10.15 am");
        timeList.add("10.30 am");
        timeList.add("10.45 am");
        timeList.add("11.00 am");

        timeList.add("11.15 am");
        timeList.add("11.30 am");
        timeList.add("11.45 am");
        timeList.add("12.00 pm");

        timeList.add("12.15 pm");
        timeList.add("12.30 pm");
        timeList.add("12.45 pm");
        timeList.add("01.00 pm");

        timeList.add("01.15 pm");
        timeList.add("01.30 pm");
        timeList.add("01.45 pm");
        timeList.add("02.00 pm");
        timeList.add("02.15 pm");

        timeList.add("02.15 pm");
        timeList.add("02.30 pm");
        timeList.add("02.45 pm");
        timeList.add("03.00 pm");

        timeList.add("03.15 pm");
        timeList.add("03.30 pm");
        timeList.add("03.45 pm");
        timeList.add("04.00 pm");

        timeList.add("04.15 pm");
        timeList.add("04.30 pm");
        timeList.add("04.45 pm");
        timeList.add("05.00 pm");

        timeList.add("05.15 pm");
        timeList.add("05.30 pm");
        timeList.add("05.45 pm");
        timeList.add("06.00 pm");

        timeList.add("06.15 pm");
        timeList.add("06.30 pm");
        timeList.add("06.45 pm");
        timeList.add("07.00 pm");

        timeList.add("07.15 pm");
        timeList.add("07.30 pm");
        timeList.add("07.45 pm");
        timeList.add("08.00 pm");

        timeList.add("08.15 pm");
        timeList.add("08.30 pm");
        timeList.add("08.45 pm");
        timeList.add("09.00 pm");

        timeList.add("09.15 pm");
        timeList.add("09.30 pm");
        timeList.add("09.45 pm");
        timeList.add("10.00 pm");

        timeList.add("10.15 pm");
        timeList.add("10.30 pm");
        timeList.add("10.45 pm");
        timeList.add("11.00 pm");

        timeList.add("11.15 pm");
        timeList.add("11.30 pm");
        timeList.add("11.45 pm");
        timeList.add("12.00 am");

        timeListAdapter = new TimeListAdapter(R.layout.calender_row, timeList, getActivity());
        GridLayoutManager llm = new GridLayoutManager(getActivity(), 3);
        llm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (timeListAdapter.getItemViewType(position)) {
                    case TimeListAdapter.VIEW_ITEM:
                        return 1;
                    case TimeListAdapter.VIEW_PROG:
                        return 3; //number of columns of the grid
                    default:
                        return -1;
                }
            }
        });

        timeview.setLayoutManager(llm);
        timeview.setAdapter(timeListAdapter);

        timeListAdapter.setOnItemClickListener(new TimeListAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                String time = (String) timeListAdapter.dataSet.get(position);
                UserDetail userDetail = ApplicationController.getInstance().getAppPrefs().getObject(USER_DETAIL,UserDetail.class);
                userDetail.setTime(time);
                ApplicationController.getInstance().getAppPrefs().putObject(USER_DETAIL,userDetail);
                ApplicationController.getInstance().getAppPrefs().commit();

            }
        });
    }

}
