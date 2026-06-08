package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.models.Amizade;
import br.ufal.ic.jackut.exceptions.FalhaAoSalvarException;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AmizadeRepository {
    public static void save(List<Amizade> amizadesList) throws FalhaAoSalvarException {
        try {
            String path = "data/amizade_data.xml";
            XMLEncoder encoder = new XMLEncoder(
                    new BufferedOutputStream(new FileOutputStream(path)));
            encoder.writeObject(amizadesList);
            encoder.close();
        } catch (Exception e) {
            throw new FalhaAoSalvarException("Falha ao salvar as amizades");
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Amizade> load() {
        try {
            String path = "data/amizade_data.xml";
            XMLDecoder decoder = new XMLDecoder(
                    new BufferedInputStream(new FileInputStream(path)));
            List<Amizade> amizadesList = (List<Amizade>) decoder.readObject();
            decoder.close();
            return amizadesList;
        } catch (Exception e) {
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
