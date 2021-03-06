package oxilo.com.lyfyo.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import oxilo.com.lyfyo.ApplicationController;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.ui.activity.DetailActivity;
import oxilo.com.lyfyo.ui.modal.FilterDatum;
import oxilo.com.lyfyo.ui.modal.Package;
import oxilo.com.lyfyo.ui.modal.Service;
import oxilo.com.lyfyo.ui.modal.UserDetail;

import static oxilo.com.lyfyo.AppConstant.USER_DETAIL;


/**
 * Created by ericbasendra on 02/12/15.
 */
public class PackageListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int VIEW_ITEM = 1;
    public static final int VIEW_PROG = 0;
    private Context mContext;
    public List<T> dataSet;
    private static MyClickListener myClickListener;
    private int inflated_row;
    public static boolean isLoading;
    TextView price;
    StringBuilder stringBuilder;

    public PackageListAdapter(int inflated_row, List<T> productLists, TextView price,Context mContext) {
        this.mContext = mContext;
        this.dataSet = productLists;
        this.inflated_row = inflated_row;
        this.price = price;
        stringBuilder = new StringBuilder();
    }
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
    public void setLoaded() {
        isLoading = false;
    }

    public void animateTo(List<T> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }


    private void applyAndAnimateRemovals(List<T> newModels) {
        for (int i = dataSet.size() - 1; i >= 0; i--) {
            final T model = dataSet.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }


    private void applyAndAnimateAdditions(List<T> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final T model = newModels.get(i);
            if (!dataSet.contains(model)) {
                addItem(i, model);
            }
        }
    }


    private void applyAndAnimateMovedItems(List<T> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final T model = newModels.get(toPosition);
            final int fromPosition = dataSet.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public void addItem(T item) {
        if (!dataSet.contains(item)) {
            dataSet.add(item);
            notifyItemInserted(dataSet.size() - 1);
        }
    }

    public void addItem(int position, T model) {
        dataSet.add(position, model);
        notifyItemInserted(position);
    }

    public void removeItem(T item) {
        int indexOfItem = dataSet.indexOf(item);
        if (indexOfItem != -1) {
            this.dataSet.remove(indexOfItem);
            notifyItemRemoved(indexOfItem);
        }
    }

    public T removeItem(int position) {
        final T model = dataSet.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void clearItem(){
        if (dataSet != null)
            dataSet.clear();
    }

    public void clear(){
        if (dataSet != null){
            dataSet.clear();
            notifyDataSetChanged();
        }

    }

    public void moveItem(int fromPosition, int toPosition) {
        final T model = dataSet.remove(fromPosition);
        dataSet.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public T getItem(int index) {
        if (dataSet != null && dataSet.get(index) != null) {
            return dataSet.get(index);
        } else {
            throw new IllegalArgumentException("Item with index " + index + " doesn't exist, dataSet is " + dataSet);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dataSet.get(position) == null ? VIEW_PROG : VIEW_ITEM ;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        if (viewType == VIEW_ITEM){
            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(inflated_row, parent, false);
            vh = new EventViewHolder(itemView);
        }
        else if(viewType == VIEW_PROG){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progress_item, parent, false);
            vh = new ProgressViewHolder(v);
        }else {
            throw new IllegalStateException("Invalid type, this type ot items " + viewType + " can't be handled");
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof EventViewHolder){
            T dataItem = dataSet.get(position);
       ((EventViewHolder) holder).offer_services.setText(Html.fromHtml(((Package)dataItem).getOFDescription()));
       ((EventViewHolder) holder).main_price.setText("Rs " + ((Package)dataItem).getPckcartTotalprice());
       ((EventViewHolder) holder).discount_price.setText("Rs " + ((Package)dataItem).getPckcartValueprice() + "");

            if (((Package)dataItem).getPackageDiscount().equals("0 %")){
                ((EventViewHolder) holder).percent_discount.setVisibility(View.GONE);
                ((EventViewHolder) holder).main_price.setVisibility(View.GONE);
                ((EventViewHolder) holder).offer_services.setVisibility(View.VISIBLE);
            }else{
                ((EventViewHolder) holder).percent_discount.setVisibility(View.VISIBLE);
                ((EventViewHolder) holder).percent_discount.setText(((Package)dataItem).getPackageDiscount());
                ((EventViewHolder) holder).main_price.setVisibility(View.VISIBLE);
                ((EventViewHolder) holder).offer_services.setVisibility(View.VISIBLE);
            }

            if (!((Package)dataItem).is_add_button_clicked()){
                ((EventViewHolder) holder).add.setText("Add");
            }else{
                ((EventViewHolder) holder).add.setText("Remove");
            }
                final String image_Url =  ((Package)dataItem).getImages();
                if (image_Url!=null && !image_Url.equals("")){
                    Picasso
                            .with(mContext)
                            .load(image_Url)
                            .fit()
                           .centerCrop()// or .centerCrop() to avoid a stretched image
                            .into(((EventViewHolder) holder).img);

                }
            ((EventViewHolder) holder).img_clear.setTag(((EventViewHolder) holder));
            ((EventViewHolder) holder).add.setTag(((EventViewHolder) holder));
            ((EventViewHolder) holder).view_Details.setTag(((EventViewHolder) holder));

            ((EventViewHolder) holder).img_clear.setId(position);
            ((EventViewHolder) holder).add.setId(position);
            ((EventViewHolder) holder).view_Details.setId(position);

            ((EventViewHolder) holder).add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventViewHolder eventViewHolder = (EventViewHolder) v.getTag();
                    T dataItem = dataSet.get((Integer) v.getId());
                    Package aPackage = ((Package)dataItem);//(Service) v.getTag();
//                    double _price = 0;
//                    if (eventViewHolder.add.getText().toString().equals("Add")){
//                        eventViewHolder.add.setText("Remove");
//                        aPackage.setIs_Detail_Open(true);
//                        _price = ((DetailActivity)mContext).getTotal() + Double.parseDouble(aPackage.getPckcartValueprice());
//                    }else if (eventViewHolder.add.getText().toString().equals("Remove")){
//                        eventViewHolder.add.setText("Add");
//                        aPackage.setIs_Detail_Open(false);
//                        _price = ((DetailActivity)mContext).getTotal() - Double.parseDouble(aPackage.getPckcartValueprice());
//                    }
                    double _price = ((DetailActivity)mContext).getTotal() + Double.parseDouble(aPackage.getPckcartValueprice());
                    ((DetailActivity)mContext).setTotal(_price);
                    price.setText("" + ((DetailActivity)mContext).getTotal());

                    stringBuilder.append(aPackage.getPckcartId() + ",");
                    UserDetail userDetail = ApplicationController.getInstance().getAppPrefs().getObject(USER_DETAIL, UserDetail.class);
                    if (userDetail == null) {
                        userDetail = new UserDetail();
                        userDetail.setPackage_id(stringBuilder.toString());
                    }
                    else{
                        userDetail.setPackage_id(stringBuilder.toString());
                    }
                    ApplicationController.getInstance().getAppPrefs().putObject(USER_DETAIL,userDetail);
                    ApplicationController.getInstance().getAppPrefs().commit();


                }
            });

            ((EventViewHolder) holder).view_Details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventViewHolder eventViewHolder = (EventViewHolder) v.getTag();
                    T dataItem = dataSet.get((Integer) v.getId());
                    Package aPackage = ((Package)dataItem);
                    aPackage.setIs_Detail_Open(true);
                    eventViewHolder.linearLayout_popup.setVisibility(View.VISIBLE);
                    eventViewHolder.total_services.setText(Html.fromHtml(aPackage.getOFHighlights()));

                }
            });

            ((EventViewHolder) holder).img_clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventViewHolder eventViewHolder = (EventViewHolder) v.getTag();
                    T dataItem = dataSet.get((Integer) v.getId());
                    Package aPackage = ((Package)dataItem);
                    aPackage.setIs_Detail_Open(false);
                    eventViewHolder.linearLayout_popup.setVisibility(View.GONE);
                }
            });


        }
        else if (holder instanceof ProgressViewHolder) {
            ((ProgressViewHolder)holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        if (dataSet!=null)
            return dataSet.size();
        else
            return 0;
    }


    public String getServiceString(List<Service> serviceList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0 ; i< serviceList.size(); i++){
            stringBuilder.append(serviceList.get(i).getSEDescription1() + ",");
        }

        return stringBuilder.toString();
    }



    private void setFadeAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RESTART, 0.5f, Animation.RESTART, 0.5f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected ImageView img;
        protected TextView offer_services,view_Details,main_price,add,discount_price,percent_discount;
        ImageView img_clear;
        TextView total_services;
         LinearLayout linearLayout_popup;
        public EventViewHolder(View v) {
            super(v);
            img = (ImageView)v.findViewById(R.id.imageView);
            percent_discount = (TextView)v.findViewById(R.id.percent_discount);
            offer_services = (TextView)v.findViewById(R.id.service_list) ;
            view_Details = (TextView)v.findViewById(R.id.view_details) ;
            main_price = (TextView)v.findViewById(R.id.main_price);
            discount_price =(TextView)v.findViewById(R.id.discount_price) ;
            add = (TextView)v.findViewById(R.id.count_number);
            linearLayout_popup = (LinearLayout)v.findViewById(R.id.popup) ;

            img_clear = (ImageView)v.findViewById(R.id.img_clear) ;
            total_services  = (TextView)v.findViewById(R.id.total_services);

//            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            try{
                if(null != myClickListener){
                    myClickListener.onItemClick(getLayoutPosition(), view);
                }else{
//                    Toast.makeText(view.getContext(),"Click Event Null hai", Toast.LENGTH_SHORT).show();
                }
            }catch(NullPointerException e){
                e.printStackTrace();
//                Toast.makeText(view.getContext(),"Click Event Null Ex", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar)v.findViewById(R.id.progress_bar);
        }
    }


    /**
     * y Custom Item Listener
     */

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }


}
