package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.URL;

public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        String caminhoArquivo = getCaminhoArquivo(nomeArquivo);

        URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(),
                caminhoArquivo);

        return FotoRecuperada.builder()
                .url(url.toString()).build();
    }

    @Override
    public void remover(String nomeAquivo) {
        try {
            String caminhoAquivo = getCaminhoArquivo(nomeAquivo);

            var deleteObjectRequest = new DeleteObjectRequest(
                    storageProperties.getS3().getBucket(),
                    caminhoAquivo);
            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo na Amazon S3.", e);
        }
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            String caminhoAquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(novaFoto.getContenType());

            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    caminhoAquivo,
                    novaFoto.getInputStream(),
                    objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception ex){
            throw new StorageException("Não foi possivel enviar arquivo para a Amazon S3", ex);
        }
    }

    private String getCaminhoArquivo(String nomeArquivo){
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }
}
