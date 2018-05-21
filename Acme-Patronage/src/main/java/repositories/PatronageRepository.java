
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Patronage;

@Repository
public interface PatronageRepository extends JpaRepository<Patronage, Integer> {

}
