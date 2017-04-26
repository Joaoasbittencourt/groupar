package com.jotape.sisdic.CustomDialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jotape.sisdic.Obj.Membro;
import com.jotape.sisdic.R;


/**
 * Created by João Bittencourt on 14/04/2017.
 */

public class MembroInfoDialog extends AlertDialog.Builder{

    TextView nameDialogTxt,phoneDialogTxt,emailDialogTxt,balanceDialogTxt,atvsDialogTxt;
    ImageButton callDialogBtn,addBalanceDialogBtn,closeDialogBtn;
    Context context;

    public MembroInfoDialog(Context context) {
        super(context);

        this.context = context;
    }

    public void createDialog(final Membro selectedMembro){

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.membro_info_dialog,null);

        nameDialogTxt = (TextView) view.findViewById(R.id.nameDialogTxt);
        phoneDialogTxt = (TextView) view.findViewById(R.id.phoneDialogTxt);
        emailDialogTxt = (TextView) view.findViewById(R.id.emailDialogTxt);
        balanceDialogTxt = (TextView) view.findViewById(R.id.balanceDialogTxt);
        atvsDialogTxt = (TextView) view.findViewById(R.id.atvsDialogTxt);

        nameDialogTxt.setText(selectedMembro.getName());
        phoneDialogTxt.setText("Número de contato: "+selectedMembro.getPhone());
        emailDialogTxt.setText("e-mail: "+selectedMembro.getEmail());
        balanceDialogTxt.setText("Balanço: 0");
        atvsDialogTxt.setText("Atividades Finalizadas: "+selectedMembro.getTarefasCompletadas());


        callDialogBtn = (ImageButton) view.findViewById(R.id.callDialogBtn);
        closeDialogBtn = (ImageButton) view.findViewById(R.id.closeDialogBtn);

        this.setView(view);

        final AlertDialog builtDialog = this.create();

        builtDialog.show();

        callDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String phone = selectedMembro.getPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",phone,null));
                context.startActivity(intent);

                builtDialog.dismiss();
            }
        });

        closeDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builtDialog.dismiss();
            }
        });




    }



}
