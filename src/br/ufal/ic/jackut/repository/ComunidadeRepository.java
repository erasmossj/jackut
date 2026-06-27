package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.models.Comunidade;
import br.ufal.ic.jackut.exceptions.FalhaAoSalvarException;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ComunidadeRepository {
    public static void save(List<Comunidade> comunidades) throws FalhaAoSalvarException {
        try {
            String path = "data/comunidade_data.xml";
            XMLEncoder encoder = new XMLEncoder(
                    new BufferedOutputStream(new FileOutputStream(path)));
            encoder.writeObject(comunidades);
            encoder.close();
        } catch (FileNotFoundException e) {
            throw new FalhaAoSalvarException("Falha ao salvar as comunidades");
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Comunidade> load() {
        try {
            String path = "data/comunidade_data.xml";
            XMLDecoder decoder = new XMLDecoder(
                    new BufferedInputStream(new FileInputStream(path)));
            List<Comunidade> comunidades = (List<Comunidade>) decoder.readObject();
            decoder.close();
            return comunidades;
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static void clear() {
        try {
            save(new ArrayList<>());
        } catch (FalhaAoSalvarException e) {
            throw new FalhaAoSalvarException("Falha ao limpar as comunidades");
        }
    }
}
