package com.pdxdev.agenda.tasks;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.pdxdev.agenda.web.WebClient;
import com.pdxdev.agenda.converter.AlunoConverter;
import com.pdxdev.agenda.dao.AlunoDAO;
import com.pdxdev.agenda.modelo.Aluno;

import java.util.List;

/**
 * Created by allys on 10/10/2016.
 */

public class EnviaAlunosTask extends AsyncTask<Object, Object, String> {
    private Context context;
    private Dialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde...", "Enviado alunos...", true, true);
    }

    @Override
    protected String doInBackground(Object... params) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converteParaJSON(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(json);

        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
    }
}
