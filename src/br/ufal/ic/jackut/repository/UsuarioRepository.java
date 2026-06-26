package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.exceptions.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.exceptions.FalhaAoSalvarException;
import br.ufal.ic.jackut.models.Usuario;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    public static void save(List<Usuario> usuariosList) throws FalhaAoSalvarException {
        try {
            String path = "data/usuario_data.xml";
            XMLEncoder encoder = new XMLEncoder(
                    new BufferedOutputStream(new FileOutputStream(path)));
            encoder.writeObject(usuariosList);
            encoder.close();
        } catch (RuntimeException | FileNotFoundException e) {
            throw new FalhaAoSalvarException("Falha ao salvar os usu\ufffdrios");
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Usuario> load() {
        try {
            String path = "data/usuario_data.xml";
            XMLDecoder decoder = new XMLDecoder(
                    new BufferedInputStream(new FileInputStream(path)));
            List<Usuario> usuariosList = (List<Usuario>) decoder.readObject();
            decoder.close();
            return usuariosList;
        } catch (RuntimeException | FileNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static void clear() {
        save(new ArrayList<>());
    }

    public static Usuario findByLogin(String login) throws UsuarioNaoCadastradoException {
        List<Usuario> usuariosList = load();

        for (Usuario usuario : usuariosList) {
            if (login.equals(usuario.getLogin()))
                return usuario;
        }

        throw new UsuarioNaoCadastradoException();
    }

    public static boolean existsByLogin(String login) {
        List<Usuario> usuariosList = load();

        for (Usuario usuario : usuariosList) {
            if (login.equals(usuario.getLogin()))
                return true;
        }
        return false;
    }
}

