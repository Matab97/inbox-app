package mr.medabbad.inboxapp.repository;

import java.util.UUID;

import mr.medabbad.inboxapp.model.Email;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface EmailRepository extends CassandraRepository<Email, UUID>  {

}
