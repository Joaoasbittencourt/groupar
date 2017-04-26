package com.jotape.sisdic.CustomAdapters;

import android.content.Context;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jotape.sisdic.Obj.Membro;
import com.jotape.sisdic.R;

import java.util.List;

/**
 * Created by João Bittencourt on 12/04/2017.
 */

public class MembroAdapter extends ArrayAdapter<Membro>{

    Context context;
    int layout;
    List<Membro> membros;

    public MembroAdapter(@NonNull Context context, List<Membro> membros) {
        super(context, R.layout.member_list,membros);

        this.context = context;
        this.layout = R.layout.member_list;
        this.membros = membros;

    }

    @Override
    public View getView(int pos, View view, ViewGroup parent){

        if (view == null){

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(this.layout,null);
        }

        Membro currentMembro = this.membros.get(pos);

        TextView nameTxt = (TextView) view.findViewById(R.id.nameDialogTxt);
        TextView emailTxt = (TextView) view.findViewById(R.id.emailDialogTxt);
        TextView currentTaskTxt = (TextView) view.findViewById(R.id.currentTaskTxt);

        nameTxt.setText(currentMembro.getName());
        emailTxt.setText(currentMembro.getEmail());
        currentTaskTxt.setText("Atividades finalizadas: " + currentMembro.getTarefasCompletadas());

        return view;
    }
}
