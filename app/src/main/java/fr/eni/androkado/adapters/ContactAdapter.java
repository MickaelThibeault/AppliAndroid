package fr.eni.androkado.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.eni.androkado.R;
import fr.eni.androkado.bo.Friend;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    ArrayList<Friend> contacts = null;
    private View.OnClickListener monClickListener;

    public ContactAdapter(ArrayList<Friend> contacts, View.OnClickListener monClickListener) {
        this.contacts = contacts;
        this.monClickListener = monClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextViewName;
        public TextView mTextViewNumber;

        public ViewHolder(View v, View.OnClickListener monClickListener)
        {
            super(v);
            mTextViewName = v.findViewById(R.id.tv_contact_name);
            mTextViewNumber = v.findViewById(R.id.tv_contact_number);
            v.setOnClickListener(monClickListener);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_contacts, parent, false);

        ViewHolder vh = new ViewHolder(v,monClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        Friend current = contacts.get(position);
        holder.mTextViewName.setText(current.getNom());
        holder.mTextViewNumber.setText(current.getNumber());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
