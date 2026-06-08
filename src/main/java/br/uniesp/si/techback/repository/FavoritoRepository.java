package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritoRepository
        extends JpaRepository<Favorito, Long> {

    List<Favorito> findByUsuarioIdOrderByCriadoEmDesc(
            Long usuarioId);
}