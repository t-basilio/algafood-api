package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    FotoRecuperada recuperar(String nomeArquivo);

    void remover(String nomeAquivo);

    void armazenar(NovaFoto novaFoto);

    default String gerarNomeArquivo(String nomeOriginal){
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }

    default void substituir(String nomeArquivoExistente, NovaFoto novaFoto){
        this.armazenar(novaFoto);

        if(nomeArquivoExistente != null){
            this.remover(nomeArquivoExistente);
        }
    }

    @Builder
    @Getter
    class NovaFoto{
        private String nomeArquivo;
        private String contenType;
        private InputStream inputStream;
    }

    @Builder
    @Getter
    class FotoRecuperada {
        private InputStream inputStream;
        private String url;

        public boolean temUrl(){
            return url != null;
        }

        public boolean temInputStream(){
            return inputStream != null;
        }
    }
}
