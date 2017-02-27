package com.pdxdev.agenda.converter;

import com.pdxdev.agenda.modelo.Aluno;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

/**
 * Created by allys on 09/10/2016.
 */
public class AlunoConverter {

    public String converteParaJSON(List<Aluno> alunos) {
        JSONStringer js = new JSONStringer();

        try {
            js.object().key("list").array().object().key("aluno").array();
            for (Aluno aluno : alunos) {
                js.object();
                js.key("nome").value(aluno.getNome());
                js.key("nota").value(aluno.getNota());
                js.endObject();
            }
            js.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return js.toString();
    }

    public String converteParaJSONCompleto(Aluno aluno) {
        JSONStringer js = new JSONStringer();
        try {
            js.object()
                    .key("id").value(aluno.getId())
                    .key("nome").value(aluno.getNome())
                    .key("endereco").value(aluno.getEndereco())
                    .key("telefone").value(aluno.getTelefone())
                    .key("site").value(aluno.getSite())
                    .key("nota").value(aluno.getNota())
                    .key("caminhoFoto").value(aluno.getCaminhoFoto())
                    .endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js.toString();
    }
}
