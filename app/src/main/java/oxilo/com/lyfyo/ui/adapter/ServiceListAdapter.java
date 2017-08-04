package oxilo.com.lyfyo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.ui.activity.DetailActivity;
import oxilo.com.lyfyo.ui.modal.PCollection;
import oxilo.com.lyfyo.ui.modal.Package;
import oxilo.com.lyfyo.ui.modal.Service;


/**
 * Created by ericbasendra on 02/12/15.
 */
public class ServiceListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_ITEM = 1;
    public static final int VIEW_HORIZENATAL_ITEM = 2;
    private final int VIEW_PROG = 0;
    private Context mContext;
    public List<T> dataSet;
    List<T> packages;
    private static MyClickListener myClickListener;
    private int inflated_row;
    TextView price;

    public ServiceListAdapter(int inflated_row, List<T> productLists, List<T> packages,TextView price, Context mContext) {
        this.mContext = mContext;
        this.dataSet = productLists;
        this.packages = packages;
        this.inflated_row = inflated_row;
        this.price = price;
    }
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
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
        if (dataSet.get(position) == null)
            return VIEW_PROG;
        if (position  == 0)
            return VIEW_HORIZENATAL_ITEM;
        else
            return VIEW_ITEM;
//        return dataSet.get(position) == null ? VIEW_PROG : VIEW_ITEM ;
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
        else if(viewType == VIEW_HORIZENATAL_ITEM){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.package_fragments, parent, false);
            vh = new HorizenatlViewHolder(v);
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof EventViewHolder){
            final T dataItem = dataSet.get(position);
       ((EventViewHolder) holder).service_name.setText(((Service)dataItem).getSEName());
       ((EventViewHolder) holder).service_price.setText(((Service)dataItem).getSECost());

           ((EventViewHolder) holder).tv_minus.setTag(((EventViewHolder) holder));
            ((EventViewHolder) holder).tv_plus.setTag(((EventViewHolder) holder));

            ((EventViewHolder) holder).tv_minus.setId(position);
            ((EventViewHolder) holder).tv_plus.setId(position);

            if (((Service)dataItem).getCount()==0){
                ((EventViewHolder) holder).tv_minus.setVisibility(View.GONE);
                ((EventViewHolder) holder).tv_count_number.setText("Add");
            }else{
                ((EventViewHolder) holder).tv_minus.setVisibility(View.VISIBLE);
                ((EventViewHolder) holder).tv_count_number.setText("" + ((Service)dataItem).getCount());
            }

            ((EventViewHolder) holder).tv_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventViewHolder eventViewHolder = (EventViewHolder) v.getTag();
                    T dataItem = dataSet.get((Integer) v.getId());
                    Service service = ((Service)dataItem);
                    if (((Service)dataItem).getCount()>1){
                        int minus_value = service.getCount()-1;
                        service.setCount(minus_value);
                        eventViewHolder.tv_count_number.setText(minus_value + "");
                    }
                    else{
                        service.setCount(0);
                         eventViewHolder.tv_minus.setVisibility(View.GONE);
                        eventViewHolder.tv_count_number.setText("Add");
                    }
                    double _price = ((DetailActivity)mContext).getTotal() - Double.parseDouble(service.getSECost());
                    ((DetailActivity)mContext).setTotal(_price);
                    price.setText("" + ((DetailActivity)mContext).getTotal());
                }
            });

            ((EventViewHolder) holder).tv_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        EventViewHolder eventViewHolder = (EventViewHolder) v.getTag();
                        T dataItem = dataSet.get((Integer) v.getId());
                        Service service = ((Service)dataItem);//(Service) v.getTag();
                        int plus_value = service.getCount() + 1;
                        service.setCount(plus_value);
                        double _price = ((DetailActivity)mContext).getTotal() + Double.parseDouble(service.getSECost());
                        ((DetailActivity)mContext).setTotal(_price);
                        eventViewHolder.tv_minus.setVisibility(View.VISIBLE);
                        eventViewHolder.tv_count_number.setText(plus_value + "");
                        price.setText("" + ((DetailActivity)mContext).getTotal());
                }
            });
        }

        else if (holder instanceof ServiceListAdapter.HorizenatlViewHolder) {
            if (position > packages.size())
                return;
            if (packages.size()==0)
                return;

            T dataItem = packages.get(position);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            ((HorizenatlViewHolder) holder).recyclerView.setLayoutManager(linearLayoutManager);
             PackageListAdapter nearBySaloonListAdapter = new PackageListAdapter(R.layout.package_row, packages,price, mContext);
            ((HorizenatlViewHolder) holder).recyclerView.setAdapter(nearBySaloonListAdapter);
        }
        else if (holder instanceof ServiceListAdapter.ProgressViewHolder) {
            ((ServiceListAdapter.ProgressViewHolder)holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        if (dataSet!=null)
            return dataSet.size();
        else
            return 0;
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

        public TextView service_name,service_price;
        public TextView tv_minus,tv_plus,tv_count_number;
        public EventViewHolder(View v) {
            super(v);
           service_name = (TextView) v.findViewById(R.id.service_name);
           service_price = (TextView)v.findViewById(R.id.service_price);
           tv_minus = (TextView)v.findViewById(R.id.minus_btn);
           tv_plus = (TextView)v.findViewById(R.id.plus_btn);
            tv_count_number = (TextView)v.findViewById(R.id.count_number);
//           tv_minus.setOnClickListener(this);
//           tv_plus.setOnClickListener(this);
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

    public static class HorizenatlViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView recyclerView;
        public HorizenatlViewHolder(View v) {
            super(v);
            recyclerView = (RecyclerView) v.findViewById(R.id.recyle_id);

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
