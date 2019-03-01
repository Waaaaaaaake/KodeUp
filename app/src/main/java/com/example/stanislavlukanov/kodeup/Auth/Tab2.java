package com.example.stanislavlukanov.kodeup.Auth;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stanislavlukanov.kodeup.Auth.SQL.SQL_Divider_Item_Decoration;
import com.example.stanislavlukanov.kodeup.Auth.SQL.SQL_RecyclerTouchListener;
import com.example.stanislavlukanov.kodeup.Auth.SQL.SQL_Tab2;
import com.example.stanislavlukanov.kodeup.Auth.SQL.SQL_Tab2_Adapter;
import com.example.stanislavlukanov.kodeup.Auth.SQL.SQL_Tab2_DatabaseHelper;
import com.example.stanislavlukanov.kodeup.R;

import java.util.ArrayList;
import java.util.List;

public class Tab2 extends Fragment {

    private OnFragmentInteractionListener mListener;
    SQL_Tab2_Adapter mAdapter;
    List<SQL_Tab2> CatalogList = new ArrayList<>();
    CoordinatorLayout coordinatorLayout;
    RecyclerView recyclerView;
    TextView noContactsView;

    SQL_Tab2_DatabaseHelper db;


    public static Tab2 newInstance(){
        Bundle args = new Bundle();
        Tab2 fragment = new Tab2();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fr_tab2, container, false);
        coordinatorLayout = v.findViewById(R.id.coordinator_tab2);
        recyclerView = v.findViewById(R.id.tab2_recycler);
        noContactsView = v.findViewById(R.id.empty_tab2_view);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity.getSupportActionBar() != null;

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab_tab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCatalogDialog(false, null, -1);
            }
        });

        return v;
    }

    public void onButtonPressed(Uri uri){
        if (mListener != null){
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db = new SQL_Tab2_DatabaseHelper(getContext());
        CatalogList.addAll(db.getAllContacts());
        mAdapter = new SQL_Tab2_Adapter(getContext(), CatalogList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SQL_Divider_Item_Decoration(getContext(),LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyCatalogs();

        recyclerView.addOnItemTouchListener(new SQL_RecyclerTouchListener(getContext(),
                recyclerView, new SQL_RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                com.example.stanislavlukanov.kodeup.Auth.BottomSheetDialog bottomSheet = new com.example.stanislavlukanov.kodeup.Auth.BottomSheetDialog();
                SQL_Tab2 n = CatalogList.get(position);

                bottomSheet.setResources(n.getContact().toString(), n.getMail().toString(), n.getPhone().toString());
                bottomSheet.show(getFragmentManager(), "my_tag");

            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }

    private void createContact(String contact, String description, String mail, String phone) {
        long id = db.insertCatalog(contact, description, mail, phone);
        SQL_Tab2 n = db.getCatalog(id);

        if (n != null) {
            CatalogList.add(0, n);
            mAdapter.notifyDataSetChanged();
            toggleEmptyCatalogs();
        }
    }


    private void updateContact(String contact, String description, String mail, String phone, int position) {
        SQL_Tab2 n = CatalogList.get(position);
        n.setContact(contact);
        n.setDescription(description);
        n.setMail(mail);
        n.setPhone(phone);

        db.updateContact(n);
        CatalogList.set(position, n);
        mAdapter.notifyItemChanged(position);

        toggleEmptyCatalogs();
    }


    private void deleteContact(int position) {
        db.deleteContact(CatalogList.get(position));

        CatalogList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyCatalogs();
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Редактировать запись", "Удалить"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Выберите действие");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0) {
                    showCatalogDialog(true, CatalogList.get(position), position);
                } else {
                    deleteContact(position);
                }
            }
        });
        builder.show();
    }

    private void showCatalogDialog(final boolean shouldUpdate, final SQL_Tab2 contact, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
        View view = layoutInflaterAndroid.inflate(R.layout.sql_tab2_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
        alertDialogBuilderUserInput.setView(view);

        final EditText inputContact = view.findViewById(R.id.layout_tab2_contact);
        final EditText inputDescription = view.findViewById(R.id.layout_tab2_description);
        final EditText inputMail = view.findViewById(R.id.layout_tab2_mail);
        final EditText inputPhone = view.findViewById(R.id.layout_tab2_phone);

        TextView dialogTitle = view.findViewById(R.id.tab2_dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.msg_new_contact) : getString(R.string.lbl_edit_contact_title));

        if (shouldUpdate && contact != null) {
            inputContact.setText(contact.getContact());
            inputDescription.setText(contact.getDescription());
            inputMail.setText(contact.getMail());
            inputPhone.setText(contact.getPhone());
        }

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "Изменить" : "Сохранить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputContact.getText().toString()) || TextUtils.isEmpty(inputPhone.getText().toString())
                        || TextUtils.isEmpty(inputMail.getText().toString())) {
                    Toast.makeText(getActivity(), "Не все данные введены!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                if (shouldUpdate && contact != null) {
                    updateContact(inputContact.getText().toString(), inputDescription.getText().toString(),
                            inputMail.getText().toString(), inputPhone.getText().toString(), position);
                } else {
                    createContact(inputContact.getText().toString(), inputDescription.getText().toString(),
                            inputMail.getText().toString(), inputPhone.getText().toString());
                }
            }
        });
    }

    private void toggleEmptyCatalogs () {
        if (db.getContactsCount() > 0) {
            noContactsView.setVisibility(View.GONE);
        } else {
            noContactsView.setVisibility(View.VISIBLE);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
