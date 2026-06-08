package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.models.Recado;
import br.ufal.ic.jackut.exceptions.FalhaAoSalvarException;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecadoRepository {
    public static void save(List<Recado> recadosList) throws FalhaAoSalvarException {
        try {
            String path = "data/recado_data.xml";
            XMLEncoder encoder = new XMLEncoder(
                    new BufferedOutputStream(new FileOutputStream(path)));
            encoder.writeObject(recadosList);
            encoder.close();
        } catch (Exception e) {
            throw new FalhaAoSalvarException("Falha ao salvar os recados");
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Recado> load() {
        try {
            String path = "data/recado_data.xml";
            XMLDecoder decoder = new XMLDecoder(
                    new BufferedInputStream(new FileInputStream(path)));
            List<Recado> recadosList = (List<Recado>) decoder.readObject();
            decoder.close();
            return recadosList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void clear() {
        try {
            save(new ArrayList<>());
        } catch (FalhaAoSalvarException e) {
            throw new FalhaAoSalvarException("Falha ao limpar os recados");
        }
    }
}
