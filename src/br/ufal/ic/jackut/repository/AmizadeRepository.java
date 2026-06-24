package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.models.Relacionamento;
import br.ufal.ic.jackut.exceptions.FalhaAoSalvarException;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AmizadeRepository {
    public static void save(List<Relacionamento> relacionamentosList) throws FalhaAoSalvarException {
        try {
            String path = "data/amizade_data.xml";
            XMLEncoder encoder = new XMLEncoder(
                    new BufferedOutputStream(new FileOutputStream(path)));
            encoder.writeObject(relacionamentosList);
            encoder.close();
        } catch (FileNotFoundException e) {
            throw new FalhaAoSalvarException("Falha ao salvar as amizades");
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Relacionamento> load() {
        try {
            String path = "data/amizade_data.xml";
            XMLDecoder decoder = new XMLDecoder(
                    new BufferedInputStream(new FileInputStream(path)));
            List<Relacionamento> relacionamentosList = (List<Relacionamento>) decoder.readObject();
            decoder.close();
            return relacionamentosList;
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static void clear() {
        try {
            save(new ArrayList<>());
        } catch (FalhaAoSalvarException e) {
            throw new FalhaAoSalvarException("Falha ao limpar as amizades");
        }
    }
}
