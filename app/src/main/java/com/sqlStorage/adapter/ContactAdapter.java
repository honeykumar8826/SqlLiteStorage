package com.sqlStorage.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sqlStorage.R;
import com.sqlStorage.modal.ContactListModal;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private final List<ContactListModal> contactListModals;

    public ContactAdapter(List<ContactListModal> contactListModals) {
        this.contactListModals = contactListModals;
    }

    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_card_layout, viewGroup, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder contactViewHolder, int i) {
        ContactListModal contactListModal = contactListModals.get(i);
        contactViewHolder.textViewName.setText(contactListModal.getName());
        contactViewHolder.textViewContact.setText(contactListModal.getContact());
    }

    @Override
    public int getItemCount() {
        return contactListModals.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewContact;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.contact_name);
            textViewContact = itemView.findViewById(R.id.contact_number);
        }
    }
}
