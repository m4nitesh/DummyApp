package desk.cricintro.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import desk.cricintro.R;
import desk.cricintro.models.Restaurant;


public class RestroAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Restaurant> mData;

    public RestroAdapter(Context context , ArrayList<Restaurant> data){
        mContext = context;
        mData = data;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;

        if (viewType == 0){
            v = LayoutInflater.from(mContext).inflate(R.layout.item_restro,parent,false);
            return new ViewHolderRestro(v);
        }else {
            v = LayoutInflater.from(mContext).inflate(R.layout.item_cuisine_header,parent,false);
            return new ViewHolderHeader(v);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == 0){
            ViewHolderRestro holderRestro = (ViewHolderRestro)holder;
            holderRestro.name.setText(mData.get(position).getRestaurant().getName());
            holderRestro.cost.setText("Rs. " + mData.get(position).getRestaurant().getAverageCostForTwo());
        }else {
            ViewHolderHeader holderHeader = (ViewHolderHeader)holder;
            holderHeader.header.setText(mData.get(position).getHeader());
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private class ViewHolderRestro extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView cost;


        ViewHolderRestro(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txt_restro_name);
            cost = (TextView) itemView.findViewById(R.id.txt_cost_for_two);
        }
    }
    private class ViewHolderHeader extends RecyclerView.ViewHolder{

        private TextView header;
        ViewHolderHeader(View itemView) {
            super(itemView);
            header = (TextView)itemView.findViewById(R.id.txt_header);
        }
    }

}
