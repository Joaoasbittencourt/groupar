package com.jotape.sisdic.CustomAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jotape.sisdic.Obj.Membro;
import com.jotape.sisdic.Obj.Tarefa;
import com.jotape.sisdic.R;

import java.util.List;

/**
 * Created by João Bittencourt on 12/04/2017.
 */

public class TarefaAdapter extends ArrayAdapter<Tarefa> {

    Context context;
    int layout;
    List<Tarefa> tarefas;

    public TarefaAdapter(@NonNull Context context, List<Tarefa> tarefas) {
        super(context, R.layout.tarefa_list,tarefas);

        this.context = context;
        this.layout = R.layout.tarefa_list;
        this.tarefas = tarefas;

    }

    @Override
    public View getView(int pos, View view, ViewGroup parent){

        if (view == null){

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(this.layout,null);
        }

        Tarefa t = this.tarefas.get(pos);

        TextView descriptionTxt = (TextView) view.findViewById(R.id.descriptionTxt);
        TextView personTxt = (TextView) view.findViewById(R.id.personTxt);
        TextView dateTxt = (TextView) view.findViewById(R.id.dateTxt);
        TextView statusTxt = (TextView) view.findViewById(R.id.statusTxt);

        descriptionTxt.setText(t.getDescription());
        personTxt.setText(t.getPerson());
        dateTxt.setText(t.getDate());
        statusTxt.setText(t.getStatus());

        return view;
    }
}
