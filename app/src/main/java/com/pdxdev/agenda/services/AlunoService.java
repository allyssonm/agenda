package com.pdxdev.agenda.services;

import com.pdxdev.agenda.dto.AlunoSync;
import com.pdxdev.agenda.modelo.Aluno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by allysson.matheus on 27/02/2017.
 */

public interface AlunoService {

    @POST("aluno")
    Call<Void> insere(@Body Aluno aluno);

    @GET("aluno")
    Call<AlunoSync> lista();
}
