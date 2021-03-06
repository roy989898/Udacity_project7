package pom.poly.com.tabatatimer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pom.poly.com.tabatatimer.Firebase.Message;
import pom.poly.com.tabatatimer.R;
import pom.poly.com.tabatatimer.Utility.Utility;


public class MessageRecycleAdapter extends RecyclerView.Adapter<MessageRecycleAdapter.ViewHolder> {

    private ArrayList<Message> messageArra;

    public MessageRecycleAdapter(ArrayList<Message> messageArra) {
        this.messageArra = messageArra;
    }

    public ArrayList<Message> getMessageArra() {
        return messageArra;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_recycle_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = messageArra.get(position);
        holder.tvName.setText(String.format("From: %s",message.fromuserName));
        holder.tvMessage.setText(message.message);
        holder.tvDate.setText(Utility.formatDate(message.dateTime));
    }


    @Override
    public int getItemCount() {
        if (messageArra != null)
            return messageArra.size();
        else
            return 0;
    }

    public void swapArray(ArrayList<Message> messageArray) {
        this.messageArra = messageArray;
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if (messageArra != null)
            messageArra.remove(position);

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvMessage)
        TextView tvMessage;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
