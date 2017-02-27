package com.pdxdev.agenda.tasks;

import android.os.AsyncTask;

import com.pdxdev.agenda.WebClient;
import com.pdxdev.agenda.converter.AlunoConverter;
import com.pdxdev.agenda.modelo.Aluno;

/**
 * Created by allysson.matheus on 26/02/2017.
 */
public class InsereAlunoTask extends AsyncTask {
    private final Aluno aluno;

    public InsereAlunoTask(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    protected Object doInBackground(Object... params) {
        String json =  new AlunoConverter().converteParaJSONCompleto(aluno);
        new WebClient().insere(json);
        return null;
    }
}
