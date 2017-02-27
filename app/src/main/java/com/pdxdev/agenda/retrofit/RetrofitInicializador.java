package com.pdxdev.agenda.retrofit;

import com.pdxdev.agenda.services.AlunoService;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by allysson.matheus on 27/02/2017.
 */

public class RetrofitInicializador {

    private final Retrofit retrofit;

    public RetrofitInicializador() {
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.25.16:8080/api/").addConverterFactory(JacksonConverterFactory.create()).build();
    }

    public AlunoService getAlunoService() {
        return retrofit.create(AlunoService.class);
    }
}
