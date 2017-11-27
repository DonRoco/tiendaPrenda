package service;

import entity.Prenda;
import exception.PrendaNoEncontradaException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class PrendaService {

    static final long serialVersionUID = 11L;
    
    @PersistenceContext
    EntityManager em;

    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public PrendaService() {
    }

    public Prenda crearPrenda(Prenda prenda) {
        em.persist(prenda);
        return prenda;
    }

    public List<Prenda> getPrendas() {
        TypedQuery<Prenda> query = em.createQuery("SELECT p FROM Prenda p", Prenda.class);
        return query.getResultList();
    }

    /**
     *
     * @param id
     * @return retorna el prenda o nulo en caso de no ser encontrado
     */
    public Prenda getPrendaById(Long id) {
        return em.find(Prenda.class, id);
    }

    public List<Prenda> buscarPrenda(String nombrePrenda, Long categoriaId) {
        if (nombrePrenda != null && !nombrePrenda.isEmpty() && categoriaId != null && categoriaId > 0) {
            String jpql = "SELECT p FROM Prenda p WHERE LOWER(p.nombre) LIKE :nombre AND p.categoria.id = :categoriaId";
            TypedQuery<Prenda> query = em.createQuery(jpql, Prenda.class);
            query.setParameter("nombre", "%" + nombrePrenda + "%");
            query.setParameter("categoriaId", categoriaId);
            return query.getResultList();
        }

        if (nombrePrenda != null && !nombrePrenda.isEmpty()) {
            String jpql = "SELECT p FROM Prenda p WHERE LOWER(p.nombre) LIKE :nombre";
            TypedQuery<Prenda> query = em.createQuery(jpql, Prenda.class);
            query.setParameter("nombre", "%" + nombrePrenda + "%");
            return query.getResultList();
        }

        if (nombrePrenda == null || nombrePrenda.isEmpty()) {
            if (categoriaId != null && categoriaId > 0) {
                String jpql = "SELECT p FROM Prenda p WHERE p.categoria.id = :categoriaId";
                TypedQuery<Prenda> query = em.createQuery(jpql, Prenda.class);                
                query.setParameter("categoriaId", categoriaId);
                return query.getResultList();
            }

        }

        // sino devuelve la lista completa de prendas
        return getPrendas();
    }

    public void eliminarPrenda(Long prendaId) throws PrendaNoEncontradaException {
        Prenda p = getPrendaById(prendaId);
        if (p == null) {
            String mensajeException = String.format("Prenda con ID %s no encontrado para ser eliminado", prendaId);
            logger.log(Level.SEVERE, mensajeException);
            throw new PrendaNoEncontradaException(mensajeException);
        }
        em.remove(p);
    }

}
