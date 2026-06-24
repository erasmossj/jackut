package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.models.Session;
import br.ufal.ic.jackut.exceptions.FalhaAoSalvarException;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SessionRepository {
    public static void save(List<Session> sessoesList) throws FalhaAoSalvarException {
        try {
            String path = "data/session_data.xml";
            XMLEncoder encoder = new XMLEncoder(
                    new BufferedOutputStream(new FileOutputStream(path)));
            encoder.writeObject(sessoesList);
            encoder.close();
        } catch (FileNotFoundException e) {
            throw new FalhaAoSalvarException("Falha ao salvar as sess\u00f5es");
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Session> load() {
        try {
            String path = "data/session_data.xml";
            XMLDecoder decoder = new XMLDecoder(
                    new BufferedInputStream(new FileInputStream(path)));
            List<Session> sessoesList = (List<Session>) decoder.readObject();
            decoder.close();
            return sessoesList;
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static void clear() {
        try {
            save(new ArrayList<>());
        } catch (FalhaAoSalvarException e) {
            throw new FalhaAoSalvarException("Falha ao limpar as sessões");
        }
    }
}
