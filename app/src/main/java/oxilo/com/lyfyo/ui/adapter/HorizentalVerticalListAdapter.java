package oxilo.com.lyfyo.ui.adapter;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import oxilo.com.lyfyo.LyfoPrefs;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.network.api.ServiceFactory;
import oxilo.com.lyfyo.network.api.WebService;
import oxilo.com.lyfyo.ui.modal.Result;
import oxilo.com.lyfyo.ui.modal.PCollection;
import retrofit2.Response;


/**
 * Created by ericbasendra on 02/12/15.
 */
public class HorizentalVerticalListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int VIEW_VERTICAL_ITEM = 1;
    public static final int VIEW_HORIZENATAL_ITEM = 2;
    public static final int VIEW_PROG = 0;
    private final float lat;
    private final float lng;
    private Context mContext;
    public List<T> dataSet;
    public List<T> pcList;
    private static MyClickListener myClickListener;
    private int inflated_row;
    public static boolean isLoading;
    ArrayList<Result>[] ResultArrayList;

    LyfoPrefs lyfoPrefs ;

    public HorizentalVerticalListAdapter(int inflated_row, List<T> productLists, List<T> pcList,Context mContext) {
        this.mContext = mContext;
        this.dataSet = productLists;
        this.pcList = pcList;
        this.inflated_row = inflated_row;



        lyfoPrefs = new LyfoPrefs();
        lyfoPrefs.getLyfoPrefs(mContext);
         lat = lyfoPrefs.getLat(mContext);
         lng = lyfoPrefs.getlng(mContext);
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

    public void initListArray(){
        if (pcList!=null)
            ResultArrayList = new ArrayList[this.pcList.size()];
    }

    public void addItem(T item) {
        if (!dataSet.contains(item)) {
            dataSet.add(item);
            notifyItemInserted(dataSet.size() - 1);
        }
    }

    public void addItem1(T item) {
        if (!pcList.contains(item)) {
            pcList.add(item);
            notifyItemInserted(pcList.size() - 1);
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
        if (pcList!=null)
            pcList.clear();
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
        if (dataSet.get(position) == null)
            return VIEW_PROG;
        if (position > 1 &&position % 4 == 0)
            return VIEW_HORIZENATAL_ITEM;
        else
            return VIEW_VERTICAL_ITEM;
//        return dataSet.get(position) == null ? VIEW_PROG : VIEW_ITEM ;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        if (viewType == VIEW_VERTICAL_ITEM){
            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(inflated_row, parent, false);
            vh = new EventViewHolder(itemView);
        }
        else if(viewType == VIEW_HORIZENATAL_ITEM){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.horizental, parent, false);
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
        T dataItem = dataSet.get(position);
       ((EventViewHolder) holder).sl_name.setText(((Result)dataItem).getBusinessName());
            ((EventViewHolder) holder).sl_location_city.setText(((Result)dataItem).getLocation());
       ((EventViewHolder) holder).sl_location.setText(((Result)dataItem).getDistance()+"");
        ((EventViewHolder) holder).sl_type.setText(((Result)dataItem).getSlType());
       ((EventViewHolder) holder).sl_votes.setText(((Result)dataItem).getSlVote() + " votes");
//        if (((Result)dataItem).getOffers().size()>0){
//            ((EventViewHolder) holder).rl_offer.setVisibility(View.VISIBLE);
//        }else{
//            ((EventViewHolder) holder).rl_offer.setVisibility(View.GONE);
//        }
                final String image_Url =  ((Result)dataItem).getBimgPrimImg();
                if (image_Url!=null && !image_Url.equals("")){
                    Picasso
                            .with(mContext)
                            .load(image_Url)
                            .fit()
                           .centerCrop()// or .centerCrop() to avoid a stretched image
                            .into(((EventViewHolder) holder).img);

                }


            if (!((Result)dataItem).getSlRating().equals("") && ((Result)dataItem).getSlRating() != null) {
                double f = ((Result)dataItem).getSlRating();
                if (f < 3) {
                    ((EventViewHolder) holder).sl_rating.setBackgroundColor(Color.RED);
                } else if (f < 4 && f >= 3) {
                    ((EventViewHolder) holder).sl_rating.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                } else if (f >= 4) {
                    ((EventViewHolder) holder).sl_rating.setBackgroundColor(mContext.getResources().getColor(R.color.green_color));
                }
                try {
                    DecimalFormat df = new DecimalFormat("#.#");
                    ((EventViewHolder) holder).sl_rating.setText(df.format(f) + "");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        else if (holder instanceof HorizenatlViewHolder) {
            if (position>pcList.size())
                return;
          T dataItem = pcList.get(position);
            ResultArrayList[position] = ((PCollection) dataItem).getList();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
            ((HorizenatlViewHolder) holder).recyclerView.setLayoutManager(linearLayoutManager);
            final NearBySaloonListAdapter nearBySaloonListAdapter = new NearBySaloonListAdapter(R.layout.filter_row, ResultArrayList[position],mContext);
            ((HorizenatlViewHolder) holder).recyclerView.setAdapter(nearBySaloonListAdapter);
            ((HorizenatlViewHolder) holder).title.setText(((PCollection)dataItem).getTitle());
            try {
                WebService webService = ServiceFactory.createRetrofitService(WebService.class);
                webService.presentingCollectionById(((PCollection)dataItem).getId(),lat,lng)
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
                                    if (mapping.has("Salon")) {
                                    ObjectMapper mapper = new ObjectMapper();
                                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                                    ResultArrayList[position] = mapper.readValue(mapping.getString("Salon"), new TypeReference<List<Result>>() {
                                    });

                                    for (Result Result : ResultArrayList[position]) {
                                                nearBySaloonListAdapter.addItem(Result);
                                    }
                                    if (ResultArrayList[position].size()!=0){
                                            ((HorizenatlViewHolder) holder).no_collection_found.setVisibility(View.GONE);
                                    }else {
                                        ((HorizenatlViewHolder) holder).no_collection_found.setVisibility(View.VISIBLE);
                                        }

                                }
//
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
        protected TextView sl_location,sl_type,sl_name,sl_rating,sl_votes,sl_discount,sl_location_city;
        protected RelativeLayout rl_offer;
        public EventViewHolder(View v) {
            super(v);
            img = (ImageView)v.findViewById(R.id.imageView);
            sl_location = (TextView)v.findViewById(R.id.textView11);
            sl_type = (TextView)v.findViewById(R.id.textView8);
            sl_name = (TextView)v.findViewById(R.id.textView9) ;
            sl_rating = (TextView)v.findViewById(R.id.textView7) ;
            sl_votes = (TextView)v.findViewById(R.id.sl_votes) ;
            sl_discount = (TextView)v.findViewById(R.id.textView10) ;
            sl_location_city = (TextView)v.findViewById(R.id.location);
            rl_offer = (RelativeLayout) v.findViewById(R.id.rel_offer);
            v.setOnClickListener(this);
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
        public TextView title,no_collection_found;
        public HorizenatlViewHolder(View v) {
            super(v);
            title = (TextView)v.findViewById(R.id.title);
            no_collection_found = (TextView)v.findViewById(R.id.no_saloon_found);
            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
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
