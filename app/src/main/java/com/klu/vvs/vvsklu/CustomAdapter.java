package com.klu.vvs.vvsklu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bhavna on 11/15/2018.
 */

public class CustomAdapter extends ArrayAdapter<AuditList> {

    private List<AuditList> lst;
    Context context;

    public CustomAdapter(List<AuditList> list,Context context)
    {
        super(context,R.layout.audit_list,list);
        this.context = context;
        this.lst = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        AuditList currentlist = lst.get(position);
        final View result;

        if(convertView==null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.audit_list, parent, false);



            TextView percentage = convertView.findViewById(R.id.percentage);
            TextView scholarship= convertView.findViewById(R.id.scholarship);
            TextView standard = convertView.findViewById(R.id.standard);
            TextView AuditDate = convertView.findViewById(R.id.AuditDate);
            TextView AuditNo = convertView.findViewById(R.id.AuditNo);


            percentage.setText(currentlist.getPercent());
            scholarship.setText(currentlist.getScholar());
            standard.setText(currentlist.getStand());
            AuditDate.setText(currentlist.getDate());
            AuditNo.setText(currentlist.getAuditno());

            result = convertView;


        }

        return convertView;

    }
}
