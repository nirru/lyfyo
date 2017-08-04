package oxilo.com.lyfyo.ui.fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.ui.activity.DetailActivity;
import oxilo.com.lyfyo.ui.activity.LoginActivity;
import oxilo.com.lyfyo.ui.adapter.ImagePagerAdapter;
import oxilo.com.lyfyo.ui.adapter.ServicesPagerAdapter;
import oxilo.com.lyfyo.ui.modal.Category;
import oxilo.com.lyfyo.ui.modal.FilterDatum;
import oxilo.com.lyfyo.ui.modal.Service;
import oxilo.com.lyfyo.ui.view.MyViewPager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    MyViewPager viewPager;
    Unbinder unbinder;
    @BindView(R.id.sl_name)
    TextView slName;
    @BindView(R.id.sl_address)
    TextView slAddress;
    @BindView(R.id.img_pager)
    ViewPager imgPager;
    @BindView(R.id.photos)
    TextView photos;
    @BindView(R.id.rating)
    TextView rating;
    @BindView(R.id.rel_xy)
    RelativeLayout relXy;
//    @BindView(R.id.scroll)
//    MyScrollView scroll;

    // TODO: Rename and change types of parameters
    private FilterDatum filterDatum;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(FilterDatum param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            filterDatum = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.test, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDetail();
        initPager();
        setUpImagePager();
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

    @OnClick(R.id.back_btn)
    public void onViewClicked() {
        ((DetailActivity) getActivity()).finish();
    }

    @OnClick(R.id.rel_xy)
    public void getDirection() {
        if (filterDatum.getAddress1()!=null && !filterDatum.getAddress1().equals("")){
            navigateMap();
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

    private void setDetail() {
        if (filterDatum.getBusinessName() != null)
            slName.setText(filterDatum.getBusinessName().toString());
        if (filterDatum.getAddress1() != null)
            slAddress.setText(filterDatum.getAddress1());
        if (filterDatum.getImages().size() > 0)
            photos.setText(filterDatum.getImages().size() + " Photos");
        else
            photos.setText(0 + "Photos");
        if (!filterDatum.getSlRating().equals("") && filterDatum.getSlRating() != null) {
            double f = Double.parseDouble(filterDatum.getSlRating());
            if (f < 3) {
                rating.setBackgroundColor(Color.RED);
            } else if (f < 4 && f >= 3) {
                rating.setBackgroundColor(getResources().getColor(R.color.yellow));
            } else if (f >= 4) {
                rating.setBackgroundColor(getResources().getColor(R.color.green_color));
            }
            try {
                DecimalFormat df = new DecimalFormat("#.#");
                rating.setText(df.format(f) + "");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void initPager() {

        final List<Fragment> fragments = new Vector<Fragment>();
        final Bundle page = new Bundle();
        page.putString("url", "d");

        ArrayList<Category> categoryArrayList = new ArrayList<>();
        Category category = new Category();
        category.setCAName("Package");
        category.setService(getAllPackageServices());
        category.setPackages(filterDatum.getPackage());


        categoryArrayList.add(category);
        categoryArrayList.addAll(filterDatum.getCategory());

        for (int i = 0; i < categoryArrayList.size(); i++) {
            ServiceFragments serviceFragments = ServiceFragments.newInstance(categoryArrayList.get(i).getService(), categoryArrayList.get(i).getPackages());
            fragments.add(i, serviceFragments);
        }
        ServicesPagerAdapter servicesPagerAdapter = new ServicesPagerAdapter(getActivity(), ((DetailFragment.this)).getChildFragmentManager(), fragments, (ArrayList<Category>) categoryArrayList);
        viewPager.setAdapter(servicesPagerAdapter);
        int limit = (servicesPagerAdapter.getCount() > 1 ? servicesPagerAdapter.getCount() - 1 : 1);
        viewPager.setOffscreenPageLimit(limit);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setUpImagePager() {
        if (!(filterDatum.getImages().size() > 0))
            return;
        ArrayList<String> imgList = new ArrayList<>();
        for (int i = 0; i < filterDatum.getImages().size(); i++) {
            imgList.add(filterDatum.getImages().get(i).getBimgPrimImg());
        }
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(getActivity(), imgList.toArray(new String[0]));
        imgPager.setAdapter(mAdapter);
        imgPager.setCurrentItem(0);
        imgPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<Service> getAllPackageServices(){
        List<Service> services = new ArrayList<>();
        for (int i = 0; i < filterDatum.getPackage().size(); i++){
            for (int j = 0; j < filterDatum.getPackage().get(i).getService().size();j++){
                Service service = filterDatum.getPackage().get(i).getService().get(j);
                services.add(service);
            }

        }

        return services;
    }

    private void navigateMap() {
        if (filterDatum.getSlLat()!=null && filterDatum.getSlLong()!=null)
        if (!filterDatum.getSlLat().equals("") && !filterDatum.getSlLong().equals("")){
            String bu_address = "Address";
            double lat = Double.parseDouble(filterDatum.getSlLat());
            double lng = Double.parseDouble(filterDatum.getSlLong());
            if (!filterDatum.getAddress1().equals(""))
             bu_address = filterDatum.getAddress1();
            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", lat, lng, bu_address);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                try {
                    Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(unrestrictedIntent);
                } catch (ActivityNotFoundException innerEx) {
                    Toast.makeText(getActivity(), "Please install a maps application", Toast.LENGTH_LONG).show();
                    Uri uri1 = Uri.parse("market://details?id=com.google.android.apps.maps");
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri1);
                    startActivity(goToMarket);
                }
            }
        }
    }


}
